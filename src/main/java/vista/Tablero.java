package vista;
import javax.swing.*;

import Puntuacion;
import model.entities.Blinky;
import model.entities.Clyde;
import model.entities.Fantasma;
import model.entities.Pacman;
import model.entities.Pinky;
import model.mapas.Galleta;
import model.mapas.GalletaGrande;
import model.mapas.GalletaPequena;
import model.mapas.Mapa;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Esta clase es el CONTROLADOR-VISTA del juego.<br/><br/>
 * Extiende JPanel e implementa ActionListener para poder recoger los eventos del timer.
 * @author Rubén Lastres Villar
 * @version 1.0 (15-05-2014)
 */
public class Tablero extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	// Variable del tipo Timer.
    private Timer timer;
    
    // Creo una variable de la clase Pacman
    private Pacman pacman;
    
    // Variable que referenciará un objeto de la clase Clyde.
    private Clyde clyde;
    
    // Variable que referenciará un objeto de la clase Clyde.
    private Blinky blinky;
    
    // Variable que referenciará un objeto de la clase Clyde.
    private Pinky pinky;
    
    // ArrayList de tipo fantasma que contiene a los tres fantasma
    private ArrayList<Fantasma> fantasmas;
    
    // Creo una variable de la clase Matriz
    private Mapa mapa;
    
    // Creo una variable de tipo Puntuacion
    private Puntuacion puntuacion;
    
    // Variable que controlará el estado del juego.
    private int estado;
    
    // Variable que contendrá la tecla que se pulse en teclado
    private int tecla;
    
    // Variable del tipo Image, para la pantalla inicial del juego.
    private Image pantalla_inicial;
    
    // JLabels para poder pintar el mapa.
    private JLabel[][] celda = new JLabel[28][31];

    // Un ArrayList de rectángulos, que contendrá los rectángulos con las coordenadas de las celdas que contienen "muro"
    // para después, en otro método, poder comprobar las colisiones y que Pacman no se salga del Mapa.
    private ArrayList<Rectangle> rectangulos_muros = new ArrayList<Rectangle>();
    
    // Un ArrayList de objetos Rectangle que contendrá los rectángulos con las coordenadas de las celdas que contienen 
    // casillas de teletransporte, para poder saber cuando Pacman debe trasladarse al otro lado del mapa.
    private ArrayList<Rectangle> rectangulos_teleport = new ArrayList<Rectangle>();
    
    // Un ArrayList de rectángulos, que contendrá los rectángulos con las coordenadas de las celdas que contienen "puerta" de la casa de fantasmas
    // para después, en otro método, poder comprobar las colisiones. Pacman no podrá atravesarlas pero los fantasmas sí.
    private ArrayList<Rectangle> rectangulos_puertas = new ArrayList<Rectangle>();
    
    // Declaramos una variable que será del tipo ArrayList<GalletaPequena>
    private ArrayList <Galleta> galletas_activas;
    
    /**
     * Constructor de la clase Tablero.<br/><br/>
     * Se establecen una serie de características básicas del JPanel (visibilidad, foco, tamaño, color de fondo, etc).<br/>
     * Se crean instancias de todas las clases que participarán en el juego (Pacman, fantasmas, galletas, mapa, puntuación...)
     */
    public Tablero(){     
        // Se establece doble buffer para el JPanel
        setDoubleBuffered(true);
        
        // Se establece el foco en el JPanel
        setFocusable(true);
        
        // Se establece el tamaño del JPanel
        setPreferredSize(new Dimension(888, 651));
        
        // Se establece el JPanel como visible
        setVisible(true);
        
        // El color de fondo del JPanel será negro.
        setBackground(Color.BLACK);
        
        // No se establece ningún Layout.
        setLayout(null);
        
        // Se implementa el escuchador de eventos de teclado, para poder recogerlos correctamente.
        addKeyListener(new TAdapter());
        
        // Se crea una instancia de Pacman.
        pacman = new Pacman();
        
        // Se crea la instancia de Clyde
        clyde = new Clyde();
        
        // Se crea la instancia de Clyde
        blinky = new Blinky();
        
        // Se creala instancia de Pinky
        pinky = new Pinky();
        
        // Se añaden los tres fantasmas a un ArrayList que contenga objetos de la clase Fantasma
        fantasmas = new ArrayList<Fantasma>();
        fantasmas.add(pinky);
        fantasmas.add(blinky);
        fantasmas.add(clyde);
        
        // Se crea un nuevo mapa
        mapa = new Mapa();
        
        // Se crea una instancia de la clase Puntuación
        puntuacion = new Puntuacion();
        
        // El estado inicial será 0, que indica que se mostrará la pantalla inicial.
        estado = 0;

        // Se instancia la clase Timer.
        timer = new Timer(4,this);
        
        // ArrayList que guardará las galletas activas que deben pintarse en el mapa.
        galletas_activas = new ArrayList<Galleta>();       
    }
    
    /**
     * Se sobreescribe el método paint de la superclase.<br/><br/>
     * <ul>
     * <li>Si el estado del juego es 0, se pinta la pantalla inicial del juego.</li>
     * <li>Si el estado del juego es distinto de 0, se pintan todas las galletas que contiene el ArrayList de galletas,
     * los fantasmas (que están en el ArrayList fantasmas) y a Pacman</li>
     * </ul>
     */
    public void paint(Graphics g){
        // Llamada al constructor de la superclase paint
        super.paint(g);

        //Convierto el contexto gráfico en Graphics2D
        Graphics2D g2d = (Graphics2D)g;
        
        // Si el estado del juego es 0, se debe pintar la pantalla inicial.
        // Si el estado del juego es 1, se debe pintar el tablero, a pacman, a los fantasmas y mostrar el JLabel para iniciar partida.
        // Si el estado del juego es 2, el juego está activo. Debemos seguir repintando los elementos del tablero.
        if(estado == 0){
            
            ImageIcon ii = new ImageIcon(this.getClass().getResource("imagenes/fondo/pantalla_inicial.jpg"));
            pantalla_inicial = ii.getImage();
            g2d.drawImage(pantalla_inicial,0,0, this);
            
        } else{        
            // Se pintan las galletas que están en el ArrayList galletas_activas
            // Las que Pacman se va comiendo, son borradas del ArrayList por el método CheckColissions y no se volverán a pintar.
            for(int x = 0; x < galletas_activas.size(); x++){
                Galleta gp = (Galleta) galletas_activas.get(x);
                g2d.drawImage(gp.getImage(),gp.getX(),gp.getY(), this);
            }
    
            // Plasmo a Pacman en el panel
            // Como parámetros le paso la imágen (la obtiene mediante un get del modelo)
            // Le paso la coordenada x del modelo (mediante el método getX del modelo)
            // Le paso la coordenada y del  modelo (mediante el método getY del modelo)
            g2d.drawImage(pacman.getImage(),pacman.getX(),pacman.getY(), this);
            
            // Se pintan los fantasmas, recorriendo el ArrayList que los contiene (fantasmas)
            for(int x = 0; x < fantasmas.size(); x++){
                Fantasma fantasma = fantasmas.get(x);
                g2d.drawImage(fantasma.getImage(),fantasma.getX(),fantasma.getY(), this);
            }
        }
        
        // Para problemas de saltos en los movimientos de las imágenes.
        Toolkit.getDefaultToolkit().sync();
        
        // Indico que ya hemos pintado todo lo que teníamos que pintar.
        g.dispose();       
    }
    
    /**
     * Método que se dispara cada vez que pasan los segundos establecidos en el timer.<br/><br/>
     * Se realizan las siguientes acciones:
     * <ul>
     * <li>Se llama al método checkColisiones(), para poder detectar las colisiones que se producen entre
     * los elementos del juego (Pacman, los fantasmas, los muros del mapa, las celdas de teletransporte y las galletas)</li>
     * <li>Se actualizan los JLabels de la parte derecha del tablero, que muestran todo lo relativo a la puntuación.</li>
     * <li>Se comprueba si el ArrayList de galletas está vacío; si lo está quiere decir que se ha ganado la partida y se hace
     * una llamada al método pantallaVictoria()</li>
     * <li>Se vuelve a llamar al método paint para que se repinte el tablero.</li>
     * </ul>
     */
    public void actionPerformed(ActionEvent e){        
        // Llamada al método que comprueba las colisiones entre los elementos del juego (Pacman, fantasmas, galletas, muros...)
        checkColisiones();

        // Se vuelve a llamar al método paint.
        repaint();
        
        // Se añade al JPanel el JLabel puntuacion_titulo, desde la clase Puntuacion.
        add(puntuacion.getPuntuacionTitulo());
            
        // Añado al JPanel el JLabel puntuacion, desde la clase Puntuacion.
        add(puntuacion.getPuntuacion());
        
        // Añado al JPanel el JLabel puntuacion_vidas, desde la clase Puntuacion.
        add(puntuacion.getPuntuacionVidas());
        
        // Se añade al JPanel el JLabel que contiene la imagen de fondo de toda la parte de la puntuación.
        add(puntuacion.getLogoScore());
        
        // Se comprueba si Pacman ha comido todas las galletas.
        // Si lo ha hecho debe finalizar la partida. Llamamos al método
        // que muestra la pantalla de victoria y las opciones que se pueden escoger
        // (jugar nueva partida o salir del juego)
        if (galletas_activas.size() == 0){  
            pantallaVictoria();    
        }
    }
    
    /**
     * Es la parte del teclado.
     * Definimos una clase interna que extiende la clase KeyAdapter
     * KeyAdapter sirve para recibir pulsaciones sobre el teclado (eventos de teclado)
     * Implementamos el método de la clase KeyAdapter que detecta si se ha pulsado una tecla
     * 
     * Algunas pulsaciones de tecla serán procesadas por esta clase, mediante el método estadoAccion()
     * Por ejemplo, las pausas en el juego mediante la pulsación de la tecla "P"
     * Las pulsaciones de teclado para el movimiento de Pacman se delegan en la clase modelo de Pacman.
     * ESTO ES INDEPENDIENTE AL TIMER.
     */
   private class TAdapter extends KeyAdapter{      
        public void keyPressed(KeyEvent e){         
            // Se crea una variable entera que guarde la última tecla pulsada en el teclado.
            int tecla = e.getKeyCode();
            
            // Se llama al método estadoAccion pasándole como parámetro la tecla pulsada.
            estadoAccion(tecla);
            
            // Delegamos en Pacman su movimiento a través del mapa.
            pacman.keyPressed(e);          
        }     
    }
   
   /**
    * En este método se realizarán ciertas acciones según el estado en el que se encuentra el juego y 
    * según la última pulsación de teclado.<br/><br/>
    * Posibles acciones:
    * <ul>
    * <li>Si se pulsa la tecla P cuando el timer está en marcha, se pausará el juego.</li>
    * <li>Si se pulsa la tecla P cuando el timer está parado, se reanudará el juego.</li>
    * <li>Si, estando en la pantalla de inicio del juego, se pulsa la tecla S, se pintará el laberinto y se dispondrá todo en el JPanel para iniciar una nueva partida.</li>
    * <li>Si, estando en una nueva partida que todavía no se ha iniciado, se pulsa la tecla S, se iniciará la partida.</li>
    * <li>Cuando Pacman ha perdido una vida, para arrancar de nuevo el juego habrá que pulsar la tecla S.</li>
    * </ul>
    * @param int La última pulsación de teclado.
    */
   public void estadoAccion(int pulsacion){
        //Asigno a la variable tecla la última pulsación de teclado que se ha recibido como parámetro.
        tecla = pulsacion;

        // Si se pulsa la tecla P, se pausa el juego (se detiene el timer).
        // Si el timer está parado (juego pausado), al pusar P el timer vuelve a correr.
        if(tecla == KeyEvent.VK_P){
            if(timer.isRunning()){ 
                add(puntuacion.getCartelEmpezar("Pulsa 'P' para reanudar"));
                timer.stop();
            } else{          
                timer.start();
                add(puntuacion.getCartelEmpezar("Pulsa 'P' para pausar"));   
            }
        }

        // Si el estado es 0 y pulsamos la tecla "S" es que queremos iniciar una nueva partida desde la pantalla inicial del juego.
        if(estado == 0 && tecla == KeyEvent.VK_S){  
            // Se pinta el laberinto.
            pintarLaberinto();
            
            // El estado pasa a ser 1.
            estado = 1;
            
            // Se añade al JPanel el JLabel puntuacion_titulo, desde la clase Puntuacion.
            add(puntuacion.getPuntuacionTitulo());
            
            // Añado al JPanel el JLabel puntuacion, desde la clase Puntuacion.
            add(puntuacion.getPuntuacion());
            
            // Añado al JPanel el JLabel puntuacion_vidas, desde la clase Puntuacion.
            add(puntuacion.getPuntuacionVidas());
            
            // Se añade al JPanel el JLabel cartel_empezar, desde la clase Puntuacion
            add(puntuacion.getCartelEmpezar("Pulsa 'S' para empezar"));
            
            // Se añade al JPanel el JLabel que contiene la imagen de fondo de toda la parte de la puntuación.
            add(puntuacion.getLogoScore());
            
            // Se repinta el lienzo.
            repaint();  
        }

        // En el estado 1, se ha pintado el laberinto, a pacman, los fantasmas y las galletas. Cuando se pulsa la tecla "S" el timer empieza a correr.
        else if(estado == 1 && tecla == KeyEvent.VK_S){ 
            timer.start();
            estado = 2;
            add(puntuacion.getCartelEmpezar("Pulsa 'P' para pausar"));
            pacman.setEstado("vivo");   
        }
        
        // EN EL ESTADO 2 EL JUEGO ESTÁ CORRIENDO.
        // Si Pacman ha perdido una vida se pasa al estado 3. En este estado Pacman es reubicado en su posición
        // inicial. Para poner de nuevo en marcha el timer habrá que pulsar la tecla S.
        else if(estado == 3 && tecla == KeyEvent.VK_S){
            timer.start();
            estado = 2;
            add(puntuacion.getCartelEmpezar("Pulsa 'P' para pausar"));
            pacman.setEstado("vivo");   
        }     
    }
   
    
    /**
     * Método para pintar el mapa, mediante una matriz de JLabels.
     * Como el mapa va a permanecer inalterable durante toda la ejecución, se pinta en un método 
     * aparte y no en el método paint que se repinta en cada golpe de timer.<br/><br/>
     * A medida que se va recorriendo la matriz que representa el mapa y se van pintando las casillas con
     * las imágenes adecuadas, se van haciendo las siguientes acciones:
     * <ul>
     * <li>Cuando se está recorriendo la matriz y se encuentra una casilla que representa un muro, se hace una llamada al 
     * método creaMuros() para que la añada a un ArrayList de objetos Rectangle que contiene todos los muros del mapa.</li>
     * <li>Cuando se está recorriendo la matriz y se encuentra una casilla que representa una puerta de la casa de fantasmas, se hace una llamada al 
     * método creaPuertas() para que la añada a un ArrayList de objetos Rectangle que contiene todos las puertas del mapa.</li>
     * <li>Cuando se está recorriendo la matriz y se encuentra una casilla que representa una celda de teletransporte, se hace una llamada al 
     * método creaTeleport() para que la añada a un ArrayList de objetos Rectangle que contiene todos las celdas de teletransporte del mapa.</li>
     * <li>Cuando se está recorriendo la matriz y se encuentra una casilla que representa la celda de la que debe partir un fantasma o Pacman, 
     * se hace una llamada al método setPosInicial() del objeto para pasarle las coordenadas de las que debe partir de inicio.</li>
     * <li>Cuando se está recorriendo la matriz y se encuentra una casilla que representa una celda en la que debe de ubicarse una galleta, 
     * se crea una instancia de la clase adecuada (GalletaGrande o GalletaPequena) y se añade al ArrayList de galletas.</li>
     * </ul>
     */
    public void pintarLaberinto(){
        // Obtengo el ancho y alto de la celda desde el modelo del mapa, para después poder pintarlas mediante JLabels.
        int ancho = mapa.getAnchoCelda();
        int alto = mapa.getAltoCelda();
        
        // Bucle for que recorre la matriz del mapa para ir pintandola mediante JLabels
        for (int x = 0; x < mapa.getFilas(); x++){
            
            for  (int y = 0; y < mapa.getColumnas(); y++){
                
                // Obtengo el contenido de la celda, para poder pintar en la celda un muro o un camino.
                // Lo hago llamando al método getContenidoCelda() de mapa.
                int contenidocelda = mapa.getContenidoCelda(x,y);
                
                // Creo el JLabel
                celda[x][y] = new JLabel();
                
                // Establezco donde se va a pintar la celda
                celda[x][y].setBounds(x*ancho, y*alto, ancho, alto);
                
                // Añado el JLabel al JPanel
                add(celda[x][y]);
                
                // Y le establezo la imagen de fondo según el tipo de celda.
                celda[x][y].setIcon(new ImageIcon("imagenes/celdas/" + contenidocelda + ".jpg"));
                
                // Si el contenido de la celda es  distinto de 13,14,15 o 32, es intransitable.
                // En este caso, debemos crear un rectángulo sobre ese JLabel y añadirlo al array de objetos
                // Rectangle. Nos va a permitir detectar las colisiones de Pacman y de los fantasmas con los muros.
                // Esto lo realizamos en otro método aparte, desde este lo llamamos.
                if (contenidocelda == 0 || contenidocelda == 3 || contenidocelda == 1 || contenidocelda == 2
                || contenidocelda == 4 || contenidocelda == 5 || contenidocelda == 6 || contenidocelda == 7 
                || contenidocelda == 8 || contenidocelda == 9 || contenidocelda == 10 || contenidocelda == 11 
                || contenidocelda == 12 || contenidocelda == 17 || contenidocelda == 18 
                || contenidocelda == 19 || contenidocelda == 20 || contenidocelda == 21 || contenidocelda == 22 
                || contenidocelda == 23 || contenidocelda == 24 || contenidocelda == 25 || contenidocelda == 26 
                || contenidocelda == 27 || contenidocelda == 28 || contenidocelda == 29 || contenidocelda == 30 || contenidocelda == 31){
                   
                    creaMuros(x*ancho, y*alto, ancho, alto);
                    
                }
                
                // Si el contenido de la celda es 16, se trata de una casilla de puerta de la casa de fantasmas.
                // Estas casillas son intransitables para Pacman pero transitables para los fantasmas.
                // Se hace una llamada al método que añade un objeto de la clase Rectangle (sobre esta casilla)
                // a un ArrayList de puertas de casa de fantasmas.
                if (contenidocelda == 16){
                    creaPuertas(x*ancho, y*alto, ancho, alto);
                }
                
                // Las celdas que contienen 33 o 34, son casillas de teletransporte,
                // Se hace una llamada al método creaTeleport() que crea un objeto de tipo Rectangle sobre estas celdas
                // y los añade a un ArrayList de celdas de teletransporte.
                if(contenidocelda == 33 || contenidocelda == 34){
                    creaTeleport(contenidocelda, x*ancho, y*alto, ancho, alto);
                }
                
                // La celda del mapa con el contenido 32, es la zona de la que debe partir Pacman en cada partida.
                // Debemos decirle a Pacman donde se debe de colocar al principio de la partida y cuando pierde una vida.
                if(contenidocelda == 32){
                    pacman.setPosInicial(x*ancho,y*alto);
                }
                
                // La celda del mapa con el contenido 35, es la zona de la que debe partir Clyde en cada partida.
                // Debemos decirle a Clyde donde se debe de colocar al principio de la partida y cuando es comido.
                if(contenidocelda == 35){
                    clyde.setPosInicial(x*ancho,y*alto);
                }
                
                // La celda del mapa con el contenido 36, es la zona de la que debe partir Blinky en cada partida.
                // Debemos decirle a Blinky donde se debe de colocar al principio de la partida y cuando es comido.
                if(contenidocelda == 36){
                    blinky.setPosInicial(x*ancho,y*alto);
                }
                
                // La celda del mapa con el contenido 36, es la zona de la que debe partir Pinky en cada partida.
                // Debemos decirle a Pinky donde se debe de colocar al principio de la partida y cuando es comido
                if(contenidocelda == 37){
                    pinky.setPosInicial(x*ancho,y*alto);
                }
                
                // Si el contenido de la celda es 14, debemos pintar una galleta pequeña en esa celda.
                // Se crea una instancia de la clase GalletaPequena y se añade al ArrayList de galletas.
                if(contenidocelda == 14){
                     galletas_activas.add(new GalletaPequena(x*ancho + 8, y*ancho + 8));
                }
                
                // Si el contenido de la celda es 15, debemos pintar una galleta grande en esa celda.
                // Se crea una instancia de la clase GalletaGrande que se añade al ArrayList de galletas
                if(contenidocelda == 15){
                     galletas_activas.add(new GalletaGrande(x*ancho + 3, y*ancho + 3));
                }
                
                celda[x][y].validate();           
            }               
        }
    }
    
    /** 
     * Método que crea objetos de la clase Rectangle encima de cada casilla que representa un muro.
     * Estos objetos Rectangle serán añadidos a un ArrayList de objetos Rectangle.<br/><br/>
     * Nos va a permitir saber cuando Pacman o un fantasma está colisionando contra un muro.
     * @param int x. La coordenada X donde debe crearse el Rectangle.
     * @param int y. La coordenada Y donde debe crearse el Rectangle.
     * @param int ancho. El ancho que debe tener el objeto Rectangle.
     * @param int alto. El alto que debe tener el objeto Rectangle.
     */
    public void creaMuros(int x, int y, int ancho, int alto){       
        // Creamos un objeto rectangle
        Rectangle r;
        r = new Rectangle(x, y, ancho, alto);
                    
        // Añado el rectángulo al arraylist de muros.
        rectangulos_muros.add(r);   
    }
    
     /** 
     * Método que crea objetos de la clase Rectangle encima de cada casilla que representa una puerta de casa de fantasmas.
     * Estos objetos Rectangle serán añadidos a un ArrayList de objetos Rectangle.<br/><br/>
     * Pacman no las podrá atravesar, pero sí los fantasmas.
     * @param int x. La coordenada X donde debe crearse el Rectangle.
     * @param int y. La coordenada Y donde debe crearse el Rectangle.
     * @param int ancho. El ancho que debe tener el objeto Rectangle.
     * @param int alto. El alto que debe tener el objeto Rectangle.
     */
    public void creaPuertas(int x, int y, int ancho, int alto){     
        // Creamos un objeto rectangle
        Rectangle r;
        r = new Rectangle(x, y, ancho, alto);
                    
        // Añado el rectángulo al arraylist de puertas.
        rectangulos_puertas.add(r);  
    }
    
    /** 
     * Método que crea objetos de la clase Rectangle encima de cada casilla que representa una celda de teletransporte.
     * Estos objetos Rectangle serán añadidos a un ArrayList de objetos Rectangle.<br/><br/>
     * @param int x. La coordenada X donde debe crearse el Rectangle.
     * @param int y. La coordenada Y donde debe crearse el Rectangle.
     * @param int ancho. El ancho que debe tener el objeto Rectangle.
     * @param int alto. El alto que debe tener el objeto Rectangle.
     */
    public void creaTeleport(int contenidocelda, int x, int y, int ancho, int alto){    
        int celda = contenidocelda;
        
        // Se crean los rectángulos sobre las casillas de teletransporte.
        if(celda == 33){
            // Creamos un objeto rectangle
            Rectangle teleport_der;
            teleport_der = new Rectangle(x, y, ancho, alto);
                    
            // Añado el rectángulo al arraylist de teletransporte.
            rectangulos_teleport.add(teleport_der); 
        }else if(celda == 34){
            // Creamos un objeto rectangle
            Rectangle teleport_izq;
            teleport_izq = new Rectangle(x, y, ancho, alto);
                    
            // Añado el rectángulo al arraylist de teletransporte.
            rectangulos_teleport.add(teleport_izq);  
        }  
    }
    
    /**
     * Detecta las colisiones entre los distintos elementos del juego.<br/><br/>
     * Acciones que realiza:
     * <ul>
     * <li><b>Primero:</b> Se detectan las colisiones de Pacman y los fantasmas contra los muros del mapa.
     * Cuando se detecta una colisión contra un muro, se setean las banderas del modelo adecuadamente para que
     * el fantasma o Pacman no se salgan de los límites del mapa y puedan generar movimientos válidos.</li>
     * <li><b>Segundo:</b> Se detectan las colisiones de Pacman y los fantasmas con las celdas de teletransporte.
     * Cuando se detecta una colisión contra una de estas casillas, se setean las banderas de teletransporte del modelo
     * para que pueda realizar la función adecuada (teletransportarse en el caso de Pacman o cambiar de dirección
     * en el caso de los fantasmas).</li> 
     * <li><b>Tercero:</b> Se detecta si Pacman ha colisionado contra una puerta de la casa de fantasmas. El tratamiento
     * es idéntico que cuando Pacman colisiona contra un muro; en caso de colisión habrá que setear las banderas de colisión
     * del modelo para que Pacman no se salga de los límites del mapa.</li>
     * <li><b>Cuarto:</b> Se detecta si Pacman ha colisionado contra una galleta del ArrayList de galletas. Si es así habrá
     * que eliminarla del ArrayList. Además, habrá que sumar los puntos por comer una galleta a la puntuación del juego y, en 
     * caso de tratarse de una galleta grande, poner a los fantasmas en modo vulnerable.</li>
     * <li><b>Quinto:</b> Se detectan las colisiones entre Pacman y los fantasmas. Si pacman colisiona contra un fantasma cuando
     * éste está en modo vulnerable, el fantasma muere y regresa a la casa de fantasmas; además se aumentarán 100 puntos
     * a la puntuación. Si Pacman colisiona contra un fantasma y éste está en modo normal, Pacman perderá una vida; si no le
     * restan más vidas el juego acabará y se mostrará un JOptionPane.</li>
     * <li><b>Sexto:</b> Si colisionan dos fantasmas, se setearán en sus modelos las banderas de colisión de forma que puedan
     * generar un nuevo movimiento adecuado a la situación.</li>
     * </ul>
     */
    public void checkColisiones(){
        // Obtengo la coordenada X de Pacman.
        int x_pacman = pacman.getX();
        
        //Obtengo la coordenada Y de Pacman.
        int y_pacman = pacman.getY();
        
        // Un objeto Rectangle sobre la imagen de Pacman, para comprobar colisiones
        Rectangle bounds_pacman = pacman.getBounds();
        
        // PRIMERO: SE COMPRUEBAN COLISIONES CON LOS MUROS DE PACMAN Y DE LOS FANTASMAS:
        // For que recorre todos los rectángulos que hemos guardado en el ArrayList de muros.
        // Es decir, contiene los muros contra los que podemos colisionar y que no podemos atravesar.
        for(int x = 0; x < rectangulos_muros.size(); x++){
        	
                // COLISIONES DE PACMAN CONTRA LOS MUROS DEL MAPA
                // Si Pacman ha colisionado contra un muro cuando se desplazaba hacia la izquierda
                // la bandera izquierda se seteará como TRUE
                if(rectangulos_muros.get(x).contains(x_pacman - 1,y_pacman) || rectangulos_muros.get(x).contains(x_pacman - 1,y_pacman + 21 - 1)){
                    pacman.setBanderaIzq(true);
                }
            
                // Si Pacman ha colisionado contra un muro cuando se desplazaba hacia la derecha
                // la bandera derecha se seteará como TRUE
                if(rectangulos_muros.get(x).contains(x_pacman + 21,y_pacman) || rectangulos_muros.get(x).contains(x_pacman + 21,y_pacman + 21 - 1)){
                    pacman.setBanderaDer(true);
                }
            
            
                // Si Pacman ha colisionado contra un muro cuando se desplazaba hacia arriba
                // la bandera superior se seteará como TRUE
                if(rectangulos_muros.get(x).contains(x_pacman,y_pacman - 1) || rectangulos_muros.get(x).contains(x_pacman + 21 - 1,y_pacman - 1)){
                    pacman.setBanderaSup(true);
                }
            
            
                // Si Pacman ha colisionado contra un muro cuando se desplazaba hacia abajo
                // la bandera inferior se seteará como TRUE
                if(rectangulos_muros.get(x).contains(x_pacman,y_pacman + 21) || rectangulos_muros.get(x).contains(x_pacman + 21 - 1,y_pacman + 21)){
                    pacman.setBanderaInf(true);
                }
                
                // COLISIONES DE LOS FANTASMAS CONTRA LOS MUROS DEL MAPA
                for(int i = 0; i < fantasmas.size(); i++){
                    
                    Fantasma fantasma = fantasmas.get(i);
                    
                    // Si el fantasma ha colisionado contra un muro cuando se desplazaba hacia la izquierda
                    // la bandera izquierda se seteará como TRUE
                    if(rectangulos_muros.get(x).contains(fantasma.getX() - 1,fantasma.getY()) || rectangulos_muros.get(x).contains(fantasma.getX() - 1,fantasma.getY() + 21 - 1)){
                        fantasma.setBanderaIzq(true);
                    }
            
                    // Si el fantasma ha colisionado contra un muro cuando se desplazaba hacia la derecha
                    // la bandera derecha se seteará como TRUE
                    if(rectangulos_muros.get(x).contains(fantasma.getX() + 21,fantasma.getY()) || rectangulos_muros.get(x).contains(fantasma.getX() + 21,fantasma.getY() + 21 - 1)){
                        fantasma.setBanderaDer(true);
                    }
                
                
                    // Si el fantasma ha colisionado contra un muro cuando se desplazaba hacia arriba
                    // la bandera superior se seteará como TRUE
                    if(rectangulos_muros.get(x).contains(fantasma.getX(),fantasma.getY() - 1) || rectangulos_muros.get(x).contains(fantasma.getX() + 21 - 1,fantasma.getY() - 1)){
                        fantasma.setBanderaSup(true);
                    }
                
                
                    // Si el fantasma ha colisionado contra un muro cuando se desplazaba hacia abajo
                    // la bandera inferior se seteará como TRUE
                    if(rectangulos_muros.get(x).contains(fantasma.getX(),fantasma.getY() + 21) || rectangulos_muros.get(x).contains(fantasma.getX() + 21 - 1,fantasma.getY() + 21)){
                        fantasma.setBanderaInf(true);
                    }  
                }   
        }
        
        // SEGUNDO: COMPROBAMOS SI LOS PERSONAJES HAN COLISIONADO CON UNA CASILLA DE TELETRANSPORTE(en caso de los fantasmas)
        // O SI ESTÁN SOBRE UNA CELDA DE TELETRANSPORTE (en caso de Pacman) Y SI ES ASÍ SETEAMOS LAS BANDERAS DE TELETRANSPORTE DE LOS MODELOS.
        for(int y = 0; y < rectangulos_teleport.size(); y++){
            
            // Comprueba si Pacman está sobre una celda de teletransporte
            if(y == 0 && rectangulos_teleport.get(y).contains(x_pacman, y_pacman, 21, 21)){
                pacman.setTeleportIzq(true);
            }else if(y == 1 && rectangulos_teleport.get(y).contains(x_pacman, y_pacman, 21, 21)){
                pacman.setTeleportDer(true);
            }
            
            // Comprueba si un fantasma ha llegado a una celda de teletransporte
            for(int i = 0; i < fantasmas.size(); i++){    
                Fantasma fantasma = fantasmas.get(i);
                
                if(y == 0 && rectangulos_teleport.get(y).contains(fantasma.getX(), fantasma.getY(), 21, 21)){
                    fantasma.setTeleportIzq(true);
                }
                
                else if(y == 1 && rectangulos_teleport.get(y).contains(fantasma.getX(), fantasma.getY(), 21, 21)){
                    fantasma.setTeleportDer(true);
                }   
            } 
        }
        
        // TERCERO: SE COMPRUEBA SI PACMAN HA COLISIONADO CONTRA LAS PUERTAS DE LA CASA DE FANTASMAS
        // For que recorre todos los rectángulos que hemos guardado en el ArrayList de puertas.
        // Es decir, contiene llas puertas de la casa de fantasmas que Pacman no puede atravesar.
        for(int x = 0; x < rectangulos_puertas.size(); x++){
                
                // Si Pacman ha colisionado contra una puerta cuando se desplazaba hacia la izquierda
                // la bandera izquierda se seteará como TRUE
                if(rectangulos_puertas.get(x).contains(x_pacman - 1,y_pacman) || rectangulos_puertas.get(x).contains(x_pacman - 1,y_pacman + 21 - 1)){
                    pacman.setBanderaIzq(true);
                }
            
                // Si Pacman ha colisionado contra una puerta cuando se desplazaba hacia la derecha
                // la bandera derecha se seteará como TRUE
                if(rectangulos_puertas.get(x).contains(x_pacman + 21,y_pacman) || rectangulos_puertas.get(x).contains(x_pacman + 21,y_pacman + 21 - 1)){
                    pacman.setBanderaDer(true);
                }
            
                // Si Pacman ha colisionado contra una puerta cuando se desplazaba hacia arriba
                // la bandera superior se seteará como TRUE
                if(rectangulos_puertas.get(x).contains(x_pacman,y_pacman - 1) || rectangulos_puertas.get(x).contains(x_pacman + 21 - 1,y_pacman - 1)){
                    pacman.setBanderaSup(true);
                }
            
                // Si Pacman ha colisionado contra una puerta cuando se desplazaba hacia abajo
                // la bandera inferior se seteará como TRUE
                if(rectangulos_puertas.get(x).contains(x_pacman,y_pacman + 21) || rectangulos_puertas.get(x).contains(x_pacman + 21 - 1,y_pacman + 21)){
                    pacman.setBanderaInf(true);
                }
        }
         
        // CUARTO: COMPROBAMOS SI PACMAN HA COLISIONADO CON UNA GALLETA.
        // SI ES ASÍ LA ELIMINAREMOS.
        // Si se trata de una galleta pequeña, la puntuación aumentará 10 puntos
        // Si se trata de una galleta grande, la puntuación se incrementará en 50 puntos.
        // Si come una galleta grande, Pacman se pone en modo invencible y, consecuentemente, los fantasmas en modo vulnerable.
        for(int x = 0; x < galletas_activas.size(); x++){
            
            Rectangle r1 = galletas_activas.get(x).getBounds();
            int puntos = galletas_activas.get(x).getPuntos();
            
            if(r1.intersects(bounds_pacman)){  
                if(galletas_activas.get(x) instanceof GalletaGrande){
                    clyde.setVulnerable(true);
                    blinky.setVulnerable(true);
                    pinky.setVulnerable(true);
                }
                
                galletas_activas.remove(galletas_activas.get(x));
                
                puntuacion.setPuntuacion(puntos);   
            }              
        }
        
        // QUINTO: DETECCIÓN DE COLISIONES ENTRE PACMAN Y LOS FANTASMAS.
        for(int x = 0; x < fantasmas.size(); x++){
            Fantasma fantasma = fantasmas.get(x);
            
            // Se obtiene el estado de vulnerabilidad del fantasma
            boolean fantasma_vulnerable = fantasmas.get(x).getVulnerable();
            
            // Acciones que se realizan si Pacman intersecta con un fantasma y este
            // está en modo de vulnerabilidad
            if(bounds_pacman.intersects(fantasma.getBounds())){
                if(fantasma_vulnerable){
                    // Se obtienen los puntos de la clase Fantasma...
                    int puntos = fantasma.getPuntos();
                    // Y se suman a la puntuación del juego.
                    puntuacion.setPuntuacion(puntos);
                    
                    // El fantasma vuelve a su posición inicial.
                    fantasma.resetPosInicial();
                    
                    // Reseteamos el tiempo de vulnerabilidad del fantasma a cero.
                    fantasma.resetVulnerable();
                    
                }
                
                // Si el fantasma no está en modo vulnerable:
                else{
                    // Se piede una vida.
                    puntuacion.pierdeVida();
                    
                    // Si no quedan más vidas, la partida finaliza y se muestra un cuadro de diálogo para escoger
                    // jugar otra partida o cerrar el juego.
                    if (puntuacion.getVidas() == 0){
                       estado = 4;
                       int seleccion = JOptionPane.showOptionDialog(this,"GAME OVER" +"\n"+ "¿Que quieres hacer ahora?","Game Over",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[] 
                       {"Jugar de nuevo", "Salir"},null);
                       
                       // Si seleccionamos jugar de nuevo se realizan las siguientes acciones:
                       if (seleccion == 0){ 
                            // Removemos todos los elementos del JPanel.
                            this.removeAll();
                            
                            // Limpiamos los arrays que contienen las galletas.
                            galletas_activas.clear();
                            
                            // SE vuelve a pintar el laberinto y todos sus elementos.
                            pintarLaberinto();
                            
                            // Colocamos a Pacman en su posición inicial
                            pacman.resetPosInicial();
                            
                            // Se pasa al estado 1, de inicio de juego.
                            estado = 1;
                            
                            add(puntuacion.getCartelEmpezar("Pulsa 'S' para empezar"));
                            
                            // Reseteamos la puntuación al valor inicial
                            puntuacion.setPuntuacionReset();
                            
                            // Reseteamos las vidas al valor inicial
                            puntuacion.setVidasReset();
                        }
                       
                       // Si seleccionamos "Salir", se cierra el juego (el JFrame y todos sus componentes).
                       if (seleccion == 1){
                            System.exit(-1);
                       }
                    }
                    
                    // Si quedan más vidas:
                    else if(puntuacion.getVidas() > 0){
                        // Volvemos a ubicar a Pacman en su posición inicial
                        pacman.resetPosInicial();
                        
                        // Reubicamos a los fantasmas en su posición inicial
                        // Se resetean también los estados de vulnerabilidad
                        clyde.resetPosInicial();
                        clyde.resetVulnerable();
                        blinky.resetPosInicial();
                        blinky.resetVulnerable();
                        pinky.resetPosInicial();
                        pinky.resetVulnerable();
                        
                        // Se añade al JPanel el JLabel cartel_empezar, desde la clase Puntuacion
                        add(puntuacion.getCartelEmpezar("Pulsa 'S' para empezar"));
            
                        // Se pasa al estado 3
                        estado = 3; 
                    }
                        
                    // Se detiene el timer.
                    timer.stop();
                } 
            }
        }
        
              
        // SEXTO: DETECCIÓN DE LAS COLISIONES ENTRE FANTASMAS
        // Colisiones entre Blinky y Pinky
        if(blinky.getBounds().contains(pinky.getX() - 1,pinky.getY()) || blinky.getBounds().contains(pinky.getX() - 1,pinky.getY() + 21 - 1)){
            pinky.setBanderaIzq(true);
            blinky.setBanderaDer(true);
        }
    
        if(blinky.getBounds().contains(pinky.getX() + 21,pinky.getY()) || blinky.getBounds().contains(pinky.getX() + 21,pinky.getY() + 21 - 1)){
            pinky.setBanderaDer(true);
            blinky.setBanderaIzq(true);
        }
    
        if(blinky.getBounds().contains(pinky.getX(),pinky.getY() - 1) || blinky.getBounds().contains(pinky.getX() + 21 - 1,pinky.getY() - 1)){
            pinky.setBanderaSup(true);
            blinky.setBanderaInf(true);
        }
    
        if(blinky.getBounds().contains(pinky.getX(),pinky.getY() + 21) || blinky.getBounds().contains(pinky.getX() + 21 - 1,pinky.getY() + 21)){
            pinky.setBanderaInf(true);
            blinky.setBanderaSup(true);
        }
        // --------------------------------------------------------------------------------------------------------------------------------------- //
        // --------------------------------------------------------------------------------------------------------------------------------------- //
        
        // Colisiones entre Clyde y Pinky
        if(clyde.getBounds().contains(pinky.getX() - 1,pinky.getY()) || clyde.getBounds().contains(pinky.getX() - 1,pinky.getY() + 21 - 1)){
            pinky.setBanderaIzq(true);
            clyde.setBanderaDer(true);
        }
    
        if(clyde.getBounds().contains(pinky.getX() + 21,pinky.getY()) || clyde.getBounds().contains(pinky.getX() + 21,pinky.getY() + 21 - 1)){
            pinky.setBanderaDer(true);
            clyde.setBanderaIzq(true);
        }
    
        if(clyde.getBounds().contains(pinky.getX(),pinky.getY() - 1) || clyde.getBounds().contains(pinky.getX() + 21 - 1,pinky.getY() - 1)){
            pinky.setBanderaSup(true);
            clyde.setBanderaInf(true);
        }
    
        if(clyde.getBounds().contains(pinky.getX(),pinky.getY() + 21) || clyde.getBounds().contains(pinky.getX() + 21 - 1,pinky.getY() + 21)){
            pinky.setBanderaInf(true);
            clyde.setBanderaSup(true);
        }
        // --------------------------------------------------------------------------------------------------------------------------------------- //
        // --------------------------------------------------------------------------------------------------------------------------------------- //
        
        // Colisiones entre Clyde y Blinky
        if(clyde.getBounds().contains(blinky.getX() - 1,blinky.getY()) || clyde.getBounds().contains(blinky.getX() - 1,blinky.getY() + 21 - 1)){
            blinky.setBanderaIzq(true);
            clyde.setBanderaDer(true);
        }
    
        if(clyde.getBounds().contains(blinky.getX() + 21,blinky.getY()) || clyde.getBounds().contains(blinky.getX() + 21,blinky.getY() + 21 - 1)){
            blinky.setBanderaDer(true);
            clyde.setBanderaIzq(true);
        }
    
        if(clyde.getBounds().contains(blinky.getX(),blinky.getY() - 1) || clyde.getBounds().contains(blinky.getX() + 21 - 1,blinky.getY() - 1)){
            blinky.setBanderaSup(true);
            clyde.setBanderaInf(true);
        }
    
        if(clyde.getBounds().contains(blinky.getX(),blinky.getY() + 21) || clyde.getBounds().contains(blinky.getX() + 21 - 1,blinky.getY() + 21)){
            blinky.setBanderaInf(true);
            clyde.setBanderaSup(true);
        }
        // --------------------------------------------------------------------------------------------------------------------------------------- //
        // --------------------------------------------------------------------------------------------------------------------------------------- //
        
        // Le pasamos a Blinky y Pinky las coordenadas X e Y de Pacman, para que pueda calcular movimientos.
        blinky.setXPacman(x_pacman);
        blinky.setYPacman(y_pacman);
        pinky.setXPacman(x_pacman);
        pinky.setYPacman(y_pacman);

        // Llamada al método que chequea los movimientos válidos de Pacman en virtud de su posición en el mapa
        pacman.checkMov();  
 
        // Llamada al método que chequea los movimientos válidos de Clyde en virtud de su posición en el mapa
        clyde.checkMov();
        
        // Llamada al método que chequea los movimientos válidos de Blinky en virtud de su posición en el mapa
        blinky.checkMov();
        
        // Llamada al método que chequea los movimientos válidos de Pinky en virtud de su posición en el mapa
        pinky.checkMov();
    }
    
    /**
     * Cuando Pacman ha comido todas las galletas, se hará una llamada a este método para mostrar que se ha ganado la partida.<br/><br/>
     * Se crea, simplemente, un JOptionPane con dos opciones: Salir del juego o Jugar otra partida.
     * <ul>
     * <li>Si se selecciona jugar otra partida, se restauran todos los elementos del Tablero.</li>
     * <li>Si se selecciona salir, se cierra el JFrame mediante un System.exit.</li>
     * </ul>
     */
    public void pantallaVictoria(){
        //Se pasa al estado 5.
       estado = 5;
       
       // Se crea un JOptionPane con dos opciones.
       int seleccion = JOptionPane.showOptionDialog(this,"VICTORIA!" +"\n"+ "¿Que quieres hacer ahora?","Has Ganado",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[] 
       {"Jugar de nuevo", "Salir"},null);
       
       // Si seleccionamos jugar de nuevo se realizan las siguientes acciones:
       if (seleccion == 0){
            // Removemos todos los elementos del JPanel.
            this.removeAll();
            
            // Limpiamos los arrays que contienen las galletas.
            galletas_activas.clear();
            
            // SE vuelve a pintar el laberinto y todos sus elementos.
            pintarLaberinto();
            
            // Colocamos a Pacman en su posición inicial
            pacman.resetPosInicial();
            
            // Se pasa al estado 1, de inicio de juego.
            estado = 1;
            
            // Se añade al JPanel el JLabel puntuacion_titulo, desde la clase Puntuacion.
            add(puntuacion.getPuntuacionTitulo());
            
            // Añado al JPanel el JLabel puntuacion, desde la clase Puntuacion.
            add(puntuacion.getPuntuacion());
            
            // Añado al JPanel el JLabel puntuacion_vidas, desde la clase Puntuacion.
            add(puntuacion.getPuntuacionVidas());
            
            // Se añade al JPanel el JLabel cartel_empezar, desde la clase Puntuacion
            add(puntuacion.getCartelEmpezar("Pulsa 'S' para empezar"));
            
            // Se añade al JPanel el JLabel que contiene la imagen de fondo de toda la parte de la puntuación.
            add(puntuacion.getLogoScore());
            
            // Reseteamos la puntuación al valor inicial
            puntuacion.setPuntuacionReset();
            
            // Reseteamos las vidas al valor inicial
            puntuacion.setVidasReset();
        }
       
       // Si seleccionamos "Salir", se cierra el juego (el JFrame y todos sus componentes).
       if (seleccion == 1){
            System.exit(-1);
       }
        
       // Se detiene el timer.
       timer.stop();
    }
}