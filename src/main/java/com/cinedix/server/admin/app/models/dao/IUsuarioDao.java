package com.cinedix.server.admin.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.cinedix.server.admin.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

	//@Query("select u from Usuario where u.username = ?1")
	public Usuario findByUsername(String username);
	
	public Usuario findById(Integer id);
}
