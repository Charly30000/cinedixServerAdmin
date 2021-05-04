package com.cinedix.server.admin.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinedix.server.admin.app.models.dao.ISesionPeliculaDao;
import com.cinedix.server.admin.app.models.entity.SesionPelicula;

@Service
public class SesionPeliculaServiceImpl implements ISesionPeliculaService {

	@Autowired
	private ISesionPeliculaDao sesionPeliculaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<SesionPelicula> findAll() {
		return (List<SesionPelicula>) sesionPeliculaDao.findAll();
	}

	@Override
	@Transactional
	public void save(SesionPelicula sesionPelicula) {
		sesionPeliculaDao.save(sesionPelicula);
	}

	@Override
	@Transactional(readOnly = true)
	public SesionPelicula findOne(Long id) {
		return sesionPeliculaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		sesionPeliculaDao.deleteById(id);
	}

	@Override
	public List<SesionPelicula> obtenerSesionesPeliculasPorPelicula(Long id) {
		return sesionPeliculaDao.obtenerSesionesPeliculasPorPelicula(id);
	}

}
