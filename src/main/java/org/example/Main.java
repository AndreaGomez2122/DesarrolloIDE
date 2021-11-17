package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import org.example.vista.Vista;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class Main {
    public static void main(String[] args) {


        try {

            UIManager.setLookAndFeel ( new com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme());
        } catch (
                UnsupportedLookAndFeelException
                        ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Vista().setVisible(true);
            }
        });
    }




    }


