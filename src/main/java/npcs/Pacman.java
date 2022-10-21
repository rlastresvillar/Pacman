package npcs;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 * Clase modelo de Pacman.
 * Se implementan todas sus características propias
 * @author Rubén Lastres Villar
 * @version 1.0 (15-05-2014)
 */
public class Pacman{
    //Variables que contienen las rutas de las imágenes de Pacman para todas las direcciones en
    //las que se puede mover (arriba, abajo, derecha, izquierda).
    private String pacman_arriba = "imagenes/pacman/arriba/pacman_arriba.gif";
    private String pacman_abajo = "imagenes/pacman/abajo/pacman_abajo.gif";
    private String pacman_der = "imagenes/pacman/derecha/pacman_der.gif";
    private String pacman_izq = "imagenes/pacman/izquierda/pacman_izq.gif";
    
    // Variables de desplazamiento en el eje x y en el eje y.
    // Las usaremos para determinar el desplazamiento de Pacman
    private int dx;
    private int dy;
    
    // Variables que indican el siguiente movimiento que quiere hacer Pacman.
    // Si el  movimiento está permitido (si Pacman no está colisionando contra un muro
    // en la dirección del desplazamiento deseado) el movimiento se realiza.
    // Sino, el desplazzamiento se realizará en el momento en el que Pacman no esté
    // colisionando contra un muro en esa dirección.
    private int siguiente_x;
    private int siguiente_y;
    
    // Variables que determinan las coordenadas de Pacman en los ejes x e y.
    private int x;
    private int y;
    
    // Posición inicial de Pacman.
    private int x_inicial;
    private int y_inicial;
    
    // Constantes con el ancho y alto de la imágen del Pacman
    int width;
    int height;
    
    // Variable que contendrá un String que indicará si Pacman está vivo o ha muerto
    // (ha perdido una vida o todas).
    private String estado;
    
    // Variable que contendrá la tecla que se ha pulsado por última vez.
    private int direccion;
    
    // Las banderas que nos dirán si Pacman está colisionando contra un muro y en que dirección.
    // Son seteadas desde el Controlador.
    private boolean bandera_der;
    private boolean bandera_izq;
    private boolean bandera_sup;
    private boolean bandera_inf;
    private boolean bandera_teleport_d;
    private boolean bandera_teleport_i;
    
    // Un objeto de la clase Image.
    // Contendrá la imágen de Pacman que se pasará al Controlador-Vista
    private Image imagen;
    
    // La constante velocidad de Pacman
    // Es la velocidad a la que se moverá el Pacman.
    private static final int VELOCIDAD = 1;
    
    /**
     * Crea un objeto de la clase Pacman.<br/>
     * Se establece:
     * <ul>
     * <li>El tamaño de Pacman, en las variables width y height.</li>
     * <li>Se inicializa la variable direccion, que recogerá la tecla que se ha pulsado en el teclado.</li>
     * <li>Se inicializan las variables del siguiente movimiento de Pacman.</li>
     * <li>Se inicializan las variables dx y dy, que indican el desplazamiento de Pacman </li>
     * <li>Todas las banderas que indican si Pacman ha colisionado con los muros del mapa o con las casillas de teletransporte se inicializarán como false.</li>
     * </ul>
     */
    public Pacman(){
        
        // Inicializo las variables de ancho y alto del Pacman, con su tamaño.
        width = 21;
        height = 21;
        
        // Inicializo la variable dirección, (de tipo int).
        // Esta variable nos dirá que tecla está siendo pulsada. 
        // Inicialmente será 0.
        direccion = 0;
        
        // Inicialmente, las variables del siguiente movimiento de Pacman serán 0,0 (eje x, eje y).
        siguiente_x = 0;
        siguiente_y = 0;
        
        // Inicialmente Pacman se desplazará hacia la izquierda desde su posición inicial.
        dx = -1;
        dy = 0;
        
        
        estado = "vivo";
        
        // Las banderas, por defecto, estarán seteadas con el valor FALSE.
        bandera_der = false;
        bandera_izq = false;
        bandera_sup = false;
        bandera_inf = false;
        bandera_teleport_d = false;
        bandera_teleport_i = false;

    }
    
    /**
     * Le indica a Pacman que debe moverse en cada golpe de timer.<br/>
     * Sus coordenadas se modificarán el desplazamiento que indiquen las variables dx y dy<br/>
     * Todas las banderas de colisión se setearán en false.
     */
    public void move(){
        
       x += dx;
       y += dy;
       
       // Establecemos todas las banderas con el valor FALSE.
       bandera_der = false;
       bandera_izq = false;
       bandera_sup = false;
       bandera_inf = false;
       bandera_teleport_d = false;
       bandera_teleport_i = false;
       
    }
    
    /**
     * Devuelve la coordenada X de Pacman al controlador, para que pueda pintar a Pacman en
     * su posición actualizada.
     * @return La coordenada X donde está ubicado Pacman.
     */
    public int getX(){
        return x;
    }
    
    /**
     * Devuelve la coordenada Y de Pacman al controlador, para que pueda pintar a Pacman en
     * su posición actualizada.
     * @return La coordenada Y donde está ubicado Pacman.
     */
    public int getY(){
        return y;
    }
    
    /**
     * Este método sirve para que la clase modelo de Pacman devuelva al controlador
     * la imágen de Pacman para que la vista lo pueda pintar en el JPanel.<br/>
     * Según la tecla pulsada, que indica la dirección en la que se está moviendo Pacman, devolverá una imágen de Pacman u otra.
     * @return La imagen de Pacman.
     */
    public Image getImage(){
        
        if (dx == 0 && dy == -1){
            ImageIcon ii = new ImageIcon(this.getClass().getResource(pacman_arriba));
            imagen = ii.getImage();
        }
        
        if (dx == 0 && dy == 1){
            ImageIcon iii = new ImageIcon(this.getClass().getResource(pacman_abajo));
            imagen = iii.getImage();
        }
        
        if (dx == 1 && dy == 0){
            ImageIcon iiii = new ImageIcon(this.getClass().getResource(pacman_der));
            imagen = iiii.getImage();
        }
        
        if (dx == -1 && dy == 0){
            ImageIcon iiiii = new ImageIcon(this.getClass().getResource(pacman_izq));
            imagen = iiiii.getImage();
        }
        
        return imagen;
        
    }
     
    /**
     * Detecta las pulsaciones del teclado. <br/>
     * Cada vez que se pulsa una tecla se llama a este método para actualizar el siguiente movimiento que quiere hacer Pacman.
     * @param KeyEvent
     */
    public void keyPressed(KeyEvent e){
        
        // Devuelve la tecla que se ha pulsado.
        direccion = e.getKeyCode();
        
        switch (direccion){
            // Si se pulsa la tecla izquierda, se restaría 1 en el eje x a la coordenada x.
            // El siguiente movimiento que quiere hacer Pacman es x = -1 , y = 0
            case KeyEvent.VK_LEFT:
                siguiente_x = -VELOCIDAD;
                siguiente_y = 0;
            break;
            
            // Si se pulsa la tecla derecha, se sumaría 1 en el eje x a la coordenada x.
            // El siguiente movimiento que quiere hacer Pacman es x = 1 , y = 0
            case KeyEvent.VK_RIGHT:
                siguiente_x = VELOCIDAD;
                siguiente_y = 0;
            break;
                
            // Si se pulsa la tecla arriba, se restaría 1 en el eje y a la coordenada y.  
            // El siguiente movimiento que quiere hacer Pacman es x = 0 , y = -1
            case KeyEvent.VK_UP:
                siguiente_y = -VELOCIDAD;
                siguiente_x = 0;
            break;
                
            // Si se pulsa la tecla abajo, se sumaría 1 en el eje y a la coordenada y.  
            // El siguiente movimiento que quiere hacer Pacman es x = 0 , y = 1
            case KeyEvent.VK_DOWN:
                siguiente_y = VELOCIDAD;
                siguiente_x = 0;
            break;
               
        }
        
        
    }
    
    /**
     * Método para obtener, desde la clase Tablero, los límites de la imagen de Pacman para poder
     * detectar colisiones con los muros, las galletas y los fantasmas.
     * @return Un objeto de la clase Rectangle sobre la imagen de Pacman.
     */
    public Rectangle getBounds(){
        return new Rectangle (x, y, width, height);
    }
    
    /**
     * Método para modificar la bandera de colisión izquierda
     * @param boolean Un booleano que pone la bandera en true o false
     */
    public void setBanderaIzq (boolean b){
        bandera_izq = b;
    }
    
    /**
     * Método para modificar la bandera de colisión derecha
     * @param boolean Un booleano que pone la bandera en true o false
     */
    public void setBanderaDer(boolean b){
        bandera_der = b;
    }
    
    /**
     * Método para modificar la bandera de colisión superior
     * @param boolean Un booleano que pone la bandera en true o false
     */
    public void setBanderaSup(boolean b){
        bandera_sup = b;
    }
    
    /**
     * Método para modificar la bandera de colisión inferior
     * @param boolean Un booleano que pone la bandera en true o false
     */
    public void setBanderaInf(boolean b){
        bandera_inf = b;
    }
    
    /**
     * Método para modificar la bandera que le indica a Pacman si está sobre la casilla de teletransporte derecho
     * @param boolean Un booleano que pone la bandera en true o false
     */
    public void setTeleportDer(boolean b){
        bandera_teleport_d = b;
    }
    
    /**
     * Método para modificar la bandera que le indica a Pacman si está sobre la casilla de teletransporte izquierdo
     * @param boolean Un booleano que pone la bandera en true o false
     */
    public void setTeleportIzq(boolean b){
        bandera_teleport_i = b;
    }
    
    /**
     * Método para modificar el estado actual de Pacman.<br/>
     * Si Pacman está en juego, la variable estado contendrá la cadena "vivo". Si Pacman ha perdido una vida, la variable estado se seteará como "muerto"
     * @param String String para modificar el estado de Pacman.
     */
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    /** 
     * Método que establece a Pacman en su posición inicial dentro del laberinto.<br/>
     * También se guarda en las variables x_inicial e y_inicial la posición inicial, para que cuando Pacman pierda una vida
     * poder reubicarlo en dicha posición.
     * @param int Dos enteros que representan las coordenadas x e y.
     */
    public void setPosInicial(int x, int y){
        this.x = x;
        this.y = y;
        x_inicial = x;
        y_inicial = y;
    }
    
    /**
     * Método para reubicar a Pacman en su posición inicial.
     */
    public void resetPosInicial(){
        x = x_inicial;
        y = y_inicial;
        dx = -1;
        dy = 0;
        siguiente_x = -1;
        siguiente_y = 0;
        estado = "muerto";
    }
    
    /**
     * Devuelve la coordenada X en la que se sitúa Pacman al inicio de cada partida, o cuando pierde una vida.
     * @return La posición inicial de Pacman en el eje X
     */
    public int getXInicial(){
        return x_inicial;
    }
    
    /**
     * Devuelve la coordenada Y en la que se sitúa Pacman al inicio de cada partida, o cuando pierde una vida.
     * @return La posición inicial de Pacman en el eje Y
     */
    public int getYInicial(){
        return y_inicial;
    }
    
    /**
     * Este método establece si un movimiento deseado (que es el movimiento guardado en las variables siguiente_x y siguiente_y)
     * es posible (valido) o no (inválido).<br/><br/>
     * 
     * En el caso de que el movimiento sea inválido, se mantendrán las variables de desplazamiento (dx y dy) en sus valores actuales.<br/>
     * En el caso de que el movimiento deseado sea válido, se actualizarán las variables de desplazamiento (dx y dy) con los valores
     * de siguiente_x y siguiente_y).<br/><br/>
     * 
     * Si Pacman está colisionando contra un muro y se desea mover en esa dirección, las variables dx y dy se establecerán a 0 y, por
     * consiguiente, Pacman se quedará parado contra el muro.<br/><br/>
     * 
     * Cuando Pacman está en una casilla de teletransporte, deberá moverse al otro lado del mapa.<br/><br/>
     * 
     * Finalmente se llama al método move() que ordena a Pacman que se mueva.
     */
    public void checkMov(){
       
        if(estado == "vivo"){
            
            // Si la bandera izquierda es true, Pacman no se podrá mover en esa dirección
            if(bandera_izq == true && siguiente_x == -1 && siguiente_y == 0){
                
                // Movimiento inválido. No actualizamos dx ni dy y Pacman sigue moviéndose en la dirección previa.
                
            }
            // Si la bandera izquierda es false, Pacman podrá moverse en esa dirección.
            else if(bandera_izq == false && siguiente_x == -1 && siguiente_y == 0){
                
                dx = siguiente_x;
                dy = siguiente_y;
                
            }
            
            // Si la bandera derecha es true, Pacman no se podrá mover en esa dirección
            if(bandera_der == true && siguiente_x == 1 && siguiente_y == 0){
                
                // Movimiento inválido. No actualizamos dx ni dy y Pacman sigue moviéndose en la dirección previa.
                
            }
            // Si la bandera derecha es false, Pacman podrá moverse en esa dirección.
            else if(bandera_der == false && siguiente_x == 1 && siguiente_y == 0){
                
                dx = siguiente_x;
                dy = siguiente_y;
                
            }
            
            // Si la bandera superior es true, Pacman no se podrá mover en esa dirección
            if(bandera_sup == true && siguiente_x == 0 && siguiente_y == -1){
                
                // Movimiento inválido. No actualizamos dx ni dy y Pacman sigue moviéndose en la dirección previa.
                
            }
            // Si la bandera superior es false, Pacman podrá moverse en esa dirección.
            else if(bandera_sup == false && siguiente_x == 0 && siguiente_y == -1){
                
                dx = siguiente_x;
                dy = siguiente_y;
                
            }
            
            // Si la bandera inferior es true, Pacman no se podrá mover en esa dirección
            if(bandera_inf == true && siguiente_x == 0 && siguiente_y == 1){
                
                // Movimiento inválido. No actualizamos dx ni dy y Pacman sigue moviéndose en la dirección previa.
                
            }
            // Si la bandera inferior es false, Pacman podrá moverse en esa dirección.
            else if(bandera_inf == false && siguiente_x == 0 && siguiente_y == 1){
                
                dx = siguiente_x;
                dy = siguiente_y;
                
            }
            
            
            //CHEQUEO COLISIÓN CONTRA MURO. ESTOS SON LOS CUATRO CASOS EN LOS QUE PACMAN DEBE PERMANECER PARADO.
            if(bandera_izq == true && dx == -1 && dy == 0){
                dx= 0;
                dy = 0;
            }
            
            if(bandera_sup == true && dx == 0 && dy == -1){
                dx= 0;
                dy = 0;
            }
            
            if(bandera_inf == true && dx == 0 && dy == 1){
                dx= 0;
                dy = 0;
            }
            
            if(bandera_der == true && dx == 1 && dy == 0){
                dx= 0;
                dy = 0;
            }
            
            
            // CHEQUEA CUANDO PACMAN COINCIDE CON UNA CELDA DE TELETRANSPORTE
            // Si coincide con la casilla de teletransporte izquierda, el desplazamiento se aumenta en el ancho
            // del JPanel - 1 px.
            if(bandera_teleport_i == true){
                dx = 567- 1;
            }
            
            // Si coincide con la casilla de teletransporte derecha, el desplazamiento se adisminuye en el ancho
            // del JPanel + 1 px.
            if(bandera_teleport_d == true){
                dx = -567 + 1;
            }
            
            // Se llama al método move().
            move();
        }
    }
}