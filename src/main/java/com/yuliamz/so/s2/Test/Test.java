package com.yuliamz.so.s2.Test;

import com.yuliamz.so.s2.Modelo.AdministradorProcesos;
import com.yuliamz.so.s2.Modelo.Proceso;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;

public class Test {
    
    public static void main(String[] args) {
        ArrayList<Proceso> list = new ArrayList<>();
        Proceso proceso = new Proceso("P1", 10, true, 3, 2, true, null);
        list.add(proceso);
        Proceso proceso1 = new Proceso("P2", 5, false, 7, 8, true, null);
        list.add(proceso1);
        Proceso proceso2 = new Proceso("P3", 6, false, 2, 1, false, proceso);
        list.add(proceso2);
        try {
            AdministradorProcesos administradorProcesos = new AdministradorProcesos(list);
            administradorProcesos.iniciarSecuencia();
            System.out.println("========Listos==========");
            mostrarLista(administradorProcesos.getListos());
            System.out.println("========Despachados======");
            mostrarLista(administradorProcesos.getDespachados());
            System.out.println("========Ejecucion======");
            mostrarLista(administradorProcesos.getEjecucion());
            System.out.println("========Expiracion de Tiempo======");
            mostrarLista(administradorProcesos.getExpiracionTiempo());
            System.out.println("========Bloqueando======");
            mostrarLista(administradorProcesos.getBloqueando());
            System.out.println("========Bloqueados======");
            mostrarLista(administradorProcesos.getBloqueados());
            System.out.println("========Comunicacion======");
            mostrarLista(administradorProcesos.getComunicaciones());
            System.out.println("========Destruidos======");
            mostrarLista(administradorProcesos.getDestruidos());
            System.out.println("========Despertados======");
            mostrarLista(administradorProcesos.getDespertados());
            System.out.println("========Finalizados======");
            mostrarLista(administradorProcesos.getFinalizados());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mostrarLista(ArrayList<Proceso> list) {
        list.forEach(System.out::println);
    }
    
}
