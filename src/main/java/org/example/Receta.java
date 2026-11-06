package org.example;

public class Receta{
    String nombre;/*27*/
   int id;/*4*/
    int tiempo;/*4*/

    public Receta(String nombre, int id, int tiempo) {
        this.nombre = nombre;
        this.id = id;
        this.tiempo = tiempo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public String toString() {
        return "Receta{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                ", tiempo=" + tiempo +
                '}';
    }
}
