package org.example;

import java.io.File;

public class no_tiene_que_ver {
    static void main() {
        File directorio = new File("src/main/java/org/example/");
        String [] elementos= directorio.list();
        if (elementos != null){
            for (String nombre: elementos){
                File elemento=new File(directorio, nombre);
                if(elemento.isDirectory()){
                    System.out.println("Directorio: "+nombre);
                    File posible= new File(elemento.getAbsolutePath()+"/");
                    if (posible.list()!=null){
                        hijos(posible);
                    }
                }
                else {
                    System.out.println("Fichero: "+nombre);
                }
            }
        }
    }
    public static void hijos(File directorio){
        String[] elementos =directorio.list();
        if (directorio != null){
            for(String nombre: elementos){
                File elemento=new File(directorio, nombre);
                if(elemento.isDirectory()){
                    System.out.println(" Directorio: "+nombre);
                    File posible= new File(elemento.getAbsolutePath()+"/");
                    if (posible.list()!=null){
                        hijos(posible);
                    }
                }
                else {
                    System.out.println(" Fichero: "+nombre);
                }
            }
        }
    }
}

