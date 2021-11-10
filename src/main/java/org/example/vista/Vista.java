package org.example.vista;

import org.example.model.DocumentSave;
import org.example.model.Servicios;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Vista extends JFrame {
    private JPanel panelPrincipal;
    private JSplitPane panelEditorConsola;
    private JSplitPane panelArchivoEditor;

    private JMenuBar menu;
    private JMenu fileMenuOption;
    private JMenu editMenuOption;
    private JMenu helpMenuOption;
    private JMenuItem fileNewOption;
    private JMenuItem fileOpenOption;
    private JMenuItem fileSaveOption;
    private JMenuItem fileSaveAsOption;
    private JMenuItem fileExitOption;
    private JTextArea textAreaCentral;
    private JTree explorer;
    private JTextArea console;
    private JTextArea textoNorte;
    private Servicios servicio;

    private DocumentSave doc;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JTree tree;


    public Vista() {
        initComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panelPrincipal.setLayout(new BorderLayout());
        this.add(panelPrincipal);

        setPreferredSize(new Dimension(800, 500));
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panelPrincipal.add(panelEditorConsola, BorderLayout.CENTER);

        panelPrincipal.add(textoNorte, BorderLayout.NORTH);


    }

    public void initComponents() {
        panelPrincipal = new JPanel();
        textoNorte = new JTextArea();
        servicio = new Servicios();
        doc= new DocumentSave();
        createdMenu();
        createdContextualMenu();
        createdCentralPanel();
    }

    public void createdMenu() {

        menu = new JMenuBar();
        fileMenuOption = new JMenu();
        editMenuOption = new JMenu();
        helpMenuOption = new JMenu();
        fileNewOption = new JMenuItem();
        fileOpenOption = new JMenuItem();
        fileSaveOption = new JMenuItem();
        fileSaveAsOption = new JMenuItem();
        fileExitOption = new JMenuItem();

        fileMenuOption.setText("File");
        editMenuOption.setText("Edit");
        helpMenuOption.setText("Help");

        fileNewOption.setText("New");
        fileOpenOption.setText("Open");
        fileSaveOption.setText("Save");
        fileSaveAsOption.setText("Save as");
        fileExitOption.setText("Exit");

        fileMenuOption.add(fileNewOption);
        fileMenuOption.add(fileOpenOption);
        fileMenuOption.add(fileSaveOption);
        fileMenuOption.add(fileSaveAsOption);
        fileMenuOption.add(fileExitOption);


        menu.add(fileMenuOption);
        menu.add(editMenuOption);
        menu.add(helpMenuOption);

        this.setJMenuBar(menu);


    }

    public void createdContextualMenu() {

        //Escuchador boton Save as.
        fileSaveAsOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    servicio.fileSaveAs(textAreaCentral);
                } catch (IOException ex) {
                    System.out.println("No se ha podido ejecutar el metodo fileSaveAs");
                }
            }
        });

        //Escuchador boton Save.
        fileSaveOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if(doc== null) {
                        servicio.fileSaveAs(textAreaCentral);
                    }
                    else {


                    }
                } catch (IOException ex) {
                    System.out.println("No se ha podido ejecutar el metodo fileSave");
                }
            }
        });

        //Escuchador boton Open.
        fileOpenOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    servicio.openFile(textAreaCentral);
                } catch (IOException ex) {
                    System.out.println("No se ha podido ejecutar el metodo openFile");
                }
            }
        });

    }

    public void createdCentralPanel() {

        textAreaCentral = new JTextArea();
        explorer = new JTree();
        console = new JTextArea();

        panelArchivoEditor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                explorer, textAreaCentral);
        panelEditorConsola = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                panelArchivoEditor, console);

        panelEditorConsola.setOneTouchExpandable(true);
        panelEditorConsola.setDividerLocation(500);
        panelArchivoEditor.setOneTouchExpandable(true);
        panelArchivoEditor.setDividerLocation(200);
        //Provide minimum sizes for the two components in the split pane
        Dimension minimumSize = new Dimension(50, 50);
        Dimension sizeExplorer = new Dimension(10, 100);

        textAreaCentral.setMinimumSize(minimumSize);
        console.setMinimumSize(minimumSize);
        explorer.setMinimumSize(sizeExplorer);

    }

    public void createdExplorer() {
        File fileRoot = new File("C:/");
        //root = new DefaultMutableTreeNode(new FileNode(fileRoot));
        treeModel = new DefaultTreeModel(root);

    }


}
