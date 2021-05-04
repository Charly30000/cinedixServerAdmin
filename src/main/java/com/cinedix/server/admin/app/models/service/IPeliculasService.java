package com.cinedix.server.admin.app.models.service;

import java.util.List;

import com.cinedix.server.admin.app.models.entity.Pelicula;

public interface IPeliculasService {

	public List<Pelicula> findAll();
	
	public void save(Pelicula pelicula);
	
	public Pelicula findOne(Long id);
	
	public void delete (Long id);
}
