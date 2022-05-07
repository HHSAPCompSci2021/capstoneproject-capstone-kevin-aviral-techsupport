package sprites;

import java.awt.*;
import processing.core.*;
import aviral.shapes.Shape;
import aviral.shapes.Rectangle;
/**
 * The platform class represents a double precision platform that can be displayed with processing 
 * @author Aviral
 *
 */
public class Platform extends Sprite {

    // creates a platform at a random location
    // represented by a line
	/**
	 * creates a platform object at a random location
	 * @param s shape of the platform
	 * @param vx x velocity of platform
	 * @param vy y velocity of platform
	 */
    public Platform(Shape s, double vx, double vy) {
        super(s, vx, vy);
    }

    /**
     * draws the platform
     * @param p surface to draw on
     * @param player the player 
     * @post platform is drawn on the surface
     */
    public void draw(PApplet p, Player player) {
        super.draw(p);
        p.fill(255);
        
    }

}
