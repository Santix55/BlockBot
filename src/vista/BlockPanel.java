/*
 * Contiene una etiqueta con el color que de la paleta especificado
 * por el índice y JText area donde el usuario especificará el bloque para
 * representar dicho color.
 */
package vista;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import modelo.Modelo;

/**
 *
 * @author Santix55
 */
public class BlockPanel extends JPanel{
    // Valor que tienen que suparar los colores para considerarse claros
    private static int UMBRAL_DE_LUZ = 382; // 255*3/2
    
    private Modelo modelo;
    
    private JLabel label;
    private JTextField text_field;
    
    public BlockPanel(Modelo _modelo,int index) {
        modelo = _modelo;
        
        Color color = modelo.getColorPaleta(index);
        
        // Poner etiqueta con el índice de la paleta 
        label = new JLabel(Integer.toString(index)+"\t");
        label.setBackground(color);
        if(color.getRed() + color.getBlue()+ color.getGreen() < UMBRAL_DE_LUZ)
            label.setForeground(Color.WHITE);
        else
            label.setForeground(Color.BLACK);
        label.setOpaque(true);
        this.add(label);
        
        text_field = new JTextField(10);
        this.add(text_field);
    }
    
    public JTextField getTextField() {
        return text_field;
    }
}
