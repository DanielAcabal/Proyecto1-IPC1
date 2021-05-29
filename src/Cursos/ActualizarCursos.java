/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cursos;

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
import main.Principal;
import static main.Principal.ag;
import static main.Principal.agcursos1;
import main.panelcursos;

/**
 *
 * @author Sony Vaio
 */
public class ActualizarCursos extends JFrame{
    private JPanel panel;
    public JTextField txtcodigo, txtnombre, txtcreditos;
    private JLabel titulo, codigo, nombre, creditos, profesor;
    private JComboBox combo;
    public JButton agregar,buscar;
    private int indicador,numprofes;
    Principal prin = new Principal();
    public static panelcursos metodo = new panelcursos();
    Cursos aux = new Cursos();
    
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
        titulo = new JLabel("Actualizar cursos");
        codigo = new JLabel("Código:");
        nombre = new JLabel("Nombre:");
        creditos = new JLabel("Créditos:");
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
        combo.addItem("Seleccionar profesor");
        try {
            prin.carnt();
        } catch (IOException ex) {
            Logger.getLogger(ActualizarCursos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ActualizarCursos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = 0; i < ag.profes.length; i++) {
            if (ag.profes[i] == null) {
               // System.out.println("Vacío");

                break;
            } else {
                combo.addItem(ag.profes[i].getNombre() + " " + ag.profes[i].getApellido());
                //System.out.println(ag.profes[i].getNombre());
                //combo.addItem("Hola");
            }}
        
        panel.add(combo);
    }

    public void actu() {
       
        agregar = new JButton("Actualizar");
        agregar.setBounds(137, 210, 125, 25);
        buscar = new JButton("Buscar");
        buscar.setBounds(260, 20, 100, 25);
        panel.add(buscar);
        panel.add(agregar);
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String hola = "Fulano";
                hola = ag.profes[combo.getSelectedIndex()-1].getNombre() + " " + ag.profes[combo.getSelectedIndex()-1].getApellido();
                metodo.actualizarfila(txtcodigo.getText(), txtnombre.getText(), txtcreditos.getText(),"0", hola);
                
                agcursos1.nuevocurso[indicador].setCodigo(Integer.parseInt(txtcodigo.getText()));
                agcursos1.nuevocurso[indicador].setNombre(txtnombre.getText());
                agcursos1.nuevocurso[indicador].setCreditos(Integer.parseInt(txtcreditos.getText()));
                agcursos1.nuevocurso[indicador].setProfesores(combo.getSelectedIndex());
               // System.out.println("Id del combo: "+combo.getSelectedIndex());
                
                for (int i = 0; i < numprofes; i++) {
                    aux = agcursos1.nuevocurso[i];
                    //System.out.println(agcursos1.nuevocurso[i].getNombre());
                    
                        if(i==0){
                        try {
                            prin.sercursos(aux);
                        } catch (IOException ex) {
                            Logger.getLogger(ActualizarCursos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }else{
                        try {
                            prin.ser2cursos(aux);
                        } catch (IOException ex) {
                            Logger.getLogger(ActualizarCursos.class.getName()).log(Level.SEVERE, null, ex);
                        }
}
                
                try {
                    prin.cargaCursosnt();
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtcodigo.setEnabled(true);
                txtcodigo.setText("");
                txtnombre.setText("");
            }
                
                
            }
        };
        agregar.addActionListener(accion);
        ActionListener accion2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if ("".equals(txtcodigo.getText())) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese el código correcto");
                }else {
                    for (int i = 0; i < agcursos1.nuevocurso.length; i++) {
                        if (agcursos1.nuevocurso[i].getCodigo() == Integer.parseInt(txtcodigo.getText())) {
                            indicador =i;
                            //System.out.println("Posicion profe"+indicador);
                            buscar(i);
                            break;
                        }

                    }
                    for (int i = 0; i < agcursos1.nuevocurso.length; i++) {
                        if(agcursos1.nuevocurso[i]==null){
                            numprofes =i;
                            break;
                        }
                        
                    }
                    

                }
                
                
            }

           
        };
        buscar.addActionListener(accion2);
        

    }
     private void buscar(int i) {
     
        // System.out.println("Valor i de buscar:" +i);
        txtcodigo.setEnabled(false);
        txtnombre.setText(agcursos1.nuevocurso[i].getNombre());
        txtcreditos.setText(agcursos1.nuevocurso[i].getCreditos()+"");
        
        combo.setSelectedIndex(agcursos1.nuevocurso[i].getProfesores());
     }
}
