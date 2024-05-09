package com.avaliacaoinfuse.dto;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class ClienteDTO {
	
	@Size(min = 1, max = 100)
	String nome;
	
	@CPF(message = "CPF inválido")
	String cpf;
}
