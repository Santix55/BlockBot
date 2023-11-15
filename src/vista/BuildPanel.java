/*
 * Panel inferior que muestra el bloque inicial y la orientación en una etiqueta,
 * junto al botón para empezar a escribir
 */
package vista;

import controlador.Controlador;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import modelo.Modelo;

/**
 *
 * @author usuario
 */
public class BuildPanel extends JPanel{
    private Modelo modelo;
    
    // private JProgressBar progress = new JProgressBar(0,100);
    private JButton start = new JButton("Construir ==>");
    private JLabel info = new JLabel();
    private JLabel espacio = new JLabel(                                      );
    
    public BuildPanel(Modelo _modelo) {
        modelo = _modelo;
        
        info.setText("Posición inicial = ("
                + modelo.getIniX() + ","
                + modelo.getIniY() + ","
                + modelo.getIniZ() + ")"
                + "    "
                + "Ejes = {"
                + modelo.getEjeAncho() + ","
                + modelo.getEjeAlto()
                + "}"
        );
        this.add(info);
        this.add(espacio);
        
        //this.add(progress);
        //this.add(espacio);
        
        this.add(start);
    }

    public void addMiActionListener(Controlador.MiActionListener al) {
        start.setActionCommand("construir");
        start.addActionListener(al);
    }

    public void actualizarInfo() {
        info.setText("Posición inicial = ("
                + modelo.getIniX() + ","
                + modelo.getIniY() + ","
                + modelo.getIniZ() + ")"
                + "    "
                + "Ejes = {"
                + modelo.getEjeAncho() + ","
                + modelo.getEjeAlto()
                + "}"
        );
    }
    
    /*
    public JProgressBar getProgressBar() {
        return progress;
    }
    */
}
