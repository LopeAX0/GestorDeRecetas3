package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GestorRecetas {
    static File documento=new File("src/main/java/org/example/Documentos/recetas.dat");
    static File documentoxml=new File("src/main/java/org/example/Documentos/recetas.xml");
    static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) {
       if(!documento.exists()){
           try{documento.createNewFile();}
           catch(Exception e){
               throw new RuntimeException(e);
           }
       }
        boolean salida=false;

        int opcion=0;
        comprobar();
        while (salida==false){
            System.out.println("1.Mostrar recetas \n" +
                    "2.Buscar receta\n" +
                    "3.Modificar tiempo de receta\n" +
                    "4.Añadir nueva receta" +
                    "\n5.Exportar a xml"+
                    "\n6. Salir");
            opcion=sc.nextInt();
            switch (opcion){
                case 1:{
                    mostrar();
                    break;
                }
                case 2:{
                    Buscar();break;
                }
                case 3:{
                    modificarTiempo();
                    break;
                }
                case 4:{
                    Añadir();
                    break;
                }
                case 5:{
                    Exportar();
                    break;
                }
                case 6:{
                    salida=true;
                    break;
                }
                default:{
                    System.out.println("Esa opcion es invalida");
                }
            }
        }
    }

    private static void Exportar() {

        try(RandomAccessFile leer=new RandomAccessFile(documento,"r")) {
            if(documentoxml.exists()){
                documentoxml.delete();
                documentoxml.createNewFile();
            }else{
                documentoxml.createNewFile();
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xml = builder.newDocument();// <-- AQUÍ ESTABA EL CAMBIO PRINCIPAL
            // si ahi cosas dentro Document xml = builder.parse(new File(documentoxml.toURI()));
            // 2. Creamos los nodos del XML
            Element raiz = xml.createElement("Catalogo");
            xml.appendChild(raiz);
            Element recetas = xml.createElement("Recetas");
            raiz.appendChild(recetas);

            while(leer.getFilePointer()<documento.length()){
                char[] nombres = new char[27];
                for (int i=0;i<nombres.length;i++){
                    nombres[i] = leer.readChar();
                }
                String newreceta = new String(nombres).trim();
                int ids=leer.readInt();
                int tiempo2=leer.readInt();
                Element receta=xml.createElement("Receta");
                receta.setAttribute("id", String.valueOf(ids));
                recetas.appendChild(receta);
                Element nombre=xml.createElement("Nombre");
                nombre.setTextContent(newreceta);
                receta.appendChild(nombre);
                Element tiempo=xml.createElement("Tiempo");
                tiempo.setTextContent(String.valueOf(tiempo2));
                receta.appendChild(tiempo);

            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(xml),new
                    StreamResult(new File(documentoxml.toURI())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Exportado correctamente");
    }

    private static void Añadir() {

        char[] nombre=new char[27];
        ArrayList<Integer> ids=new ArrayList<>();

        try(RandomAccessFile leer=new RandomAccessFile(documento,"rw")) {
            while(leer.getFilePointer()<documento.length()){
                for (int i=0;i<nombre.length;i++){
                    nombre[i] = leer.readChar();
                }
                ids.add(leer.readInt());
                int tiempo2=leer.readInt();
            }
            sc.nextLine();
            System.out.println("Ingrese el nombre de la receta.");
            String nombre1=sc.nextLine();
            int newid= Collections.max(ids)+1;
            System.out.println("Ingrese el tiempo de elaboracion");
            int newtiempo=sc.nextInt();
           StringBuffer newnombre=new StringBuffer(nombre1);
           newnombre.setLength(27);
           leer.writeChars(newnombre.toString());
           leer.writeInt(newid);
           leer.writeInt(newtiempo);
            System.out.println("Receta añadida con exito.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void modificarTiempo(){

        try(RandomAccessFile leer=new RandomAccessFile(documento,"rw")) {
            System.out.println("Ingrese el id de la receta que deseas modificar");
            int id=sc.nextInt();
            boolean encontrado=false;
            int ids=0;
            int tiempo=0;
            char[] nombre=new char[27];

            while(leer.getFilePointer()<documento.length()){
                for (int i=0;i<nombre.length;i++){
                    nombre[i] = leer.readChar();
                }
                ids=leer.readInt();
                int tiempo2=leer.readInt();
                if(id==ids){
                    leer.seek(leer.getFilePointer()-4);
                    System.out.println("Se a encontrado la receta, actualmente tiene de tiempo "+tiempo2);
                    System.out.println("Ingrese el tiempo que deseas modifcar");
                    tiempo=sc.nextInt();
                    leer.writeInt(tiempo);
                    System.out.println("Se a cambiado correctamente tiempo");
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void Buscar() {
        System.out.println("Ingrese el id de la receta que deseas ver");
        int id=sc.nextInt();
        boolean encontrado=false;
        int ids=0;
        int tiempo=0;
        char[] nombre=new char[27];
        String nombrereceta="";
        try(RandomAccessFile leer=new RandomAccessFile(documento,"r")) {
            while(leer.getFilePointer()<documento.length()){
                for (int i=0;i<nombre.length;i++){
                    nombre[i] = leer.readChar();
                }
                ids=leer.readInt();
                int tiempo2=leer.readInt();
                if(id==ids){
                    nombrereceta=new String(nombre).trim();
                    tiempo=tiempo2;
                    encontrado=true;
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(encontrado==false){
            System.out.println("No existe el receta que deseas ver");
        }
        else{
            System.out.println(id+" "+nombrereceta+" "+tiempo);
        }

    }

    private static void comprobar(){
    if (documento.length()==0){
       try( DataOutputStream escribeFichero = new DataOutputStream( new FileOutputStream(documento,true))){
           String[] Nombres = {"Croquetas de jamon", "macarrones con tomate", "Pizza"};
           int[] ids={1,2,3};
           int [] tiempo={40,20,20};
           try (RandomAccessFile fichero = new RandomAccessFile(documento, "rw")) {
               for (int i = 0; i < Nombres.length; i++) {

                   //Escribe nombre de tamaño fijo 27 caracteres
                   StringBuffer nombre = new
                           StringBuffer(Nombres[i]);
                   nombre.setLength(27);
                   escribeFichero.writeChars(nombre.toString());
                   //Escribe edad
                   escribeFichero.writeInt(ids[i]);
                   //Escribe tiempo
                   escribeFichero.writeInt(tiempo[i]);

               }
               System.out.println("Registros almacenados correctamente.");
           }

       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }
    }

    public static void mostrar(){
        try(RandomAccessFile leer=new RandomAccessFile(documento,"r")) {
            while(leer.getFilePointer()<documento.length()){
                char[] nombre=new char[27];

                for (int i=0;i<nombre.length;i++){
                        nombre[i] = leer.readChar();
                }
                String nombreaparece=new String(nombre).trim();

                System.out.println("ID: "+leer.readInt());
                System.out.println("Nombre: "+nombreaparece);
                System.out.println("Tiempo de elavoracion: "+leer.readInt());
                System.out.println("-----------------------------------");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
