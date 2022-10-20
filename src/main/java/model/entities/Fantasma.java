package model.entities;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * Superclase de las clases Clyde, Blinky y Pinky.<br/><br/>
 * Establece todos los campos y métodos comunes a los tres fantasmas.
 * @author Rubén Lastres Villar
 * @version 1.0 (15-05-2014)
 */
public abstract class Fantasma
{

    /** Contiene la imagen que se ha de mostrar del fantasma cuando se está desplazando hacia arriba. */
    protected String imagen_arriba;
    
    /** Contiene la imagen que se ha de mostrar del fantasma cuando se está desplazando hacia abajo. */
    protected String imagen_abajo;
    
    /** Contiene la imagen que se ha de mostrar del fantasma cuando se está desplazando hacia la derecha. */
    protected String imagen_derecha;
    
    /** Contiene la imagen que se ha de mostrar del fantasma cuando se está desplazando hacia la izquierda. */
    protected String imagen_izquierda;
    
    /** Contiene la imagen que se ha de mostrar del fantasma cuando está en modo vulnerable. */
    protected String imagen_vulnerable;
    
    /** Contiene la imagen que se ha de mostrar del fantasma cuando está en modo vulnerable y a punto de volver a su estado normal. */
    protected String imagen_resucitando;
    
    /** Puntuación que le otorga a Pacman el comer un fantasma */
    private static final int PUNTOS = 100;
    
    /** Ancho de la imagen del fantasma en pixeles */
    public static final int WIDTH = 21;
    
    /** Alto de la imagen del fantasma en pixeles */
    public static final int HEIGHT = 21;
    
    /** Desplazamiento del fantasma en el eje X. */
    protected int dx;
    
    /** Desplazamiento del fantasma en el eje Y. */
    protected int dy;
    
    /** Indica el siguiente movimiento en el eje X que quiere realizar el fantasma<br/><br/>
     * Si el  movimiento está permitido (si el fantasma no está colisionando contra un muro
     * o contra otro fantasma en la dirección del desplazamiento deseado) el movimiento se realiza.
     * Sino, el desplazamiento se realizará en el momento en el que el fantasma no esté
     * colisionando contra un muro o contra un fantasma en esa dirección.
    */
    protected int siguiente_x;
    
    /** Indica el siguiente movimiento en el eje Y que quiere realizar el fantasma<br/><br/>
     * Si el  movimiento está permitido (si el fantasma no está colisionando contra un muro
     * o contra otro fantasma en la dirección del desplazamiento deseado) el movimiento se realiza.
     * Sino, el desplazamiento se realizará en el momento en el que el fantasma no esté
     * colisionando contra un muro o contra un fantasma en esa dirección.
    */
    protected int siguiente_y;
    
    /** Contiene la coordenada X del fantasma.*/
    protected int x;
    
    /** Contiene la coordenada Y del fantasma.*/
    protected int y;
    
    /** Posición inicial del fantasma en el eje X. */
    protected int x_inicial;
    
    /** Posición inicial del fantasma en el eje Y. */
    protected int y_inicial;
    
    /** Contiene la imágen del fantasma. */
    protected Image imagen;
    
    /** La velocidad del fantasma (los píxeles que se mueve en cada golpe de timer. */
    private static final int VELOCIDAD = 1;
    
    /** Bandera que establece si el fantasma está colisionando con un muro o con otro fantasma hacia la derecha. */
    protected boolean bandera_der;
    
    /** Bandera que establece si el fantasma está colisionando con un muro o con otro fantasma hacia la izquierda. */
    protected boolean bandera_izq;
    
    /** Bandera que establece si el fantasma está colisionando con un muro o con otro fantasma hacia arriba. */
    protected boolean bandera_sup;
    
    /** Bandera que establece si el fantasma está colisionando con un muro o con otro fantasma hacia abajo. */
    protected boolean bandera_inf;
    
    /** Bandera que establece si el fantasma está sobre la celda de teletransporte derecho. */
    protected boolean bandera_teleport_d;
    
    /** Bandera que establece si el fantasma está sobre la celda de teletransporte izquierdo. */
    protected boolean bandera_teleport_i;
    
    /** Bandera para que el fantasma sepa si debe estar en modo vulnerable */
    protected boolean vulnerable;
    
    /** Bandera que indica al fantasma que le queda poco tiempo para que salga de un estado de vulnerabilidad */
    protected boolean resucitando;
    
    /** Un contador que ayudará a calcular el tiempo que el fantasma tenga que
      * permanecer en estado vulnerable, cuando Pacman coma una galleta grande.
    **/
    protected int tiempo_vulnerable;
    
    /** ArrayList con los movimientos válidos que puede realizar el fantasma en cada momento. */
    protected ArrayList<String> movimientos_validos;
    
    /** Timer, para calcular movimientos aleatorios del fantasma en cada golpe de reloj. */
    protected Timer timer;
    
    /**
     * Constructor de la clase Fantasma.<br/><br/>
     */
    public Fantasma(){
        
        //Establezco un movimiento inicial para el fantasma
        dx = -1;
        dy = 0;
        
        // Se inicializa a 0 el contador de vulnerabilidad.
        tiempo_vulnerable = 0;
        
        // A la hora de crear la instancia, las banderas de colisión contra los muros estarán seteadas
        // en false (el fantasma se podrá mover en cualquier dirección).
        bandera_der = false;
        bandera_izq = false;
        bandera_sup = false;
        bandera_inf = false;
        bandera_teleport_d = false;
        bandera_teleport_i = false;
        vulnerable = false;
        resucitando = false;
        
        // Inicialmente el fantasma no estará colisionando con ningún muro.
        movimientos_validos = new ArrayList<String>();
        movimientos_validos.add("Arriba");
        movimientos_validos.add("Abajo");
        movimientos_validos.add("Izquierda");
        movimientos_validos.add("Derecha");
        
        
    }
    
    /**
     * El fantasma se mueve (actualiza sus coordenadas según los valores de las variables dx y dy) cada vez que este método es llamado.<br/><br/>
     * Sus coordenadas se modificarán en cada golpe del Timer y lo hará según el desplazamiento que indiquen las variables dx y dy<br/><br/>
     * Todas las banderas de colisión se setearán en false.<br/><br/>
     * Por último, se reestablece el ArrayList de movimientos válidos a su estado inicial.
     */
    public void move(){
       
      // Se actualizan las coordenadas del fantasma.
      x = x + dx;
      y = y + dy;
      
      // Vuelvo a poner todas las banderas en false.
      bandera_der = false;
      bandera_izq = false;
      bandera_sup = false;
      bandera_inf = false;
      bandera_teleport_d = false;
      bandera_teleport_i = false;
      
      // Restauramos el Array de movimientos válidos.
      movimientos_validos.clear();
      movimientos_validos.add("Arriba");
      movimientos_validos.add("Abajo");
      movimientos_validos.add("Izquierda");
      movimientos_validos.add("Derecha");
      
    }
    
     /**
     * Chequea si un movimiento del fantasma es válido o no. <br/><br/>
     * Lo hace en tres pasos:
     * <ul>
     * <li>Comprueba si el siguiente movimiento que desea hacer el fantasma es un movimiento válido o no lo es.</li>
     * <li>Comprueba si el fantasma ha colisionado contra un muro o contra otro fantasma. Si lo ha hecho se generará otro
     * movimiento mediante una llamada al método generarMovimiento()</li>
     * <li>Comprueba si el fantasma ha llegado a una celda de teletransporte; si lo ha hecho, se le ordena que se mueva
     * en sentido opuesto</li>
     * </ul>
     */
    public void checkMov(){
        
        // ----------------------------------------------------------------------------------------------------------//
        // ----------------------------------------------------------------------------------------------------------//
        // Se comprueba si el siguiente movimiento que quiere realizar el fantasma es válido o no. Si es válido, lo realizará;
        // si no lo es simplemente no hará nada.
        if(bandera_izq == true && siguiente_x == -1 && siguiente_y == 0){

            // Movimiento inválido. No actualizamos dx ni dy y el fantasma sigue moviéndose en la dirección previa.
            // Estamos avanzando en una dirección, se crean un nuevo movimiento y no se puede realizar porque hay un muro hacia donde se quiere mover.
            
        }else if(bandera_izq == false && siguiente_x == -1 && siguiente_y == 0){
            
            //Cuando hacia la izquierda haya un camino, ejecutará el movimiento.
            dx = siguiente_x;
            dy = siguiente_y;
            
        }
        
        if(bandera_der == true && siguiente_x == 1 && siguiente_y == 0){

            // Movimiento inválido. No actualizamos dx ni dy y el fantasma sigue moviéndose en la dirección previa.
            // Estamos avanzando en una dirección, se crean un nuevo movimiento y no se puede realizar porque hay un muro hacia donde se quiere mover.
            
        }else if(bandera_der == false && siguiente_x == 1 && siguiente_y == 0){
            
            //Cuando hacia la derecha haya un camino, ejecutará el movimiento.
            dx = siguiente_x;
            dy = siguiente_y;
            
        }
        
        if(bandera_sup == true && siguiente_x == 0 && siguiente_y == -1){

            // Movimiento inválido. No actualizamos dx ni dy y el fantasma sigue moviéndose en la dirección previa.
            // Estamos avanzando en una dirección, se crean un nuevo movimiento y no se puede realizar porque hay un muro hacia donde se quiere mover.
            
        }else if(bandera_sup == false && siguiente_x == 0 && siguiente_y == -1){
            
            //Cuando hacia arriba haya un camino, ejecutará el movimiento.
            dx = siguiente_x;
            dy = siguiente_y;
            
        }
        
        if(bandera_inf == true && siguiente_x == 0 && siguiente_y == 1){

            // Movimiento inválido. No actualizamos dx ni dy y el fantasma sigue moviéndose en la dirección previa.
            // Estamos avanzando en una dirección, se crean un nuevo movimiento y no se puede realizar porque hay un muro hacia donde se quiere mover.
            
        }else if(bandera_inf == false && siguiente_x == 0 && siguiente_y == 1){
            
            //Cuando hacia abajo haya un camino, ejecutará el movimiento.
            dx = siguiente_x;
            dy = siguiente_y;
            
        }
        // ----------------------------------------------------------------------------------------------------------//
        // ----------------------------------------------------------------------------------------------------------//
        
        // ----------------------------------------------------------------------------------------------------------//
        // ----------------------------------------------------------------------------------------------------------//
        // CHEQUEO COLISIÓN CONTRA MURO. Estos son los casos en los que el fantasma colisiona contra un muro.
        // Se comprueba en que dirección se movía cuando ha colisionado y se hace una llamada al método
        // generarMovimiento() para que cree otro nuevo movimiento para el fantasma en una dirección válida.
        int contador = 0;
        if(bandera_izq == true && dx == -1 && dy == 0){
            dx = 0;
            dy = 0;
            contador ++;
        }
        
        if(bandera_sup == true && dx == 0 && dy == -1){
            dx = 0;
            dy = 0;
            contador++;
        }
        
        if(bandera_inf == true && dx == 0 && dy == 1){
            dx = 0;
            dy = 0;
            contador++;
        }
        
        if(bandera_der == true && dx == 1 && dy == 0){
            dx = 0;
            dy = 0;
            contador++;
        }
        
        if(contador > 0){
           generarMovimiento(); 
        }
        // ----------------------------------------------------------------------------------------------------------//
        // ----------------------------------------------------------------------------------------------------------//
        
        // ----------------------------------------------------------------------------------------------------------//
        // ----------------------------------------------------------------------------------------------------------//
        // Se comprueba si el fantasma ha llegado a una celda de teletransporte. Si es así, se mueve en la dirección opuesta.
        if(bandera_teleport_i == true){
            siguiente_x = 1;
            siguiente_y = 0;
        }
        
        if(bandera_teleport_d == true){
            siguiente_x = -1;
            siguiente_y = 0;
        }
        // ----------------------------------------------------------------------------------------------------------//
        // ----------------------------------------------------------------------------------------------------------//
        
        // Le decimos al fantasma que se mueva.
        move();
    }
    
    /**
     * Genera un nuevo movimiento del fantasma cuando colisiona contra un muro
     * o contra otro fantasma. Será sustituido en cada subclase, ya que cada
     * fantasma implementa una generación de movimientos diferente.
     */
    public void generarMovimiento(){}
    
    /**
     * Devuelve la coordenada X actual al controlador, para que pueda pintar al fantasma en
     * su posición actualizada
     * @return La coordenada X en la que se encuentra el fantasma dentro del mapa
     */
    public int getX(){
        return x;
    }
    
    /**
     * Devuelve la coordenada Y actual al controlador, para que pueda pintar al fantasma en
     * su posición actualizada
     * @return La coordenada Y en la que se encuentra el fantasma dentro del mapa
     */
    public int getY(){
        return y;
    }
    
    /**
     * Método para obtener, desde la clase Tablero, los límites de la imagen del fantasma para poder
     * detectar colisiones con los muros, con Pacman y con otros fantasmas.
     * @return Un objeto de la clase Rectangle sobre la imagen del fantasma.
     */
    public Rectangle getBounds(){
        return new Rectangle (x, y, WIDTH, HEIGHT);
    }
    
    /**
     * Devuelve la cantidad de puntos que ha de sumarse a la puntuación cada vez que Pacman come un fantasma.
     * @return Los puntos que otorga comer al fantasma.
     */
    public int getPuntos(){
        return PUNTOS;
    }
    
    /**
     * Devuelve la imágen del fantasma para que la vista lo pueda pintar en el JPanel.<br/><br/>
     * Dependiendo de la dirección en la que se esté desplazando el fantasma, se devolverá una imagen u otra. 
     * Cuando la bandera de vulnerabilidad del fantasma está seteada como true, se devolverá una imagen común a todos los fantasmas. 
     * Si además de estar la bandera de vulnerabilidad seteada en true, la bandera de resucitando también lo está, se mostrará
     * una imagen parpadeante del fantasma para indicar que está a punto de volver a su estado normal.
     * @return La imagen del fantasma.
     */
    public Image getImage(){
        
        if (dx == 0 && dy == -1){
            ImageIcon ii = new ImageIcon(this.getClass().getResource(imagen_arriba));
            imagen = ii.getImage();
        }
        
        if (dx == 0 && dy == 1){
            ImageIcon iii = new ImageIcon(this.getClass().getResource(imagen_abajo));
            imagen = iii.getImage();
        }
        
        if (dx == 1 && dy == 0){
            ImageIcon iiii = new ImageIcon(this.getClass().getResource(imagen_derecha));
            imagen = iiii.getImage();
        }
        
        if (dx == -1 && dy == 0){
            ImageIcon iiiii = new ImageIcon(this.getClass().getResource(imagen_izquierda));
            imagen = iiiii.getImage();
        }
        
        if(vulnerable){
            ImageIcon iiiii = new ImageIcon(this.getClass().getResource(imagen_vulnerable));
            imagen = iiiii.getImage();
        }
        
        if(vulnerable && resucitando){
            ImageIcon iiiii = new ImageIcon(this.getClass().getResource(imagen_resucitando));
            imagen = iiiii.getImage();
        }
        
        return imagen;
        
    } 
    
    /**
     * Método para modificar la bandera de colisión izquierda.
     * @param boolean Un booleano que pone la bandera en true o false.
     */
    public void setBanderaIzq (boolean b){
        bandera_izq = b;
    }
    
    /**
     * Método para modificar la bandera de colisión derecha.
     * @param boolean Un booleano que pone la bandera en true o false.
     */
    public void setBanderaDer(boolean b){
        bandera_der = b;
    }
    
    /**
     * Modificar la bandera de colisión superior.
     * @param boolean Un booleano que pone la bandera en true o false.
     */
    public void setBanderaSup(boolean b){
        bandera_sup = b;
    }
    
    /**
     * Modificar la bandera de colisión inferior.
     * @param boolean Un booleano que pone la bandera en true o false.
     */
    public void setBanderaInf(boolean b){
        bandera_inf = b;
    }
    
    /**
     * Modifica la bandera que le indica al fantasma si está sobre la casilla de teletransporte derecho
     * @param boolean Un booleano que pone la bandera en true o false
     */
    public void setTeleportDer(boolean b){
        bandera_teleport_d = b;
    }
    
    /**
     * Modifica la bandera que le indica al fantasma si está sobre la casilla de teletransporte izquierdo
     * @param boolean Un booleano que pone la bandera en true o false
     */
    public void setTeleportIzq(boolean b){
        bandera_teleport_i = b;
    }

    /** 
     * Método que establece la posición inicial del fantasma dentro del laberinto.<br/><br/>
     * También se guarda en las variables x_inicial e y_inicial la posición inicial, para poder reubicar a los fantasmas en ella
     * cada vez que Pacman pierde una vida o se inicia una nueva partida.
     * @param int Coordenada X en donde debe ubicarse al fantasma.
     * @param int Coordenada Y en donde debe ubicarse al fantasma.
     */
    public void setPosInicial(int x, int y){
        this.x = x;
        this.y = y;
        x_inicial = x;
        y_inicial = y;
    }
    
    /**
     * Reubica al fantasma en su posición de inicio. 
     * Se resetea también el contador de tiempo de vulnerabilidad
     */
    public void resetPosInicial(){
        x = x_inicial;
        y = y_inicial;
        tiempo_vulnerable = 0;
    }
    
    /**
     * Devuelve la coordenada X inicial del fantasma.
     * @return La posición inicial del fantasma en el eje X
     */
    public int getXInicial(){
        return x_inicial;
    }
    
    /**
     * Devuelve la coordenada Y inicial del fantasma.
     * @return La posición inicial del fantasma en el eje Y
     */
    public int getYInicial(){
        return y_inicial;
    }
    
    /**
     * Método para modificar la bandera que le dice al fantasma si está en modo vulnerable o en modo normal.<br/><br/>
     * Este método será llamado para poner a los fantasmas en modo vulnerable, y para que se haga correctamente se realizan
     * las siguientes acciones:
     * <ul>
     * <li>Setear la bandera vulnerable como true.</li>
     * <li>Setear la bandera resucitando como false.</li>
     * <li>Resetear el contador de vulnerabilidad.</li>
     * <li>Detener y poner de nuevo en marcha el timer, para que la vulnerabilidad dure dos ciclos completos.</li>
     * </ul>
     * @param boolean Un valor booleano que establece la bandera de vulnerabilidad del fantasma en true o false.
     */
    public void setVulnerable(boolean b){
        vulnerable = b;
        resucitando = false;
        tiempo_vulnerable = 0;
        timer.stop();
        timer.start();
    }
    
    /**
     * Devuelve el valor de la bandera que indica si el fantasma está en modo vulnerable o en modo normal.
     * @return La bandera de vulnerabilidad del fantasma.
     */
    public boolean getVulnerable(){
        return vulnerable;
    }
    
    /**
     * Método para modificar la bandera que le indica al fantasma si debe entrar en modo de "resucitacion" (es decir, 
     * si le queda poco tiempo para volver a su estado normal desde un estado de vulnerabilidad).
     * @param boolean Un valor booleano que establece la bandera resucitando del fantasma en true o false.
     */
    public void setResucitando(boolean b){
        resucitando = b;
    }

    
    /**
     * Devuelve un valor booleano, que dice si el fantasma está a punto de volver
     * a su estado normal (está saliendo de la vulnerabilidad).
     * @return La bandera de resucitacion del fantasma.
     */
    public boolean getResucitando(){
        return resucitando;
    }
    
    /**
     * Cuando un fantasma es comido, se debe resetear las banderas de vulnerabilidad, resurrección y
     * el contador de tiempo de vulnerabilidad.
     * Este método hace eso precisamente.
     */
    public void resetVulnerable(){
        vulnerable = false;
        resucitando = false;
        tiempo_vulnerable = 0;
    }
}
