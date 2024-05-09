package com.avaliacaoinfuse.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public abstract class AbstractService <T, PK extends Serializable> implements Serializable {
 
	private static final long serialVersionUID = 1L;

	public abstract CrudRepository<T, PK> getRepository();
	
	public abstract PagingAndSortingRepository<T, PK> getPagingAndSortingRepository();
	
	public T save(T entity) throws ServiceException {
		try {
			return getRepository().save(entity);
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new ServiceException("Erro na gravação", ex);
		}
	}
	
	public T  findById(PK id) throws ServiceException {
		try {
			return getRepository().findById(id).get();
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new ServiceException("Erro na consulta", ex);
		}
	}
	
	public List<T> findAll() throws ServiceException {
		try {
			return (List<T>) getRepository().findAll();

		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new ServiceException("Erro na consulta", ex);
		}
	}
	
	public Page<T>  findAllPg(int page, int size) {
		try {
			PageRequest pageRequest = PageRequest.of(page, size);
			return  getPagingAndSortingRepository().findAll(pageRequest);

		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new ServiceException("Erro na consulta", ex);
		}
		 
	}
	
	public void saveAll(List<T> lst) throws ServiceException {
		try {
			getRepository().saveAll(lst);

		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new ServiceException("Erro gravacao em Lista", ex);
		}
	}
	
	

}
