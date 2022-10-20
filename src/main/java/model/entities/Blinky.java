package model.entities;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * Clase modelo del fantasma Blinky, que hereda de la superclase Fantasmas<br/><br/>
 * Se implementan todas las características distintivas de Blinky.<br
 * @author Rubén Lastres Villar
 * @version 1.0 (15-05-2014)
 */
public class Blinky extends Fantasma implements ActionListener{
    
    // Posición de Pacman en cada golpe de timer.
    // Para calcular el siguiente movimiento.
    private int x_pacman;
    private int y_pacman;
    
    /**
     * Se definen las rutas de las diferentes imágenes que representan a Blinky. 
     * Serán 4 objetos Image (una por cada dirección de movimiento de Blinky) y las imágenes de vulnerabilidad.<br/><br/>
     * También se establece la posición inicial de Blinky y se crea y arranca el timer.
     */
    public Blinky(){
        
        // Llamada al constructor de la superclase Fantasma
        super();
        
        imagen_arriba = "imagenes/fantasmas/blinky/arriba.gif";
        imagen_abajo = "imagenes/fantasmas/blinky/abajo.gif";
        imagen_derecha = "imagenes/fantasmas/blinky/derecha.gif";
        imagen_izquierda = "imagenes/fantasmas/blinky/izquierda.gif";
        imagen_vulnerable = "imagenes/fantasmas/vulnerable/vulnerable.gif";
        imagen_resucitando = "imagenes/fantasmas/vulnerable/vulnerable2.gif";
        
        // Inicializo el timer...
        timer = new Timer(3000,this);
        // y lo pongo a andar.
        timer.start();

    }
    
    /**
     * Método que calcula un nuevo movimiento aleatorio de Blinky en cada golpe de timer.<br/><br/>
     * Blinky busca colisionar con Pacman intentando acercarse primero verticalmente y luego horizontalmente.
     * Aún así, debemos crear cierta aleatoriedad en el movimiento de Blinky con el fin de evitar bucles y atascos
     * del fantasma dentro del mapa. Así, dependiendo de la posición que ocupa Pacman en el tablero, al generar
     * un movimiento aleatorio en cada golpe de timer buscaremos acercarnos a él en horizontal o en vertical
     * indistintamente. El algoritmo para dar preferencia al acercamiento en vertical no lo implementa este método,
     * sino el método generarMovimiento() que se encarga de crear nuevos movimientos cuando Blinky colisiona
     * con un muro o con otro fantasma.<br/><br/>
     * Cuando se detecta que Pacman ha comido una galleta grande, el fantasma entra en modo de vulnerabilidad 
     * durante 6 segundos (dos golpes de timer, que se cuentan gracias a la variable tiempo_vulnerable,
     * que ejerce de contador). Cuando entra en modo de vulnerabilidad, al primer golpe de timer siguiente
     * (cuando han transcurrido 3 segundos) el fantasma entra en modo de resucitación para indicar que le queda 
     * poco tiempo en ese estado antes de volver a su estado normal. 
     */
    public void actionPerformed(ActionEvent e){

        String movimiento = "";
        
        Random random = new Random();
        
        siguiente_x = random.nextInt(2);
        
        int signo = random.nextInt(2);
        
        if(x > x_pacman && y == y_pacman){
                
            movimiento = "Izquierda";

        }
        
        else if(x > x_pacman && y < y_pacman){
            if(signo == 0){
                movimiento = "Abajo";
            }else{
                movimiento = "Izquierda";
            }

        }
        
        else if(x > x_pacman && y > y_pacman){
            
            if(signo == 0){
                movimiento = "Arriba";
            }else{
                movimiento = "Izquierda";
            }
   
        }
        
        else if(x < x_pacman && y == y_pacman){
            
            movimiento = "Derecha";

        }
        
        else if(x < x_pacman && y > y_pacman){
            
            if(signo == 0){
                movimiento = "Arriba";
            }else{
                movimiento = "Derecha";
            }
            
        }
        
        else if(x < x_pacman && y < y_pacman){
            
            if(signo == 0){
                movimiento = "Abajo";
            }else{
                movimiento = "Derecha";
            }

        }
        
        else if(x == x_pacman && y < y_pacman){
            
            movimiento = "Abajo";

        }
        
        else if(x == x_pacman && y > y_pacman){
            
            movimiento = "Arriba";
            
        }
        
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
     * Obtiene la coordenada X en la que se encuentra Pacman y la asigna al campo x_pacman.
     * @param int La coordenada X de Pacman
     */
    public void setXPacman(int x_pacman){
        this.x_pacman = x_pacman;
    }
    
     /**
     * Obtiene la coordenada Y en la que se encuentra Pacman y la asigna al campo y_pacman.
     * @param int La coordenada Y de Pacman
     */
    public void setYPacman(int y_pacman){
        this.y_pacman = y_pacman;
    }
    
    /**
     * Genera un movimiento aleatorio para Blinky, dependiendo del estado de las banderas de colisión. 
     * Este método es llamado cada vez que Blinky colisiona contra un muro o contra otro fantasma.<br/><br/>
     * El ArrayList movimientos_validos contiene, inicialmente, los movimientos que el fantasma puede realizar (Arriba, Abajo, Derecha e Izquierda).
     * Si una bandera está seteada con el valor true, eliminaremos ese movimiento del ArrayList de movimientos_validos, para indicar a Blinky que ese
     * no es un movimiento que pueda hacer en ese momento.<br/><br/>
     * Una vez se tiene el ArrayList movimientos_validos únicamente con los que puede hacer el fantasma en ese momento, crearemos un nuevo
     * "siguiente movimiento" adecuado para el fantasma, intentando que el fantasma se acerce a Pacman y dando proridad al acercamiento en vertical.
     */
    public void generarMovimiento(){
        
        String movimiento = "";
        
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
            siguiente_x = -21;
            siguiente_y= 0;
        }
        
        // Si el ArrayList sólo tiene un movimiento válido, lo realizaremos.
        else if(movimientos_validos.size()==1){
            movimiento = movimientos_validos.get(0);
        }
        
        // Si no, Blinky hace el movimiento más adecuado, intentando acercarse a las coordenads de Pacman
        // y dando prioridad al acercamiento en vertical.
        else{
            if(x > x_pacman && y == y_pacman){
                
                if(movimientos_validos.contains("Izquierda")){
                    movimiento = "Izquierda";
                }
                
                else{
                    
                    Random random = new Random();
                    int pos_aleatoria = random.nextInt(movimientos_validos.size());
                    movimiento = movimientos_validos.get(pos_aleatoria);
            
                }
                
            }
            
            else if(x > x_pacman && y < y_pacman){
                
                if(movimientos_validos.contains("Abajo")){
                    movimiento = "Abajo";
                }
                
                else if(movimientos_validos.contains("Izquierda")){
                    movimiento = "Izquierda";
                }
                
                else{
                    
                    Random random = new Random();
                    int pos_aleatoria = random.nextInt(movimientos_validos.size());
                    movimiento = movimientos_validos.get(pos_aleatoria);
            
                }
                
            }
            
            else if(x > x_pacman && y > y_pacman){
                
                if(movimientos_validos.contains("Arriba")){
                    movimiento = "Arriba";
                }
                
                else if(movimientos_validos.contains("Izquierda")){
                    movimiento = "Izquierda";
                }
                
                else{
                    
                    Random random = new Random();
                    int pos_aleatoria = random.nextInt(movimientos_validos.size());
                    movimiento = movimientos_validos.get(pos_aleatoria);
            
                }
                
            }
            
            else if(x < x_pacman && y == y_pacman){
                
                if(movimientos_validos.contains("Derecha")){
                    movimiento = "Derecha";
                }
                
                else{
                    
                    Random random = new Random();
                    int pos_aleatoria = random.nextInt(movimientos_validos.size());
                    movimiento = movimientos_validos.get(pos_aleatoria);
            
                }
                
            }
            
            else if(x < x_pacman && y > y_pacman){
                
                if(movimientos_validos.contains("Arriba")){
                    movimiento = "Arriba";
                }
                
                else if(movimientos_validos.contains("Derecha")){
                    movimiento = "Derecha";
                }
                
                else{
                    
                    Random random = new Random();
                    int pos_aleatoria = random.nextInt(movimientos_validos.size());
                    movimiento = movimientos_validos.get(pos_aleatoria);
            
                }
                
            }
            
            else if(x < x_pacman && y < y_pacman){
                
                if(movimientos_validos.contains("Abajo")){
                    movimiento = "Abajo";
                }
                
                else if(movimientos_validos.contains("Derecha")){
                    movimiento = "Derecha";
                }
                
                else{
                    
                    Random random = new Random();
                    int pos_aleatoria = random.nextInt(movimientos_validos.size());
                    movimiento = movimientos_validos.get(pos_aleatoria);
            
                }
                
            }
            
            else if(x == x_pacman && y < y_pacman){
                
                if(movimientos_validos.contains("Abajo")){
                    movimiento = "Abajo";
                }
                
                else{
                    
                    Random random = new Random();
                    int pos_aleatoria = random.nextInt(movimientos_validos.size());
                    movimiento = movimientos_validos.get(pos_aleatoria);
            
                }
                
            }
            
            else if(x == x_pacman && y > y_pacman){
                
                if(movimientos_validos.contains("Arriba")){
                    movimiento = "Arriba";
                }
                
                else{
                    
                    Random random = new Random();
                    int pos_aleatoria = random.nextInt(movimientos_validos.size());
                    movimiento = movimientos_validos.get(pos_aleatoria);
            
                }
                
            }
            
        }
        
        // Se establece el siguiente movimiento creado
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
