/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cursos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.AgregarProfesores;
import main.panelcursos;

public class ActualizarCursos extends JFrame{
    private JPanel panel;
    private JTextField txtcodigo, txtnombre, txtcreditos;
    private JLabel profesor;
    private JComboBox combo;
    private JButton agregar,buscar;
    
    public ActualizarCursos() {
        setLocationRelativeTo(null);
        setSize(400, 300);
        componentes();
    }

    private void panel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.orange);
        this.getContentPane().add(panel);
        add(panel);
    }

    private void componentes() {
        panel();
        etiquetas();
        txtbox();
        combo();
        actu();
    }
    private void txtbox() {
        txtcodigo = new JTextField();
        txtnombre = new JTextField();
        txtcreditos = new JTextField();

        txtcodigo.setBounds(100, 50, 200, 25);
        txtnombre.setBounds(100, 85, 200, 25);
        txtcreditos.setBounds(100, 125, 200, 25);

        panel.add(txtcodigo);
        panel.add(txtnombre);
        panel.add(txtcreditos);

    }
    private void etiquetas() {
        JLabel titulo = new JLabel("Actualizar cursos");
        JLabel codigo = new JLabel("Código:");
        JLabel nombre = new JLabel("Nombre:");
        JLabel creditos = new JLabel("Créditos:");
        profesor = new JLabel("Profesor:");
        titulo.setBounds(10, 10, 200, 15);
        codigo.setBounds(10, 50, 200, 15);
        nombre.setBounds(10, 90, 200, 15);
        creditos.setBounds(10, 130, 200, 15);
        profesor.setBounds(10, 170, 200, 15);
        panel.add(titulo);
        panel.add(nombre);
        panel.add(codigo);
        panel.add(creditos);
        panel.add(profesor);
    }

    private void combo() {
        combo = new JComboBox();
        combo.setBounds(100, 170, 200, 25);
        for (int i = 0; i < AgregarProfesores.profes.length; i++) {
            if (AgregarProfesores.profes[i] != null) {
                combo.addItem(AgregarProfesores.profes[i].getNombre() + " " + AgregarProfesores.profes[i].getApellido());
            }
        }
        panel.add(combo);
    }

    private void actu() {
       
        agregar = new JButton("Actualizar");
        agregar.setBounds(137, 210, 125, 25);
        panel.add(agregar);
       ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                for (Cursos curso: AgregarCursos.nuevocurso) {
                    if (curso != null){
                        if (curso.getCodigo() == Integer.parseInt(txtcodigo.getText())){
                            curso.setNombre(txtnombre.getText());
                            curso.setCreditos(Integer.parseInt(txtcreditos.getText()));
                            curso.setProfesores(combo.getSelectedIndex()+1);
                            panelcursos.actualizarfila(txtcodigo.getText(), txtnombre.getText(), txtcreditos.getText(),curso.getAlumnos(), curso.getProfesores());
                            break;
                        }
                    }
                }
                Cargas.Serializar.serializar(AgregarCursos.nuevocurso,"cursos.bin",false);
            }
        };
        agregar.addActionListener(accion);
    }

    public JTextField getTxtcodigo() {
        return txtcodigo;
    }

    public JTextField getTxtnombre() {
        return txtnombre;
    }

    public JTextField getTxtcreditos() {
        return txtcreditos;
    }

    public JComboBox getCombo() {
        return combo;
    }
}
