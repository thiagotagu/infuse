package com.avaliacaoinfuse.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class PedidoItem {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
	    @ManyToOne
	    private Produto produto;
	    Long quantidade;
	    
	    @Column(name = "valor_pago")
	    BigDecimal valorPago;
}
