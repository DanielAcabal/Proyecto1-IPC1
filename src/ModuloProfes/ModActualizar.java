
package ModuloProfes;

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

import main.AgregarProfesores;
import main.Principal;
import main.Ventana;


public class ModActualizar extends JFrame {
    public JPanel panel;
    public JTextField txtcodigo, txtnombre, txtapellido, txtcorreo, txtcontra;
    private JLabel titulo, nombre, apellido, codigo, correo, contra, genero;
    public JComboBox combo;
    public JButton actu, buscar;
    Principal prin = new Principal();
    private int codi;
    static Ventana ven;//nuevo ob

     
    public ModActualizar() {

        setLocationRelativeTo(null);
        setSize(400, 400);
        componentes();
        
        
    }
    public ModActualizar(String cod,String nombre, String apellido,String correo,String contra,int gen) {

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
        if(AgregarProfesores.profes[codi]==null){
          txtnombre = new JTextField("");
        txtapellido = new JTextField("");
        txtcorreo = new JTextField("");
        txtcontra = new JTextField("");  
        }else{
        txtnombre = new JTextField(AgregarProfesores.profes[codi].getNombre());
        txtapellido = new JTextField(AgregarProfesores.profes[codi].getApellido());
        txtcorreo = new JTextField(AgregarProfesores.profes[codi].getCorreo());
        txtcontra = new JTextField(AgregarProfesores.profes[codi].getContraseña());
        }
        txtnombre.setBounds(100, 45, 200, 25);
        txtapellido.setBounds(100, 85, 200, 25);
        txtcorreo.setBounds(100, 125, 200, 25);
        txtcontra.setBounds(100, 165, 200, 25);

        panel.add(txtnombre);
        panel.add(txtapellido);
        panel.add(txtcorreo);
        panel.add(txtcontra);

    }

    private void etiquetas() {
        titulo = new JLabel("Actualizar datos");
        nombre = new JLabel("Nombre:");
        apellido = new JLabel("Apellido:");
        correo = new JLabel("Correo:");
        contra = new JLabel("Contraseña: ");
        genero = new JLabel("Género: ");
        
        titulo.setBounds(10, 10, 200, 15);
        nombre.setBounds(10, 50, 200, 15);
        apellido.setBounds(10, 90, 200, 15);
        correo.setBounds(10, 130, 200, 15);
        contra.setBounds(10, 170, 200, 15);
        genero.setBounds(10, 210, 200, 15);
        
        panel.add(titulo);
        panel.add(nombre);
        panel.add(apellido);
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
        combo.setBounds(100, 205, 100, 25);
        combo.addItem("Seleccionar");
        combo.addItem("Masculino");
        combo.addItem("Femenino");
        panel.add(combo);
    }

    private void actualizar() {
        actu = new JButton("Actualizar");
        actu.setBounds(137, 285, 125, 25);
        panel.add(actu);
        ActionListener acc = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //ven.actuali();
            }
        };actu.addActionListener(acc);
    }
   /* public void buscar(int ind){
        int gen=0;
        if("f".equals(ag.profes[ind].getGenero())){
            gen=1;
        }
        else{
            gen=0;
        }
        System.out.println(ag.profes[ind].getCodigo());
        System.out.println(ag.profes[ind].getNombre());
        System.out.println(ag.profes[ind].getApellido());
        System.out.println(ag.profes[ind].getCorreo());
        System.out.println(ag.profes[ind].getContraseña());
       // modactu.txtcodigo.setText(ag.profes[ind].getCodigo()+"");
        txtnombre.setText(ag.profes[ind].getNombre());
        txtapellido.setText(ag.profes[ind].getApellido());
        txtcorreo.setText(ag.profes[ind].getCorreo());
        txtcontra.setText(ag.profes[ind].getContraseña());
        combo.setSelectedIndex(gen);
    }*/
}
