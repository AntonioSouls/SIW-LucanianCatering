package it.uniroma3.siw.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.ChefService;

@Component
public class ChefValidator implements Validator{
	
	@Autowired
	private ChefService chefService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Chef.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		if(chefService.alreadyExist((Chef)target)) {
			errors.reject("duplicate.chef");
		}
		
		if(((Chef)target).getNome().trim().isEmpty()) {
			errors.rejectValue("nome","required");
		}
		else {
			if(((Chef)target).getNome().length()<2 ||((Chef)target).getNome().length()>100) {
				errors.rejectValue("nome","size");
			}
		}
		
		if(((Chef)target).getCognome().trim().isEmpty()) {
			errors.rejectValue("cognome", "required");
		}
		else {
			if(((Chef)target).getCognome().length()<2 ||((Chef)target).getCognome().length()>100) {
				errors.rejectValue("cognome","size");
			}
		}
		
		if(((Chef)target).getNazionalita().trim().isEmpty()) {
			errors.rejectValue("nazionalita", "required");
		}
	}

}
