package com.avaliacaoinfuse.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.avaliacaoinfuse.dto.PedidoDTO;
import com.avaliacaoinfuse.entity.Cliente;
import com.avaliacaoinfuse.entity.Pedido;
import com.avaliacaoinfuse.entity.PedidoItem;
import com.avaliacaoinfuse.entity.Produto;
import com.avaliacaoinfuse.repository.PedidoRepository;
import com.avaliacaoinfuse.response.PedidoError;
import com.avaliacaoinfuse.response.RetornoSolicitacaoResponse;

@Service
public class PedidoService extends AbstractService<Pedido, Long> {

	private static final long serialVersionUID = 1L;
	@Autowired
	PedidoRepository dao;

	@Autowired
	ClienteService clienteService;

	@Autowired
	ProdutoService produtoService;

	public Pedido solicitarPedido(PedidoDTO pedidoDto) {

		Optional<Cliente> cliente = clienteService.findByCpf(pedidoDto.getCpfCliente());
		if (!cliente.isPresent()) {
			throw new ServiceException("Cliente nao localizado.");
		}

		Optional<Pedido> pedidoExistente = findByNumeroPedido(pedidoDto.getNumeroPedido());

		pedidoExistente.ifPresent(pdExistente -> {
			throw new ServiceException("Número de Pedido já cadastrado: " + pdExistente.getNumeroPedido());
		});

		if (pedidoDto.getPedidoItem() == null || pedidoDto.getPedidoItem().size() <= 0) {
			throw new ServiceException("Nenhum Produto foi solicitado ");
		}

		List<PedidoItem> lstPedidoItem = new ArrayList<>();
		pedidoDto.getPedidoItem().forEach(item -> {
			Produto produto = produtoService.findById(item.getId());
			if (produto == null) {
				throw new ServiceException("Produto inexistente. ");
			}

			PedidoItem pedidoItem = new PedidoItem();
			pedidoItem.setProduto(produto);
			pedidoItem.setQuantidade(item.getQuantidade());
			pedidoItem.setValorPago(aplicarDesconto(produto.getValor(), item.getQuantidade()));

			lstPedidoItem.add(pedidoItem);

		});
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente.get());
		pedido.setDataCadastro(pedidoDto.getDataCadastro());
		pedido.setNumeroPedido(pedidoDto.getNumeroPedido());
		pedido.setItens(lstPedidoItem);
		save(pedido);
		return pedido;

	}

	public RetornoSolicitacaoResponse solicitarPedido(List<PedidoDTO> pedidos) {

		RetornoSolicitacaoResponse pedidoRetorno = new RetornoSolicitacaoResponse();

		List<Pedido> lstSucesso = new ArrayList<>();
		List<PedidoError> lstError = new ArrayList<>();

		if (pedidos.size() > 11) {
			throw new ServiceException("Quantidade limite de pedidos é <= a 10");
		}

		pedidos.forEach(pedidoDto -> {
			try {
				lstSucesso.add(solicitarPedido(pedidoDto));
			} catch (Exception ex) {
				PedidoError pedidoError = new PedidoError();
				pedidoError.setCpf(pedidoDto.getCpfCliente());
				pedidoError.setNumeroPedido(pedidoDto.getNumeroPedido());
				pedidoError.setMensgemErro(ex.getMessage());
				lstError.add(pedidoError);
			}
		});
		pedidoRetorno.setListaSucesso(lstSucesso);
		pedidoRetorno.setListaErro(lstError);
		return pedidoRetorno;
	}

	private BigDecimal aplicarDesconto(BigDecimal valor, Long quantidade) {
		BigDecimal DESCONTO_5 = BigDecimal.valueOf(0.05);
		BigDecimal DESCONTO_10 = BigDecimal.valueOf(0.10);
		BigDecimal desconto;
		if (quantidade >= 10) {
			desconto = valor.multiply(DESCONTO_10);
		} else if (quantidade >= 5) {
			desconto = valor.multiply(DESCONTO_5);
		} else {
			desconto = BigDecimal.ZERO;
		}
		return valor.subtract(desconto);
	}

	@Override
	public CrudRepository<Pedido, Long> getRepository() {
		return dao;
	}

	public Optional<Pedido> findByNumeroPedido(Long numeroPedido) {
		return dao.findByNumeroPedido(numeroPedido);
	}

	@Override
	public PagingAndSortingRepository<Pedido, Long> getPagingAndSortingRepository() {
		return dao;
	}

	public Optional<Pedido> findByCpf(String cpf) {
		Optional<Cliente> cliente = clienteService.findByCpf(cpf);

		if (!cliente.isPresent()) {
			throw new ServiceException("Cliente nao localizado.");
		}

		return dao.findByCliente(cliente);
	}

}
