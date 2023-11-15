/*
 * Contiene todo aquello que se puede ver
 */
package vista;

import controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modelo.Modelo;

/**
 *
 * @author Santix55
 */
public class VentanaBloqueInicial extends JFrame{
    private Modelo modelo;
    
    private JLabel labelX = new JLabel("X");
    private JLabel labelY = new JLabel("Y");
    private JLabel labelZ = new JLabel("Z");
    
    private JTextField campoX = new JTextField(10);
    private JTextField campoY = new JTextField(10);
    private JTextField campoZ = new JTextField(10);
    
    private JButton boton = new JButton("OK");
    
    public VentanaBloqueInicial(Modelo _modelo) {
        modelo = _modelo;
        
        // Configuración de ventana //
        this.setTitle("BlockBot: Bloque Inicial");
        this.setVisible(false);
        this.setSize(400, 150);
        
        // Componentes de la ventana
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2,1, 1,1));
        infoPanel.add(new JLabel("Selecciona las coordenadas donde se colocará"));
        infoPanel.add(new JLabel("el píxel superior izquierdo (0,0) de la imagen"));
        this.add(infoPanel, BorderLayout.NORTH);
        
        
        JPanel panelX = new JPanel();
        panelX.add(labelX);
        panelX.add(campoX);
        campoX.setText(Integer.toString(modelo.getIniX()));
        
        JPanel panelY = new JPanel();
        panelY.add(labelY);
        panelY.add(campoY);
        campoY.setText(Integer.toString(modelo.getIniY()));
        
        JPanel panelZ = new JPanel();
        panelZ.add(labelZ);
        panelZ.add(campoZ);
        campoZ.setText(Integer.toString(modelo.getIniY()));
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,3, 10,10));
        mainPanel.add(panelX);
        mainPanel.add(panelY);
        mainPanel.add(panelZ);
        this.add(mainPanel, BorderLayout.CENTER);
        
        
        this.add(boton, BorderLayout.SOUTH);
    }

    public void addMiActionListener(Controlador.MiActionListener al) {
        boton.setActionCommand("bloque_ini ok");
        boton.addActionListener(al);
    }

    public JTextField getCampoXini() {
        return campoX;
    }

    public JTextField getCampoYini() {
        return campoY;
    }

    public JTextField getCampoZini() {
        return campoZ;
    }

    public void actualizar() {
        campoX.setText(Integer.toString(modelo.getIniX()));
        campoY.setText(Integer.toString(modelo.getIniY()));
        campoZ.setText(Integer.toString(modelo.getIniZ()));
    }
}