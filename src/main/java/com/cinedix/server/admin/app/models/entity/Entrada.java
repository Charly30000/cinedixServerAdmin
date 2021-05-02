package com.cinedix.server.admin.app.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "entradas")
public class Entrada implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date fecha;

	@NotEmpty
	@Column(nullable = false, unique = true)
	private String codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	private SesionPelicula sesionPelicula;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "entrada")
	private List<SitioOcupado> sitiosOcupados;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public SesionPelicula getSesionPelicula() {
		return sesionPelicula;
	}

	public void setSesionPelicula(SesionPelicula sesionPelicula) {
		this.sesionPelicula = sesionPelicula;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
