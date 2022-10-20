import javax.swing.JFrame;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;

/**
 * Clase principal del juego, que ha de instanciarse para crear una partida.<br/><br/>
 * Extiende un JFrame que contendrá el JPanel donde se pintarán el mapa y los personajes.
 * @author Rubén Lastres Villar
 * @version 1.0 (15-05-2014)
 */
public class Inicio extends JFrame{
    
    /**
     * Se añade una instancia de la clase Tablero (que es la clase que extiende el JPanel).<br/><br/>
     * Además se establecen alguna opciones del JFrame (tamaño, color de fondo, layout, título....).
     */
    public Inicio(){
        
        add(new Tablero());
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());
        setPreferredSize(new Dimension (1000,689));
        setMinimumSize(new Dimension (1000,689));
        setBackground(Color.BLACK);
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setTitle("·: Classic Pacman :· Práctica POO (2013-2014) de Rubén Lastres Villar");;
        setResizable(false);
        setFocusable(false);
        setVisible(true);

    }
    
    public static void main (String[] args){
        new Inicio();
    }
    
}
