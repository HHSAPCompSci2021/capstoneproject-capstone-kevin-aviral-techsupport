package sprites;

import processing.core.*;
import java.awt.*;
import aviral.shapes.Shape;
import aviral.shapes.*;
/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 * Double precision enemy which can be represented using processing
 */
public class Enemy extends Sprite {

	/**
	 * Creates an enemy
	 * @param s shape of enemy
	 * @param vx x velocity of enemy
	 * @param vy y velocity of enemy
	 * @param ax x acceleration of enemy	
	 * @param ay y acceleration of enemy
	 */
    public Enemy(Shape s, double vx, double vy, double ax, double ay) {
        super(s, vx, vy, ax, ay);
    }
    /**
     * Shoots a projectile towards x,y
     * @param targetX x target
     * @param targetY y target
     */ 
    public void shoot(double targetX, double targetY) {
        // create new projectile with velocity in the direciton of target point
        
        // Projectile p = new Projectile()
    }
    /**
     * draws enemy
     * @param p surface to be drawn on
     */
    public void draw(PApplet p) {
        super.draw(p);
        p.fill(200, 100, 100);

    }
    
}