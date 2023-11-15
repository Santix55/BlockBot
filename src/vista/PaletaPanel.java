/*
 * Panel que contiene todos los colores de paleta
 */
package vista;

import controlador.Controlador;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import modelo.Modelo;

/**
 *
 * @author usuario
 */
public class PaletaPanel extends JPanel {
    private Modelo modelo;
    private ArrayList<BlockPanel> block_panels = new ArrayList<BlockPanel>();
            
    public PaletaPanel(Modelo _modelo) {
        modelo = _modelo;
        
        // Poner los componentes en vertical
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Añadir un BlockPanel por color de la paleta y guardarlo en el array
        BlockPanel block_panel;
        for(int i=0; i<modelo.getPaletaSize(); i++) {
            block_panel = new BlockPanel(modelo, i);
            block_panels.add(block_panel);
            this.add(block_panel);
        }
    }

    /* Devuelve un array de punteros que apuntan a todos los campos de texto de
    la paleta */
    ArrayList<JTextField> getCamposBloques() {
        int i;
        ArrayList<JTextField> text_fields = new ArrayList<JTextField>();
        for(i=0; i<block_panels.size(); i++){
            text_fields.add(block_panels.get(i).getTextField());
        }
        return text_fields;
    }
}
