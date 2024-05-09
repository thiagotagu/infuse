package com.avaliacaoinfuse.response;

import java.util.List;

import com.avaliacaoinfuse.entity.Pedido;

import lombok.Data;


@Data
public class RetornoSolicitacaoResponse {
	
	List<Pedido> listaSucesso;
	List<PedidoError> listaErro;

}
