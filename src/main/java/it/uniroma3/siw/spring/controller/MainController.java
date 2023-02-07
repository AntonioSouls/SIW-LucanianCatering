package it.uniroma3.siw.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
	
	@GetMapping({"/", "index"})
	public String index(Model model) {
			return "index";
	}
}