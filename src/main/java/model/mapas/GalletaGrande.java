package model.mapas;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

/**
 * Clase modelo de las galletas grandes, que hereda de la superclase Galleta.
 * Se implementan todas las características distintivas de las galletas pequeñas.
 * @author Rubén Lastres Villar
 * @version 1.0 (15-05-2014)
 */
public class GalletaGrande extends Galleta{   
    /**
     * Constructor de la clase.<br/><br/>
     * Recibe como parámetros, a la hora de ser creada una instancia, las coordenadas X e Y
     * donde debe ser creada. Estos dos parámetros deben de ser pasados a la superclase con 
     * la palabra super.<br/><br/>
     * Se define también la ruta de la imagen que representa a las galletas grandes, así como
     * su tamaño y los puntos que otorga a Pacman comerse una.
     * @param int Dos enteros con las coordenadas X e Y donde debe de ser creada la galleta.
     */
    public GalletaGrande(int x, int y){
        super(x,y);
        
        ruta = "imagenes/galletas/grande.jpg";
        
        ImageIcon ii = new ImageIcon(this.getClass().getResource(ruta));
        imagen = ii.getImage();
        
        // Tamaño de la galleta
        width = 15;
        height = 15;
        
        // Constante entera que contendrá la puntuación por comer la galleta.
        puntos = 50; 
    }
}