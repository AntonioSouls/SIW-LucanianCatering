package it.uniroma3.siw.spring.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.spring.controller.validator.PiattoValidator;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.PiattoService;

@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private PiattoValidator piattoValidator;
	
	
	@GetMapping("/visualizzaListaPiatti")
	public String listaPiatti(Model model) {
		model.addAttribute("listaPiatti", piattoService.findAll());
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "/admin/listaPiattiAdmin.html";
        }
		return "listaPiatti.html";
	}
	
	@GetMapping("/visualizzaListaPiatti/{id}")
	public String listaPiattiDiUnBuffet(@PathVariable("id") Long idBuffet, Model model) {
		Buffet buffet = buffetService.findById(idBuffet);
		buffet.setListaPiatti(piattoService.piattiDelloStessoBuffet(idBuffet));
		model.addAttribute("buffet",buffet);
		System.out.println(piattoService.piattiAltroBuffet(idBuffet));
		model.addAttribute("outOfBuffet",piattoService.piattiAltroBuffet(idBuffet));
		return "listaPiattiDelBuffet.html";
	}
	
	@GetMapping("/admin/inseriscipiatto")
	public String inserisciPiattoForm(Model model) {
		Piatto piatto = new Piatto();
		piatto.setBuffet(new Buffet());
		piatto.setListaIngredienti(new ArrayList<>());
		model.addAttribute("piatto",piatto);
		return "admin/aggiungiPiattoForm.html";
	}
	
	@PostMapping("/admin/addPiatto")
	public String inserisciPiatto(@Valid @ModelAttribute(value="piatto") Piatto piatto, BindingResult bindingResult, Model model) {
		this.piattoValidator.validate(piatto, bindingResult);
		if(!bindingResult.hasErrors()) {
			piattoService.aggiungiPiatto(piatto);
			model.addAttribute("piatto",piatto);
			return "piatto.html";
		}
		return "admin/aggiungiPiattoForm.html";
	}
	
	@GetMapping("/eliminapiatto/{id}")
	public String eliminaPiatto(@PathVariable("id") Long id, Model model) {
		piattoService.remove(id);
		model.addAttribute("listaPiatti",piattoService.findAll());
		return "admin/listaPiattiAdmin.html";
	}
	
	@GetMapping("/visualizzapiatto/{id}")
	public String getPiatto(@PathVariable("id")Long id,Model model) {
		Piatto piatto = piattoService.findById(id);
		piatto.setListaIngredienti(new ArrayList<>());
		model.addAttribute("piatto",piatto);
		return "piatto.html";
	}
	
	@GetMapping("/aggiungipiatto/{piatto_id}/albuffet/{buffet_id}")
	public String aggiungiPiattoAlBuffet(@PathVariable("piatto_id") Long piatto_id, @PathVariable("buffet_id") Long buffet_id, Model model ) {
		Buffet buffet = buffetService.findById(buffet_id);
		Piatto piatto = piattoService.findById(piatto_id);
		piatto.setBuffet(buffet);
		piattoService.aggiungiPiatto(piatto);
		buffet.setListaPiatti(piattoService.piattiDelloStessoBuffet(buffet_id));
		model.addAttribute("buffet",buffet);
		model.addAttribute("outOfBuffet",piattoService.piattiAltroBuffet(buffet_id));
		return "listaPiattiDelBuffet.html";
	}
	
	@GetMapping("/rimuovipiatto/{piatto_id}/dalbuffet/{buffet_id}")
	public String rimuoviPiattoDalBuffet(@PathVariable("piatto_id") Long piatto_id, @PathVariable("buffet_id")Long buffet_id, Model model) {
		Buffet buffet = buffetService.findById(buffet_id);
		Piatto piatto = piattoService.findById(piatto_id);
		piatto.setBuffet(null);
		piattoService.aggiungiPiatto(piatto);
		buffet.setListaPiatti(piattoService.piattiDelloStessoBuffet(buffet_id));
		model.addAttribute("buffet",buffet);
		model.addAttribute("outOfBuffet",piattoService.piattiAltroBuffet(buffet_id));
		return "listaPiattiDelBuffet.html";
	}
}
