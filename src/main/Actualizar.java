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
import static main.Principal.ag;

/**
 *
 * @author Sony Vaio
 */
public class Actualizar extends JFrame {

    private JPanel panel;
    public JTextField txtcodigo, txtnombre, txtapellido, txtcorreo, txtcontra;
    private JLabel titulo, nombre, apellido, codigo, correo, contra, genero;
    private JComboBox combo;
    public JButton actu, buscar;
    
    AgregarProfesores actprofesor = new AgregarProfesores();
    public static PanelProfesor metodo = new PanelProfesor();
    Principal princ = new Principal();
    private int indicador;
    CrearProfesores aux;
    public static PanelProfesor ta = new PanelProfesor();
    public static int numprofes;

    public Actualizar() {

        setLocationRelativeTo(null);
        setSize(400, 400);
        componentes();
    }
    public Actualizar(String cod,String nombre, String apellido,String correo,String contra,int gen) {

        setLocationRelativeTo(null);
        setSize(400, 400);
        componentes();
        txtcodigo.setText(cod);
        txtcodigo.setEnabled(false);
        txtnombre.setText(nombre);
        txtapellido.setText(apellido);
        txtcorreo.setText(correo);
        txtcontra.setText(contra);
        combo.setSelectedIndex(gen);
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
        buscar = new JButton("Buscar");
        buscar.setBounds(260, 20, 100, 25);
        panel.add(buscar);
        panel.add(actu);

        ActionListener acc = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if ("".equals(txtcodigo.getText())) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese el código correcto");
                } else {
                    for (int i = 0; i < ag.profes.length; i++) {
                        if (ag.profes[i].getCodigo() == Integer.parseInt(txtcodigo.getText())) {
                            indicador =i;
                            //System.out.println("Posicion profe"+indicador);
                            buscar(i);
                            break;
                        }

                    }
                    for (int i = 0; i < ag.profes.length; i++) {
                        if(ag.profes[i]==null){
                            numprofes =i;
                            break;
                        }
                        
                    }
                    

                }
            }

        };
        buscar.addActionListener(acc);
        ActionListener acc2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String g = "";
                if (combo.getSelectedIndex() == 1) {
                    g = "M";
                } else if (combo.getSelectedIndex() == 2) {
                    g = "F";
                }
                metodo.actualizarfila(txtcodigo.getText(), txtnombre.getText(), txtapellido.getText(), txtcorreo.getText(), g);
                //System.out.println(actprofesor.x);
                ag.profes[indicador].setCodigo(Integer.parseInt(txtcodigo.getText()));
                ag.profes[indicador].setNombre(txtnombre.getText());
                ag.profes[indicador].setApellido(txtapellido.getText());
                ag.profes[indicador].setCorreo(txtcorreo.getText());
                ag.profes[indicador].setGenero(g);
                
                
                for (int i = 0; i < numprofes; i++) {
                    aux = ag.profes[i];
                   // System.out.println(ag.profes[i].getNombre());
                    try {
                        if(i==0){
                            princ.ser(aux);
                        }else{
                    princ.ser2(aux);}
                } catch (IOException ex) {
                    Logger.getLogger(AgregarProfesores.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    princ.carnt();
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtcodigo.setEnabled(true);
                txtcodigo.setText("");
                txtnombre.setText("");
                txtapellido.setText("");
                txtcorreo.setText("");
                txtcontra.setText("");
            }
                //metodo.ColocarGrafica();
                }
                
        };
        actu.addActionListener(acc2);
    }

    public void buscar(int i) {
        int j = 0;
        if ("m".equals(ag.profes[i].getGenero())) {
            j = 1;
        } else {
            j = 2;
        }
        txtcodigo.setEnabled(false);
        txtnombre.setText(ag.profes[i].getNombre());
        txtapellido.setText(ag.profes[i].getApellido());
        txtcorreo.setText(ag.profes[i].getCorreo());
        txtcontra.setText(ag.profes[i].getContraseña());
        combo.setSelectedIndex(j);

    }

}
