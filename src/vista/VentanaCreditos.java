/*
 * Ventana que contiene un JTextArea con los créditos
 */
package vista;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author Santix
 */
class VentanaCreditos extends JFrame {
    public VentanaCreditos() {
        this.setSize(300,110);
        this.setTitle("BlockBot: Créditos");
        this.setVisible(false);
        
        JTextArea texto = new JTextArea();
        texto.append("LICENCIA GPL-3 \n");
        texto.append("-------------------------------------- \n");
        texto.append("Hecho por Santix55 \n");
        texto.append("Agradecimiento especial a Almagar3 \n");
        texto.setEditable(false);
        this.add(texto);
    }
}
