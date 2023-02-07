package it.uniroma3.siw.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.ChefService;

@Component
public class BuffetValidator implements Validator{
	
	@Autowired
	public ChefService chefService;
	
	@Autowired
	public BuffetService buffetService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Buffet.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		if(buffetService.alreadyExist(((Buffet)target).getNome())) {
			errors.reject("duplicate.buffet");
		}
		
		if(((Buffet)target).getNome().trim().isEmpty()) {
			errors.rejectValue("nome", "required");
		}
		else {
			if(((Buffet)target).getNome().length()<2 || ((Buffet)target).getNome().length()>100 ) {
				errors.rejectValue("nome", "size");
			}
		}
		
		
		
		if(((Buffet)target).getDescrizione().trim().isEmpty()) {
			errors.rejectValue("descrizione", "required");
		}
		else {
			if(((Buffet)target).getDescrizione().length()<2 || ((Buffet)target).getDescrizione().length()>500 ) {
				errors.rejectValue("descrizione", "size");
			}
		}
		
		
		
		if(((Buffet)target).getChefProprietario().getId() == null) {
			errors.reject("required.buffet.chefProprietario");
		}
		else {
			if(!chefService.existById(((Buffet)target).getChefProprietario().getId())) {
				errors.reject("invalid.buffet.chefProprietario");
			}
		}
		
		
	}

}
