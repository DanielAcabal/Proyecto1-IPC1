package ModuloProfes;

import Alumnos.Alumno;
import com.google.gson.Gson;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import main.Ventana;

public class AdminCurso extends JFrame {

    private JPanel panel;
    private JTable alumnos, actividades;
    public DefaultTableModel modalum, modacti;
    public JScrollPane scrolalumnos, scrolacti;
    public static JLabel titulo, list, acti, repor, crear, acumul,acum, nom, des, pon, not;
    private JButton masiva, top1, top2, arcsv, nueva;
    private JTextField nombre, descrip, ponde;
    private FileDialog fd, fd2;
    Alumno[] alumnomasivo = new Alumno[50];
    private boolean csv=false;
    String[][] notas;
    private float prom=0;
    private float acumulado=0;
    public AdminCurso() {
        setSize(850, 500);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(500, 540));
        setTitle("Módulo de profesores");

        componentes();

    }

    private void componentes() {
        panel();
        etiquetas();
        botones();
        tablas();
        txtbox();
    }

    private void panel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.orange);
        this.getContentPane().add(panel);
    }

    private void etiquetas() {
        titulo = new JLabel("clase");
        list = new JLabel("Listado de Alumnos");
        acti = new JLabel("Actividades");
        repor = new JLabel("Reportes");
        crear = new JLabel("Crear Actividades");
        acumul = new JLabel("Acumulado:");
        acum = new JLabel();
        nom = new JLabel("Nombre");
        des = new JLabel("Descripción");
        pon = new JLabel("Ponderación");
        not = new JLabel("Notas");

        titulo.setBounds(10, 10, 200, 20);
        list.setBounds(10, 50, 250, 20);
        acti.setBounds(400, 50, 250, 20);
        repor.setBounds(10, 300, 200, 20);
        crear.setBounds(400, 270, 250, 20);
        acumul.setBounds(500, 240, 250, 20);
        acum.setBounds(575, 240, 250, 20);
        nom.setBounds(400, 300, 250, 20);
        des.setBounds(400, 330, 250, 20);
        pon.setBounds(400, 360, 250, 20);
        not.setBounds(400, 390, 250, 20);

        panel.add(titulo);
        panel.add(list);
        panel.add(acti);
        panel.add(repor);
        panel.add(crear);
        panel.add(acumul);
        panel.add(acum);
        panel.add(nom);
        panel.add(des);
        panel.add(pon);
        panel.add(not);
    }

    private void botones() {
        masiva = new JButton("Carga Masiva Alumnos");
        top1 = new JButton("Top 5 - Estudiantes con Mejor Rendimiento");
        top2 = new JButton("Top 5 - Estudiantes con Peor Rendimiento");
        arcsv = new JButton("Selecciona archivo CSV");
        nueva = new JButton("Crear Actividad");

        masiva.setBounds(10, 270, 375, 20);
        top1.setBounds(10, 350, 375, 20);
        top2.setBounds(10, 400, 375, 20);
        arcsv.setBounds(475, 390, 250, 20);
        nueva.setBounds(475, 420, 250, 20);

        panel.add(masiva);
        panel.add(top1);
        panel.add(top2);
        panel.add(arcsv);
        panel.add(nueva);

        ActionListener acc = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fd = new FileDialog(new Frame(), "Abrir");
                fd.setVisible(true);
                //System.out.println(fd.getDirectory());
                if ("null".equals(fd.getDirectory())) {
                    JOptionPane.showMessageDialog(null, "Por favor seleccione un archivo");
                } else {
                    //JOptionPane.showMessageDialog(null, fd.getDirectory() + fd.getFile());
                    cargamasiva(fd.getDirectory() + fd.getFile());
                }
            }
        };
        masiva.addActionListener(acc);

        ActionListener acc2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        };
        top1.addActionListener(acc2);

        ActionListener acc3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

            }
        };
        top2.addActionListener(acc3);

        ActionListener acc4 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                float sum=0;
                fd2 = new FileDialog(new Frame(), "Abrir");
                fd2.setVisible(true);
                if ("null".equals(fd2.getDirectory())) {
                    JOptionPane.showMessageDialog(null, "Por favor seleccione un archivo");
                } else {
                    try {
                        notas = archivoscsv((fd2.getDirectory() + fd2.getFile()), 2, 50);
                        for (int i = 1; i < notas.length; i++) {
                            sum+= Float.parseFloat(notas[i][1]);
                        }
                        prom = sum/notas.length;
                        csv=true;
                    } catch (IOException ex) {
                        Logger.getLogger(AdminCurso.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        arcsv.addActionListener(acc4);

        ActionListener acc5 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(csv==true){
                nuevaactividad();
                csv=false;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe ingesar las notas");
                }
            }
        };
        nueva.addActionListener(acc5);
    }

    private void tablas() {
        modalum = new DefaultTableModel();
        modalum.addColumn("Codigo");
        modalum.addColumn("Nombre");
        modalum.addColumn("Apellido");
        modalum.addColumn("Acciones");
        alumnos = new JTable(modalum);
        scrolalumnos = new JScrollPane(alumnos);
        alumnos.setBounds(10, 70, 375, 195);
        scrolalumnos.setBounds(10, 70, 375, 195);
        //scroll.setEnabled(false);
        alumnos.setBackground(Color.gray);
        alumnos.setEnabled(false);

        modacti = new DefaultTableModel();
        modacti.addColumn("Nombre");
        modacti.addColumn("Descripción");
        modacti.addColumn("Ponderación");
        modacti.addColumn("Promedio");
        actividades = new JTable(modacti);
        scrolacti = new JScrollPane(actividades);
        actividades.setBounds(400, 70, 375, 175);
        scrolacti.setBounds(400, 70, 375, 175);
        //scroll.setEnabled(false);
        actividades.setBackground(Color.gray);
        actividades.setEnabled(false);

        panel.add(scrolalumnos);
        panel.add(scrolacti);
    }

    private void txtbox() {
        nombre = new JTextField();
        descrip = new JTextField();
        ponde = new JTextField();
        nombre.setBounds(475, 300, 250, 20);
        descrip.setBounds(475, 330, 250, 20);
        ponde.setBounds(475, 360, 250, 20);

        panel.add(nombre);
        panel.add(ponde);
        panel.add(descrip);
    }

    private void cargamasiva(String ruta) {
        Object[] jeto = new Object[4];
        BufferedReader br = null;
        String Json = "";

        try {
            br = new BufferedReader(new FileReader(ruta));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminCurso.class.getName()).log(Level.SEVERE, null, ex);
        }

        String linea;
        try {
            while ((linea = br.readLine()) != null) {
                Json += linea;
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminCurso.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(AdminCurso.class.getName()).log(Level.SEVERE, null, ex);
        }

        //System.out.println(Json);
        Gson gson = new Gson();
        Alumno[] cop = gson.fromJson(Json, Alumno[].class);
        for (int i = 0; i < cop.length; i++) {
            if (i == alumnomasivo.length) {
                JOptionPane.showMessageDialog(null, "Limite de alumnos alcanzado");
                break;
            }
            alumnomasivo[i] = new Alumno(cop[i].getCodigo(), cop[i].getNombre(), cop[i].getApellido(), cop[i].getCorreo(), cop[i].getGenero(), "1234");
            //System.out.println(cop[i].getCodigo());
            //alumnomasivo[i].setCodigo(cop[i].getCodigo());

         /*   for (int j = 0; j < cop.length; j++) {
                if (alumnomasivo[i].getCodigo() == agalumno.nuevoalumno[i].getCodigo()) {
                    jeto[0] = agalumno.nuevoalumno[i].getCodigo();
                    jeto[1] = agalumno.nuevoalumno[i].getNombre();
                    jeto[2] = agalumno.nuevoalumno[i].getApellido();
                    jeto[3] = "Ver mas informacion";
                }

            }*/
            anadirfila(jeto);

        }

    }

    private void anadirfila(Object[] jeto) {
        DefaultTableModel mod = (DefaultTableModel) alumnos.getModel();
        mod.addRow(jeto);
    }

    private void nuevafila(Object[] jeto) {
        DefaultTableModel mod = (DefaultTableModel) actividades.getModel();
        mod.addRow(jeto);
    }

    private void nuevaactividad() {
        float falta=0;
        Object[] ob = new Object[4];

        ob[0] = nombre.getText();
        ob[1] = descrip.getText();
        ob[2] = ponde.getText();
        ob[3] = Math.round(prom);
        acumulado += Float.parseFloat(ponde.getText());
        if(acumulado<=100){
        acum.setText(acumulado+"/100");
        nuevafila(ob);
        }else{
        falta = (100 - (acumulado -= Float.parseFloat(ponde.getText())));
        JOptionPane.showMessageDialog(null, "Se llegó al máximo acumulado, la ponderación faltante es: "+falta);
        }
    }

    public String[][] archivoscsv(String ruta, int columna, int n) throws FileNotFoundException, IOException {
        int fila;
        String linea, lin;
        int i = 0;
        fila = 0;

        BufferedReader bf = new BufferedReader(new FileReader(ruta));
        BufferedReader bf2 = new BufferedReader(new FileReader(ruta));
        while ((linea = bf.readLine()) != null && fila <= n) {
            fila++;

        }
        String[][] matriz = new String[fila][columna];
        while ((lin = bf2.readLine()) != null && i <= n) {
            for (int j = 0; j < columna; j++) {
                String[] leer = lin.split(",");
                matriz[i][j] = leer[j];
            }
            i++;
        }

        return matriz;
    }
}
