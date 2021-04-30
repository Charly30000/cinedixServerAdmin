package com.cinedix.server.admin.app.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping({"/usuarios"})
public class UsuariosController {
	
	@GetMapping("")
	public String index(Model model) {
		model.addAttribute("titulo", "index");
		model.addAttribute("linkSelectedNav", "usuarios");
		return "usuarios";
	}
}
