package Cargas;

import main.CrearProfesores;

import java.io.*;

public class Serializar {

    public static void serializar(Object objetos,String path,boolean append){
       try{
            FileOutputStream fos = new FileOutputStream(path,append);
            ObjectOutputStream og = new ObjectOutputStream(fos);
            og.writeObject(objetos);  // this es de tipo DatoUdp
            og.close();
       }catch (IOException e){
           System.out.println("Error en carga");
       }
    }
    public static Object getSerializable(String path){
        try{
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object objeto = ois.readObject();
            ois.close();
            return objeto;
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Archivo no encontrado");
            System.out.println(e);
        }
        return null;
    }
}
