package com.yuliamz.so.s2.Modelo;

import javafx.scene.control.Button;

@lombok.Data
@lombok.AllArgsConstructor
public class Proceso implements Cloneable {
    
    private String nombre;
    private int tiempo;
    private boolean isBloqueado;
    private int prioridad;
    private int nuevaPrioridad;
    private boolean isDestruido;
    private Proceso comunicacion;
    
    protected Proceso clonar() throws CloneNotSupportedException {
        return (Proceso) clone();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return this.nombre + " " + this.tiempo + " " + this.isBloqueado + " " + prioridad + " " + nuevaPrioridad + " " + isDestruido  + " " + ((comunicacion != null) ? "true" : "false");
    }
    
}
