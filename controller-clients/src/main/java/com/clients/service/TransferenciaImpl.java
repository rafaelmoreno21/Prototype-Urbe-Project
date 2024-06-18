package com.clients.service;

import com.clients.entities.Activo;
import com.clients.entities.Departamento;
import com.clients.entities.Transferencia;
import com.clients.repository.ActivoRepository;
import com.clients.repository.DepartamentoRepository;
import com.clients.repository.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TransferenciaImpl implements TransferenciaService {

    @Autowired
    private ActivoRepository activoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Override
    @Transactional
    public void transferirActivos(Long idActivo, Long idDepartamentoActual, Long idNuevoDepartamento, Date fecha, String justificacion, Long idTransferencia) {
        Departamento departamentoActual = departamentoRepository.findById(idDepartamentoActual)
                .orElseThrow(() -> new RuntimeException("Departamento actual no encontrado"));
        Departamento nuevoDepartamento = departamentoRepository.findById(idNuevoDepartamento)
                .orElseThrow(() -> new RuntimeException("Nuevo Departamento no encontrado"));

        Activo activo = activoRepository.findById(idActivo)
                .orElseThrow(() -> new RuntimeException("Activo no encontrado"));

        activo.setDepartamento(nuevoDepartamento);

        Transferencia transferencia = new Transferencia();
        transferencia.setId(idTransferencia);
        transferencia.setActivo(activo);
        transferencia.setFecha(fecha);
        transferencia.setJustificacion(justificacion);

        transferenciaRepository.save(transferencia);

        activoRepository.save(activo);
    }

}
