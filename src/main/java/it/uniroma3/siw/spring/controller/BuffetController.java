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

import it.uniroma3.siw.spring.controller.validator.BuffetValidator;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.ChefService;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.PiattoService;

@Controller
public class BuffetController {
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private BuffetValidator buffetValidator;

	@GetMapping("/listbuffets")
	public String getBuffetList(Model model) {
		model.addAttribute("buffets",buffetService.findAll());
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "/admin/listaBuffetAdmin.html";
        }
		return "listaBuffets.html";
	}
	
	@GetMapping("/admin/inseriscibuffet")
	public String inserisciBuffetForm(Model model) {
		Buffet buffet = new Buffet();
		buffet.setChefProprietario(new Chef());
		buffet.setListaPiatti(new ArrayList<>());
		model.addAttribute("buffet", buffet);
		return "admin/addBuffetForm.html";
	}
	
	@PostMapping("/addBuffet")
	public String inserisciBuffet(@Valid @ModelAttribute(value="buffet") Buffet buffet, BindingResult bindingResult, Model model) {
		this.buffetValidator.validate(buffet, bindingResult);
		if(!bindingResult.hasErrors()) {
			Chef chefProprietario = buffet.getChefProprietario();
			buffetService.aggiungiBuffet(buffet);
			buffet.getChefProprietario().setNome(chefService.findById(chefProprietario.getId()).getNome());
			buffet.getChefProprietario().setCognome(chefService.findById(chefProprietario.getId()).getCognome());
			model.addAttribute("buffet",buffet);
			return "buffet.html";
		}
		return "admin/addBuffetForm.html";
	}
	
	@GetMapping("/buffet/{id}")
	public String getBuffet(@PathVariable("id")Long id,Model model) {
		Buffet buffet = buffetService.findById(id);
		buffet.setListaPiatti(piattoService.piattiDelloStessoBuffet(id));
		model.addAttribute("buffet",buffet);
		return "buffet.html";
	}
	
	@GetMapping("/admin/rimuovibuffet/{id}")
	public String rimuoviBuffet(@PathVariable("id")Long id) {
		Buffet buffet = buffetService.findById(id);
		buffetService.removeBuffet(buffet);
		return "redirect:/listbuffets";
	}
	
	@GetMapping("/buffetsPerChef/{id}")
	public String visualizzaBuffetPerChef(@PathVariable("id")Long idChef,Model model) {
		Chef chef = chefService.findById(idChef);
		model.addAttribute("buffets",buffetService.findBuffetPerChef(idChef));
		model.addAttribute("chef",chef);
		return "listaBuffets.html";
	}
	
	@GetMapping("/cambiachef/{id}")
	public String cambiaChefForm(@PathVariable("id")Long idBuffet, Model model) {
		Buffet buffet = buffetService.findById(idBuffet);
		model.addAttribute("buffet",buffet);
		model.addAttribute("chefs",chefService.findByDifferentId(buffet.getChefProprietario().getId()));
		return "cambiaChefForm.html";
	}
	
	@PostMapping("/cambiachef")
	public String cambiaChef(@ModelAttribute(value="buffet") Buffet buffet,Model model) {
		Buffet buffetNuovoChef = buffetService.findById(buffet.getId());
		if(chefService.existById(buffet.getChefProprietario().getId())) {
			Chef nuovoChef = chefService.findById(buffet.getChefProprietario().getId());
			buffetNuovoChef.setChefProprietario(nuovoChef);
			buffetService.aggiornaBuffet(buffetNuovoChef);
			model.addAttribute("buffet",buffetNuovoChef);
			return "buffet.html";
		}
		model.addAttribute("buffet",buffetNuovoChef);
		model.addAttribute("chefs",chefService.findByDifferentId(buffet.getChefProprietario().getId()));
		return "cambiaChefForm.html";
	}
}
