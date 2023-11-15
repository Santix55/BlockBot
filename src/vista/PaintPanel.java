/*
 * Panel que representa un color de la paleta. A la izquierda de dicho panel hay
 * un JLabel con el fondo del color de la paleta a reemplazar por el bloque de
 * la derecha. El label contiene el número pasado como parámetro del constructor
 */
package vista;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import modelo.Modelo;

/**
 *
 * @author Santix55
 */
public class PaintPanel extends JPanel{
    
    private Modelo modelo;
    
    public PaintPanel(Modelo _modelo){
        modelo = _modelo;
    }
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        
        // No funciona cuando no se inicializa
        BufferedImage imagen = modelo.getImagen();
        if(imagen != null)
            g2d.drawImage(imagen, 0 , 0, imagen.getWidth() * modelo.getEscalado(), imagen.getHeight() * modelo.getEscalado(), this);
        else
            System.out.println("La imagen no existe");
        
        
    }
}
