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

    public DocumentSave fileSave(DocumentSave doc, JTextArea texto) throws IOException {

        String text;
        if (texto.getText() != null) {
            text = texto.getText();
            doc.setContenido(text);
        } else {
            text = "";
        }
        File archivo = doc.getRuta();
        try (FileWriter escritor = new FileWriter(archivo)) {
            escritor.write(text);
            JOptionPane.showMessageDialog(null, "Archivo guardado", "Información", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return this.doc;
    }


    public DocumentSave fileSaveAs(JTextArea texto ) throws IOException {

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

    public DocumentSave openFile(JTextArea texto) throws IOException {

        JFileChooser selector = new JFileChooser();
        selector.setCurrentDirectory(new File(System.getProperty("user.home")));
        selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
        selector.addChoosableFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));
        selector.addChoosableFileFilter(new FileNameExtensionFilter("Clases java (*.java)", "java"));
        selector.setAcceptAllFileFilterUsed(false);

        int option = selector.showOpenDialog(texto);
        File archivo = selector.getSelectedFile();


        if (option == JFileChooser.APPROVE_OPTION) {

            DocumentSave doc = new DocumentSave();
            doc.setRuta(archivo);
            doc.setNombreArchivo(archivo.getName());

            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(archivo)));
            String line = br.readLine();
            String textonuevo = "";
            while (line != null) {
                textonuevo = textonuevo + line + "\n";
                line = br.readLine();
            }
            doc.setContenido(textonuevo);
            texto.setText(doc.getContenido());
            return doc;
        } else {
            JOptionPane.showMessageDialog(null, "Archivo no valido", "Información", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    }





