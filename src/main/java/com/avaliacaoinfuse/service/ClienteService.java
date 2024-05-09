package com.avaliacaoinfuse.service;

import java.util.Optional;
import java.util.Random;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.avaliacaoinfuse.dto.ClienteDTO;
import com.avaliacaoinfuse.entity.Cliente;
import com.avaliacaoinfuse.repository.ClienteRepository;

	
	

@Service
public class ClienteService  extends AbstractService<Cliente, Long> {
	private static final String[] nomes = {"Ana", "Pedro", "Maria", "João", "Juliana", "Lucas", "Carla", "Rafael", "Fernanda", "Rodrigo"};
 
	private static final long serialVersionUID = 1L;
	@Autowired
	ClienteRepository dao;

	@Override
	public CrudRepository<Cliente, Long> getRepository() {
		return dao;
	}

	public Optional<Cliente> findByCpf(String cpfCliente) {
		return dao.findByCpf(formatCPF(cpfCliente));
	}
	
	
	public Cliente gravar(ClienteDTO clienteDTO) {
		
		Optional<Cliente> clienteExistente =  findByCpf(clienteDTO.getCpf());
		 
		clienteExistente.ifPresent(pdExistente->{
			  throw new ServiceException("CPF já cadastrado , Codigo do Cliente " + pdExistente.getId());
		});
		Cliente cliente = new Cliente();
		BeanUtils.copyProperties(clienteDTO, cliente);
		
		return save(cliente);
	}
	
	
	public void gerarCarga10Clientes() {
		
		for (int i = 0; i < 10; i++) {
			ClienteDTO dto = new ClienteDTO();
			dto.setNome(nomes[i]);
			dto.setCpf(generateCPF());
			gravar(dto);
		}
	}
	
	
	 public  String formatCPF(String cpf) {
	        cpf = cpf.replaceAll("\\D", "");
	        if (cpf.length() != 11) {
	            throw new IllegalArgumentException("O CPF deve conter 11 dígitos");
	        }
	        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
	    }
	
	
	private  String generateCPF() {
		Random  random = new Random();
		int[] cpf = new int[11];

        for (int i = 0; i < 9; i++) {
            cpf[i] = random.nextInt(10);
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += cpf[i] * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        cpf[9] = (firstDigit >= 10) ? 0 : firstDigit;

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += cpf[i] * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        cpf[10] = (secondDigit >= 10) ? 0 : secondDigit;

        StringBuilder cpfFormatted = new StringBuilder();
        cpfFormatted.append(cpf[0]).append(cpf[1]).append(cpf[2]);
        cpfFormatted.append('.').append(cpf[3]).append(cpf[4]).append(cpf[5]);
        cpfFormatted.append('.').append(cpf[6]).append(cpf[7]).append(cpf[8]);
        cpfFormatted.append('-').append(cpf[9]).append(cpf[10]);

        return cpfFormatted.toString();
    }

	@Override
	public PagingAndSortingRepository<Cliente, Long> getPagingAndSortingRepository() {
		return dao;
	}
	

}
