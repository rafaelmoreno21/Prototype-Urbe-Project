package com.clients.service;

import com.clients.entities.Departamento;

import java.util.List;

public interface DepartamentoService {
    public List<Departamento> findAll();
    public void save(Departamento departamento);
    public Departamento findOne(Long id);
    public void delete(Long id);
}
