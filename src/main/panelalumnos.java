package main;

import Alumnos.Alumno;
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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class panelalumnos extends JPanel {

    private JLabel label1;
    private JButton masiva, exportar;
    private static JTable tablalumnos;
    private  DefaultTableModel modalumnos;
    private JScrollPane scrolalumnos;
    private FileDialog fd;
    public static Alumno[] nuevoalumno = new Alumno[300];
    private  DefaultPieDataset dataset;
    private int conM = 0, conH = 0,x;

    public panelalumnos() {
        setLayout(null);
        label1 = new JLabel();
        setLayout(null);
        setBackground(Color.orange);
        label1.setBounds(10, 10, 150, 15);
        label1.setText("Listado Oficial Alumnos");
        componentes();
        add(label1);
    }

    private void botones() {
        masiva = new JButton("Carga Masiva");
        exportar = new JButton("Exportar Listado a PDF");

        masiva.setEnabled(true);
        exportar.setEnabled(true);

        masiva.setBounds(500, 25, 285, 25);
        exportar.setBounds(500, 55, 285, 25);

        add(masiva);
        add(exportar);
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
                try {
                    pdf();
                } catch (IOException ex) {
                    Logger.getLogger(panelalumnos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        exportar.addActionListener(acc2);

    }

    private void componentes() {
        botones();
        tabla();
        ColocarGrafica();
        CargaAlumno();
    }

    private void tabla() {
        modalumnos = new DefaultTableModel();
        modalumnos.addColumn("Codigo");
        modalumnos.addColumn("Nombre");
        modalumnos.addColumn("Apellido");
        modalumnos.addColumn("Correo");
        modalumnos.addColumn("Genero");
        tablalumnos = new JTable(modalumnos);
        scrolalumnos = new JScrollPane(tablalumnos);
        tablalumnos.setBounds(10, 30, 480, 425);
        scrolalumnos.setBounds(10, 30, 480, 425);
        //scroll.setEnabled(false);
        tablalumnos.setBackground(Color.gray);
        tablalumnos.setEnabled(false);

        add(scrolalumnos);

    }
    public static void anadirfila(Object[] dataRow) {
        DefaultTableModel mod = (DefaultTableModel) tablalumnos.getModel();
        mod.addRow(dataRow);
    }
    
    public void ColocarGrafica() {
        dataset = new DefaultPieDataset();
        JFreeChart graf = ChartFactory.createPieChart("Genero de Alumnos",dataset,false,true, false);
        ChartPanel contenedor = new ChartPanel(graf);
        contenedor.setBounds(500, 130, 315, 280);
        contenedor.setBackground(Color.WHITE);
        add(contenedor);
        validate();
    }

   

    private void pdf() throws IOException {

        String path = "ListadoAlumnos.pdf";
        PdfWriter lapiz = new PdfWriter(path);
        PdfDocument pdfDoc = new PdfDocument(lapiz);
        Document documento = new Document(pdfDoc);

        String texto = "Listado de Alumnos";
        Paragraph text = new Paragraph(texto);  
        documento.add(text);
        float[] pointColumnWidths = {150F, 150F, 150F, 150F, 150F};
        Table tabla = new Table(pointColumnWidths);
        tabla.addCell(new Cell().add(new Paragraph("Codigo")));
        tabla.addCell(new Cell().add(new Paragraph("Nombre")));
        tabla.addCell(new Cell().add(new Paragraph("Apellido")));
        tabla.addCell(new Cell().add(new Paragraph("Correo")));
        tabla.addCell(new Cell().add(new Paragraph("Género")));
        for (int i = 0; i < nuevoalumno.length; i++) {
            if (nuevoalumno[i] == null) {
                break;

            } else {
                tabla.addCell(new Cell().add(new Paragraph(nuevoalumno[i].getCodigo() + "")));
                tabla.addCell(new Cell().add(new Paragraph(nuevoalumno[i].getNombre())));
                tabla.addCell(new Cell().add(new Paragraph(nuevoalumno[i].getApellido())));
                tabla.addCell(new Cell().add(new Paragraph(nuevoalumno[i].getCorreo())));
                tabla.addCell(new Cell().add(new Paragraph(nuevoalumno[i].getGenero())));
            }
        }
        documento.add(tabla);
        documento.close();
        JOptionPane.showMessageDialog(null,"PDF Alumnos ha sido creado");

    }

    private void cargamasiva(String ruta) {
        Object[] jeto = new Object[5];
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
        Alumno[] cop = gson.fromJson(Json.toString(), Alumno[].class);
        for (int i = 0; i < cop.length; i++) {
            if (x<300){
            nuevoalumno[x] = new Alumno(cop[i].getCodigo(), cop[i].getNombre(), cop[i].getApellido(), cop[i].getCorreo(), cop[i].getGenero(), "1234");
            jeto[0] = nuevoalumno[x].getCodigo();
            jeto[1] = nuevoalumno[x].getNombre();
            jeto[2] = nuevoalumno[x].getApellido();
            jeto[3] = nuevoalumno[x].getCorreo();
            jeto[4] = nuevoalumno[x].getGenero();
            if (nuevoalumno[x].getGenero().equals("m")){
                conH++;
                dataset.setValue("Hombres",conH);
            }else if (nuevoalumno[x].getGenero().equals("f")){
                conM++;
                dataset.setValue("Mujeres",conM);
            }
            anadirfila(jeto);
            x++;
            }
        }
        Cargas.Serializar.serializar(nuevoalumno,"alumnos.bin",false);
    }
    private void CargaAlumno(){
        x=0;
        conH=0;
        conM=0;
        Alumno[] aux= (Alumno[]) Cargas.Serializar.getSerializable("alumnos.bin");
        Object[] objects = new Object[5];
        if (aux != null) {
            for (int i = 0; i < 50; i++) {
                if (aux[i]!=null){
                    objects[0] = aux[i].getCodigo();
                    objects[1] = aux[i].getNombre();
                    objects[2] = aux[i].getApellido();
                    objects[3] = aux[i].getCorreo();
                    objects[4] = aux[i].getGenero();
                    anadirfila(objects);
                    if (aux[i].getGenero().equals("m")){
                        conH++;
                        dataset.setValue("Hombres",conH);
                    }
                    else if (aux[i].getGenero().equals("f")){
                        conM++;
                        dataset.setValue("Mujeres",conM);
                    }
                    x++;
                }
            }
            nuevoalumno = aux;
        }
    }

}
