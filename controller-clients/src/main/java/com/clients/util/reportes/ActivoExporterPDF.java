package com.clients.util.reportes;

import com.clients.entities.Activo;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ActivoExporterPDF {

    private List<Activo> listaActivo;


    public ActivoExporterPDF(List<Activo> listaActivo){
        super();
        this.listaActivo = listaActivo;
    }

    private void headerTable(PdfPTable tabla){
        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(Color.BLUE);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("Nombre",fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Serial",fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Etiqueta",fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Fecha",fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Departamento",fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Costo",fuente));
        tabla.addCell(celda);
    }

    private void writeTextTable(PdfPTable tabla){
        for(Activo activo : listaActivo){
            tabla.addCell(String.valueOf(activo.getConcepto()));
            tabla.addCell(String.valueOf(activo.getSerial()));
            tabla.addCell(String.valueOf(activo.getEtiqueta()));
            tabla.addCell(String.valueOf(activo.getFecha()));
            tabla.addCell(String.valueOf(activo.getDepartamento().getNombre()));
            tabla.addCell(String.valueOf(activo.getCosto()));

        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLUE);
        fuente.setSize(12);

        Paragraph titulo = new Paragraph("Lista de Activos", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(30);
        tabla.setWidths(new float[]{5f,5f,3f,3f,4f,3f});
        tabla.setWidthPercentage(100);

        headerTable(tabla);
        writeTextTable(tabla);
        documento.add(tabla);
        documento.close();

    }

}
