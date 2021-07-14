package main;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Admin extends JFrame {

    private JTabbedPane pestañas;
    private panelcursos cursos;
    private panelalumnos alumnos;
    private PanelProfesor profesor;

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
        profesor = new PanelProfesor();
        cursos = new panelcursos();
        alumnos = new panelalumnos();
        pestañas.add("Profesores", profesor);
        pestañas.add("Cursos", cursos);
        pestañas.add("Alumnos", alumnos);
        add(pestañas);
    }
}
