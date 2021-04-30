package com.cinedix.server.admin.app.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/")
public class PeliculasController {

	@GetMapping({"/", "/index","/peliculas", "/peliculas/cartelera"})
	public String index(Model model) {
		model.addAttribute("titulo", "index");
		model.addAttribute("linkSelectedNav", "peliculas");
		model.addAttribute("linkSelected", "cartelera");
		return "index";
	}
	
	@GetMapping({"/peliculas/annadir"})
	public String annadir(Model model) {
		model.addAttribute("titulo", "index");
		model.addAttribute("linkSelectedNav", "peliculas");
		model.addAttribute("linkSelected", "annadir");
		return "index";
	}
}
