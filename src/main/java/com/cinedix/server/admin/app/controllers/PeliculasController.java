package com.cinedix.server.admin.app.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinedix.server.admin.app.models.entity.Pelicula;
import com.cinedix.server.admin.app.models.service.IPeliculasService;
import com.cinedix.server.admin.app.models.service.IUploadFileService;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/")
public class PeliculasController {

	@Autowired
	private IPeliculasService peliculaService;
	
	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping({ "/", "/index", "/peliculas", "/peliculas/cartelera" })
	public String index(Model model) {
		model.addAttribute("titulo", "Peliculas - Cartelera");
		model.addAttribute("linkSelectedNav", "peliculas");
		model.addAttribute("linkSelected", "cartelera");
		model.addAttribute("peliculas", peliculaService.findAll());

		return "index";
	}

	@GetMapping({ "/peliculas/annadir" })
	public String annadir(Model model) {
		model.addAttribute("titulo", "Annadir pelicula");
		model.addAttribute("linkSelectedNav", "peliculas");
		model.addAttribute("linkSelected", "annadir");
		
		model.addAttribute("pelicula", new Pelicula());
		
		return "index";
	}
	
	@PostMapping({ "/peliculas/annadir" })
	public String annadirPelicula(@Valid Pelicula pelicula, BindingResult result, Model model, 
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash) {
		
		boolean formatoImagen = foto.getOriginalFilename().endsWith(".png");
		if (result.hasErrors() || !formatoImagen) {
			model.addAttribute("titulo", "Annadir pelicula");
			model.addAttribute("linkSelectedNav", "peliculas");
			model.addAttribute("linkSelected", "annadir");
			if (!formatoImagen) {
				model.addAttribute("error", "Debe de incluirse una imagen en formato .png");
			}
			
			return "index";
		}
		
		if (!foto.isEmpty()) {
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				e.printStackTrace();
			}
			pelicula.setRutaImagen(uniqueFilename);
		} else {
			pelicula.setRutaImagen("");
		}
		
		peliculaService.save(pelicula);
		
		flash.addFlashAttribute("success", "Pelicula creada correctamente!");
		
		return "redirect:/";
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
			Pelicula pelicula = peliculaService.findOne(id);
			peliculaService.delete(id);
			
			
			if(uploadFileService.delete(pelicula.getRutaImagen())) {
				flash.addFlashAttribute("info", "Foto " + pelicula.getRutaImagen() + " eliminada con exito!");
			}
			
			flash.addFlashAttribute("success", "Pelicula borrada correctamente!");
		} else {
			flash.addFlashAttribute("error", "La operacion que has intentado realizar esta prohibida");
			return "redirect:/";
		}
		
		return "redirect:/";
	}

}
