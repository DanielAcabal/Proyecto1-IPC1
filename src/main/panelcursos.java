package main;

import Cursos.ActualizarCursos;
import Cursos.AgregarCursos;
import Cursos.Cursos;
import com.google.gson.Gson;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.awt.Color;
import java.awt.FileDialog;
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
import javax.swing.table.DefaultTableModel;
import static main.Principal.ag;
import static main.Principal.agcursos1;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class panelcursos extends JPanel {

    private JLabel label1;
    private JButton crear, masiva, actualizar, eliminar, exportar;
    private static JTable tablacursos;
    public DefaultTableModel modCursos;
    public JScrollPane scursos;
    AgregarCursos agcursos = new AgregarCursos();
    ActualizarCursos actcursos = new ActualizarCursos();
    Principal prin = new Principal();

    public panelcursos() {
        setLayout(null);
        label1 = new JLabel();
        setBackground(Color.orange);
        label1.setText("Listado Oficial Cursos");
        label1.setBounds(10, 10, 150, 15);
        componentes();
        //agcursos.igual();
        add(label1);
    }

    private void botones() {
        crear = new JButton("Crear");
        masiva = new JButton("Carga Masiva");
        actualizar = new JButton("Actualizar");
        eliminar = new JButton("Eliminar");
        exportar = new JButton("Exportar Listado a PDF");

        crear.setEnabled(true);
        masiva.setEnabled(true);
        actualizar.setEnabled(true);
        eliminar.setEnabled(true);
        exportar.setEnabled(true);

        crear.setBounds(500, 20, 125, 25);
        masiva.setBounds(660, 20, 125, 25);
        actualizar.setBounds(500, 55, 125, 25);
        eliminar.setBounds(660, 55, 125, 25);
        exportar.setBounds(500, 90, 285, 25);

        add(crear);
        add(masiva);
        add(actualizar);
        add(eliminar);
        add(exportar);

        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    prin.carnt();
                } catch (IOException ex) {
                    Logger.getLogger(AgregarCursos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AgregarCursos.class.getName()).log(Level.SEVERE, null, ex);
                }
                agcursos.setVisible(true);
            }

        };
        crear.addActionListener(accion);
        ActionListener accion2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actcursos.setVisible(true);
            }

        };
        actualizar.addActionListener(accion2);
        ActionListener accion3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    pdf();
                } catch (IOException ex) {
                    Logger.getLogger(panelcursos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        exportar.addActionListener(accion3);
        ActionListener accion4 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FileDialog fda = new FileDialog(new JFrame(), "Abrir");
                fda.setVisible(true);
                System.out.println(fda.getDirectory());
                if ("null".equals(fda.getDirectory())) {
                    JOptionPane.showMessageDialog(null, "Por favor seleccione un archivo");
                } else {
                    // JOptionPane.showMessageDialog(null,fda.getDirectory()+fda.getFile());
                    cargamasiva(fda.getDirectory() + fda.getFile());

                }
            }

        };
        masiva.addActionListener(accion4);
    }

    private void componentes() {
        botones();
        tabla();
        grafica();
    }

    private void tabla() {
        modCursos = new DefaultTableModel();
        modCursos.addColumn("Codigo");
        modCursos.addColumn("Nombre");
        modCursos.addColumn("Creditos");
        modCursos.addColumn("Alumnos");
        modCursos.addColumn("Profesor");
        tablacursos = new JTable(modCursos);
        scursos = new JScrollPane(tablacursos);
        tablacursos.setBounds(10, 30, 480, 425);
        scursos.setBounds(10, 30, 480, 425);
        //scroll.setEnabled(false);
        tablacursos.setBackground(Color.gray);
        tablacursos.setEnabled(false);

        add(scursos);

    }

    public static void anadirfila(Object[] dataRow) {
        DefaultTableModel mod = (DefaultTableModel) tablacursos.getModel();
        mod.addRow(dataRow);
    }

    private void pdf() throws IOException {

        int conta = 0;
        String path = "ListadoCursos.pdf";
        PdfWriter lapiz = new PdfWriter(path);

        // Creating a PdfDocument       
        PdfDocument pdfDoc = new PdfDocument(lapiz);

        // Creating a Document        
        Document documento = new Document(pdfDoc);

        String texto = "Listado de Cursos";
        Paragraph text = new Paragraph(texto);
        // Adding area break to the PDF       
        documento.add(text);
        float[] pointColumnWidths = {150F, 150F, 150F, 150F, 150F};
        Table tabla = new Table(pointColumnWidths);
        tabla.addCell(new Cell().add(new Paragraph("Codigo")));
        tabla.addCell(new Cell().add(new Paragraph("Nombre")));
        tabla.addCell(new Cell().add(new Paragraph("Creditos")));
        tabla.addCell(new Cell().add(new Paragraph("Alumnos")));
        tabla.addCell(new Cell().add(new Paragraph("Profesor")));
        for (int i = 0; i < agcursos1.nuevocurso.length; i++) {
            if (agcursos1.nuevocurso[i] == null) {
                break;

            } else {
                tabla.addCell(new Cell().add(new Paragraph(agcursos1.nuevocurso[i].getCodigo() + "")));
                tabla.addCell(new Cell().add(new Paragraph(agcursos1.nuevocurso[i].getNombre())));
                tabla.addCell(new Cell().add(new Paragraph(agcursos1.nuevocurso[i].getCreditos() + "")));
                tabla.addCell(new Cell().add(new Paragraph(agcursos1.nuevocurso[i].getAlumnos() + "")));
                for (int j = 0; j < agcursos1.nuevocurso.length; j++) {
                    if (ag.profes[j] == null || agcursos1.nuevocurso[i] == null) {
                        break;
                    } else if (agcursos1.nuevocurso[i].getProfesores() == ag.profes[j].getCodigo()) {

                        conta = j;
                        break;
                    }

                }
                tabla.addCell(new Cell().add(new Paragraph(ag.profes[conta].getNombre() + " " + ag.profes[conta].getApellido())));
            }
        }
        documento.add(tabla);
        documento.close();
        System.out.println("PDF Creado");

    }

    private void cargamasiva(String ruta) {
        try {
            prin.car();
        } catch (IOException ex) {
            Logger.getLogger(panelcursos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(panelcursos.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object[] je = new Object[5];
        BufferedReader br = null;
        int cod = 0;
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

        System.out.println(Json);
        Gson gson = new Gson();
        Cursos[] cop = gson.fromJson(Json, Cursos[].class);
        for (int i = 0; i < cop.length; i++) {
            je[0] = cop[i].getCodigo();
            je[1] = cop[i].getNombre();
            je[2] = cop[i].getCreditos();
            je[3] = 0;
            for (int j = 0; j < ag.profes.length; j++) {
                if (ag.profes[j] == null) {
                    break;
                }
                if (cop.length == 50) {
                    JOptionPane.showMessageDialog(null, "Límite de cursos alcanzado");
                    break;
                }

                if (cop[i].getProfesores() == ag.profes[j].getCodigo()) {
                    //System.out.println("Suma cod");
                    cod = j;
                }

            }
            //System.out.println(cod);
            je[4] = ag.profes[cod].getNombre() + " " + ag.profes[cod].getApellido();
            anadirfila(je);
            //System.out.println(ag.profes[cod].getNombre() + " " + ag.profes[cod].getApellido());
            //agcursos.igual(cop);
            //System.out.println("obj " + i + " " + cop);
        }
        agcursos.igual(cop);
    }

    public static void actualizarfila(String c, String n, String a, String al, String co) {
        int fila = 0;
        DefaultTableModel mod2 = (DefaultTableModel) tablacursos.getModel();
        String[] info = new String[5];
        info[0] = c;
        info[1] = n;
        info[2] = a;
        info[3] = al;
        info[4] = co;
        for (int i = 0; i < 50; i++) {
            if (c.equals(String.valueOf(tablacursos.getValueAt(i, 0)))) {
                fila = i;
                break;
            }

        }

        for (int i = 0; i < tablacursos.getColumnCount(); i++) {
            mod2.setValueAt(info[i], fila, i);

        }
    }

    private void grafica() {
//        try {
//            prin.cargaCursos();
//        } catch (IOException ex) {
//            Logger.getLogger(panelcursos.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(panelcursos.class.getName()).log(Level.SEVERE, null, ex);
//        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        for (int i = 0; i < 3; i++) {
//            System.out.println("grafica");
//          dataset.setValue(Integer.parseInt(tablacursos.getValueAt(i, 3).toString()),tablacursos.getValueAt(i, 1).toString(),tablacursos.getValueAt(i, 2).toString());
//          
//        }
//        dataset.setValue(5, "Hola", "Hola");
//        dataset.setValue(7, "sd", "asdf");
//        dataset.setValue(1, "Otro", "otro curso");

        JFreeChart graf = ChartFactory.createBarChart3D("Top 3 - Cursos con más estudiantes", "Curso", "Cantidad", dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel contenedor = new ChartPanel(graf);
        contenedor.setBounds(500, 130, 315, 280);
        contenedor.setBackground(Color.WHITE);
        add(contenedor);
        validate();
    }

}
