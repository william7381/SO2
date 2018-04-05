package com.yuliamz.so.s1.Modelo;

@lombok.Data
@lombok.AllArgsConstructor
public class Proceso implements Cloneable {
    
    String nombre;
    int tiempo;
    boolean isBloqueado;
    
    protected Proceso clonar() throws CloneNotSupportedException {
        return (Proceso) clone();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return this.nombre + " " + this.tiempo + " " + this.isBloqueado;
    }
    
}
