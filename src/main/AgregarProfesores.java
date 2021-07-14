package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Sony Vaio
 */
public class AgregarProfesores extends JFrame {

    private JPanel panel;
    public JTextField txtcodigo, txtnombre, txtapellido, txtcorreo, txtcontra;
    private JLabel titulo, nombre, apellido, codigo, correo, contra, genero;
    private JComboBox combo;
    public JButton agregar;
    public static CrearProfesores[] profes = new CrearProfesores[50];
    Object[] os = new Object[6];
    public static Actualizar act = new Actualizar();
    public static int x=0;

    public AgregarProfesores() {
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

        panel.add(txtcodigo);
        panel.add(txtnombre);
        panel.add(txtapellido);
        panel.add(txtcorreo);
        panel.add(txtcontra);

    }

    private void etiquetas() {
        titulo = new JLabel("Agregar nuevo profesor");
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
        agregar();

    }

    private void combox() {
        combo = new JComboBox();
        combo.setBounds(100, 245, 100, 25);
        combo.addItem("Seleccionar");
        combo.addItem("Masculino");
        combo.addItem("Femenino");
        panel.add(combo);
    }

    public void agregar() {
        agregar = new JButton("Agregar");
        agregar.setBounds(137, 285, 125, 25);
        panel.add(agregar);
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Cantidad X: "+x);
                if (x<50){
                if (!vacio()){
                    String g = "";
                    if (combo.getSelectedIndex() == 1) {
                        g = "m";
                    } else if (combo.getSelectedIndex() == 2) {
                        g = "f";
                    }
                    try{
                    profes[x] = new CrearProfesores(Integer.parseInt(txtcodigo.getText()), txtnombre.getText(), txtapellido.getText(), txtcorreo.getText(), txtcontra.getText(), g);
                    os[0] = profes[x].getCodigo();
                    os[1] = profes[x].getNombre();
                    os[2] = profes[x].getApellido();
                    os[3] = profes[x].getCorreo();
                    os[4] = profes[x].getGenero();
                    os[5] = profes[x].getContraseña();
                    PanelProfesor.anadirfila(os);
                    if (profes[x].getGenero().equals("m"))
                        PanelProfesor.conH++;
                        PanelProfesor.aniadirGrafica(PanelProfesor.dataset,"Hombres",PanelProfesor.conH);
                    if (profes[x].getGenero().equals("f"))
                        PanelProfesor.conM++;
                        PanelProfesor.aniadirGrafica(PanelProfesor.dataset,"Mujeres",PanelProfesor.conM);
                    txtcodigo.setText("");
                    txtnombre.setText("");
                    txtapellido.setText("");
                    txtcorreo.setText("");
                    txtcontra.setText("");
                    combo.setSelectedIndex(0);
                    Cargas.Serializar.serializar(profes,"profesores.bin",false);
                    x++;
                    } catch (Exception e){
                        JOptionPane.showMessageDialog(null,"Algún dato es erróneo");
                    }
                }
                }else{
                    JOptionPane.showMessageDialog(null,"Cantidad máxima de Profesores");
                }
            }
        };
        agregar.addActionListener(accion);
    }
    public boolean vacio(){
        if (txtcodigo.equals("") || txtnombre.equals("")|| txtapellido.equals("") || txtcorreo.equals("") ||txtcontra.equals("")|| combo.getSelectedIndex()==0){
            JOptionPane.showMessageDialog(null,"Algún campo está vacío");
            return true;
        }
        return false;
    }
}
