/*
 * Contiene todos los listeners del programa
 */
package controlador;

import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import modelo.Modelo;
import vista.Vista;

/**
 * LOOK AT THAT --> ñ
 * @author Santix55
 */

public class Controlador {
    private Modelo modelo;
    private Vista vista;
    
    private JTextField campoXini;
    private JTextField campoYini;
    private JTextField campoZini;
    
    private JComboBox comboAncho;
    private JComboBox comboAlto;
    
    // private JProgressBar progressBar;
    
    private ArrayList<JTextField> campos_bloque;
    
    MiActionListener al;

    // Crea los listeners y los añade a la vista. 
    public Controlador(Modelo _modelo, Vista _vista) {
        modelo = _modelo;
        vista = _vista;
        
        campoXini = vista.getCampoXini();
        campoYini = vista.getCampoYini();
        campoZini = vista.getCampoZini();
        
        comboAncho = vista.getComboAncho();
        comboAlto = vista.getComboAlto();
        
        campos_bloque = vista.getCamposBloques();
        
        //progressBar = vista.getProgressBar();
        // modelo.setProgressBar(progressBar);
        
        al = new MiActionListener();
        vista.addMiActionListener(al);
    }
    
    public class MiActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Boolean ok; //< variable auxiliar de comprobación de asiganación
            int i;      //< iterador
            
            String cmd = e.getActionCommand();
            System.out.println(cmd);
            
            switch(cmd){
                case "cargar":
                    break;
                    
                case "bloque_ini":  // Abre selección del bloque inicial
                    vista.verBloqueInicial(true);
                    break;
                
                // Presiona OK en la selección del bloque inicial y se
                // asignan los páremetros del usuario si son correctos
                case "bloque_ini ok":     
                    vista.verBloqueInicial(false);
                    ok = modelo.setIni(
                            campoXini.getText(),
                            campoYini.getText(),
                            campoZini.getText()
                    );
                    if(ok) {
                        vista.actualizarInfo();
                        //vista.actualizarCampoBloqueIni();   //< no funciona
                    }
                    break;
                    
                case "ejes":    // Abre selección de la orientación
                    vista.verEjes(true);
                    break;
                    
                // Presiona OK en la selección y se asignan los ejes si son 
                // correctos 
                case "ejes ok": 
                    vista.verEjes(false);
                    ok = modelo.setEjes( 
                            (String) vista.getComboAncho().getSelectedItem(),
                            (String) vista.getComboAlto().getSelectedItem()
                    );
                    if(ok) {
                        vista.actualizarInfo();
                        //vista.actualizarComboEjes();  //< no funciona
                    }
                    break;
                
                // Primero crea la lista de comandos para construir y después
                // espera 5 segundos y empieza a construir la imagen cargada
                case "construir":
                    ArrayList<String> nombres_bloque = new ArrayList<String>();
                    for(i=0; i<campos_bloque.size(); i++)
                       nombres_bloque.add(campos_bloque.get(i).getText());
                    modelo.createCommandList(nombres_bloque);

                    try {
                        modelo.escribirComandos();
                    } catch (AWTException ex) {
                        System.err.println("Robotiko falló :'(");
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                    
                // Muestra el autor del programa
                case "creditos":
                    vista.verCreditos(true);
                    break;

                    
                default:
                    System.err.println("! Comando no encontrado");
            }
        }    
    }
}

