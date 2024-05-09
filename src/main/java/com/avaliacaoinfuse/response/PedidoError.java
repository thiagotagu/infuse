package com.avaliacaoinfuse.response;

import lombok.Data;

@Data
public class PedidoError {
	
	String cpf;
	Long numeroPedido;
	String mensgemErro;

}
