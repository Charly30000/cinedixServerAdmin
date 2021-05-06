package com.cinedix.server.admin.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinedix.server.admin.app.models.entity.Usuario;
import com.cinedix.server.admin.app.models.service.IUsuarioService;
import com.cinedix.server.admin.app.util.paginator.PageRender;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping({"/usuarios"})
public class UsuariosController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page,Model model) {
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Usuario> usuarios = usuarioService.findAll(pageRequest);
		
		PageRender<Usuario> pageRender = new PageRender<>("", usuarios);
		
		model.addAttribute("titulo", "Usuarios");
		model.addAttribute("linkSelectedNav", "usuarios");
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("page", pageRender);
		
		return "usuarios";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		
		if (id <= 0) {
			flash.addFlashAttribute("error", "El usuario que intentas eliminar no existe");
			return "redirect:/usuarios";
		}
		
		Usuario usuario = usuarioService.findOne(id);
		if (usuario == null) {
			flash.addFlashAttribute("error", "El usuario que intentas eliminar no existe");
			return "redirect:/usuarios";
		}
		
		usuarioService.delete(id);
		
		flash.addFlashAttribute("success", "Usuario eliminado correctamente!");
		
		return "redirect:/";
	}
}
