package com.avaliacaoinfuse.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avaliacaoinfuse.dto.ProdutoDTO;
import com.avaliacaoinfuse.entity.Cliente;
import com.avaliacaoinfuse.entity.Produto;
import com.avaliacaoinfuse.service.ProdutoService;


@Controller
@RequestMapping("produto")
public class ProdutoController {
	
	@Autowired
	ProdutoService service;
	
	@GetMapping
    public ResponseEntity<Page<Produto>> lstAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)  {
	  return ResponseEntity.ok().body( service.findAllPg(page,size));
    }
	 

		@PostMapping
	    public ResponseEntity<Produto> add(@RequestBody @Valid ProdutoDTO dto)  {
			
			Produto retorno = service.gravar(dto);
		  return ResponseEntity.ok().body(retorno);
	    }

}
