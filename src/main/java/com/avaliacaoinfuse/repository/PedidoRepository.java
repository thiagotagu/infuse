package com.avaliacaoinfuse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avaliacaoinfuse.entity.Cliente;
import com.avaliacaoinfuse.entity.Pedido;

@Repository
public interface PedidoRepository  extends JpaRepository<Pedido, Long> {

	Optional<Pedido> findByNumeroPedido(Long numeroPedido);

	Optional<Pedido> findByCliente(Optional<Cliente> cliente);


}
