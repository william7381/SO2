package com.yuliamz.so.s2.Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    private final ArrayList<Proceso> comunicaciones;
    private final ArrayList<Proceso> destruidos;
    private final ArrayList<Proceso> finalizados;

    public AdministradorProcesos(ArrayList<Proceso> listaProcesos){
        listos = listaProcesos;
        despachados = new ArrayList<>();
        ejecucion = new ArrayList<>();
        expiracionTiempo = new ArrayList<>();
        bloqueando = new ArrayList<>();
        bloqueados = new ArrayList<>();
        despertados = new ArrayList<>();
        comunicaciones = new ArrayList<>();
        destruidos = new ArrayList<>();
        finalizados = new ArrayList<>();
    }
    
    public void iniciarSecuencia() throws CloneNotSupportedException {
        ordenarPorNuevaPrioridad();
        int tamanioActual = listos.size();
        for (int i = 0; i < tamanioActual; i++) {
            Proceso procesoActual = listos.get(i).clonar();
            boolean procesoFinalizado = false;
            while(!procesoFinalizado) {
                despachados.add(procesoActual.clonar());
                ejecucion.add(procesoActual.clonar());
                if (procesoActual.getTiempo() <= QUANTUM) {
                    procesoActual.setTiempo(0);
                    finalizados.add(procesoActual.clonar());
                    procesoFinalizado = true;
                }else {
                    procesoActual.setTiempo(procesoActual.getTiempo() - QUANTUM);
                    boolean bloqueadoDestruido = false;
                    if (procesoActual.isBloqueado()) {
                        bloqueando.add(procesoActual.clonar());
                        bloqueados.add(procesoActual.clonar());
                        despertados.add(procesoActual.clonar());
                        bloqueadoDestruido = true;
                    }
                    if (procesoActual.isDestruido()) {
                        destruidos.add(procesoActual.clonar());
                        bloqueadoDestruido = true;
                    }
                    if (procesoActual.getComunicacion() != null) {
                        comunicaciones.add(procesoActual.clonar());
                    }
                    if(!bloqueadoDestruido) {
                        expiracionTiempo.add(procesoActual.clonar());
                    }
                    listos.add(procesoActual.clonar());
                }
            }
        }
    }
    
    private void ordenarPorNuevaPrioridad() {
//        Collections.sort(listos, comparadorDePrioridad);
        ArrayList<Proceso> listosActual = (ArrayList<Proceso>) listos.clone();
        for (int i = 0; i < listos.size(); i++) {
        	Proceso proceso = listos.get(i);
            if(proceso.getNuevaPrioridad() != 0 && proceso.getNuevaPrioridad() < proceso.getPrioridad() && proceso.getNuevaPrioridad() != proceso.getPrioridad()) {
                for (int j = 0; j < listos.size(); j++) {
                    if(proceso.getNuevaPrioridad() == listos.get(j).getPrioridad()) {
                        listos.remove(proceso);
                        listos.add(j, proceso);
                        List<Proceso> subLista = listos.subList(j, listos.size());
                        for (Proceso procesoActual : subLista) {
                            procesoActual.setPrioridad(procesoActual.getPrioridad()+1);
                        }
                    }
                }
                proceso.setPrioridad(proceso.getNuevaPrioridad());
            }
        }
        Collections.sort(listos, comparadorDePrioridad);
    }
    
    private Comparator<Proceso> comparadorDePrioridad = new Comparator<Proceso>() {
        @Override
        public int compare(Proceso o1, Proceso o2) {
            return o1.getPrioridad() - o2.getPrioridad();
        }
    };
    
}
