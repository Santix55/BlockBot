/*
 * Clase principal del programa
 * Modelo-Vista-Controlador
 */
package blockbot;

import controlador.Controlador;
import modelo.Modelo;
import vista.Vista;

/**
 *
 * @author Santix55
 */
public class BlockBot {

    /**
     * 1º crea la lógica e instancia los pármetros incluyendo el selector de ficheros (Modelo) 
     * 2º crea las ventanas que muestran el contenido del modelo (Vista)
     * 3º crea los listeners y los añade a los elementos de la vista (Controlador)
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        Vista vista = new Vista(modelo);
        Controlador controlador = new Controlador (modelo, vista);
    }
}
