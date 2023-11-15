/*
 * Contiene todas la ventanas y paneles de la ventana principal
 */
package vista;

import controlador.Controlador;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import modelo.Modelo;

/**
 *
 * @author Santix55
 */
public class Vista extends JFrame{
    private Modelo modelo;
    
    private VentanaBloqueInicial ventana_bloque;
    private VentanaEjes ventana_ejes;
    private VentanaCreditos ventana_creditos;
    
    private MiMenu menu;
    private PaintPanel paint_panel;
    private PaletaPanel paleta_panel;
    private BuildPanel build_panel;
    
    public Vista(Modelo _modelo) {
        modelo = _modelo;
        
        // Subpaneles //
        menu = new MiMenu();
        this.setJMenuBar(menu);
        
        paint_panel = new PaintPanel(modelo);
        this.add(paint_panel, BorderLayout.CENTER);
        
        paleta_panel = new PaletaPanel(modelo);
        this.add(paleta_panel, BorderLayout.EAST);
        
        build_panel = new BuildPanel(modelo);
        this.add(build_panel, BorderLayout.SOUTH);

        
        // Opciones de ventana //
        this.setTitle("BlockBot");
        this.setSize(1360, 768);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        // Subventanas //
        ventana_bloque = new VentanaBloqueInicial(modelo);
        ventana_ejes = new VentanaEjes(modelo);
        ventana_creditos = new VentanaCreditos();
    }

    
    // LISTENERS //
    public void addMiActionListener(Controlador.MiActionListener al) {
        menu.addMiActionListener(al);
        build_panel.addMiActionListener(al);
        ventana_bloque.addMiActionListener(al);
        ventana_ejes.addMiActionListener(al);
    }

    

    // GETTERS //
    public JTextField getCampoXini() {
        return ventana_bloque.getCampoXini();
    }

    public JTextField getCampoYini() {
        return ventana_bloque.getCampoYini();
    }

    public JTextField getCampoZini() {
        return ventana_bloque.getCampoZini();
    }
    
    public JComboBox getComboAncho() {
        return ventana_ejes.getComboAncho();
    }

    public JComboBox getComboAlto() {
        return ventana_ejes.getComboAlto();
    }
    
    public ArrayList<JTextField> getCamposBloques() {
        return paleta_panel.getCamposBloques();
    }
    
    /*
    public JProgressBar getProgressBar() {
        return build_panel.getProgressBar();
    }
    */

    
    // ACTUALIZACIONES //
    public void actualizarInfo() {
        build_panel.actualizarInfo();
    }
    
    public void actualizarCampoBloqueIni(){
        ventana_bloque.actualizar();
    }
    
    public void actualizarComboEjes() {
        ventana_ejes.setIndices();
    }
    
    public void verBloqueInicial(boolean b) {
        ventana_bloque.setVisible(b);
    }

    public void verEjes(boolean b) {
        ventana_ejes.setVisible(b);
    }

    public void verCreditos(boolean b) {
        ventana_creditos.setVisible(b);
    }
}