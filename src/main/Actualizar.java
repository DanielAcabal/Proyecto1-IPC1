/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Sony Vaio
 */
public class Actualizar extends JFrame {

    private JPanel panel;
    public static JTextField txtcodigo, txtnombre, txtapellido, txtcorreo, txtcontra;
    private JLabel titulo, nombre, apellido, codigo, correo, contra, genero;
    public static JComboBox combo;
    public JButton actu;

    public Actualizar() {

        setLocationRelativeTo(null);
        setSize(400, 400);
        componentes();
    }

    private void panel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.orange);
        this.getContentPane().add(panel);
        add(panel);
    }

    private void txtbox() {
        txtcodigo = new JTextField();
        txtnombre = new JTextField();
        txtapellido = new JTextField();
        txtcorreo = new JTextField();
        txtcontra = new JTextField();

        txtcodigo.setBounds(100, 50, 200, 25);
        txtnombre.setBounds(100, 85, 200, 25);
        txtapellido.setBounds(100, 125, 200, 25);
        txtcorreo.setBounds(100, 165, 200, 25);
        txtcontra.setBounds(100, 205, 200, 25);

        txtcodigo.setEnabled(false);

        panel.add(txtcodigo);
        panel.add(txtnombre);
        panel.add(txtapellido);
        panel.add(txtcorreo);
        panel.add(txtcontra);

    }

    private void etiquetas() {
        titulo = new JLabel("Actualizar datosr");
        nombre = new JLabel("Nombre:");
        apellido = new JLabel("Apellido:");
        codigo = new JLabel("Código:");
        correo = new JLabel("Correo:");
        contra = new JLabel("Contraseña: ");
        genero = new JLabel("Género: ");
        titulo.setBounds(10, 10, 200, 15);
        codigo.setBounds(10, 50, 200, 15);
        nombre.setBounds(10, 90, 200, 15);
        apellido.setBounds(10, 130, 200, 15);
        correo.setBounds(10, 170, 200, 15);
        contra.setBounds(10, 210, 200, 15);
        genero.setBounds(10, 250, 200, 15);
        panel.add(titulo);
        panel.add(nombre);
        panel.add(apellido);
        panel.add(codigo);
        panel.add(correo);
        panel.add(contra);
        panel.add(genero);
    }

    private void componentes() {
        panel();
        txtbox();
        etiquetas();
        combox();
        actualizar();
    }

    private void combox() {
        combo = new JComboBox();
        combo.setBounds(100, 245, 100, 25);
        combo.addItem("Seleccionar");
        combo.addItem("Masculino");
        combo.addItem("Femenino");
        panel.add(combo);
    }

    public void actualizar() {

        actu = new JButton("Actualizar");
        actu.setBounds(137, 285, 125, 25);
       // panel.add(buscar);
        panel.add(actu);
         ActionListener acc2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String g = "";
                if (combo.getSelectedIndex() == 1) {
                    g = "m";
                } else if (combo.getSelectedIndex() == 2) {
                    g = "f";
                }
                PanelProfesor.actualizarfila(txtcodigo.getText(), txtnombre.getText(), txtapellido.getText(), txtcorreo.getText(), g);
                for (CrearProfesores prof: AgregarProfesores.profes) {
                    if (prof.getCodigo()==Integer.parseInt(txtcodigo.getText())){
                        prof.setCodigo(Integer.parseInt(txtcodigo.getText()));
                        prof.setNombre(txtnombre.getText());
                        prof.setApellido(txtapellido.getText());
                        prof.setCorreo(txtcorreo.getText());
                        prof.setGenero(g);
                        break;
                    }
                }
                txtcodigo.setText("");
                txtnombre.setText("");
                txtapellido.setText("");
                txtcorreo.setText("");
                txtcontra.setText("");
                Cargas.Serializar.serializar(AgregarProfesores.profes,"profesores.bin",false);
            }

                
        };
        actu.addActionListener(acc2);
    }

}
