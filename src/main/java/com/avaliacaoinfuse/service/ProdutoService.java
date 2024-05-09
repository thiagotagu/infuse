package com.avaliacaoinfuse.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.avaliacaoinfuse.dto.ProdutoDTO;
import com.avaliacaoinfuse.entity.Produto;
import com.avaliacaoinfuse.repository.ProdutoRepository;

@Service
public class ProdutoService extends AbstractService<Produto, Long> {

	private static final long serialVersionUID = 1L;
	private static final String[] alimentos = { "Arroz", "Feijão", "Carne", "Frango", "Peixe", "Macarrão", "Pão",
			"Queijo", "Leite", "Ovos" };

	@Autowired
	ProdutoRepository dao;

	public List<Produto> gerar10Produtos() {
		Random random = new Random();
		List<Produto> produtos = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Produto produto = new Produto();
			produto.setNome(alimentos[random.nextInt(alimentos.length)]);
			produto.setValor(BigDecimal.valueOf(random.nextDouble() * 20 + 5)); // Valor aleatório entre 5 e 25
			produtos.add(produto);
		}

		saveAll(produtos);
		return produtos;
	}

	@Override
	public CrudRepository<Produto, Long> getRepository() {
		return dao;
	}

	@Override
	public PagingAndSortingRepository<Produto, Long> getPagingAndSortingRepository() {
		return dao;
	}

	public Produto gravar(@Valid ProdutoDTO dto) {
		Produto produto = new Produto();
		BeanUtils.copyProperties(dto, produto);
		return save(produto);
	}

}
