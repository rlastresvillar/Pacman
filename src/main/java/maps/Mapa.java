package maps;
/**
 * Define una matriz de enteros que representa un mapa que sirve de base para el juego de Pacman
 * Cada elemento de la matriz es una casilla del mapa. 
 * @author Rubén Lastres Villar
 * @version 1.0 (15-05-2014)
 */
public class Mapa{
    private static final int ANCHO_CELDA = 21;
    private static final int ALTO_CELDA = 21;
	
    // Matriz que representa el mapa del juego.
    private int matriz[][] = {
                        {9,2,2,2,2,2,2,2,2,10,13,13,13,0,34,1,13,13,13,9,2,2,2,2,30,31,2,2,2,2,10},
                        {0,14,14,15,14,14,14,14,14,1,13,13,13,0,13,1,13,13,13,0,14,14,14,15,8,19,14,14,14,14,1},
                        {0,14,9,3,10,14,20,17,14,1,13,13,13,0,13,1,13,13,13,0,14,20,17,14,22,18,14,20,17,14,1},
                        {0,14,1,13,0,14,8,19,14,1,13,13,13,0,13,1,13,13,13,0,14,8,19,14,14,14,14,8,19,14,1},
                        {0,14,1,13,0,14,8,19,14,1,13,13,13,0,13,1,13,13,13,0,14,8,23,6,6,17,14,8,19,14,1},
                        {0,14,11,2,12,14,22,18,14,11,2,2,2,12,13,11,2,2,2,12,14,22,7,7,7,18,14,8,19,14,1},
                        {0,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,8,19,14,1},
                        {0,14,9,3,10,14,20,6,6,6,6,6,6,17,13,20,6,6,6,17,14,20,17,14,20,6,6,27,19,14,1},
                        {0,14,1,13,0,14,22,7,7,26,21,7,7,18,13,22,7,7,7,18,14,8,19,14,22,7,7,26,19,14,1},
                        {0,14,1,13,0,14,14,14,14,8,19,13,13,13,13,13,13,13,13,13,14,8,19,14,14,14,14,8,19,14,1},
                        {0,14,1,13,0,14,20,17,14,8,19,13,9,3,3,3,10,13,20,17,14,8,19,14,20,17,14,8,19,14,1},
                        {0,14,11,2,12,14,8,19,14,22,18,13,24,13,13,35,0,13,8,19,14,22,18,14,8,19,14,22,18,14,1},
                        {0,14,14,14,14,14,8,19,14,13,13,13,16,13,13,13,16,13,8,19,14,14,14,14,8,19,14,14,14,14,1},
                        {4,6,6,6,17,14,8,23,6,6,17,13,16,37,13,13,16,13,8,23,6,6,17,14,8,23,6,6,17,14,1},
                        {5,7,7,7,18,14,8,21,7,7,18,13,16,13,13,13,16,13,8,21,7,7,18,14,8,21,7,7,18,14,1},
                        {0,14,14,14,14,14,8,19,14,13,13,13,16,13,13,13,16,13,8,19,14,14,14,14,8,19,14,14,14,14,1},
                        {0,14,9,3,10,14,8,19,14,20,17,13,25,13,13,36,0,13,8,19,14,20,17,14,8,19,14,20,17,14,1},
                        {0,14,1,13,0,14,22,18,14,8,19,13,11,2,2,2,12,13,22,18,14,8,19,14,22,18,14,8,19,14,1},
                        {0,14,1,13,0,14,14,14,14,8,19,13,13,13,13,13,13,13,13,13,14,8,19,32,14,14,14,8,19,14,1},
                        {0,14,1,13,0,14,20,6,6,27,23,6,6,17,13,20,6,6,6,17,14,8,19,14,20,6,6,27,19,14,1},
                        {0,14,11,2,12,14,22,7,7,7,7,7,7,18,13,22,7,7,7,18,14,22,18,14,22,7,7,26,19,14,1},
                        {0,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,8,19,14,1},
                        {0,14,9,3,10,14,20,17,14,9,3,3,3,10,13,9,3,3,3,10,14,20,6,6,6,17,14,8,19,14,1},
                        {0,14,1,13,0,14,8,19,14,1,13,13,13,0,13,1,13,13,13,0,14,8,21,7,7,18,14,8,19,14,1},
                        {0,14,1,13,0,14,8,19,14,1,13,13,13,0,13,1,13,13,13,0,14,8,19,14,14,14,14,8,19,14,1},
                        {0,14,11,2,12,14,22,18,14,1,13,13,13,0,13,1,13,13,13,0,14,22,18,14,20,17,14,22,18,14,1},
                        {0,14,14,15,14,14,14,14,14,1,13,13,13,0,13,1,13,13,13,0,14,14,14,15,8,19,14,14,14,14,1},
                        {11,3,3,3,3,3,3,3,3,12,13,13,13,0,33,1,13,13,13,11,3,3,3,3,28,29,3,3,3,3,12}};
     
    /**
     * Constructor vacío.
     */
    public Mapa(){}
    
    /**
     * Método que devuelve el número de filas de la matriz.
     * @return El número de filas de la matriz.
     */
    public int getFilas(){
        return matriz.length;
    }
    
    /**
     * Método que devuelve el número de columnas de la matriz.
     * @return El número de columnas de la matriz.
     */
    public int getColumnas(){
        return matriz[0].length;
    }
    
    /**
     * Método para devolver el ancho de cada celda de la matriz.
     * @return El ancho de la celda en pixeles.
     */
    public int getAnchoCelda(){
        return ANCHO_CELDA;
    }
    
    /**
     * Método para devolver el alto de cada celda de la matriz.
     * @return El alto de la celda en pixeles.
     */
    public int getAltoCelda(){
        return ALTO_CELDA;
    }
    
    /**
     * Método que devuelve el contenido de la celda [x][y] de la matriz.<br/>
     * x e y son los parámetros que recibe como coordenadas, y devuelve el contenido de esa celda.
     * @return El contenido de una casilla de la matriz
     * @param int Enteros para hacer referencia a la casilla de la matriz de la que debe devolverse su contenido
     * @param int Enteros para hacer referencia a la casilla de la matriz de la que debe devolverse su contenido
     */
    public int getContenidoCelda(int x, int y){
        return matriz[x][y];
    }
    
    /**
     * Método para devolver que casilla es la que identifica la posición de partida de Pacman al inicio del juego.
     * @return La posición inicial en la que ha de colocarse Pacman en la matriz. Es la celda que contiene el entero 32.
     */
    public int getPosInicial(){
        return 32;
    }   
}