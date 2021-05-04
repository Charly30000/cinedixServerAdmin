package com.cinedix.server.admin.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinedix.server.admin.app.models.dao.IPeliculasDao;
import com.cinedix.server.admin.app.models.entity.Pelicula;

@Service
public class PeliculasServiceImpl implements IPeliculasService {

	@Autowired
	private IPeliculasDao peliculasDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Pelicula> findAll() {
		// TODO Auto-generated method stub
		return (List<Pelicula>) peliculasDao.findAll();
	}

	@Override
	@Transactional
	public void save(Pelicula pelicula) {
		peliculasDao.save(pelicula);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Pelicula findOne(Long id) {
		// TODO Auto-generated method stub
		return peliculasDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		peliculasDao.deleteById(id);
		
	}


	


}
