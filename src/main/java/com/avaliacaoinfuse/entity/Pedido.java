package com.avaliacaoinfuse.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Pedido {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "numero_pedido")
	    private Long numeroPedido;

	    @Column(name = "data_cadastro")
	    private Date dataCadastro;
	    
	    @ManyToOne
	    private Cliente cliente;
	    
	    @ManyToMany(cascade = CascadeType.ALL)
	    private List<PedidoItem> itens;

}
	