package it.uniroma3.siw.spring.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.spring.controller.validator.ChefValidator;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.service.ChefService;
import it.uniroma3.siw.spring.service.CredentialsService;

@Controller
public class ChefController {
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private ChefValidator chefValidator;
	
	@GetMapping("/listchefs")
	public String getChefs(Model model) {
		model.addAttribute("chefs",chefService.findAll());
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "/admin/listaChefsAdmin.html";
        }
		return "listaChefs.html";
	}
	
	@GetMapping("/admin/inseriscichef")
	public String inserisciChefForm(Model model) {
		model.addAttribute("chef", new Chef());
		return "admin/addChefForm.html";
	}
	
	@PostMapping("/addChef")
	public String inserisciChef(@Valid @ModelAttribute(value="chef") Chef chef, BindingResult bindingResult, Model model) {
		this.chefValidator.validate(chef, bindingResult);
		if(!bindingResult.hasErrors()) {
			chefService.aggiungiChef(chef);
			model.addAttribute("chef",chef);
			return "chef.html";
		}
		return "admin/addChefForm.html";
	}
	
	@GetMapping("/chef/{id}")
	public String getChef(@PathVariable("id")Long id,Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("chef",chef);
		return "chef.html";
	}
	
	@GetMapping("/admin/rimuovichef/{id}")
	public String rimuoviChef(@PathVariable("id")Long id,Model model) {
		chefService.remove(id);
		model.addAttribute("chefs",chefService.findAll());
		return "admin/listaChefsAdmin.html";
	}
}
