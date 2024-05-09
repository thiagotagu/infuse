package com.avaliacaoinfuse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.avaliacaoinfuse.service.ClienteService;
import com.avaliacaoinfuse.service.ProdutoService;

@SpringBootApplication
public class AvaliacaoinfuseApplication {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ProdutoService produtoService;

	public static void main(String[] args) {
		SpringApplication.run(AvaliacaoinfuseApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		clienteService.gerarCarga10Clientes();
		produtoService.gerar10Produtos();
	}

}
