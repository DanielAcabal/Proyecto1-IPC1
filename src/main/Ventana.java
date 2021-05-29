package main;

import ModuloProfes.AdminCurso;
import ModuloProfes.ModActualizar;
import ModuloProfes.ModProfesor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static main.Principal.ag;
import static main.Principal.agcursos1;

public class Ventana extends JFrame {

    private JPanel panel;
    public JTextField txtcodigo;
    private JPasswordField txtcontra;
    Admin administrar = new Admin();
    AdminProfesores mod = new AdminProfesores();
    Principal prin = new Principal();
    ModActualizar modactu = new ModActualizar(); //Nuevo objeto (cambiado 11:58)
    public String user = "", con = "";
    public int[] gi = new int[1];
    AdminCurso adcurso = new AdminCurso();

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    ModProfesor modprofe = new ModProfesor();

    public Ventana() {
        setSize(600, 400); //Tamaño de la ventana
        setLocationRelativeTo(null); //Centro de Pantalla
        setMinimumSize(new Dimension(600, 400));
        IniciarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("DTT");
        try {
            prin.car();
            prin.cargaCursos();
            prin.caralumnos();
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void IniciarComponentes() {
        paneles();
        Eti();
        botones();
        textbox();
    }

    private void Eti() {
        JLabel etiqueta = new JLabel();
        JLabel eti = new JLabel();
        etiqueta.setText("Código");
        etiqueta.setBounds(100, 100, 50, 30);
        eti.setText("Contraseña");
        eti.setBounds(100, 200, 80, 30);
        panel.add(etiqueta);
        panel.add(eti);
    }

    private void paneles() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.orange);
        this.getContentPane().add(panel);
    }

    private void botones() {
        JButton btnIngresar = new JButton();
        btnIngresar.setText("Iniciar Sesión");
        btnIngresar.setEnabled(true);
        btnIngresar.setBounds(250, 250, 200, 40);
        btnIngresar.setMnemonic('a');
        panel.add(btnIngresar);

        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean f = false;

                user = txtcodigo.getText();
                con = txtcontra.getText();
                //buscar(user, con);
                if ("admin".equals(txtcodigo.getText()) && "admin".equals(txtcontra.getText())) {
                    dispose();
                    administrar.setVisible(true);
                    f = true;
                } else if (f == false) {
                    buscar(user, con);
                } else {

                    JOptionPane.showMessageDialog(null, "Datos erróneos, Porfavor inténtelo de nuevo");
                    txtcodigo.setText("");
                    txtcontra.setText("");
                }

            }

        };
        btnIngresar.addActionListener(accion);
    }

    private void textbox() {
        txtcodigo = new JTextField();
        txtcontra = new JPasswordField();
        txtcodigo.setHorizontalAlignment(JTextField.CENTER);
        txtcontra.setHorizontalAlignment(JPasswordField.CENTER);
        txtcodigo.setBounds(200, 100, 300, 30);
        txtcontra.setBounds(200, 200, 300, 30);
        panel.add(txtcodigo);
        panel.add(txtcontra);
    }

    private void buscar(String user, String con) {
        try {
            prin.carnt();
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        int contador = 0;
//        if (user.equals(txtcodigo.getText()) && con.equals(txtcontra.getText())) {
//                    dispose();
//                    administrar.setVisible(true);
//
//                }else{
        //System.out.println(user + "  " + con);
        while (contador < 50) {
            if (ag.profes[contador] == null) {
                //System.out.println("nada" + contador);
                JOptionPane.showMessageDialog(null, "Datos erróneos, Porfavor inténtelo de nuevo");
                txtcodigo.setText("");
                txtcontra.setText("");
                break;
            }
            if (ag.profes[contador].getCodigo() == Integer.parseInt(user)) {
                //System.out.println("Compara User");
                if (con.equals(ag.profes[contador].getContraseña())) {
                    //System.out.println("Compara contra");
                    //modprofe.comparar(Integer.parseInt(user));
                    //ModProfesor model = new ModProfesor(Integer.parseInt(user));
                    bot(Integer.parseInt(user));
                    gi[0] = Integer.parseInt(user);

                    dispose();
                    modprofe.setVisible(true);
                    break;
                }
            }
            contador++;

        }
        //}
    }

    public void bot(int cod) {
        int contador = 0;
        //System.out.println("Cpdigo profe:" + cod);
        JButton[] boton2 = new JButton[50];
        for (int i = 0; i < agcursos1.nuevocurso.length; i++) {
            if (agcursos1.nuevocurso[i] == null) {
                //System.out.println("Mal");
                break;
            }
            if (agcursos1.nuevocurso[i].getProfesores() == cod) {
                boton2[i] = new JButton();
                boton2[i].setText(agcursos1.nuevocurso[i].getNombre());
                boton2[i].setEnabled(true);
                boton2[i].setBounds(260, (20 + 50 * contador), 200, 25);
                boton2[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        adcurso.setVisible(true);
                    }
                });
                modprofe.panel.add(boton2[i]);
                contador++;
            }
        }

    }

    public void actuali() {

        for (int i = 0; i < ag.profes.length; i++) {
            if (ag.profes[i].getCodigo() == Integer.parseInt(user)) {

                //System.out.println("Posicion profe" + i);
                modactu.buscar(i);
                break;
            }

        }
//        for (int i = 0; i < ag.profes.length; i++) {
//            if (ag.profes[i] == null) {
//                numprofes = i;
//                break;
//            }
//        }

    }

}
