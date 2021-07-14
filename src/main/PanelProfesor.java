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
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    public DefaultTableModel model;
    private ListSelectionModel listSelectionModel;
    private FileDialog fd;
    public JScrollPane scroll;
    AgregarProfesores profesor = new AgregarProfesores();
    Actualizar act = new Actualizar();
    public static int conM = 0, conH = 0, selectedRow;
    public static DefaultPieDataset dataset;

    public PanelProfesor() {
        setLayout(null);
        setBackground(Color.orange);
        label1 = new JLabel();
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
                int contador=0;
                for (int i = 0; i < AgregarProfesores.profes.length; i++) {
                   if (AgregarProfesores.profes[i]==null){
                       break;
                   }else{
                    contador++;
                   }
                }
                if (contador==50){
                    JOptionPane.showMessageDialog(null, "No pueden haber mas de 50 profes");
                }else{
                profesor.setVisible(true);
                }
                
            }

        };
        crear.addActionListener(accion);//YA
        ActionListener accion2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                act.setVisible(true);
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
        exportar.addActionListener(accion3);//Ya
        ActionListener accion4 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fd = new FileDialog(new Frame(), "Abrir");
                fd.setVisible(true);
                if ("null".equals(fd.getDirectory())) {
                    JOptionPane.showMessageDialog(null, "Por favor seleccione un archivo");
                } else {
                    cargamasiva(fd.getDirectory() + fd.getFile());
                }
            }

        };
        masiva.addActionListener(accion4);//YA
        ActionListener accion5 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                for (int i = 0; i <AgregarProfesores.profes.length ; i++) {
                    if (AgregarProfesores.profes[i]==null){
                        continue;
                    }
                    if (AgregarProfesores.profes[i].getCodigo()==Integer.parseInt(tablaprofesor.getValueAt(selectedRow,0).toString())){
                        if (AgregarProfesores.profes[i].getGenero().equals("m"))
                            PanelProfesor.conH--;
                            PanelProfesor.aniadirGrafica(PanelProfesor.dataset,"Hombres",PanelProfesor.conH);
                        if (AgregarProfesores.profes[i].getGenero().equals("f"))
                            PanelProfesor.conM--;
                            PanelProfesor.aniadirGrafica(PanelProfesor.dataset,"Mujeres",PanelProfesor.conM);
                        AgregarProfesores.profes[i]= null;
                        reOrdenar(AgregarProfesores.profes,i);
                        AgregarProfesores.x--;
                        break;
                    }
                }
                Cargas.Serializar.serializar(AgregarProfesores.profes,"profesores.bin",false);
            }

        };
        eliminar.addActionListener(accion5);
    }

    private void componentes() {
        botones();
        tabla();
        ColocarGrafica();
        cargarGrafica();
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
        tablaprofesor.setBackground(Color.gray);
        add(scroll);
        listSelectionModel = tablaprofesor.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                    selectedRow = tablaprofesor.getSelectedRow();
                for (CrearProfesores prof: AgregarProfesores.profes) {
                    if (prof==null){
                        continue;
                    }
                    if (prof.getCodigo()==Integer.parseInt(tablaprofesor.getValueAt(selectedRow,0).toString())){
                        Actualizar.txtcodigo.setText(prof.getCodigo()+"");
                        Actualizar.txtnombre.setText(prof.getNombre());
                        Actualizar.txtapellido.setText(prof.getApellido());
                        Actualizar.txtcorreo.setText(prof.getCorreo());
                        Actualizar.txtcontra.setText(prof.getContraseña());
                        Actualizar.combo.setSelectedIndex(prof.getGenero().equals("m")?1:2);
                        break;
                    }
                }
            }
        });
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
    public void ColocarGrafica() {
        dataset = new DefaultPieDataset();

        JFreeChart graf = ChartFactory.createPieChart("Genero Profesores",dataset,false,true, false);

        ChartPanel contenedor = new ChartPanel(graf);
        contenedor.setBounds(500, 130, 315, 280);
        contenedor.setBackground(Color.WHITE);
        add(contenedor);
        validate();
    }
    public static void aniadirGrafica(DefaultPieDataset defaultPieDataset,String gen,int canti){
        defaultPieDataset.setValue(gen,canti);
    }
    private void pdf() throws IOException {
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
        for (int i = 0; i < AgregarProfesores.profes.length; i++) {
            if (AgregarProfesores.profes[i] == null) {
                break;

            } else {
                tabla.addCell(new Cell().add(new Paragraph(AgregarProfesores.profes[i].getCodigo() + "")));
                tabla.addCell(new Cell().add(new Paragraph(AgregarProfesores.profes[i].getNombre())));
                tabla.addCell(new Cell().add(new Paragraph(AgregarProfesores.profes[i].getApellido())));
                tabla.addCell(new Cell().add(new Paragraph(AgregarProfesores.profes[i].getCorreo())));
                tabla.addCell(new Cell().add(new Paragraph(AgregarProfesores.profes[i].getGenero())));
            }
        }
        documento.add(tabla);
        documento.close();
        JOptionPane.showMessageDialog(null,"PDF Creado con éxito");
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
        CrearProfesores[] cop = gson.fromJson(Json.toString(), CrearProfesores[].class);
        for (int i = 0; i < cop.length; i++) {
            je[0] = cop[i].getCodigo();
            je[1] = cop[i].getNombre();
            je[2] = cop[i].getApellido();
            je[3] = cop[i].getCorreo();
            je[4] = cop[i].getGenero();
            cop[i].setContraseña("1234");
            if (AgregarProfesores.x<50){
            if (cop[i].getGenero().equals("m"))
                conH++;
                aniadirGrafica(dataset,"Hombres",conH);
            if (cop[i].getGenero().equals("f"))
                conM++;
                aniadirGrafica(dataset,"Mujeres",conM);
            anadirfila(je);
            AgregarProfesores.profes[AgregarProfesores.x] = cop[i];
            AgregarProfesores.x++;
            }
        }
        Cargas.Serializar.serializar(AgregarProfesores.profes,"profesores.bin",false);
    }
    private void cargarGrafica(){
        AgregarProfesores.x=0;
        conH=0;
        conM=0;
        CrearProfesores[] aux= (CrearProfesores[]) Cargas.Serializar.getSerializable("profesores.bin");
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
                    aniadirGrafica(dataset,"Hombres",conH);
                }
                else if (aux[i].getGenero().equals("f")){
                    conM++;
                    aniadirGrafica(dataset,"Mujeres",conM);
                }
                AgregarProfesores.x++;
            }
        }
        AgregarProfesores.profes = aux;
        }
    }
    private void reOrdenar(CrearProfesores[] arr,int lim){
        for (int i = lim; i<arr.length-1 ; i++) {
            arr[i]=arr[i+1];
            arr[arr.length-1] = null;
        }
    }
}
