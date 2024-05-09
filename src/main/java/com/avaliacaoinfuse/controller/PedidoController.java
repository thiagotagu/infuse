package com.avaliacaoinfuse.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avaliacaoinfuse.dto.PedidoDTO;
import com.avaliacaoinfuse.entity.Pedido;
import com.avaliacaoinfuse.response.RetornoSolicitacaoResponse;
import com.avaliacaoinfuse.service.PedidoService;

@Controller
@RequestMapping("pedido")
public class PedidoController {

	@Autowired
	PedidoService service;

	@GetMapping
	public ResponseEntity<Page<Pedido>> lstAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok().body(service.findAllPg(page, size));
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<RetornoSolicitacaoResponse> add(@RequestBody List<PedidoDTO> pedidos) {
		
		return ResponseEntity.ok().body(service.solicitarPedido(pedidos));
	}

	@GetMapping("cliente-cpf/{cpf}")
	public ResponseEntity<Pedido> obterPedidoPorCPF(@PathVariable String cpf) {
		Optional<Pedido> pedido = service.findByCpf(cpf);
		if (pedido.isPresent()) {
			return ResponseEntity.ok().body(pedido.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
