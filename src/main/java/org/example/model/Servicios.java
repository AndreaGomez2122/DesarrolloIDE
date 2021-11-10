package org.example.model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import java.awt.*;
import java.io.*;

public class Servicios extends Component {

    private File archivo;
    boolean saved = false;
    private File archivoGuardado;
    private DocumentSave doc = new DocumentSave();

    public Servicios() {
    }

    public void fileSave(DocumentSave doc, JTextArea texto) throws IOException {



    }


    public DocumentSave fileSaveAs(JTextArea texto) throws IOException {

        String text;
        if (texto.getText() != null) {
            text = texto.getText();
            this.doc.setContenido(text);
        } else {
            text = null;
        }
        JFileChooser selector = new JFileChooser();
        selector.setCurrentDirectory(new File(System.getProperty("user.home")));
        selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
        selector.addChoosableFileFilter(new FileNameExtensionFilter("Archivos de texto(*.txt)", "txt"));
        selector.setAcceptAllFileFilterUsed(false);

        int opcion = selector.showSaveDialog(this);
        archivo = selector.getSelectedFile();

        if (opcion == JFileChooser.APPROVE_OPTION) {
            if (!archivo.getName().endsWith(".txt")) {
                File archivoTxt = new File(archivo.getPath() + ".txt");
                archivo = archivoTxt;

            }
            if (archivo != null) {
                if (!archivo.exists()) {
                    try (FileWriter escritor = new FileWriter(archivo)) {
                        escritor.write(texto.getText());
                        JOptionPane.showMessageDialog(null, "Archivo guardado", "Información", JOptionPane.INFORMATION_MESSAGE);
                        this.doc.setRuta(archivo);
                        this.doc.setNombreArchivo(archivo.getName());


                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al guardar el archivo", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El archivo " + archivo.getName()
                            + " ya existe, seleccione otro nombre para el archivo", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        return this.doc;
    }

    public void openFile(JTextArea areaTexto) throws IOException {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Archivos de texto", ".txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Has elegido este archivo " +
                    chooser.getSelectedFile().getName());
        }

        FileReader fr = null;
        BufferedReader entrada = null;
        try {
            fr = new FileReader(chooser.getSelectedFile().getPath());
            entrada = new BufferedReader(fr);

            while (entrada.ready()) {
                areaTexto.setText(entrada.readLine());

            }
            saved = true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();

                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }


}




