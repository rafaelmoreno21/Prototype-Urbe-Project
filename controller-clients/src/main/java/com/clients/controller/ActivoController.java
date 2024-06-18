package com.clients.controller;

import com.clients.entities.Activo;
import com.clients.entities.Audit;
import com.clients.entities.Departamento;
import com.clients.service.AuditServiceImpl;
import com.clients.service.ActivoService;
import com.clients.service.DepartamentoService;
import com.clients.service.TransferenciaService;
import com.clients.util.pagi.PageRender;
import com.clients.util.reportes.ActivoExporterPDF;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ActivoController {

    @Autowired
    private ActivoService activoService;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private AuditServiceImpl auditService;

    @Autowired
    private TransferenciaService transferenciaService;


    @GetMapping("/ver/{id}")
    public String verDetallesActivos(@PathVariable(value = "id")Long id, Map<String,Object> modelo, RedirectAttributes flash){
        Activo activo = activoService.findOne(id);
        if(activo ==null){
            flash.addFlashAttribute("error","El activo no existe en la base de datos");
            return "redirect:/listar";
        }

        modelo.put("activo", activo);
        modelo.put("titulo","Detalles del activo: " + activo.getConcepto());
        return "ver";

    }

    @GetMapping({"/","/listar",""})
    public String listarActivos(@RequestParam(name = "page", defaultValue = "0")int page, Model model){
        Pageable pageRequest = PageRequest.of(page,4, Sort.by("concepto"));
        Page<Activo> activos = activoService.findAll(pageRequest);

        // Convierte la página a una lista
        List<Activo> activoList = new ArrayList<>(activos.getContent());

        Collections.sort(activoList, Comparator.comparing(Activo::getConcepto));

        // Convierte los nombres a mayúsculas y obtiene los departamentos
        for (Activo activo : activoList) {
            activo.setConcepto(activo.getConcepto().toUpperCase());
            Departamento departamento = departamentoService.findOne(activo.getDepartamento().getId());
            activo.setDepartamento(departamento);
        }

        // Convierte la lista de nuevo a una página
        Page<Activo> activosMayus = new PageImpl<>(activoList, pageRequest, activos.getTotalElements());

        PageRender<Activo> pageRender = new PageRender<>("/listar",activosMayus);

        model.addAttribute("titulo","Listado de activos");
        model.addAttribute("activo",activosMayus);
        model.addAttribute("page",pageRender);

        return "listar";
    }

    @GetMapping("/form")
    public String mostrarFormActivo(Map<String,Object> modelo){
        Activo activo = new Activo();
        List<Departamento> departamentos = departamentoService.findAll();
        modelo.put("activo", activo);
        modelo.put("departamentos", departamentos);
        modelo.put("titulo","Registro de Activos");
        return "form";
    }


    @PostMapping("/form")
    public String guardarActivo(@Valid Activo activo, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status){
        if(result.hasErrors()){
            modelo.addAttribute("titulo","Modificar Activo");
            return "form";
        }
        Departamento departamento = departamentoService.findOne(activo.getDepartamento().getId());
        activo.setDepartamento(departamento);
        String mensaje = (activo.getId() != null) ? "El activo ha sido editado  correctamente": "El Activo registrado con exito";
        activoService.save(activo);
        // Registra la acción en la auditoría
        String action = (activo.getId() != null) ? "Activo Creado" : "create";
        auditService.audit("user", action);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:listar";
    }



    @GetMapping("/form/{id}")
    public String editarActivo(@PathVariable(value = "id")Long id,Map<String,Object> modelo,RedirectAttributes flash){
        Activo activo = null;
        if(id>0){
            activo = activoService.findOne(id);
            if(activo == null){
                flash.addFlashAttribute("error","El ID del activo no existe en la base de datos");
                return "redirect:/listar";
            }
        }
        else{
            flash.addFlashAttribute("error","El ID del activo no puede ser cero");
            return "redirect:/listar";
        }
        List<Departamento> departamentos = departamentoService.findAll();
        modelo.put("activo", activo);
        modelo.put("departamentos", departamentos);
        modelo.put("titulo","Edición de activos");
        return "form";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarActivo(@PathVariable(value = "id")Long id, RedirectAttributes flash, Activo activo){
        if(id>0){
            activoService.delete(id);
            flash.addFlashAttribute("success","Activo eliminado con exito");
        }
        // Registra la acción en la auditoría
        String action = (activo.getId() != null) ? "Activo Eliminado" : "create";
        auditService.audit("user", action);
        return "redirect:/listar";
    }

    @GetMapping("/exportarPDF")
    public void exportListActivoPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String header = "Content-Disposition";
        String valor = "attachment; filename=Activos_" + fechaActual + ".pdf";

        response.setHeader(header,valor);

        List<Activo> activos = activoService.findAll();

        ActivoExporterPDF exporterPDF = new ActivoExporterPDF(activos);
        exporterPDF.exportar(response);


        

    }


    @GetMapping("/historico")
    public String verHistorico(Model model){
        List<Audit> historico = auditService.findAll();
        model.addAttribute("historico", historico);
        return "historico";
    }







}
