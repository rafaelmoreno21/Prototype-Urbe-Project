package com.clients.service;

import com.clients.entities.Departamento;
import com.clients.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {
    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Departamento> findAll() {
        return departamentoRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Departamento departamento) {
        departamentoRepository.save(departamento);
    }

    @Override
    @Transactional(readOnly = true)
    public Departamento findOne(Long id) {
        return departamentoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        departamentoRepository.deleteById(id);
    }
}
