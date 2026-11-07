package org.example;

public class Cinfiguraciob {
    String usado;
    int recetas;


    // Constructor con parámetros
    public Cinfiguraciob() {

    }

    public String getUsado() {
        return usado;
    }

    public void setUsado(String usado) {
        this.usado = usado;
    }

    public int getRecetas() {
        return recetas;
    }

    public void setRecetas(int recetas) {
        this.recetas = recetas;
    }

    @Override
    public String toString() {
        return "Configuración{" +
                "usado='" + usado + '\'' +
                ", recetas=" + recetas +
                '}';
    }
}