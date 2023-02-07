package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.repository.PiattoRepository;

@Service
public class PiattoService {
	
	@Autowired
	private PiattoRepository piattoRepository;
	
	public List<Piatto> findAll(){
		List<Piatto> listaPiatti = new ArrayList<>();
		for(Piatto p : piattoRepository.findAll()) {
			listaPiatti.add(p);
		}
		return listaPiatti;
	}
	
	@Transactional
	public void aggiungiPiatto(Piatto piatto) {
		piattoRepository.save(piatto);
	}
	
	public List<Piatto> piattiDelloStessoBuffet(Long idBuffet){
		List<Piatto> piatti_dello_stesso_buffet = new ArrayList<>();
		for(Piatto p : piattoRepository.findByBuffet_id(idBuffet)) {
			piatti_dello_stesso_buffet.add(p);
		}
		return piatti_dello_stesso_buffet;
	}
	
	public List<Piatto> piattiAltroBuffet(Long idBuffet){
		List<Piatto> tutti = this.findAll();
		for(Piatto p : piattoRepository.findByBuffet_id(idBuffet)) {
			tutti.remove(p);
		}
		return tutti;
	}
	
	@Transactional
	public void remove(Long id) {
		piattoRepository.deleteById(id);
	}
	
	public Piatto findById(Long id) {
		Piatto piatto = piattoRepository.findById(id).get();
		return piatto;
	}
	
	public boolean alreadyExist(Piatto piatto) {
		List<Piatto> listaPiatti = this.findAll();
		for(Piatto p : listaPiatti) {
			if(p.getNome().equals(piatto.getNome())) {
				return true;
			}
		}
		return false;
	}

}
