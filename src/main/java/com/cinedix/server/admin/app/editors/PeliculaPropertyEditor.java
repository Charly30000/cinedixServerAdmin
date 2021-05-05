package com.cinedix.server.admin.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cinedix.server.admin.app.models.service.IPeliculasService;

@Component
public class PeliculaPropertyEditor extends PropertyEditorSupport {

	@Autowired
	private IPeliculasService peliculaService;

	@Override
	public void setAsText(String idCine) throws IllegalArgumentException {

		try {
			Long id = Long.parseLong(idCine);
			this.setValue(peliculaService.findOne(id));
		} catch (NumberFormatException e) {
			this.setValue(null);
		} catch (Exception e) {
			this.setValue(null);
		}

	}

}
