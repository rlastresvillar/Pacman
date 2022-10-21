import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

/**
 * Implementa la puntuación del juego y las vidas que le restan al jugador.
 * @author Rubén Lastres Villar
 * @version 1.0 (15-05-2014)
 */
public class Puntuacion{
    private static final int PUNTUACION = 0;
    
    // Varible que guardará la puntuación del juego.
    private int i_puntuacion;
    
    // Variable de tipo String, para guardar la puntuación en una cadena de caracteres.
    // Así podremos pasársela a la clase Tablero que la mostrará dentro de un JLabel.
    private String s_puntuacion;
    
    // Variable enetera que contendrá las vidas restantes de Pacman.
    private int vidas;
    
    // Una constante que indica el número inicial de vidas del juego
    private static final int VIDAS = 2;
    
    // JLabel que contendrá la imágen que sirve de fondo a toda la parte de la puntuación (y se situará a la derecha del mapa en el JPanel).
    private JLabel logo_score;
    
    // JLabel que mostrará el texto "SCORE" como cabecera de la puntuación.
    private JLabel puntuacion_titulo;
    
    // JLabel que contendrá la puntuación en si.
    private JLabel puntuacion;
    
    // JLabel que mostrará las vidas restantes.
    private JLabel puntuacion_vidas;
    
    // JLabel que mostrará el cartel "Pulsa 'S' para empezar"
    private JLabel cartel_empezar;
    
    
    /**
     * Al principio de cada partida:
     * <ul>
     * <li> La puntuación es 0. Se establece en la variable i_puntuacion.</li>
     * <li> El jugador dispondrá de las vidas que se establecen en la constante entera VIDAS.</li>
     * <li> Se instancian los JLabels que mostrarán la información en el controlador-vista.</li>
     * </ul>
     */
    public Puntuacion(){    
        // Cada vez que se inicia una partida, la puntuación es 0
        i_puntuacion = PUNTUACION;
        
        // Cada vez que se inicia una partida, Pacman dispondrá de las vidas establecidas en la constante
        vidas = VIDAS;
        
        // Instancio los JLabels.
        logo_score = new JLabel();
        puntuacion = new JLabel();
        puntuacion_titulo = new JLabel();
        puntuacion_vidas = new JLabel();
        cartel_empezar = new JLabel();  
    }
    
    /**
     * Actualiza la puntuación del juego desde el controlador.
     * @param int Entero que se recibe desde el controlador para actualizar la puntuación del juego. 
     */
    public void setPuntuacion(int puntuacion){
        i_puntuacion += puntuacion;   
    }   
    
    /**
     * Este método devuelve las vidas que le restan al jugador en la partida actual.
     * @return Devuelve las vidas restantes de la partida.
     */
    public int getVidas(){
        return vidas;
    }
    
    /**
     * Este método resta una vida al jugador. <br/>
     * Cuando Pacman impacta contra un fantasma, este método es invocado restando una vida al número de vidas actual.
     * @param int Entero que se recibe desde el controlador para actualizar la puntuación del juego. 
     */
    public void pierdeVida(){   
        this.vidas --;  
    }  
    
    /**
     * Resetea las vidas a su número inicial.
     */
    public void setVidasReset(){
        vidas = VIDAS;
    }
    
    /**
     * Método que resetea la puntuación del juego a 0.
     */
    public void setPuntuacionReset(){
        i_puntuacion = PUNTUACION;
    }
    
    /**
     * Devuelve un JLabel con el logo "SCORE" para poder pintarlo en el juego.
     * @return Devuelve el JLabel que contiene la palabra "SCORE".
     */
    public JLabel getPuntuacionTitulo(){ 
        puntuacion_titulo.setBounds(645, 90, 100, 25);
        puntuacion_titulo.setText("SCORE");
        puntuacion_titulo.setForeground(Color.WHITE);
        puntuacion_titulo.setFont(new Font("Verdana", 0, 26));
        return puntuacion_titulo;  
    }
    
    /**
     * Devuelve un JLabel con la puntuación del juego.
     * @return Devuelve el JLabel que contiene la puntuación en formato String.
     */
    public JLabel getPuntuacion(){
        // Convierto la puntuacion en formato entero a formato String
        // para poder establecerlo como contenido del JLabel.
        s_puntuacion = Integer.toString(i_puntuacion);
        puntuacion.setBounds(645, 115, 100, 25);
        puntuacion.setText(s_puntuacion);
        puntuacion.setForeground(Color.WHITE);
        puntuacion.setFont(new Font("Verdana", 0, 26));
        return puntuacion; 
    }
    
    /**
     * Devuelve un JLabel que indica el número de vidas disponibles en la partida.
     * @return Devuelve el JLabel que contiene el número de vidas restantes de Pacman
     */
    public JLabel getPuntuacionVidas(){
        puntuacion_vidas.setBounds(775, 90, 100, 25);
        puntuacion_vidas.setText(vidas + "UP");
        puntuacion_vidas.setForeground(Color.WHITE);
        puntuacion_vidas.setFont(new Font("Verdana", 0, 26));
        return puntuacion_vidas;  
    }
    
    /**
     * Devuelve un JLabel que sirve de fondo a toda la parte derecha del JPanel (la parte de la puntuación).
     * @return JLabel Devuelve el JLabel que contiene la imagen que servirá de fondo
     * a toda la parte de la puntuación (parte derecha del JPanel).
     */
    public JLabel getLogoScore(){
        ImageIcon ii = new ImageIcon(this.getClass().getResource("/imagenes/fondo/derecha.jpg"));
        logo_score.setBounds(588, 0, 300, 651);
        logo_score.setIcon(ii);
        return logo_score;
    }
    
    /**
     * Devuelve un JLabel cuyo texto es un mensaje indicativo al jugador con indicaciones sobre el uso del juego.<br/>
     * Por ejemplo, puede mostrar los mensajes:
     * "Pulsa 'S' para empezar."
     * "Pulsa 'P' para pausar."
     * @param String - La cadena de caracteres que se quiere mostrar dentro del JLabel.
     * @return Devuelve un JLabel con un texto indicando al jugador las opciones que puede realizar.
     */
    public JLabel getCartelEmpezar(String texto){
        cartel_empezar.setBounds(623, 280, 250, 25);
        cartel_empezar.setText(texto);
        cartel_empezar.setForeground(Color.RED);
        cartel_empezar.setFont(new Font("Verdana", 0, 20));
        return cartel_empezar;
    }
}