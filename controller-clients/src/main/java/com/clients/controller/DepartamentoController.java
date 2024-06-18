package com.clients.controller;

import com.clients.entities.Departamento;
import com.clients.service.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class DepartamentoController {
    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping("/departamento/form")
    public String mostrarFormDepartamento(Map<String,Object> modelo){
        Departamento departamento = new Departamento();
        modelo.put("departamento", departamento);
        modelo.put("titulo","Registro de Departamentos");
        return "departamento/form";
    }

    @PostMapping("/departamento/form")
    public String guardarDepartamento(@Valid Departamento departamento, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status){
        if(result.hasErrors()){
            modelo.addAttribute("titulo","Modificar Departamento");
            return "departamento/form";
        }
        String mensaje = (departamento.getId() != null) ? "El departamento ha sido editado correctamente": "El departamento registrado con éxito";
        departamentoService.save(departamento);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:/listar";
    }
}

