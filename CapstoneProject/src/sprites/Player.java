package sprites;

import aviral.shapes.Shape;
import aviral.shapes.*;
import java.awt.*;
/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 * The Player class represents a double precision player that can be visualized using the processing library
 *
 */
public class Player extends Sprite {
	/**
	 * creates a player object
	 * @param s shape of player
	 * @param vx x velocity of player
	 * @param vy y velocity of player
	 * @param ax x acceleration of player
	 * @param ay y acceleration of player
	 * @param totalLives
	 */
    public Player(Shape s, double vx, double vy, double ax, double ay, int totalLives) {
        super(s, vx, vy, ax, ay, totalLives);
    }

}

