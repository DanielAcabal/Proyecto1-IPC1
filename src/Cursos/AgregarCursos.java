
package Cursos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import main.AgregarProfesores;
import main.CrearProfesores;
import main.panelcursos;

public class AgregarCursos extends JFrame {

    private JPanel panel;
    public JTextField txtcodigo, txtnombre, txtcreditos;
    private JLabel titulo, codigo, nombre, creditos, profesor;
    private  JComboBox combo;
    private JButton agregar;
    public static Cursos[] nuevocurso = new Cursos[50];
    public static int x;

    public AgregarCursos() {
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
        combo2();
        agregar();
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
        titulo = new JLabel("Agregar nuevo curso");
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

    public void combo2() {
        combo = new JComboBox();
        comboProfes();
        combo.setBounds(100, 170, 200, 25);
        panel.add(combo);
    }

    public void comboProfes() {
        CrearProfesores[] aux = (CrearProfesores[]) Cargas.Serializar.getSerializable("profesores.bin");
        for (int i = 0; i < aux.length; i++) {
            if (aux[i] != null) {
                combo.addItem(aux[i].getNombre() + " " + aux[i].getApellido());
            }
        }
    }

    public void agregar() {
        agregar = new JButton("Agregar");
        agregar.setBounds(137, 210, 125, 25);
        panel.add(agregar);
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               if (!vacio()){
                    System.out.println(combo.getSelectedIndex());
                    nuevocurso[x] = new Cursos(
                                            Integer.parseInt(txtcodigo.getText()),
                                            txtnombre.getText(),
                                            Integer.parseInt(txtcreditos.getText()),
                                            0,
                                            AgregarProfesores.profes[combo.getSelectedIndex()].getCodigo());
                    Object[] obj = new Object[5];
                    obj[0] = nuevocurso[x].getCodigo();
                    obj[1] = nuevocurso[x].getNombre();
                    obj[2] = nuevocurso[x].getCreditos();
                    obj[3] = nuevocurso[x].getAlumnos();
                    obj[4] = AgregarProfesores.profes[combo.getSelectedIndex()].getNombre()
                    + AgregarProfesores.profes[combo.getSelectedIndex()].getApellido();
                    panelcursos.anadirfila(obj);
                    Cargas.Serializar.serializar(nuevocurso,"cursos.bin",false);
                    x++;
                    txtcodigo.setText("");
                    txtcreditos.setText("");
                    txtnombre.setText("");
                    combo.setSelectedIndex(0);
                }
            }
        };
        agregar.addActionListener(accion);

    }
    private boolean vacio(){
        if (txtcodigo.getText().equals("")||txtnombre.getText().equals("")||txtcreditos.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Algún campo está vacío");
            return true;
        }
        return false;
    }


}
