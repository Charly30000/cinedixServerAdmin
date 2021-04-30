package com.cinedix.server.admin.app.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@Secured({"ROLE_ADMIN"})
	@GetMapping({"/", "/index"})
	public String index(Model model) {
		model.addAttribute("titulo", "index");
		return "index";
	}
	
	
}
