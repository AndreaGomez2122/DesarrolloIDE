package org.example.vista;

import org.example.model.DocumentSave;
import org.example.model.Servicios;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Vista extends JFrame implements TreeExpansionListener {
    //PANELES
    private JPanel panelPrincipal;
    private JSplitPane panelEditorConsola;
    private JSplitPane panelArchivoEditor;

    private JPanel tools;

    //ELEMENTOS DEL MENÚ
    private JMenuBar menu;
    private JMenu fileMenuOption;
    private JMenu editMenuOption;
    private JMenu helpMenuOption;
    //BOTONES FILE
    private JMenuItem fileNewOption;
    private JMenuItem fileOpenOption;
    private JMenuItem fileSaveOption;
    private JMenuItem fileSaveAsOption;
    private JMenuItem fileExitOption;
    private JMenuItem fileprintOption;

    //BOTONES EDIT
    private JMenuItem editDeshacer;
    private JMenuItem editCopiar;
    private JMenuItem editPegar;
    private JMenuItem editEliminar;


    //BOTONES HELP
    private JMenuItem about;
    private JMenuItem help_view;

    private Clipboard portaPapeles;
    private UndoManager manager;


    private JTextArea textAreaCentral;
    private JTextArea console;

    private Servicios servicio;
    private DocumentSave doc = null;

    //ELEMENTOS DEL ARBOL DE DIRECTORIOS
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JTree tree;


    private JScrollPane jspTextAreaCentral;
    private JScrollPane jspConsole;
    private JScrollPane jspExplorador;


    public Vista() {
        initComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panelPrincipal.setLayout(new BorderLayout());
        this.add(panelPrincipal);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panelPrincipal.add(panelEditorConsola, BorderLayout.CENTER);
    }

    public void initComponents() {
        panelPrincipal = new JPanel();
        servicio = new Servicios();
        doc = new DocumentSave();
        createdCentralPanel();
        createdMenu();
        createdContextualMenu();

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
        fileprintOption = new JMenuItem();

        editDeshacer = new JMenuItem();
        editCopiar = new JMenuItem();
        editPegar = new JMenuItem();
        editEliminar = new JMenuItem();

        about = new JMenuItem();
        help_view = new JMenuItem();


        fileMenuOption.setText("File");
        editMenuOption.setText("Edit");
        helpMenuOption.setText("Help");

        fileNewOption.setText("New");
        fileOpenOption.setText("Open");
        fileSaveOption.setText("Save");
        fileSaveAsOption.setText("Save as");
        fileprintOption.setText("Print");
        fileExitOption.setText("Exit");


        editDeshacer.setText("Undo");
        editCopiar.setText("Copy");
        editPegar.setText("Paste");
        editEliminar.setText("Delete");

        about.setText("About");
        help_view.setText("Help");

        fileMenuOption.add(fileNewOption);
        fileMenuOption.add(fileOpenOption);
        fileMenuOption.add(fileSaveOption);
        fileMenuOption.add(fileSaveAsOption);
        fileMenuOption.add(fileprintOption);
        fileMenuOption.add(fileExitOption);

        editMenuOption.add(editDeshacer);
        editMenuOption.add(editCopiar);
        editMenuOption.add(editPegar);
        editMenuOption.add(editEliminar);

        helpMenuOption.add(about);
        helpMenuOption.add(help_view);

        menu.add(fileMenuOption);
        menu.add(editMenuOption);
        menu.add(helpMenuOption);

        this.setJMenuBar(menu);

    }

    public void createdContextualMenu() {

        //BOTON SAVE AS
        fileSaveAsOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    doc = servicio.fileSaveAs(textAreaCentral);

                } catch (IOException ex) {
                    System.out.println("No se ha podido ejecutar el metodo fileSaveAs");
                }
            }
        });

        //BOTON SAVE
        fileSaveOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (doc.getContenido() == null) {
                        doc = servicio.fileSaveAs(textAreaCentral);
                    }
                    if (!doc.getContenido().equals(textAreaCentral.getText())) {
                        doc = servicio.fileSave(doc, textAreaCentral);
                    }
                } catch (IOException ex) {
                    System.out.println("No se ha podido ejecutar el metodo fileSave");
                }
            }
        });

        //BOTON OPEN
        fileOpenOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    doc = servicio.openFile(textAreaCentral);

                } catch (IOException ex) {
                    System.out.println("No se ha podido ejecutar el metodo openFile");
                }
            }
        });

        //BOTON NEW
        fileNewOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textAreaCentral.getText().isEmpty()) { //Valorar otro diálogo que tenga opcion guardar o no guardar
                    int respuesta = JOptionPane.showInternalConfirmDialog(null, "¿Desea guardar los cambios?");
                    if (respuesta == 0) {
                        try {
                            if (doc.getContenido() == null) {
                                doc = servicio.fileSaveAs(textAreaCentral);
                            } else {
                                doc = servicio.fileSave(doc, textAreaCentral);
                            }
                            textAreaCentral.setText("");
                        } catch (IOException ex) {
                            System.out.println("No se ha podido ejecutar el metodo fileSave");
                        }
                    } else if (respuesta == 1) {
                        doc = new DocumentSave();
                        doc.setContenido("");
                        textAreaCentral.setText(doc.getContenido());
                    }
                }
            }
        });

        //BOTON IMPRIMIR
        fileprintOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrinterJob pj = PrinterJob.getPrinterJob();
                if (pj.printDialog()) {
                    try {
                        pj.print();
                    } catch (PrinterException exc) {
                        System.out.println(exc);
                    }
                }

            }
        });


        //BOTON CERRAR
        fileExitOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textAreaCentral.getText().isEmpty()) {
                    int respuesta = JOptionPane.showInternalConfirmDialog(null, "¿Quieres guardar los cambios antes de salir?");
                    if (respuesta == 0) {

                        if (doc.getContenido() == null) {
                            try {
                                servicio.fileSaveAs(textAreaCentral);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            try {
                                servicio.fileSave(doc, textAreaCentral);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }

                    }

                } else {
                    System.exit(0);
                }
            }
        });


        //BOTON DESHACER
        editDeshacer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //No encuentro la manera de hacerlo
                } catch (CannotRedoException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });


        //BOTON COPIAR
        editCopiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                portaPapeles = Toolkit.getDefaultToolkit().getSystemClipboard();

                if (textAreaCentral.getSelectedText() != null) {
                    StringSelection seleccion = new StringSelection("" + textAreaCentral.getSelectedText());
                    portaPapeles.setContents(seleccion, seleccion);
                }
            }
        });


        //BOTON PEGAR
        editPegar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Transferable datos = portaPapeles.getContents(null);
                try {
                    if (datos != null && datos.isDataFlavorSupported(DataFlavor.stringFlavor))
                        textAreaCentral.replaceSelection("" + datos.getTransferData(DataFlavor.stringFlavor));
                } catch (IOException | UnsupportedFlavorException ex) {
                    System.err.println(ex);
                }
            }
        });


        //BOTON ELIMINAR
        editEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textAreaCentral.getSelectedText() != null) {
                    textAreaCentral.replaceSelection("");
                }
            }
        });

        //BOTON SOBRE IDE
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem about = new JMenuItem("About");
                JOptionPane.showMessageDialog(null, "IntelliJ IDEA 2021.2.2 (Ultimate Edition)\n" +
                        "Build #IU-212.5284.40, built on September 14, 2021\n" +
                        "Licensed to IES Luis Vives / andrea gomez\n" +
                        "Subscription is active until September 9, 2022.\n" +
                        "For educational use only.\n" +
                        "Runtime version: 11.0.12+7-b1504.28 amd64\n" +
                        "VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.\n" +
                        "Windows 10 10.0\n" +
                        "GC: G1 Young Generation, G1 Old Generation\n" +
                        "Memory: 1014M\n" +
                        "Cores: 4\n" +
                        "Registry: ide.balloon.shadow.size=0\n" +
                        "Non-Bundled Plugins: com.markskelton.one-dark-theme (5.4.0), com.chrisrm.idea.MaterialThemeUI (6.9.1), com.haulmont.jpab (5.7-212)\n" +
                        "Kotlin: 212-1.5.10-release-IJ5284.40");

            }
        });


        //BOTON HELP
        help_view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI("https://www.jetbrains.com/help/idea/2021.2/getting-started.html?utm_source=product&utm_medium=link&utm_campaign=IU&utm_content=2021.2"));
                } catch (IOException | URISyntaxException ex) {
                    ex.getMessage();
                }
            }
        });

    }


    public void createdCentralPanel() {
        textAreaCentral = new JTextArea();
        JTree explorer = new JTree();
        console = new JTextArea();

        jspTextAreaCentral = new JScrollPane();
        jspConsole = new JScrollPane();
        jspExplorador = new JScrollPane();

        jspExplorador.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jspExplorador.setViewportView(createdExplorer());
        jspTextAreaCentral.setViewportView(textAreaCentral);
        jspConsole.setViewportView(console);

        jspExplorador.getViewport().setBackground (Color.pink);

        panelArchivoEditor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                jspExplorador, jspTextAreaCentral);
        panelEditorConsola = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                panelArchivoEditor, jspConsole);



        panelEditorConsola.setOneTouchExpandable(true);
        panelEditorConsola.resetToPreferredSizes();
        panelEditorConsola.setDividerLocation(400);


        panelArchivoEditor.setDividerLocation(600);
        panelArchivoEditor.setOneTouchExpandable(true);


        jspTextAreaCentral.setMinimumSize(new Dimension(600, 600));
        jspConsole.setMinimumSize(new Dimension(1000, 50));
        jspExplorador.setMinimumSize(new Dimension(300, 100));
        textAreaCentral.setFont(new Font("Monaco", Font.PLAIN, 15));
        textAreaCentral.setForeground(new Color(0xE76DBC));
        explorer.setFont(new Font("Monaco", Font.PLAIN, 15));
        explorer.setForeground(new Color(0xBD5885A8, true));
        console.setForeground(new Color(0xFF7FFDE9, true));

    }

    public JTree createdExplorer() {

        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Directorio");
        tree = new JTree();
        treeModel = new DefaultTreeModel(top);
        tree.setModel(treeModel);
        tree.addTreeExpansionListener(this);
        File user = new File(System.getProperty("user.dir"));
        File[] directorios = user.listFiles();
        int contador = 0;
        for (File f : directorios) {
            DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(f);
            top.add(raiz);
            tree.expandRow(contador);
            tree.setForeground(new Color(0xBD90CEFF, true));
            contador++;

            //actualizaNodo(raiz, f);
        }


        return tree;

    }

    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        TreePath path = event.getPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        if (node == null || !(node.getUserObject() instanceof File)) return;
        File f = (File) node.getUserObject();
        //actualizaNodo(node, f);
        JTree tree = (JTree) event.getSource();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.nodeStructureChanged(node);
    }

    @Override
    public void treeCollapsed(TreeExpansionEvent event) {
    }

    public void estilos() {


    }
}