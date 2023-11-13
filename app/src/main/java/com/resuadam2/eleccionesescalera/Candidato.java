package com.resuadam2.eleccionesescalera;

public class Candidato {
    int codCandidato,color;
    String nombre, partido;
    public Candidato(int codCandidato, String nombre, String partido, int color) {
        this.codCandidato=codCandidato; this.nombre=nombre; this.partido=partido; this.color=color;
    }
    public int getCodCandidato() { return codCandidato;}
    public String getNombre() {return nombre;}
    public String getPartido() { return partido; }
    public int getColor() { return color; }
    @Override public String toString() { return nombre + " (" + partido + ")"; }
}
