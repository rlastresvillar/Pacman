import java.awt.Image;
import java.awt.Rectangle;

/**
 * Superclase abstracta de las clases GalletaPequena y GalletaGrande.
 * Define todos los campos y métodos comunes de esas dos subclases.
 * 
 * @author Rubén Lastres Villar
 * @version 1.0 (15-05-2014)
 */
public abstract class Galleta{  
    /** Ruta donde se encuentra la imagen de la galleta */
    protected String ruta;
    
    /** Coordenada X donde será creada la galleta */
    protected int x;
    
    /** Coordenada Y donde será creada la galleta */
    protected int y;
    
    /** Ancho de la imagen de la galleta */
    protected int width;
    
    /** Alto de la imagen de la galleta */
    protected int height;
    
    /** El número de puntos que le otorga a Pacman comer la galleta */
    protected int puntos;
    
    /**Un objeto de la clase Image.
    * Contendrá la imágen de la galleta, que se pasará al Controlador-Vista para que
    * la muestre en el JPanel. */
    protected Image imagen;
    
    /**
     * Constructor de la clase.<br/><br/>
     * Recibe como parámetros, a la hora de ser creada una instancia, las coordenadas X e Y
     * donde debe ser creada.
     * @param int Dos enteros con las coordenadas X e Y donde debe de ser creada la galleta.
     */
    public Galleta(int x, int y){
        // Se asigna la posición de la galleta.
        this.x = x;
        this.y = y;   
    }
    
    /**
     * Devuelve la imágen de la galleta guardada en el campo imagen.
     * @return La imagen de la galleta, de tipo Image.
     */
    public Image getImage(){
       return imagen;
    }
    
     /**
      * Devuelve la coordenada X donde está situada la galleta.
      * @return La coordenada X de la galleta.
      */
    public int getX(){
        return x;
    }
    
    /**
      * Devuelve la coordenada Y donde está situada la galleta.
      * @return La coordenada Y de la galleta.
      */
    public int getY(){
        return y;
    }
    
    /**
     * Devuelve un rectángulo dibujado sobre la galleta, que servirá para poder detectar las colisiones de Pacman contra la galleta.
     * @return Un objeto de tipo rectangle sobre la imagen de la galleta.
     */
    public Rectangle getBounds(){
        return new Rectangle (x,y,width,height);
    }
    
    /**
     * Devuelve el ancho de la galleta (en pixeles).
     * @return El ancho de la imagen de la galleta.
     */
    public int getAncho(){
        return width;
    }
    
    /**
     * Devuelve el alto de la galleta (en pixeles).
     * @return El alto de la imagen de la galleta.
     */
    public int getAlto(){
        return height;
    }
    
    /**
     * Devuelve los puntos que deben sumarse a la puntuación de la partida cuando Pacman come una galleta.
     * @return Los puntos que otorga a Pacman comer una galleta.
     */
    public int getPuntos(){
        return puntos;
    }
}