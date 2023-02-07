package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.repository.BuffetRepository;

@Service
public class BuffetService {

	@Autowired
	private BuffetRepository buffetRepository;
	
	@Autowired
	private PiattoService piattoService;
	
	public List<Buffet> findAll(){
		List<Buffet> listaBuffet = new ArrayList<>();
		for(Buffet b: buffetRepository.findAll()) {
			listaBuffet.add(b);
		}
		return listaBuffet;
	}
	
	@Transactional
	public void aggiungiBuffet(Buffet buffet) {
		buffetRepository.save(buffet);
	}
	
	@Transactional
	public void aggiornaBuffet(Buffet buffet) {
		buffetRepository.save(buffet);
	}
	
	public Buffet findById(Long id) {
		Buffet buffet = buffetRepository.findById(id).get();
		return buffet;
	}
	
	public boolean alreadyExist(String nome) {
		List<Buffet> listaBuffets= this.findAll();
		for(Buffet b : listaBuffets) {
			if(b.getNome().equals(nome)) {
				return true;
			}
		}
		return false;
	}
	
	public List<Buffet> findBuffetPerChef(Long chef_proprietario_id){
		List<Buffet> listaBuffetPerChef = new ArrayList<Buffet>();
		for(Buffet b: buffetRepository.findByChefProprietario_id(chef_proprietario_id)) {
			listaBuffetPerChef.add(b);
		}
		return listaBuffetPerChef;
	}
	
	@Transactional
	public void removeBuffet(Buffet buffet) {
		Chef c = buffet.getChefProprietario();
		c.getListaBuffet().remove(buffet);
		List<Piatto> listaPiattiDelBuffet = piattoService.piattiDelloStessoBuffet(buffet.getId());
		buffet.getListaPiatti().clear();
		for(Piatto p : listaPiattiDelBuffet) {
			p.setBuffet(null);
			piattoService.aggiungiPiatto(p);
		}
		buffetRepository.delete(buffet);
	}
}
