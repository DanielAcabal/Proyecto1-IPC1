
package Cursos;

import java.io.Serializable;

public class Cursos implements Serializable{
    private int codigo;
    private String nombre;
    private int creditos;
    private int alumnos;
    private int profesor;
    
    public Cursos() {
        this.codigo = 0;
        this.nombre = "";
        this.creditos = 0;
        this.alumnos = 0;
        this.profesor = 0;
    }
    public Cursos(int codigo, String combre, int creditos, int alumnos, int profesores) {
        this.codigo = codigo;
        this.nombre = combre;
        this.creditos = creditos;
        this.alumnos = alumnos;
        this.profesor = profesores;
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

    public void setNombre(String combre) {
        this.nombre = combre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(int alumnos) {
        this.alumnos = alumnos;
    }

    public int getProfesores() {
        return profesor;
    }

    public void setProfesores(int profesores) {
        this.profesor = profesores;
    }
    
}
