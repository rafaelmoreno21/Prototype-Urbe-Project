package com.clients.service;

import com.clients.entities.Departamento;
import com.clients.entities.Transferencia;

import java.util.Date;
import java.util.List;

public interface TransferenciaService {
    void transferirActivos(Long idsActivos, Long idDepartamentoActual, Long idNuevoDepartamento, Date fecha, String justificacion, Long idTransferencia);
}
