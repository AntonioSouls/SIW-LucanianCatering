package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.spring.model.Buffet;

public interface BuffetRepository extends CrudRepository<Buffet, Long>{
	
	public List<Buffet> findByChefProprietario_id (Long chef_proprietario_id);
}
