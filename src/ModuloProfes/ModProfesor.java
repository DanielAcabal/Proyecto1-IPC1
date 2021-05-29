package ModuloProfes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import main.Actualizar;
import main.Principal;
import static main.Principal.agcursos1;
import main.Ventana;

public class ModProfesor extends JFrame {

    private int codigo;
    public JPanel panel;
    private JButton cerrar, actual;
    static Ventana venta;
    ModActualizar act = new ModActualizar();
    Principal prin = new Principal();
    // static AgregarCursos agcursos1;
    // private int[] cantidadcurso;

    public ModProfesor() {
        setSize(850, 500);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(500, 540));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Módulo de profesores");

        componentes();

    }

    public ModProfesor(int codigo) {
        setSize(850, 500);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(500, 540));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Módulo de profesores");
        this.codigo = codigo;
        componentes();
    }

    private void componentes() {

        panel();
        botones();
    }

    private void panel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.orange);
        this.getContentPane().add(panel);
    }

    private void botones() {
        try {
            prin.cargaCursosnt();
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        cerrar = new JButton("Cerrar Sesión");
        actual = new JButton("Actualizar Datos");
        cerrar.setEnabled(true);
        actual.setEnabled(true);
        actual.setBounds(630, 50, 150, 25);
        cerrar.setBounds(630, 20, 150, 25);
        panel.add(actual);
        panel.add(cerrar);
        //System.out.println("antes del for");

        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //dispose();
               // venta.setVisible(true);
            }
        };
        cerrar.addActionListener(accion);
        ActionListener accion2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                act.setVisible(true);
            }
        };
        actual.addActionListener(accion2);
    }

    public void comparar(int comra) {
        int con = 0;
        //System.out.println(comra);
        for (int i = 0; i < agcursos1.nuevocurso.length; i++) {
            if (agcursos1.nuevocurso[i] == null) {
                //System.out.println("Mal");
                break;
            }
            if (agcursos1.nuevocurso[i].getProfesores() == 3) {
                con++;
               // System.out.println("sientra");
            }
        }
        codigo = con;
    }
    
}
