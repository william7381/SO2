package com.yuliamz.so.s1.Modelo;

import java.util.ArrayList;

@lombok.Getter
public class AdministradorProcesos {
    public static final int QUANTUM = 5;
    
    private final ArrayList<Proceso> listos;
    private final ArrayList<Proceso> despachados;
    private final ArrayList<Proceso> ejecucion;
    private final ArrayList<Proceso> expiracionTiempo;
    private final ArrayList<Proceso> bloqueando;
    private final ArrayList<Proceso> bloqueados;
    private final ArrayList<Proceso> despertados;
    private final ArrayList<Proceso> finalizados;

    public AdministradorProcesos(ArrayList<Proceso> listaProcesos){
        listos=listaProcesos;
        despachados = new ArrayList<>();
        ejecucion = new ArrayList<>();
        expiracionTiempo = new ArrayList<>();
        bloqueando = new ArrayList<>();
        bloqueados = new ArrayList<>();
        despertados = new ArrayList<>();
        finalizados = new ArrayList<>();
    }
    
    public void iniciarSecuencia() throws CloneNotSupportedException {
        for (int i = 0; i < listos.size(); i++) {
            Proceso procesoActual = listos.get(i).clonar();
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
