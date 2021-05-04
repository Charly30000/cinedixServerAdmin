package com.cinedix.server.admin.app.models.service;

import java.util.List;

import com.cinedix.server.admin.app.models.entity.Cine;

public interface ICineService {

	public List<Cine> findAll();
	
	public Cine findOne(Long id);
	
}
