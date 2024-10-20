package models;

import java.util.ArrayList;

public class Concesionario {
    private String nombre;
    private ArrayList<Coche> coches;

    public Concesionario() {}

    public Concesionario(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Coche> getCoches() {
        return coches;
    }

    public void setCoches(ArrayList<Coche> coches) {
        this.coches = coches;
    }

    @Override
    public String toString() {
        StringBuilder datosConcesionarios = new StringBuilder();
        datosConcesionarios.append("Nombre del concesionario: ").append(nombre).append("\n");
        datosConcesionarios.append("Coches: \n");
        int aux = 1;
        for (Coche coche : coches) {
            datosConcesionarios.append("\t Coche ").append(aux).append(": ").append(coche.getMarca()).append( "\n");
            aux++;
        }
        return datosConcesionarios.toString();
    }
}
