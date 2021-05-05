package com.cinedix.server.admin.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cinedix.server.admin.app.models.service.ICineService;

@Component
public class CinePropertyEditor extends PropertyEditorSupport {

	@Autowired
	private ICineService cineService;

	@Override
	public void setAsText(String idCine) throws IllegalArgumentException {

		try {
			Long id = Long.parseLong(idCine);
			this.setValue(cineService.findOne(id));
		} catch (NumberFormatException e) {
			this.setValue(null);
		} catch (Exception e) {
			this.setValue(null);
		}

	}

}
