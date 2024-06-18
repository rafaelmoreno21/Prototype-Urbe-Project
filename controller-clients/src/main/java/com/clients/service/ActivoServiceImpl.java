package com.clients.service;

import com.clients.entities.Activo;
import com.clients.entities.Departamento;
import com.clients.repository.ActivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivoServiceImpl implements ActivoService {

    @Autowired
    private ActivoRepository activoRepository;

    @Autowired
    private DepartamentoServiceImpl departamentoService;


    @Override
    @Transactional(readOnly = true)
    public List<Activo> findAll() {
        return activoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Activo> findAll(Pageable pageable) {
        return activoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Activo activo) {
        Departamento departamento = departamentoService.findOne(activo.getDepartamento().getId());
        activo.setDepartamento(departamento);
        activo.setConcepto(activo.getConcepto().toUpperCase());
        activoRepository.save(activo);
    }

    @Override
    @Transactional(readOnly = true)
    public Activo findOne(Long id) {
        return activoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        activoRepository.deleteById(id);
    }

}
