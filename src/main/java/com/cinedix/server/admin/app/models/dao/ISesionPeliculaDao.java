package com.cinedix.server.admin.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cinedix.server.admin.app.models.entity.SesionPelicula;

public interface ISesionPeliculaDao extends CrudRepository<SesionPelicula, Long> {

	@Query("SELECT sp FROM SesionPelicula sp WHERE sp.pelicula.id = ?1")
	public List<SesionPelicula> obtenerSesionesPeliculasPorPelicula(Long id);
}
