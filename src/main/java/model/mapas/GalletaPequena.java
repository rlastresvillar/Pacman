package model.mapas;
import javax.swing.ImageIcon;

/**
 * Clase modelo de las galletas pequeñas
 * Implementaremos todas sus características propias.
 * @author Rubén Lastres Villar
 * @version 1.0 (15-05-2014)
 */
public class GalletaPequena extends Galleta{ 
    /**
     * Constructor de la clase.<br/><br/>
     * Recibe como parámetros, a la hora de ser creada una instancia, las coordenadas X e Y
     * donde debe ser creada. Estos dos parámetros deben de ser pasados a la superclase con 
     * la palabra super.<br/><br/>
     * Se define también la ruta de la imagen que representa a las galletas pequeñas, así como
     * su tamaño y los puntos que otorga a Pacman comerse una.
     * @param int Dos enteros con las coordenadas X e Y donde debe de ser creada la galleta.
     */
    public GalletaPequena(int x, int y){ 
        super(x,y);
        
        // La ruta de la imagen de la galleta
        ruta = "imagenes/galletas/pequena.jpg";
        
        // Se guarda la imagen de la galleta en la variable imagen
        ImageIcon ii = new ImageIcon(this.getClass().getResource(ruta));
        imagen = ii.getImage();
        
        // Tamaño de la galleta
        width = 5;
        height = 5;
        
        // Puntuación por comer la galleta.
        puntos = 10;  
    }
}