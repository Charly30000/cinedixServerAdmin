package com.cinedix.server.admin.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.cinedix.server.admin.app.models.entity.Pelicula;

public interface IPeliculasDao extends CrudRepository<Pelicula, Long>{

}
