/*
 * Contiene toda la lógica del programa
 */
package modelo;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;

/**
 *
 * @author Santix55
 */
public class Modelo {
    
    // ATRIBUTOS DE PÁRAMETROS DE CONSTRUCCIÓN //
    // posción inicial de construcción, esquina superior izquierda (0,0) en MC
    private int iniX;
    private int iniY;
    private int iniZ;
    
    // ejes en los que se va a realizar la construcción
    private String eje_alto;    // {+-x, +y, +-z}
    private String eje_ancho;   // {+-x, -+z} 

  
    // ATRIBUTOS DE LA IMAGEN //
    private int escalado;
    private BufferedImage imagen;
    private ArrayList<Integer> paleta;   //< contiene el color de los pixeles que utiliza la imagen
    private ArrayList<Integer> pixelmap; //< contiene la imagen formada por pixeles de la paleta
    
    // OTROS //
    private ArrayList<String> commandList;  //< lista de comandos de MC que debe ejecutar el bot
    private JProgressBar progressBar;       //< puntero a la barra de progreso de la vista
    
    // CONSTRUCTOR // Inicializa la lógica
    public Modelo() {
       escalado = 10;
       
       eje_ancho = "+x";
       eje_alto = "-y";
       
       iniX = 0;
       iniY = 0;
       iniZ = 0;
       
       selectLoadImage();
       createPixelMap();
   }
    
    // AUXILIAR //
    /* A partir de la imagen cargada genera 2 array de enteros :
        - paleta: Guarda todos los colores utilizados en la imagen
        - pixelmap: Representa el índice de la paleta utilizado en cada píxel. */
    public void createPixelMap() {
        
        // Crea un nuevo mapa de pixeles y paleta
        pixelmap = new ArrayList<Integer>();
        paleta = new ArrayList<Integer>();
        
        
        int pixel;
        int i;    //< iteradores
        int x, y; //< posición
        
        // Recorre todos los píxeles de la imagen, para cada uno de ellos se le
        // asigna un índice que hace referencia al color de la paleta. Si el
        // color no esta previamente en la paleta se añade a ella
        for(y=0; y<imagen.getHeight(); y++) {
            for(x=0; x<imagen.getWidth(); x++) {
                
                // obtención de pixel
                pixel = imagen.getRGB(x, y);
                
                // bucle que finaliza cuando a recorrido toda la paleta
                // o cuando encuentra un color igual
                for(i=0; i<paleta.size(); i++) {
                    if(paleta.get(i)== pixel){
                        break;
                    }   
                }
                
                // si ha recorrido toda la paleta sin encontrar su color se 
                // añade el nuevo color
                if(i==paleta.size())
                    paleta.add(pixel);
                
                // el iterador marca el color que tiene asignado en la paleta
                // dicho pixel, por tanto añade al mapa de pixeles
                pixelmap.add(i);
                
                // mostrar por consola el la posición del color de la paleta
                // del píxel añadido
                System.out.print(i+"\t");
            }
            System.out.println();
        }
    }
    
    /* Crea la lista de comandos a partir de los ejes especificados, bloque de
     inicio (guardados previamente) y la lista de nombres de bloque como
     párametro de entrada.
      El índice de cada elemento coincide con el color de la paleta 
    que representa */
    public void createCommandList(ArrayList<String> nombresBloque) {
        int i;                              //< iterador de la imagen
        int alto, ancho;                    //< posición 2D del iterador
        int x, y, z;                        //< posición 3D en MC
        String nombreBloque;                //< tag del bloque a utilizar
        
        int n_pixeles = pixelmap.size();    //< nº de pixeles de la imagen 
        int imagenAncho = imagen.getWidth();//< resolución horizontal
        int imagenAlto = imagen.getHeight();//< resolución vertical
        
        // A patir de los ejes especificados por el usuario, obteniene los
        // valores númericos que indican se debe avanzar por cada bloque de
        // ancho y alto en cada eje.
        int factorAnchoX = 0;   int factorAltoX = 0;
                                int factorAltoY = 0;
        int factorAnchoZ = 0;   int factorAltoZ = 0;
        
        switch(eje_ancho){
            case "+x":
                factorAnchoX = 1;
                break;
                
            case "-x":
                factorAnchoX = -1;
                break;
                
            case "+z":
                factorAnchoZ = 1;
                break;
                
            case "-z":
                factorAnchoZ = -1;
                break;
        }
        
        switch(eje_alto){
            case "+x":
                factorAltoX = 1;
                break;
                
            case "-x":
                factorAltoX = -1;
                break;
                
            case "-y":
                factorAltoY = -1;
                break;
                
            case "+z":
                factorAltoZ = 1;
                break;
                
            case "-z":
                factorAltoZ = -1;
                break;
        }
        
        // Imprimir facoteres
        System.out.println("Ejes: {"+eje_ancho+","+eje_alto+"}");
        System.out.println();
        System.out.println("factorAnchoX = "+factorAnchoX);
        System.out.println("factorAnchoZ = "+factorAnchoZ);
        System.out.println();
        System.out.println("factorAltoX = "+factorAltoX);
        System.out.println("factorAltoY = "+factorAltoY);
        System.out.println("factorAltoZ = "+factorAltoZ);
        System.out.println();
        
        
        // Inicializar lista de comandos //
        commandList = new ArrayList<String>();
        
        // Recorrer todos los píxeles //
        for(i=0; i<n_pixeles; i++) {
            // obtener posición 2D de la imagen a partir del iterador
            ancho = i % imagenAncho;
            alto = i / imagenAncho;
            
            // obtener posición 3D en MC a partir de la 2D de la imagen
            x = iniX + factorAltoX*alto + factorAnchoX*ancho;
            y = iniY + factorAltoY*alto;
            z = iniZ + factorAltoZ*alto + factorAnchoZ*ancho;
            
            // obtener nombre del bloque se utiliza en este píxel
            nombreBloque = nombresBloque.get(pixelmap.get(i));
            
            // si el bloque a poner es aire no se añade
            if(!nombreBloque.equals("air")) {
                commandList.add("/setblock "+x+" "+y+" "+z+" minecraft:"+nombreBloque);
                System.out.println("/setblock "+x+" "+y+" "+z+" minecraft:"+nombreBloque);
            }   
        }
    }
    
    /* Primero espera 10 segundos para que el usuario cambia de ventana a MC
    escribe todos los comandos guardados en la variable commandList */
    public void escribirComandos() throws AWTException {
        int i, j;                              //< iteradores
        int n_comandos = commandList.size();   //< cantidad de comandos
        Robot robot = new Robot();             //< robotiko
        
        // Inicializar la barra de progreso a 0
        /* progressBar.setMinimum(0);
        progressBar.setMinimum(n_comandos);
        progressBar.setValue(0); */
        
        // Esperar 5 segundos
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Preparate! >:)");
        
        // Hacer que esciba toda la lista de comandos
        for(i=0; i<n_comandos; i++) {
            String comando = commandList.get(i);
            
            // Poner el comando en el portapapeles
            StringSelection stringSelection = new StringSelection(comando);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);

            // Esperar 10ms
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Pegar (Ctr+V) [Presionar]
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);

            // Esperar 10ms
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Pegar (Ctr+V) [Soltar]
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);

            // Esperar 10ms
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Abrir chat (T) [Presionar y Soltar]
            robot.keyPress(KeyEvent.VK_T);
            robot.keyRelease(KeyEvent.VK_T);

            // Esperar 50ms
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Actualizar la barra de progreso
            //progressBar.setValue(i);
            //progressBar.setStringPainted(true);
        }
    }
 
    
   // SETTERS //
   // Selecciona una imagen manualmente para ser cargada inmediatamente
   public void selectLoadImage()  {
       JFileChooser file_chooser = new JFileChooser();
       int seleccion = file_chooser.showOpenDialog(file_chooser);
       if( seleccion == JFileChooser.APPROVE_OPTION ) {
            File fichero = file_chooser.getSelectedFile();
               
            try {   
                imagen = ImageIO.read(fichero);
            } catch (IOException ex) {
                System.out.println("! No es una imagen");
                Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }
   
   /* Determina que el texto introuducido por el usuario corresponde a una válida
   en minecraft y si es así asigna la nueva cordenada. Devuelve si la operación
   se ha realizado con éxito */
   public Boolean setIni(String textX, String textY, String textZ) {
        int newX, newY, newZ;
        
        try{
            newX = Integer.parseInt(textX);
            newY = Integer.parseInt(textY);
            newZ = Integer.parseInt(textZ);
        }
        catch(NumberFormatException ex) {
            return false;
        }
        
        iniX = newX;
        iniY = newY;
        iniZ = newZ;
        return true;
    }
   
   /* Determina si los ejes escogidos por el usuario conforman un plano 2D,
   (son linealmente independientes) si es así se realiza la asignación de los
   nuevos ejes de alto y ancho. Devuelve el resultado de la operación. */
    public Boolean setEjes(String ancho, String alto) {
        System.out.println( "setEjes("+ancho+","+alto+")" );
        
        switch(ancho) {
            case "+x":
            case "-x":
                if(alto.equals("+x") || alto.equals("-x"))
                    return false;
                break;
                
            case "+z":
            case "-z":
                if(alto.equals("+z") || alto.equals("-z"))
                    return false;
                break;
        }
        
        eje_ancho = ancho;
        eje_alto = alto;
        return true;
    }
    
    /* Instancia un puntero hacia la barra progreso para que se pueda modificar
    mientras que se escriben los comandos 
    public void setProgressBar(JProgressBar _progressBar) {
        progressBar = _progressBar;
    }
    */
   
   
   // GETTERS //
   // Devuelve la imagen para ser mostrada
    public BufferedImage getImagen() {
        return imagen;
    }

    // Devuelve el factor de escalado de la iamgen
    public int getEscalado() {
        return escalado;
    }
    
    // Devuelve el color de la paleta, pero esta vez no representado como número
    public Color getColorPaleta(int index) {
        return new Color(paleta.get(index), true);
    }
    
    // Devuelve la cantidad de colores que tiene la imagen cargada
    public int getPaletaSize() {
        return paleta.size();
    }
    
    // Devuelve la posición X inicial
    public int getIniX() {
        return iniX;
    }
    
    // Devuelve la posición Y inicial
    public int getIniY() {
        return iniY;
    }
    
    // Devuelve la posición Z inicial
    public int getIniZ() {
        return iniZ;
    }

    // Devuelve el eje que va a actuar como ancho
    public String getEjeAncho() {
        return eje_ancho;
    }

    // Devuelve el eje que va a actuar como alto
    public String getEjeAlto() {
        return eje_alto;
    }
}
