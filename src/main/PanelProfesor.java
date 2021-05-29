package main;

import com.google.gson.Gson;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class PanelProfesor extends JPanel {

    private JLabel label1;
    private JPanel grafica;
    private JButton crear, masiva, actualizar, eliminar, exportar, cerrar;
    private static JTable tablaprofesor;
    public DefaultTableModel model, model2;
    private FileDialog fd;
    public JScrollPane scroll, scr;
    AgregarProfesores profesor = new AgregarProfesores();
    Actualizar act = new Actualizar();
    Principal pi = new Principal();
    private int conM = 0, conH = 0;
    int mprofesint;

    public PanelProfesor() {
        setLayout(null);
        label1 = new JLabel();
        setLayout(null);
        setBackground(Color.orange);
        label1.setBounds(10, 10, 150, 15);
        label1.setText("Listado Oficial Profesor");

        componentes();

        add(label1);

    }

    private void botones() {
        crear = new JButton("Crear");
        masiva = new JButton("Carga Masiva");
        actualizar = new JButton("Actualizar");
        eliminar = new JButton("Eliminar");
        exportar = new JButton("Exportar Listado a PDF");
        cerrar = new JButton("Cerrar Sesión");

        crear.setEnabled(true);
        masiva.setEnabled(true);
        actualizar.setEnabled(true);
        eliminar.setEnabled(true);
        exportar.setEnabled(true);
        cerrar.setEnabled(true);

        crear.setBounds(500, 20, 125, 25);
        masiva.setBounds(660, 20, 125, 25);
        actualizar.setBounds(500, 55, 125, 25);
        eliminar.setBounds(660, 55, 125, 25);
        exportar.setBounds(500, 90, 285, 25);
        cerrar.setBounds(500, 425, 285, 25);

        add(crear);
        add(masiva);
        add(actualizar);
        add(eliminar);
        add(exportar);
        add(cerrar);

        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                profesor.setVisible(true);
            }

        };
        crear.addActionListener(accion);
        ActionListener accion2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                act.setVisible(true);
                //profesor.actualizar(0);
            }

        };
        actualizar.addActionListener(accion2);
        ActionListener accion3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    pdf();
                } catch (IOException ex) {
                    Logger.getLogger(PanelProfesor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        };
        exportar.addActionListener(accion3);
        ActionListener accion4 = new ActionListener() {
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
        masiva.addActionListener(accion4);
        ActionListener accion5 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
//                Ventana venta = new Ventana();
//
//                Admin ad = new Admin();
//                ad.cerrar();
//                venta.setVisible(true);
            }

        };
        cerrar.addActionListener(accion5);
    }

    private void componentes() {
        botones();
        tabla();
        ColocarGrafica();

    }

    private void tabla() {
        model = new DefaultTableModel();
        model.addColumn("Codigo");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Correo");
        model.addColumn("Genero");
        tablaprofesor = new JTable(model);
        scroll = new JScrollPane(tablaprofesor);
        tablaprofesor.setBounds(10, 30, 480, 425);
        scroll.setBounds(10, 30, 480, 425);
        //scroll.setEnabled(false);
        tablaprofesor.setBackground(Color.gray);
        tablaprofesor.setEnabled(false);

        add(scroll);

    }

    public static void anadirfila(Object[] dataRow) {
        DefaultTableModel mod = (DefaultTableModel) tablaprofesor.getModel();
        mod.addRow(dataRow);
    }

    public static void actualizarfila(String c, String n, String a, String co, String g) {
        int fila = 0;
        DefaultTableModel mod2 = (DefaultTableModel) tablaprofesor.getModel();
        String[] info = new String[5];
        info[0] = c;
        info[1] = n;
        info[2] = a;
        info[3] = co;
        info[4] = g;
        for (int i = 0; i < 50; i++) {
            if (c.equals(String.valueOf(tablaprofesor.getValueAt(i, 0)))) {
                fila = i;
                break;
            }

        }

        for (int i = 0; i < tablaprofesor.getColumnCount(); i++) {
            mod2.setValueAt(info[i], fila, i);

        }
    }

    private void panelgrapie() {
        grafica = new JPanel();
        grafica.setLayout(null);
        grafica.setBounds(500, 130, 250, 250);
        add(grafica);
    }

    @SuppressWarnings("deprecation")
    public void ColocarGrafica() {

        try {
            contar2();
        } catch (IOException ex) {
            Logger.getLogger(PanelProfesor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PanelProfesor.class.getName()).log(Level.SEVERE, null, ex);
        }

        int REPm = conH;
        int REPf = conM;

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Mujeres", (REPm));
        dataset.setValue("Hombres", (REPf));

        JFreeChart graf = ChartFactory.createPieChart("Genero Profesores",dataset,false,true, false);

        ChartPanel contenedor = new ChartPanel(graf);
        contenedor.setBounds(500, 130, 315, 280);
        contenedor.setBackground(Color.WHITE);
        add(contenedor);
        validate();
    }

    public void contar2() throws IOException, ClassNotFoundException {
        int i = 0;
        try {
            File ar = new File("objetos.bin");

            if (ar.exists()) {

                FileInputStream fs = new FileInputStream("objetos.bin");
                ObjectInputStream oi;
                while (fs.available() > 0) {
                    if (i == 50) {
                        break;
                    }
                    oi = new ObjectInputStream(fs);
                    profesor.profes[i] = (CrearProfesores) oi.readObject();
                    if ("m".equals(profesor.profes[i].getGenero())) {
                        conM++; 
                    } else {
                        conH++;
                    }
                    i++;
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void pdf() throws IOException {
        try {
            contar2();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PanelProfesor.class.getName()).log(Level.SEVERE, null, ex);
        }
        String path = "ListadoProfesores.pdf";
        PdfWriter lapiz = new PdfWriter(path); 
        PdfDocument pdfDoc = new PdfDocument(lapiz);
        Document documento = new Document(pdfDoc);
        String texto = "Listado de Profesores";
        Paragraph text = new Paragraph(texto);  
        documento.add(text);
        
        float[] pointColumnWidths = {150F, 150F, 150F, 150F, 150F};
        Table tabla = new Table(pointColumnWidths);
        tabla.addCell(new Cell().add(new Paragraph("Codigo")));
        tabla.addCell(new Cell().add(new Paragraph("Nombre")));
        tabla.addCell(new Cell().add(new Paragraph("Apellido")));
        tabla.addCell(new Cell().add(new Paragraph("Correo")));
        tabla.addCell(new Cell().add(new Paragraph("Genero")));
        for (int i = 0; i < profesor.profes.length; i++) {
            if (profesor.profes[i] == null) {
                break;

            } else {
                tabla.addCell(new Cell().add(new Paragraph(profesor.profes[i].getCodigo() + "")));
                tabla.addCell(new Cell().add(new Paragraph(profesor.profes[i].getNombre())));
                tabla.addCell(new Cell().add(new Paragraph(profesor.profes[i].getApellido())));
                tabla.addCell(new Cell().add(new Paragraph(profesor.profes[i].getCorreo())));
                tabla.addCell(new Cell().add(new Paragraph(profesor.profes[i].getGenero())));
            }
        }
        documento.add(tabla);
        documento.close();
        System.out.println("PDF Creado");

    }

    private void cargamasiva(String ruta) {
        Object[] je = new Object[5];
        BufferedReader br = null;
        String Json = "";
        try {
            br = new BufferedReader(new FileReader(ruta));

            String linea;
            while ((linea = br.readLine()) != null) {
                Json += linea;
            }
            br.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(panelcursos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(panelcursos.class.getName()).log(Level.SEVERE, null, ex);
        }

        //System.out.println(Json);
        Gson gson = new Gson();
        CrearProfesores[] cop = gson.fromJson(Json, CrearProfesores[].class);
        for (int i = 0; i < cop.length; i++) {
            je[0] = cop[i].getCodigo();
            je[1] = cop[i].getNombre();
            je[2] = cop[i].getApellido();
            je[3] = cop[i].getCorreo();
            je[4] = cop[i].getGenero();
            anadirfila(je);

        }
        profesor.igualar(cop);
    }

}
