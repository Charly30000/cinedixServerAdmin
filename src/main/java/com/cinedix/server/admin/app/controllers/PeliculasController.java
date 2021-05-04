package com.cinedix.server.admin.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinedix.server.admin.app.models.entity.Pelicula;
import com.cinedix.server.admin.app.models.service.IPeliculasService;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/")
public class PeliculasController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IPeliculasService peliculaService;

	@GetMapping({ "/", "/index", "/peliculas", "/peliculas/cartelera" })
	public String index(Model model) {
		model.addAttribute("titulo", "index");
		model.addAttribute("linkSelectedNav", "peliculas");
		model.addAttribute("linkSelected", "cartelera");
		model.addAttribute("peliculas", peliculaService.findAll());

		return "index";
	}

	@GetMapping({ "/peliculas/annadir" })
	public String annadir(Model model) {
		model.addAttribute("titulo", "index");
		model.addAttribute("linkSelectedNav", "peliculas");
		model.addAttribute("linkSelected", "annadir");
		return "index";
	}

	@GetMapping("/peliculas/editar/{id}")
	public @ResponseBody Map<String, String> editar(@PathVariable(value = "id") Long id) {
		Map<String, String> result = new HashMap<>();
		if (id > 0) {

			Pelicula pelicula = peliculaService.findOne(id);
			pelicula.setEstreno(!pelicula.getEstreno());
			peliculaService.save(pelicula);

		} else {
			result.put("mensaje", "Operacion prohibida");
			return result;
		}
		result.put("mensaje", "ok");
		return result;
	}

	@GetMapping("/peliculas/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		
		if (id > 0) {
			peliculaService.delete(id);
			flash.addFlashAttribute("info", "Pelicula borrada correctamente!");
		} else {
			flash.addFlashAttribute("error", "La operacion que has intentado realizar esta prohibida");
			return "redirect:/";
		}
		
		return "redirect:/";
	}

}
