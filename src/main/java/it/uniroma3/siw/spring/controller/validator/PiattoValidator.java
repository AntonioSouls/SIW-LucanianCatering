package it.uniroma3.siw.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.service.PiattoService;

@Component
public class PiattoValidator implements Validator {
	
	@Autowired
	private PiattoService piattoService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Piatto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		if(piattoService.alreadyExist(((Piatto)target))) {
			errors.reject("duplicate.piatto");
		}
		
		
		if(((Piatto)target).getNome().trim().isEmpty()) {
			errors.rejectValue("nome", "required");
		}
		else {
			if(((Piatto)target).getNome().length()<2 || ((Piatto)target).getNome().length()>20) {
				errors.rejectValue("nome", "size");
			}
		}
		
		
		if(((Piatto)target).getDescrizione().trim().isEmpty()) {
			errors.rejectValue("descrizione", "required");
		}
		else {
			if(((Piatto)target).getDescrizione().length()<2 || ((Piatto)target).getDescrizione().length()>200) {
				errors.rejectValue("descrizione", "size");
			}
		}
		
	}

}
