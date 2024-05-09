package com.avaliacaoinfuse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
@Entity
public class Cliente {

	@Id
	@GeneratedValue
	Long id;
	@Size(min = 1, max = 100)
	String nome;
	@CPF(message = "CPF inválido")
	@Column(unique = true)
	String cpf;

}
