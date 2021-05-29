/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Sony Vaio
 */
public class AdminProfesores extends JFrame{
    private JPanel panel;
    private JLabel eti;
    
    public AdminProfesores(){
        setSize(600, 400); //Tamaño de la ventana
        setLocationRelativeTo(null); //Centro de Pantalla
        setMinimumSize(new Dimension(600, 400));
        IniciarComponentes();
        setTitle("Módulo de Profesores");
    
    }

    private void IniciarComponentes() {
        panel();
        etiqueta();
    }

    private void etiqueta() {
        eti = new JLabel("Cursos Asignados");
        eti.setBounds(100, 100, 50, 30);
        panel.add(eti);
    }

    private void panel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.orange);
        this.getContentPane().add(panel);
    }
}
