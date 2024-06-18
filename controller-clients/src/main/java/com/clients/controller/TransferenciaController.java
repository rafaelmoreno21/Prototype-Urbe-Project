package com.clients.controller;

import com.clients.dto.TransferenciaDTO;
import com.clients.entities.Activo;
import com.clients.service.ActivoServiceImpl;
import com.clients.service.AuditServiceImpl;
import com.clients.service.TransferenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller

public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @Autowired
    private AuditServiceImpl auditService;

    private ActivoServiceImpl activoService;

    // Método para mostrar la vista con el formulario de transferencia
    @GetMapping("/transferencias")
    public String mostrarFormularioTransferencia(Map<String,Object> model) {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        model.put("transferenciaDTO",transferenciaDTO);
        return "/transferencias/listar"; // Nombre del archivo HTML de Thymeleaf
    }

    // Método para procesar la transferencia de activos
    @PostMapping("/transferencias")
    public String realizarTransferencia(
            @ModelAttribute("transferencia") @Valid TransferenciaDTO transferenciaDTO, Activo activo,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Si hay errores, se vuelven a mostrar en la vista 'listar'
            return "listar";
        }


        transferenciaService.transferirActivos(
                transferenciaDTO.getIdsActivos(),
                transferenciaDTO.getIdDepartamentoActual(),
                transferenciaDTO.getIdNuevoDepartamento(),
                transferenciaDTO.getFecha(),
                transferenciaDTO.getJustificacion(),
                transferenciaDTO.getIdTransferencia());
        String action = (activo.getId() != null) ? "create" : "Transferencia Realizada";
        auditService.audit("user", action);

        redirectAttributes.addFlashAttribute("mensaje", "Transferencia realizada con éxito");
        return "redirect:/listar";
    }

}
