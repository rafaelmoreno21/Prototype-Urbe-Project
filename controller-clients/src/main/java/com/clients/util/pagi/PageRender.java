package com.clients.util.pagi;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T>{

    private String url;
    private Page<T> page;
    private int totalPaginas;
    private int numElementosPorPaginal;
    private int paginaActual;
    private List<PageItem> paginas;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<PageItem>();

        numElementosPorPaginal = 5;
        totalPaginas = page.getTotalPages();
        paginaActual = page.getNumber() + 1;

        int desde, hasta;
        if(totalPaginas <= numElementosPorPaginal){
            desde=1;
            hasta=totalPaginas;
        }else {
            if(paginaActual<= numElementosPorPaginal/2){
                desde = 1;
                hasta = numElementosPorPaginal;
            }
            else if(paginaActual >= totalPaginas - numElementosPorPaginal/2){

                desde = totalPaginas - numElementosPorPaginal+1;
                hasta = numElementosPorPaginal;
            }
            else{
                desde = paginaActual - numElementosPorPaginal/2;
                hasta = numElementosPorPaginal;
            }
        }

            for(int i = 0; i<hasta; i++){
                paginas.add(new PageItem(desde + i, paginaActual ==desde + i));

            }

        }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

    public List<PageItem> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<PageItem> paginas) {
        this.paginas = paginas;
    }

    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast(){
        return page.isLast();
    }

    public boolean ishasNext(){
        return page.hasNext();
    }

    public boolean isHasPrevius(){
        return page.hasPrevious();
    }
    

}

