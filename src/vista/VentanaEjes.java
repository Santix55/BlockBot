/*
 * Ventana para seleccionar los ejes que van a actuar como largo y ancho de la
 * imagen en Minecraft
 */
package vista;

import controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modelo.Modelo;

/**
 * @author Santix55
 */
public class VentanaEjes extends JFrame {
    private Modelo modelo;
    
    private JLabel anchoLabel = new JLabel("Ancho:");
    private JLabel altoLabel = new JLabel("Alto:");
    
    private JComboBox anchoCombo = new JComboBox();
    private JComboBox altoCombo = new JComboBox();
    
    private JButton boton = new JButton("OK");
    
    public VentanaEjes(Modelo _modelo) {
        modelo = _modelo;
        
        anchoCombo.addItem("+x");
        anchoCombo.addItem("-x");
        anchoCombo.addItem("+z");
        anchoCombo.addItem("-z");
        
        altoCombo.addItem("+x");
        altoCombo.addItem("-x");
        altoCombo.addItem("-y");
        altoCombo.addItem("+z");
        altoCombo.addItem("-z");
        
        this.setIndices();
        
        JPanel anchoPanel = new JPanel();
        anchoPanel.add(anchoLabel);
        anchoPanel.add(anchoCombo);
        
        JPanel altoPanel = new JPanel();
        altoPanel.add(altoLabel);
        altoPanel.add(altoCombo);
        
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridLayout(1,2, 10,10));
        centralPanel.add(anchoPanel);
        centralPanel.add(altoPanel);
        this.add(centralPanel, BorderLayout.CENTER);
        this.add(boton, BorderLayout.SOUTH);
        
        this.setVisible(false);
        this.setTitle("BlockBot: Orientación");
        this.setSize(400,150);
    }

    public JComboBox getComboAncho() {
        return anchoCombo;
    }

    public JComboBox getComboAlto() {
        return altoCombo;
    }

    public void addMiActionListener(Controlador.MiActionListener al) {
        boton.setActionCommand("ejes ok");
        boton.addActionListener(al);
    }

    public void setIndices() {
        switch(modelo.getEjeAncho()) {
            case "+x":
                anchoCombo.setSelectedIndex(0);
                break;
            case "-x":
                anchoCombo.setSelectedIndex(1);
                break;
            case "+z":
                anchoCombo.setSelectedIndex(2);
                break;
            case "-z":
                anchoCombo.setSelectedIndex(3);
                break;
            default:
                System.out.println("! Error setIndices eje ancho");
                System.out.println("Eje alto : " + modelo.getEjeAncho());
        }
        
        switch(modelo.getEjeAlto()) {
            case "+x":
                altoCombo.setSelectedIndex(0);
                break;
            case "-x":
                altoCombo.setSelectedIndex(1);
                break;
            case "-y":
                altoCombo.setSelectedIndex(2);
                break;
            case "+z":
                altoCombo.setSelectedIndex(3);
                break;
            case "-z":
                altoCombo.setSelectedIndex(4);
                break;
            default:
                System.out.println("! Error setIndices alto");
                System.out.println("Eje alto : " + modelo.getEjeAlto());
        }
    }
}
