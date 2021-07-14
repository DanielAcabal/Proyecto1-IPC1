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
    public DefaultTableModel modalumnos;
    public JScrollPane scrolalumnos;
    private FileDialog fd;
    public Alumno[] nuevoalumno = new Alumno[300];
    Alumno aux;
    private int conM = 0, conH = 0;

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

        JFreeChart graf = ChartFactory.createPieChart("Genero de Alumnos",dataset,false,true, false);

        ChartPanel contenedor = new ChartPanel(graf);
        contenedor.setBounds(500, 130, 315, 280);
        contenedor.setBackground(Color.WHITE);
        add(contenedor);
        validate();
    }

    public void contar2() throws IOException, ClassNotFoundException {
        int i = 0;
        try {
            File archivo = new File("Alumnos.bin");
            if (archivo.exists()){
            FileInputStream fs = new FileInputStream("Alumnos.bin");
            ObjectInputStream oi;
            while (fs.available() > 0) {
                oi = new ObjectInputStream(fs);
                nuevoalumno[i] = (Alumno) oi.readObject();
                if ("m".equals(nuevoalumno[i].getGenero())) {

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
        System.out.println("PDF Creado");

    }

    private void cargamasiva(String ruta) {
        Object[] jeto = new Object[5];
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
        Alumno[] cop = gson.fromJson(Json, Alumno[].class);
        for (int i = 0; i < cop.length; i++) {
            if (i == nuevoalumno.length) {
                JOptionPane.showMessageDialog(null, "Limite de alumnos alcanzado");
                break;
            }
            nuevoalumno[i] = new Alumno(cop[i].getCodigo(), cop[i].getNombre(), cop[i].getApellido(), cop[i].getCorreo(), cop[i].getGenero(), "1234");
            aux = nuevoalumno[i];
            jeto[0] = cop[i].getCodigo();
            jeto[1] = cop[i].getNombre();
            jeto[2] = cop[i].getApellido();
            jeto[3] = cop[i].getCorreo();
            jeto[4] = cop[i].getGenero();
            anadirfila(jeto);
            try {
                ser2(aux);
            } catch (IOException ex) {
                Logger.getLogger(panelalumnos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void ser2(Alumno objetos) throws FileNotFoundException, IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Alumnos.bin",true));
        os.writeObject(objetos); 
        os.close();
    }

}
