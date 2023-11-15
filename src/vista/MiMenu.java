/*
 * Menu de la parte superior de la ventana que contiene las opciones de Posición
 * (Bloque inicial y Orientación) y los créditos del programa
 */
package vista;

import controlador.Controlador.MiActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Santix55
 */
public class MiMenu extends JMenuBar {
    JMenu archivo = new JMenu("Archivo");
    JMenu posicion = new JMenu("Posición");
    JMenu creditos = new JMenu("Créditos");
    
    JMenuItem bloque_ini = new JMenuItem("Bloque inicial");
    JMenuItem orientacion = new JMenuItem("Orientación");
    JMenuItem creditos2 = new JMenuItem("Créditos");
    
    public MiMenu() {
        //this.add(archivo);
        this.add(posicion);
        this.add(creditos);
        
        posicion.add(bloque_ini);
        posicion.add(orientacion);  
        creditos.add(creditos2);
    }
    
    public void addMiActionListener(MiActionListener al){
        bloque_ini.setActionCommand("bloque_ini");
        bloque_ini.addActionListener(al);
        
        orientacion.setActionCommand("ejes");
        orientacion.addActionListener(al);
        
        creditos2.setActionCommand("creditos");
        creditos2.addActionListener(al);
    }
}
