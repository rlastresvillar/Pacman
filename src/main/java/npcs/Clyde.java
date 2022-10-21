package npcs;
import javax.swing.Timer;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Clase modelo del fantasma Clyde, que hereda de la superclase Fantasmas
 * Se implementan todas las características distintivas de Clyde.
 * @author Rubén Lastres Villar
 * @version 1.0 (15-05-2014)
 */
public class Clyde extends Fantasma implements ActionListener{
      
    /**
     * Se definen las rutas de las diferentes imágenes que representan a Clyde. 
     * Serán 4 objetos Image (una por cada dirección de movimiento de Clyde) y las imágenes de vulnerabilidad.
     * También se establece la posición inicial de Clyde y se crea y arranca el timer.
     */
    public Clyde(){     
        // Llamada al constructor de la superclase Fantasma
        super();
        
        // Rutas a las imágenes del fantasma Clyde
        imagen_arriba = "/imagenes/fantasmas/clyde/arriba.gif";
        imagen_abajo = "/imagenes/fantasmas/clyde/abajo.gif";
        imagen_derecha = "/imagenes/fantasmas/clyde/derecha.gif";
        imagen_izquierda = "/imagenes/fantasmas/clyde/izquierda.gif";
        imagen_vulnerable = "/imagenes/fantasmas/vulnerable/vulnerable.gif";
        imagen_resucitando = "/imagenes/fantasmas/vulnerable/vulnerable2.gif";
    
        // Inicialización del timer.
        timer = new Timer(3000,this);
        timer.start();    
    }
    
    /**
     * Método que calcula un nuevo movimiento aleatorio de Clyde en cada golpe de timer. 
     * Para hacer esto, se crea un número aleatorio entre 0 y 1 y se le asigna un signo positivo o negativo, 
     * para generar uno de entre tres posibles valores (0, 1 y -1), ya que los movimientos sólo pueden adoptar
     * uno de estos tres valores.
     * Cuando se detecta que Pacman ha comido una galleta grande, el fantasma entra en modo de vulnerabilidad 
     * durante 6 segundos (dos golpes de timer, que se cuentan gracias a la variable tiempo_vulnerable,
     * que ejerce de contador). Cuando entra en modo de vulnerabilidad, al primer golpe de timer siguiente
     * (cuando han transcurrido 3 segundos) el fantasma entra en modo de resucitación para indicar que le queda 
     * poco tiempo en ese estado antes de volver a su estado normal. 
     */
    public void actionPerformed(ActionEvent e){
        
        // Se crea una instancia de la clase Random.
        Random random = new Random();
        
        // Se asigna a la variable siguiente_x un valor entero aleatorio entre 0 y 1
        siguiente_x = random.nextInt(2);
        
        // Se establece un signo (positivo o negativo) al movimiento aleatorio
        int signo = random.nextInt(2);
        
        // Se asignan a las variables siguiente_x y siguiente_y dos valores aleatorios
        // que indicarán el siguiente movimiento que quiere hacer el fantasma.
        if(siguiente_x == 1){
           if (signo == 0){
               siguiente_x = 1;
            }else{
               siguiente_x = -1;
            }
            siguiente_y = 0;
        }
        else{
            if (signo == 0){
               siguiente_y = 1;
            }else{
               siguiente_y = -1;
            }
        }
        
        if(vulnerable){
            
            tiempo_vulnerable++;
            
            if(tiempo_vulnerable == 1){
               resucitando = true; 
            }
            
            if(tiempo_vulnerable == 2){
                vulnerable = false;
                resucitando = false;
                tiempo_vulnerable = 0;
            }

        }
        
    }
    
    /**
     * Genera un movimiento aleatorio para Clyde, dependiendo del estado de las banderas de colisión. 
     * Este método es llamado cada vez que Clyde colisiona contra un muro o contra otro fantasma.<br/><br/>
     * El ArrayList movimientos_validos contiene, inicialmente, los movimientos que el fantasma puede realizar (Arriba, Abajo, Derecha e Izquierda).
     * Si una bandera está seteada con el valor true, eliminaremos ese movimiento del ArrayList de movimientos_validos, para indicar a Clyde que ese
     * no es un movimiento que pueda hacer en ese momento.<br/><br/>
     * Una vez se establece el ArrayList con los movimientos que puede realizar Clyde en ese momento (y sólo con esos movimientos) se escoje uno 
     * de ellos para que Clyde lo lleve a cabo.
     */
    public void generarMovimiento(){
        
        // Eliminamos del ArrayList todos los movimientos que Clyde no puede hacer en este momento.
        // Esta eliminación se lleva a cabo según el estado de las banderas de colisión.
        if(bandera_izq){
            movimientos_validos.remove("Izquierda");
        }
        
        if(bandera_der){
            movimientos_validos.remove("Derecha");
        }
        
        if(bandera_sup){
            movimientos_validos.remove("Arriba");
        }
        
        if(bandera_inf){
            movimientos_validos.remove("Abajo");
        }
        
        // Si el array de movimientos está vacío, quiere decir que el fantasma ha resucitado en su casilla pero
        // estaba ocupada por otro fantasma. En este caso lo desplazamos 21 pixeles hacia la derecha
        if(movimientos_validos.isEmpty()){
            siguiente_x = 21;
            siguiente_y= 0;
        }
        
        // Si no, si el resultado de la acción anterior es que el ArrayList queda con un sólo movimiento válido, 
        // Clyde lo realiza.
        else if(movimientos_validos.size()==1){
            
            // Se obtiene el movimiento del ArrayList
            String movimiento = movimientos_validos.get(0);
            
            // Y se establece el siguiente movimiento
            if(movimiento == "Arriba"){
                siguiente_x = 0;
                siguiente_y = -1;
            }else if(movimiento == "Abajo"){
                siguiente_x = 0;
                siguiente_y = 1;
            }else if(movimiento == "Derecha"){
                siguiente_x = 1;
                siguiente_y = 0;
            }else if(movimiento == "Izquierda"){
                siguiente_x = -1;
                siguiente_y = 0;
            }
            
        }
        
        // Si no, si en el ArrayList hay más de un movimiento válido, se escoge uno al azar y Clyde lo lleva a cabo.
        else{
            
            // Se crea un objeto de la clase Random.
            Random random = new Random();
            
            // Se crea un entero aleatorio dentro del límite fijado por el tamaño del ArrayList
            int pos_aleatoria = random.nextInt(movimientos_validos.size());
            
            // Se escoge ese movimiento del ArrayList
            String movimiento = movimientos_validos.get(pos_aleatoria);
            
            // Finalmente se establece el siguiente movimiento aleatorio
            if(movimiento == "Arriba"){
                
                siguiente_x = 0;
                siguiente_y = -1;
                
            }else if(movimiento == "Abajo"){
                
                siguiente_x = 0;
                siguiente_y = 1;
                
            }else if(movimiento == "Derecha"){
                
                siguiente_x = 1;
                siguiente_y = 0;
                
            }else if(movimiento == "Izquierda"){
                
                siguiente_x = -1;
                siguiente_y = 0;
                
            }
            
        }
    }

}
