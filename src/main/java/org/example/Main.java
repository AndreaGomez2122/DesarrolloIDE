package org.example;

import org.example.vista.Vista;
import javax.swing.*;

public class Main
{
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Vista().setVisible(true);
            }
        });
    }
}
