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
    public Cinfiguraciob(String usado, int recetas) {
        this.usado = usado;
        this.recetas = recetas;
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

    // Método para guardar en JSON
    public void guardarJson(File archivoJson) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(archivoJson)) {
            gson.toJson(this, writer);
            System.out.println("Configuración guardada en JSON.");
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la configuración", e);
        }
    }

    // Método para cargar desde JSON
    public static Cinfiguraciob cargarJson(File archivoJson) {
        if (!archivoJson.exists()) {
            // Si no existe, devolvemos valores por defecto (ej: "Ninguna" o "", y 0 recetas)
            return new Cinfiguraciob("", 0);
        }

        Gson gson = new Gson();
        // Usamos FileReader dentro del try-with-resources para leer el archivo correctamente
        try (FileReader reader = new FileReader(archivoJson)) {
            return gson.fromJson(reader, Cinfiguraciob.class);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer la configuración", e);
        }
    }
}