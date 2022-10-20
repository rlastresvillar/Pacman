package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;

/**
 * Ventana principal de la aplicación.
 * @author Rubén Lastres Villar.
 * @version 2.0.0 (2022)
 */
public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	public MainFrame() {  
		// Propiedades del frame.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension (1000,689));
        this.setMinimumSize(new Dimension (1000,689));
        this.setBackground(Color.BLACK);
        this.getContentPane().setBackground(Color.BLACK);
        this.setLocationRelativeTo(null);
        this.setTitle("·: Classic Pacman v.1.0.0 (2014) :· Developed by Rubén Lastres Villar");;
        this.setResizable(false);
        this.setFocusable(false);
        this.setVisible(true);

        
	}
}