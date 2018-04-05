/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yuliamz.so.s1.Test;

import com.yuliamz.so.s1.Modelo.AdministradorProcesos;
import com.yuliamz.so.s1.Modelo.Proceso;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author equipo
 */
public class Test {
    
    public static void main(String[] args) {
        ArrayList<Proceso> list = new ArrayList<>();
        list.add(new Proceso("P1", 10, true));
        list.add(new Proceso("P2", 5, false));
        list.add(new Proceso("P3", 20, false));
        AdministradorProcesos administradorProcesos = new AdministradorProcesos(list);
        try {
            administradorProcesos.iniciarSecuencia();
            mostrarLista(administradorProcesos.getListos());
            System.out.println("Des");
            mostrarLista(administradorProcesos.getDespachados());
            System.out.println("Eje");
            mostrarLista(administradorProcesos.getEjecucion());
            System.out.println("Exp");
            mostrarLista(administradorProcesos.getExpiracionTiempo());
            System.out.println("Bloqueando");
            mostrarLista(administradorProcesos.getBloqueando());
            System.out.println("Bloqueados");
            mostrarLista(administradorProcesos.getBloqueados());
            System.out.println("Despertados");
            mostrarLista(administradorProcesos.getDespertados());
            System.out.println("Finalizados");
            mostrarLista(administradorProcesos.getFinalizados());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mostrarLista(ArrayList<Proceso> list) {
        for (Proceso proceso : list) {
            System.out.println(proceso.toString());
        }
    }
    
}
