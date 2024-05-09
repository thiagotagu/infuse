package com.avaliacaoinfuse.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avaliacaoinfuse.dto.ClienteDTO;
import com.avaliacaoinfuse.entity.Cliente;
import com.avaliacaoinfuse.service.ClienteService;

@Controller
@RequestMapping("cliente")
public class ClienteController {
	
	
	@Autowired
	ClienteService service;
	
	@GetMapping
    public ResponseEntity<Page<Cliente>> lstAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)  {
	  return ResponseEntity.ok().body( service.findAllPg(page,size));
    }
	
	
	@GetMapping("cpf/{cpf}")
    public ResponseEntity<Cliente> obterPorCPF(@PathVariable String cpf)  {
		 Optional<Cliente> cliente = service.findByCpf(cpf);
	      
		 if (cliente.isPresent()) {
	            return ResponseEntity.ok().body(cliente.get());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
    }

		@PostMapping
	    public ResponseEntity<Cliente> add(@RequestBody @Valid ClienteDTO cliente)  {
			
			Cliente retorno = service.gravar(cliente);
		  return ResponseEntity.ok().body(retorno);
	    }

}
