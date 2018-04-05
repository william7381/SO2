/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yuliamz.so.s1.Modelo;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author equipo
 */
@lombok.Getter
public class AdministradorProcesos {
    public static final int QUANTUM = 5;
    
    private ArrayList<Proceso> listos;
    private ArrayList<Proceso> despachados;
    private ArrayList<Proceso> ejecucion;
    private ArrayList<Proceso> expiracionTiempo;
    private ArrayList<Proceso> bloqueando;
    private ArrayList<Proceso> bloqueados;
    private ArrayList<Proceso> despertados;
    private ArrayList<Proceso> finalizados;

    public AdministradorProcesos(ArrayList<Proceso> listaProcesos) {
        listos = listaProcesos;
        despachados = new ArrayList<>();
        ejecucion = new ArrayList<>();
        expiracionTiempo = new ArrayList<>();
        bloqueando = new ArrayList<>();
        bloqueados = new ArrayList<>();
        despertados = new ArrayList<>();
        finalizados = new ArrayList<>();
    }
    
    public void iniciarSecuencia() throws CloneNotSupportedException {
        Iterator<Proceso> iterator = listos.iterator();
        while(iterator.hasNext()) {
            Proceso procesoActual = iterator.next();
            despachados.add(procesoActual.clonar());
            ejecucion.add(procesoActual.clonar());
            if (procesoActual.getTiempo() <= QUANTUM) {
                procesoActual.setTiempo(0);
                finalizados.add(procesoActual.clonar());
            }else {
                procesoActual.setTiempo(procesoActual.getTiempo() - QUANTUM);
                if (procesoActual.isBloqueado()) {
                    bloqueando.add(procesoActual.clonar());
                    bloqueados.add(procesoActual.clonar());
                    despertados.add(procesoActual.clonar());
                }else {
                    expiracionTiempo.add(procesoActual.clonar());
                }
                listos.add(procesoActual.clonar());
            }
        }
    }
    
}
