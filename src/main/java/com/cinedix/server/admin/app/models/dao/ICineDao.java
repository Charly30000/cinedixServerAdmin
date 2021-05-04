package com.cinedix.server.admin.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.cinedix.server.admin.app.models.entity.Cine;

public interface ICineDao extends CrudRepository<Cine, Long> {

}
