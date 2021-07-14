package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class Ventana extends JFrame {

    private JPanel panel;
    public JTextField txtcodigo;
    private JPasswordField txtcontra;


    public Ventana() {
        setSize(600, 400); //Tamaño de la ventana
        setLocationRelativeTo(null); //Centro de Pantalla
        setMinimumSize(new Dimension(600, 400));
        IniciarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("DTT");
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
                if ("admin".equals(txtcodigo.getText()) && "admin".equals(txtcontra.getText())) {
                    dispose();
                    Admin admin = new Admin();
                    admin.setVisible(true);
                }  else {
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
/*
    private void buscar(String user, String con) {
        try {
            prin.carnt();
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        int contador = 0;
        while (contador < 50) {
            if (ag.profes[contador] == null) {
                //System.out.println("nada" + contador);
                JOptionPane.showMessageDialog(null, "Datos erróneos, Porfavor inténtelo de nuevo");
                txtcodigo.setText("");
                txtcontra.setText("");
                break;
            }
            if (ag.profes[contador].getCodigo() == Integer.parseInt(user)) {
                if (con.equals(ag.profes[contador].getContraseña())) {
                    bot(Integer.parseInt(user));
                    profe = user;
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
                break;
            }
            if (agcursos1.nuevocurso[i].getProfesores() == cod) {
                ModProfesor.boton[contador] = new JButton();
                ModProfesor.clases[contador] = new JLabel();
                ModProfesor.boton[contador].setText(agcursos1.nuevocurso[i].getNombre());
                curso = agcursos1.nuevocurso[i].getNombre();
                ModProfesor.clases[contador].setText(curso);
                //System.out.println(boton2[contador].getText()+"contador: "+contador);
                ModProfesor.boton[contador].setEnabled(true);
                ModProfesor.boton[contador].setBounds(260, (20 + 50 * contador), 200, 25);
                ModProfesor.boton[contador].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        adcurso = new AdminCurso();
                        adcurso.setVisible(true);
                    }
                }); 
                modprofe.panel.add(ModProfesor.boton[contador]);
                contador++;
            }
        }

    }

*/
}
