package com.avaliacaoinfuse.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class PedidoDTO {
	@Size(min = 1 , message = "numeroPedido informado é invalido")
	Long numeroPedido;
	Date dataCadastro = new Date();
	
	@CPF(message = "CPF inválido")
	String cpfCliente;
	
	List<PedidoItemDTO> pedidoItem;

}
