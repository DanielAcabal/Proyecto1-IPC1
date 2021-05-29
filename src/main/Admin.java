package main;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Admin extends JFrame {

    private JTabbedPane pestañas;
    panelcursos cursos;
    panelalumnos alumnos;
    PanelProfesor profesor;
    private JPanel panel;

    public Admin() {
        setSize(850, 500);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(500, 540));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Administración");
       
        componentes();
    }

    private void componentes() {
        pestañas = new JTabbedPane();
        cursos = new panelcursos();
        alumnos = new panelalumnos();
        profesor = new PanelProfesor();
        pestañas.add("Profesores", profesor);
        pestañas.add("Cursos", cursos);
        pestañas.add("Alumnos", alumnos);
        add(pestañas);
       // panel();
    }
    public void cerrar(){
        setVisible(false);
        dispose();
    }

  

}
