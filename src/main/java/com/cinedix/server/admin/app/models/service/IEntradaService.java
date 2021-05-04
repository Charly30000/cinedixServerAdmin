package com.cinedix.server.admin.app.models.service;

import java.util.List;

import com.cinedix.server.admin.app.models.entity.Entrada;

public interface IEntradaService {

	public List<Entrada> findAll();
	
	public void save(Entrada entrada);
	
	public Entrada findOne(Long id);
	
	public void delete (Long id);
	
	public List<Entrada> buscarPorUsuario(Long id);
}
