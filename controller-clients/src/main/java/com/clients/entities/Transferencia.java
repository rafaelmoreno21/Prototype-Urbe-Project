package com.clients.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.List;

@Entity
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne()
    @JoinColumn(name = "activo_id")
    private Activo activo;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    @Fetch(FetchMode.JOIN)
    private Departamento departamento;


    private String justificacion;

    private Date fecha;

    public Transferencia() {
    }

    public Transferencia(Long id, Activo activo, Departamento departamento, String justificacion, Date fecha) {
        this.id = id;
        this.activo = activo;
        this.departamento = departamento;
        this.justificacion = justificacion;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activo getActivo() {
        return activo;
    }

    public void setActivo(Activo activo) {
        this.activo = activo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
