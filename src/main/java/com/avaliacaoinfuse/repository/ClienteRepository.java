package com.avaliacaoinfuse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avaliacaoinfuse.entity.Cliente;


	
	@Repository
	public interface ClienteRepository  extends JpaRepository<Cliente, Long> {

		Optional<Cliente> findByCpf(String cpfCliente);

}
