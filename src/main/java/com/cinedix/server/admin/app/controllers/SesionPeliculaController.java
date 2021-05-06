package com.cinedix.server.admin.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinedix.server.admin.app.editors.CinePropertyEditor;
import com.cinedix.server.admin.app.editors.PeliculaPropertyEditor;
import com.cinedix.server.admin.app.models.entity.SesionPelicula;
import com.cinedix.server.admin.app.models.service.ICineService;
import com.cinedix.server.admin.app.models.service.IPeliculasService;
import com.cinedix.server.admin.app.models.service.ISesionPeliculaService;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/peliculas")
public class SesionPeliculaController {
	
	@Autowired
	private IPeliculasService peliculasService;
	
	@Autowired
	private ICineService cinesService;
	
	@Autowired
	private ISesionPeliculaService sesionPeliculaService;
	
	@Autowired
	private CinePropertyEditor cineEditor;
	
	@Autowired 
	private PeliculaPropertyEditor peliculaEditor;
	
	@GetMapping("/annadir/sesion")
	public String annadir(Model model) {
		
		model.addAttribute("titulo", "Annadir sesion");
		model.addAttribute("linkSelectedNav", "peliculas");
		model.addAttribute("linkSelected", "annadirSesion");
		
		model.addAttribute("sesionPelicula", new SesionPelicula());
		
		model.addAttribute("listaPeliculas", peliculasService.findAll());
		model.addAttribute("listaCines", cinesService.findAll());
		
		model.addAttribute("cine", cineEditor);
		model.addAttribute("pelicula", peliculaEditor);
		
		return "index";
	}
	
	@PostMapping("/annadir/sesion")
	public String annadir(@Valid SesionPelicula sesionPelicula, BindingResult result, 
			Model model, RedirectAttributes flash) {
		
		SesionPelicula sesionPeliculaBBDD = sesionPeliculaService.obtenerSesionEntradaPorCinePeliculaFecha(sesionPelicula.getCine().getId(), 
				sesionPelicula.getPelicula().getId(), sesionPelicula.getHoraPelicula());
		
		if (result.hasErrors() || sesionPeliculaBBDD != null) {
			model.addAttribute("titulo", "Annadir sesion");
			model.addAttribute("linkSelectedNav", "peliculas");
			model.addAttribute("linkSelected", "annadirSesion");
			
			model.addAttribute("listaPeliculas", peliculasService.findAll());
			model.addAttribute("listaCines", cinesService.findAll());
			
			model.addAttribute("cine", cineEditor);
			model.addAttribute("pelicula", peliculaEditor);
			
			if (sesionPeliculaBBDD != null) {
				model.addAttribute("error", "La sesion que intentas crear ya existe en la base de datos!");
			}
			
			return "index";
		}
		
		sesionPeliculaService.save(sesionPelicula);
		
		flash.addFlashAttribute("success", String.format("Sesion para la pelicula '%s' en el cine '%s' añadida correctamente!", 
				sesionPelicula.getPelicula().getNombre(),
				sesionPelicula.getCine().getNombre()
				));
		return "redirect:/";
	}

}
