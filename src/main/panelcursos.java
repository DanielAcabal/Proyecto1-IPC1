package main;

import Cursos.ActualizarCursos;
import Cursos.Cursos;
import Cursos.AgregarCursos;
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
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class panelcursos extends JPanel {

    private JLabel label1;
    private JButton crear, masiva, actualizar, eliminar, exportar;
    private static JTable tablacursos;
    public DefaultTableModel modCursos;
    public JScrollPane scursos;
    private ListSelectionModel listSelectionModel;
    public static int selectedRow;
    public panelcursos() {
        setLayout(null);
        label1 = new JLabel();
        setBackground(Color.orange);
        label1.setText("Listado Oficial Cursos");
        label1.setBounds(10, 10, 150, 15);
        componentes();
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
                int contador=0;
                for (int i = 0; i < AgregarCursos.nuevocurso.length; i++) {
                   if (AgregarCursos.nuevocurso[i]==null){
                       break;
                   }else{
                    contador++;
                   }
                }
                if (contador==50){
                    JOptionPane.showMessageDialog(null, "No pueden haber mas de 50 cursos");
                }else{

                    AgregarCursos agcursos = new AgregarCursos();
                    agcursos.setVisible(true);
                }
            }

        };
        crear.addActionListener(accion);//YA
        ActionListener accion2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ActualizarCursos actcursos = new ActualizarCursos();
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
        exportar.addActionListener(accion3);//YA
        ActionListener accion4 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FileDialog fda = new FileDialog(new JFrame(), "Abrir");
                fda.setVisible(true);
                if ("null".equals(fda.getDirectory())) {
                    JOptionPane.showMessageDialog(null, "Por favor seleccione un archivo");
                } else {
                    cargamasiva(fda.getDirectory() + fda.getFile());

                }
            }

        };
        masiva.addActionListener(accion4);//YA
    }

    private void componentes() {
        botones();
        tabla();
        grafica();
        CargaCurso();
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
        tablacursos.setBackground(Color.gray);
        add(scursos);
        listSelectionModel = tablacursos.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow = tablacursos.getSelectedRow();
               for (Cursos curso: AgregarCursos.nuevocurso) {
                    if (curso!=null){
                    if (curso.getCodigo()==Integer.parseInt(tablacursos.getValueAt(selectedRow,0).toString())) {
                        /*ActualizarCursos.txtcodigo.setText(curso.getCodigo() + "");
                        ActualizarCursos.txtnombre.setText(curso.getNombre());
                        ActualizarCursos.txtcreditos.setText(curso.getCreditos() + "");*/
                        System.out.println(ActualizarCursos.txtcodigo);
                        break;
                        }
                        }
                    }
                }
        });
    }

    public static void anadirfila(Object[] dataRow) {
        DefaultTableModel mod = (DefaultTableModel) tablacursos.getModel();
        mod.addRow(dataRow);
    }

    private void pdf() throws IOException {
        String prof="";
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
        for (int i = 0; i < AgregarCursos.nuevocurso.length; i++) {
            if (AgregarCursos.nuevocurso[i] != null) {
                tabla.addCell(new Cell().add(new Paragraph(AgregarCursos.nuevocurso[i].getCodigo() + "")));
                tabla.addCell(new Cell().add(new Paragraph(AgregarCursos.nuevocurso[i].getNombre())));
                tabla.addCell(new Cell().add(new Paragraph(AgregarCursos.nuevocurso[i].getCreditos() + "")));
                tabla.addCell(new Cell().add(new Paragraph(AgregarCursos.nuevocurso[i].getAlumnos() + "")));
                for (int j = 0; j < AgregarProfesores.profes.length; j++) {
                    if (AgregarProfesores.profes[j] != null) {
                        if (AgregarCursos.nuevocurso[i].getProfesores() == AgregarProfesores.profes[j].getCodigo()) {
                           prof = AgregarProfesores.profes[j].getNombre() + " " + AgregarProfesores.profes[j].getApellido();
                            break;
                        }else{
                            prof = "Nadie" ;
                        }
                    }
                }
                tabla.addCell(new Cell().add(new Paragraph(prof)));
            }
        }
        documento.add(tabla);
        documento.close();
        JOptionPane.showMessageDialog(null,"PDF Listado de Curso Creado");

    }

    private void cargamasiva(String ruta) {
        Object[] je = new Object[5];
        BufferedReader br;
        StringBuilder Json = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(ruta));

            String linea;
            while ((linea = br.readLine()) != null) {
                Json.append(linea);
            }
            br.close();

        } catch (IOException ex) {
            Logger.getLogger(panelcursos.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();
        Cursos[] cop = gson.fromJson(Json.toString(), Cursos[].class);
        for (int i = 0; i < cop.length; i++) {
            je[0] = cop[i].getCodigo();
            je[1] = cop[i].getNombre();
            je[2] = cop[i].getCreditos();
            je[3] = 0;
            for (int j = 0; j < AgregarProfesores.profes.length; j++) {
                if (AgregarProfesores.profes[j] != null) {
                    if (cop[i].getProfesores() == AgregarProfesores.profes[j].getCodigo()) {
                        je[4] = AgregarProfesores.profes[j].getNombre() + " " + AgregarProfesores.profes[j].getApellido();
                        break;
                    }else{
                        je[4] = "Nadie";
                    }
                }
            }
            if (AgregarCursos.x<50){
                AgregarCursos.nuevocurso[AgregarCursos.x] = cop[i];
                AgregarCursos.x++;
                anadirfila(je);
            }
        }
        Cargas.Serializar.serializar(AgregarCursos.nuevocurso,"cursos.bin",false);
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
    private void CargaCurso(){
        AgregarCursos.x=0;
        Cursos[] aux= (Cursos[]) Cargas.Serializar.getSerializable("cursos.bin");
        Object[] objects = new Object[5];
        if (aux != null) {
            for (int i = 0; i < 50; i++) {
                if (aux[i]!=null){
                    objects[0] = aux[i].getCodigo();
                    objects[1] = aux[i].getNombre();
                    objects[2] = aux[i].getCreditos();
                    objects[3] = aux[i].getAlumnos();
                    for (int j = 0; j < AgregarProfesores.profes.length; j++) {
                        if (AgregarProfesores.profes[j] != null) {
                            if (aux[i].getProfesores() == AgregarProfesores.profes[j].getCodigo()) {
                                objects[4] = AgregarProfesores.profes[j].getNombre() + " " + AgregarProfesores.profes[j].getApellido();
                                break;
                            }else{
                                objects[4] = "Nadie";
                            }
                        }
                    }
                    anadirfila(objects);
                    AgregarCursos.x++;
                }
            }
            AgregarCursos.nuevocurso = aux;
        }
    }

}
