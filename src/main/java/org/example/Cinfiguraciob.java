package org.example;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
        return "Cinfiguraciob{" +
                "usado='" + usado + '\'' +
                ", recetas=" + recetas +
                '}';
    }
}