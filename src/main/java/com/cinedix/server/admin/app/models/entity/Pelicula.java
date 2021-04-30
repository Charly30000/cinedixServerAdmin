package com.cinedix.server.admin.app.models.entity;

import java.io.Serializable;
import java.util.List;

public class Pelicula implements Serializable {

	private String nombre;

	private String descripcion;

	private String rutaImagen;

	private String estreno;

	private List<Cine> cinesDisponibles;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public String getEstreno() {
		return estreno;
	}

	public void setEstreno(String estreno) {
		this.estreno = estreno;
	}

	public List<Cine> getCinesDisponibles() {
		return cinesDisponibles;
	}

	public void setCinesDisponibles(List<Cine> cinesDisponibles) {
		this.cinesDisponibles = cinesDisponibles;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
