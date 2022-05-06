package sprites;

import java.awt.*;
import processing.core.*;
import aviral.shapes.Shape;
import aviral.shapes.*;
/**
 * Double precision Projectile which can be drawin using processing
 * @author Aviral Vaidya, Kevin Ren
 *
 */
public class Projectile extends Sprite {
	/**
	 * Creates a projectile
	 * @param s shape of projectile
	 * @param vx x velocity of projectile
	 * @param vy y velocity of projectile
	 * @param ax x acelleration of projectile
	 * @param ay y acceleration of projectile
	 * @param totalLives lives of projectile
	 */
    public Projectile(Shape s, double vx, double vy, double ax, double ay) {
        super(s, vx, vy, ax, ay);
    }
    /**
     * draws projetile
     * @param p  surface to be drawn on
     * @param player target player
     * @post projectile is drawn on surface
     */
    public void draw(PApplet p, Player player) {
        super.draw(p);
        p.fill(255);
        
    }

}
