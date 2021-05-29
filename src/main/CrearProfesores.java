
package main;

import java.io.Serializable;

    public class CrearProfesores implements Serializable{
    private int codigo;
    private String nombre,apellido,correo,contraseña,genero;

    public CrearProfesores(int codigo, String nombre, String apellido, String correo, String contraseña, String genero) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.genero = genero;
    }
    public CrearProfesores(){
        this.codigo = 0;
        this.nombre = "";
        this.apellido = "";
        this.correo = "";
        this.contraseña = "";
        this.genero = "";
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
}
