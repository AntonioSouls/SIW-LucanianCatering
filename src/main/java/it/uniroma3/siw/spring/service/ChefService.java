package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.repository.ChefRepository;

@Service
public class ChefService {
	
	@Autowired
	private ChefRepository chefRepository;

	public List<Chef> findAll(){
		List<Chef> listaChef = new ArrayList<>();
		for(Chef c : chefRepository.findAll()){
			listaChef.add(c);
		}
		return listaChef;
	}
	
	@Transactional
	public void aggiungiChef(Chef chef) {
		chefRepository.save(chef);
	}
	
	public Chef findById(Long id) {
		Chef chef = chefRepository.findById(id).get();
		return chef;
	}
	
	public List<Chef> findByDifferentId(Long id){
		List<Chef> listaChefConIdDiversoDalParametro = new ArrayList<>();
		for(Chef c : chefRepository.findAll()) {
			if(c.getId()!= id) {
				listaChefConIdDiversoDalParametro.add(c);
			}
		}
		return listaChefConIdDiversoDalParametro;
	}
	
	public boolean existById(Long id) {
		return chefRepository.existsById(id);
	}
	
	public boolean alreadyExist(Chef chef) {
		List <Chef> listaChef = new ArrayList<>();
		for(Chef c : chefRepository.findAll()) {
			listaChef.add(c);
		}
		for(Chef c : listaChef) {
			if(c.getNome().equals(chef.getNome()) & c.getCognome().equals(chef.getCognome()) & c.getNazionalita().equals(chef.getNazionalita())) {
				return true;
			}
		}
		return false;
	}
	
	@Transactional
	public void remove(Long id) {
		chefRepository.deleteById(id);
		return;
	}
}
