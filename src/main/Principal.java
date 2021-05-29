/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Alumnos.Alumno;
import Cursos.AgregarCursos;
import Cursos.Cursos;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.AgregarProfesores.p;

public class Principal {

    public static AgregarProfesores ag = new AgregarProfesores();
    public static Object[] os = new Object[6];
    public static Object[] os1 = new Object[5];
    public static Object[] objet = new Object[5];
    public static int i, k;
    public static Actualizar ac = new Actualizar();
    public static AgregarCursos agcursos1 = new AgregarCursos();
    public static panelalumnos agalumno = new panelalumnos();

    public static void main(String[] args) {

        Ventana ventanas = new Ventana();
        ventanas.setVisible(true);

    }

    public void ser(CrearProfesores objetos) throws FileNotFoundException, IOException {
        ObjectOutputStream og = new ObjectOutputStream(new FileOutputStream("objetos.bin"));
        og.writeObject(objetos);  // this es de tipo DatoUdp
        og.close();
    }

    public void ser2(CrearProfesores objetos) throws FileNotFoundException, IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("objetos.bin", true));
        os.writeObject(objetos);  // this es de tipo DatoUdp
        os.close();
    }

    public void sercursos(Cursos objetos) throws FileNotFoundException, IOException {
        ObjectOutputStream og = new ObjectOutputStream(new FileOutputStream("Cursos.bin"));
        og.writeObject(objetos);  // this es de tipo DatoUdp
        og.close();
    }

    public void ser2cursos(Cursos objetos) throws FileNotFoundException, IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Cursos.bin", true));
        os.writeObject(objetos);  // this es de tipo DatoUdp
        os.close();
    }

    public void car() throws IOException, ClassNotFoundException {
        i = 0;
        int h = 0, m = 0;
        try {
            File ar = new File("objetos.bin");

            if (ar.exists()) {
                FileInputStream fs = new FileInputStream("objetos.bin");
                ObjectInputStream oi;
                while (fs.available() > 0) {

                    oi = new ObjectInputStream(fs);
                    ag.profes[i] = (CrearProfesores) oi.readObject();
                    if ("m".equals(ag.profes[i].getGenero())) {
                        m++;
                    } else {
                        h++;
                    }
                    //p.met(m,h);
                    os[0] = ag.profes[i].getCodigo();
                    os[1] = ag.profes[i].getNombre();
                    os[2] = ag.profes[i].getApellido();
                    os[3] = ag.profes[i].getCorreo();
                    os[4] = ag.profes[i].getGenero();
                    os[5] = ag.profes[i].getContraseña();
                    p.anadirfila(os);
                    i++;
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void carnt() throws IOException, ClassNotFoundException {
        i = 0;
        try {
            File ar = new File("objetos.bin");
            if (ar.exists()) {
                FileInputStream fs = new FileInputStream("objetos.bin");
                ObjectInputStream oi;
                while (fs.available() > 0) {
                    oi = new ObjectInputStream(fs);
                    ag.profes[i] = (CrearProfesores) oi.readObject();

                    i++;
                }
                //System.out.println(ag.profes[i].getNombre());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        // ac.numprofes =i;
        //System.out.println(ag.profes[0].getApellido());

    }

    public void cargaCursos() throws IOException, ClassNotFoundException {
        int li = 0;
        int conta = 0;
        try {
            File ar = new File("objetos.bin");
            File ar2 = new File("Cursos.bin");
            if (ar.exists() && ar2.exists()) {
                FileInputStream fs = new FileInputStream("Cursos.bin");
                ObjectInputStream oi;
                while (fs.available() > 0) {
                    oi = new ObjectInputStream(fs);
                    agcursos1.nuevocurso[li] = (Cursos) oi.readObject();
                    os1[0] = agcursos1.nuevocurso[li].getCodigo();
                    os1[1] = agcursos1.nuevocurso[li].getNombre();
                    os1[2] = agcursos1.nuevocurso[li].getCreditos();
                    os1[3] = agcursos1.nuevocurso[li].getAlumnos();
                    //System.out.println("");
                    for (int j = 0; j < agcursos1.nuevocurso.length + 1; j++) {
                        if (agcursos1.nuevocurso[li] == null) {
                            break;
                        } else if (agcursos1.nuevocurso[li].getProfesores() == ag.profes[j].getCodigo()) {

                            conta = j;
                            break;
                        }
                    }
                    os1[4] = ag.profes[conta].getNombre() + ag.profes[conta].getApellido();
                    //System.out.println(agcursos1.nuevocurso[li].getNombre() + " " + os1[4].toString());
                    panelcursos.anadirfila(os1);
                    li++;
                }
            }
            //System.out.println(ag.profes[i].getNombre());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        // ac.numprofes =i;
        //System.out.println(ag.profes[0].getApellido());

    }

    public void cargaCursosnt() throws IOException, ClassNotFoundException {
        int li = 0;
        int conta = 0;
        try {
            File archi = new File("Cursos.bin");
            if (archi.exists()) {
                FileInputStream fs = new FileInputStream("Cursos.bin");
                ObjectInputStream oi;
                while (fs.available() > 0) {
                    oi = new ObjectInputStream(fs);
                    agcursos1.nuevocurso[li] = (Cursos) oi.readObject();

                    li++;
                }
               // System.out.println("carga no tabla");
            }//System.out.println(ag.profes[i].getNombre());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        // ac.numprofes =i;
        //System.out.println(ag.profes[0].getApellido());

    }

    public void caralumnos() throws IOException, ClassNotFoundException {
        k = 0;

        try {
            File archi = new File("Alumnos.bin");

            if (archi.exists()) {
                FileInputStream fs = new FileInputStream("Alumnos.bin");
                ObjectInputStream oi;
                while (fs.available() > 0) {
                    oi = new ObjectInputStream(fs);
                    agalumno.nuevoalumno[k] = (Alumno) oi.readObject();

                    objet[0] = agalumno.nuevoalumno[k].getCodigo();
                    objet[1] = agalumno.nuevoalumno[k].getNombre();
                    objet[2] = agalumno.nuevoalumno[k].getApellido();
                    objet[3] = agalumno.nuevoalumno[k].getCorreo();
                    objet[4] = agalumno.nuevoalumno[k].getGenero();
                    agalumno.anadirfila(objet);
                    k++;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void del() throws FileNotFoundException, IOException {
        File f = new File("objetos.bin");
        f.delete();
    }

}
