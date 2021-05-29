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
import javax.swing.JPanel;
import javax.swing.JTextField;
import static main.Principal.ag;
import static main.Principal.i;

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
    CrearProfesores profe, aux;
    PanelProfesor pan;
    public CrearProfesores[] profes = new CrearProfesores[50];
    static PanelProfesor p = new PanelProfesor();
    Object[] os = new Object[6];
    Principal prin = new Principal();
    public static Actualizar act = new Actualizar();
    //static AgregarCursos agcursos = new AgregarCursos();;
    public int x;

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
        
        x = igual();
        agregar = new JButton("Agregar");
        agregar.setBounds(137, 285, 125, 25);
        panel.add(agregar);
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                txtcodigo.setEnabled(true);

                String g = "";
                if (combo.getSelectedIndex() == 1) {
                    g = "m";
                } else if (combo.getSelectedIndex() == 2) {
                    g = "f";
                }
                System.out.println(x);
                profes[x] = new CrearProfesores(Integer.parseInt(txtcodigo.getText()), txtnombre.getText(), txtapellido.getText(), txtcorreo.getText(), txtcontra.getText(), g);
                //aux = new CrearProfesores(Integer.parseInt(txtcodigo.getText()),txtnombre.getText(),txtapellido.getText(),txtcorreo.getText(),txtcontra.getText(),g);
                aux = profes[x];
                //profes.add(profe);

                // aux = (CrearProfesores)profes.get(i);
                os[0] = profes[x].getCodigo();
                os[1] = profes[x].getNombre();
                os[2] = profes[x].getApellido();
                os[3] = profes[x].getCorreo();
                os[4] = profes[x].getGenero();
                os[5] = profes[x].getContraseña();
                p.anadirfila(os);
                txtcodigo.setText("");
                txtnombre.setText("");
                txtapellido.setText("");
                txtcorreo.setText("");
                txtcontra.setText("");
                combo.setSelectedIndex(0);
                try {
                    // prin.ser(profes);
                    
                    prin.ser2(aux);
                    
                } catch (IOException ex) {
                    Logger.getLogger(AgregarProfesores.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    prin.carnt();
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }

                x++;
                //act.numprofes =x;
                System.out.println(x);
                
            }
        };
        agregar.addActionListener(accion);

    }

    public void actualizar(int i) {
        int j = 0;
        if ("m".equals(ag.profes[i].getGenero())) {
            j = 1;
        } else {
            j = 2;
        }
        txtcodigo.setEnabled(false);
        txtcodigo.setText(i + "");
        txtnombre.setText(ag.profes[i].getNombre());
        txtapellido.setText(ag.profes[i].getApellido());
        txtcorreo.setText(ag.profes[i].getCorreo());
        txtcontra.setText(ag.profes[i].getContraseña());
        combo.setSelectedIndex(j);
    }

    public int igual() {
        for (int j = 0; j < i; j++) {
            if (j==50){
                    break;
                }
            profes[j] = ag.profes[i];

        }
        return i;
    }
    public void igualar(CrearProfesores[] cp){
       
        for (int j = 0; j < cp.length; j++) {
            profes[j] = cp[j];
            profes[j].setContraseña("1234");
            try {
                    // prin.ser(profes);
                    
                    prin.ser2(profes[j]);
                    
                } catch (IOException ex) {
                    Logger.getLogger(AgregarProfesores.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
     }
}
