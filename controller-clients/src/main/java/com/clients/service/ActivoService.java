package com.clients.service;

import com.clients.entities.Activo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivoService {

    public List<Activo> findAll();

    public Page<Activo> findAll(Pageable pageable);

    public void save(Activo activo);

    public Activo findOne(Long id);

    public void delete(Long id);

}
