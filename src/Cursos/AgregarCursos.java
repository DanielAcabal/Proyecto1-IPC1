package Cursos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
import static main.Principal.ag;
import main.panelcursos;

public class AgregarCursos extends JFrame {

    private JPanel panel;
    public JTextField txtcodigo, txtnombre, txtcreditos;
    private JLabel titulo, codigo, nombre, creditos, profesor;
    private JComboBox combo;
    public JButton agregar;

//    public static panelcursos panelcursos = new panelcursos();
    public AgregarProfesores agprofes = new AgregarProfesores();
    Object[] obj = new Object[5];
    Principal prin = new Principal();
    Cursos curso,curso1;

    private int x;

    public  Cursos[] nuevocurso = new Cursos[50];

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
        combo.setBounds(100, 170, 200, 25);
        try {
            prin.carnt();
        } catch (IOException ex) {
            Logger.getLogger(AgregarCursos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AgregarCursos.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < ag.profes.length; i++) {
            if (ag.profes[i] == null) {
               // System.out.println("Vacío");

                break;
            } else {
                combo.addItem(ag.profes[i].getNombre() + " " + ag.profes[i].getApellido());
                //System.out.println(ag.profes[i].getNombre());
                //combo.addItem("Hola");
            }

        }

        panel.add(combo);
    }

    public void agregar() {
        x = 0;

        agregar = new JButton("Agregar");
        agregar.setBounds(137, 210, 125, 25);
        panel.add(agregar);
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String maestro = "Fulano";
                maestro = ag.profes[combo.getSelectedIndex()].getNombre() + " " + ag.profes[combo.getSelectedIndex()].getApellido();
                nuevocurso[x] = new Cursos(Integer.parseInt(txtcodigo.getText()), txtnombre.getText(), Integer.parseInt(txtcreditos.getText()), Integer.parseInt(txtcodigo.getText()), combo.getSelectedIndex());
                curso = nuevocurso[x];
                obj[0] = nuevocurso[x].getCodigo();
                obj[1] = nuevocurso[x].getNombre();
                obj[2] = nuevocurso[x].getCreditos();
                obj[3] = nuevocurso[x].getAlumnos();
                obj[4] = nuevocurso[x].getProfesores();
                panelcursos.anadirfila(obj);
                try {
                    ser2(curso);
                } catch (IOException ex) {
                    Logger.getLogger(AgregarCursos.class.getName()).log(Level.SEVERE, null, ex);
                }
                x++;
            }
        };
        agregar.addActionListener(accion);

    }

    private void ser2(Cursos objetos) throws FileNotFoundException, IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Cursos.bin", true));
        os.writeObject(objetos);  // this es de tipo DatoUdp
        os.close();
    }
    public int obtenerpo(){
        int num=0; 
        for (int i = 0; i < nuevocurso.length; i++) {
            if (nuevocurso[i] == null){
                num=i;
                break;
            }
            
        }
        System.out.println(num);
         return num;
     }
    
    public void igual(Cursos[] poc) {
         int h=obtenerpo();
         int x=0;
         if (h==0)
         {
             for (int j = 0; j < poc.length; j++) {
            nuevocurso[j] = poc[j];
           // System.out.println(nuevocurso[j].getNombre());
            //System.out.println("No marcy");
             curso1 = nuevocurso[j];
        try {
                    ser2(curso1);
                } catch (IOException ex) {
                    Logger.getLogger(AgregarCursos.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
             
         }else{
        for (int j = 0; j < h; j++) {

            nuevocurso[h] = poc[h-1];
            //System.out.println(nuevocurso[h].getNombre());
            //System.out.println("vaci");
             curso1 = nuevocurso[h];
        try {
                    ser2(curso1);
                } catch (IOException ex) {
                    Logger.getLogger(AgregarCursos.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
         }
       
    }
  

}
