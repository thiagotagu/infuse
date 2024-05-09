package com.avaliacaoinfuse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.avaliacaoinfuse.dto.PedidoDTO;
import com.avaliacaoinfuse.dto.PedidoItemDTO;
import com.avaliacaoinfuse.entity.Cliente;
import com.avaliacaoinfuse.entity.Pedido;
import com.avaliacaoinfuse.entity.Produto;
import com.avaliacaoinfuse.repository.PedidoRepository;
import com.avaliacaoinfuse.response.RetornoSolicitacaoResponse;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
	
	 @Mock
	    private PedidoRepository pedidoRepository;

	    @Mock
	    private ClienteService clienteService;

	    @Mock
	    private ProdutoService produtoService;

	    @InjectMocks
	    private PedidoService pedidoService;


	    @Test
	    public void testSolicitarPedidoSuccess() {
	        Cliente cliente = new Cliente();
	        cliente.setCpf("12345678900");
	        when(clienteService.findByCpf(anyString())).thenReturn(Optional.of(cliente));

	        Produto produto = new Produto();
	        produto.setId(1L);
	        produto.setValor(BigDecimal.valueOf(100));
	        when(produtoService.findById(anyLong())).thenReturn(produto);

	        PedidoDTO pedidoDTO = new PedidoDTO();
	        pedidoDTO.setCpfCliente("12345678900");
	        pedidoDTO.setNumeroPedido(1L);
	        List<PedidoItemDTO> pedidoItemDTOList = new ArrayList<>();
	        PedidoItemDTO pedidoItemDTO = new PedidoItemDTO();
	        pedidoItemDTO.setId(1L);
	        pedidoItemDTO.setQuantidade(5L);
	        pedidoItemDTOList.add(pedidoItemDTO);
	        pedidoDTO.setPedidoItem(pedidoItemDTOList);

	        Pedido pedido = pedidoService.solicitarPedido(pedidoDTO);

	        verify(pedidoRepository, times(1)).save(any(Pedido.class));

	        assertNotNull(pedido);
	    }

	    @Test
	    public void testSolicitarPedidoClienteNotFound() {
	        when(clienteService.findByCpf(anyString())).thenReturn(Optional.empty());

	        PedidoDTO pedidoDTO = new PedidoDTO();
	        pedidoDTO.setCpfCliente("12345678900");
	        pedidoDTO.setNumeroPedido(1L);

	        ServiceException exception = assertThrows(ServiceException.class,
	                () -> pedidoService.solicitarPedido(pedidoDTO));

	        assertEquals("Cliente nao localizado.", exception.getMessage());
	    }
	    
	    
	    @Test
	    public void testSolicitarPedidoInvalidoPedido() {
	        when(clienteService.findByCpf(anyString())).thenReturn(Optional.of(new Cliente()));
	        List<PedidoDTO> pedidos = new ArrayList<>();
	        PedidoDTO pedidoDTO1 = new PedidoDTO();
	        pedidos.add(pedidoDTO1);

	        RetornoSolicitacaoResponse response = pedidoService.solicitarPedido(pedidos);

	        assertEquals(1, response.getListaErro().size());
	        assertEquals(0, response.getListaSucesso().size());
	    }


}
