package com.clients.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class TransferenciaDTO {

    private Long idsActivos;

    private Long idDepartamentoActual;

    private Long idNuevoDepartamento;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;

    private String justificacion;

    private Long idTransferencia;


    public Long getIdsActivos() {
        return idsActivos;
    }

    public void setIdsActivos(Long idsActivos) {
        this.idsActivos = idsActivos;
    }

    public Long getIdDepartamentoActual() {
        return idDepartamentoActual;
    }

    public void setIdDepartamentoActual(Long idDepartamentoActual) {
        this.idDepartamentoActual = idDepartamentoActual;
    }

    public Long getIdNuevoDepartamento() {
        return idNuevoDepartamento;
    }

    public void setIdNuevoDepartamento(Long idNuevoDepartamento) {
        this.idNuevoDepartamento = idNuevoDepartamento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Long getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(Long idTransferencia) {
        this.idTransferencia = idTransferencia;
    }
}
