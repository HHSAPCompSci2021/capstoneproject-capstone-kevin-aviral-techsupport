package sprites;

import processing.core.*;
import java.awt.*;
import java.util.ArrayList;
import aviral.shapes.Shape;
import aviral.shapes.Rectangle;
import aviral.shapes.Circle;
/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 * Double precision enemy which can be represented using processing
 */
public class Enemy extends Sprite {

    private float sideLength;

	/**
	 * Creates an enemy
	 * @param s shape of enemy
	 * @param vx x velocity of enemy
	 * @param vy y velocity of enemy
	 * @param ax x acceleration of enemy	
	 * @param ay y acceleration of enemy
	 */
    public Enemy(Rectangle s, double vx, double vy, double ax, double ay) {
        super(s, vx, vy, ax, ay);
        sideLength = (float)s.getPerimeter()/4;
    }
    /**
     * Shoots a projectile towards x,y
     * @param targetX x target
     * @param targetY y target
     * @return The projectile shot by this enemy.
     */ 
    public Projectile shoot(double targetX, double targetY) {
        double pv = 3;
        double projVx = 0.02*(targetX - getX());
        double projVy = 0.02*(targetY - getY());

        Circle circle = new Circle(getX() + projVx, getY() + projVy, 4);
        return new Projectile(circle, projVx, projVy, 0, 0);
    }
    /**
     * draws enemy
     * @param p surface to be drawn on
     */
    public void draw(PApplet p) {
        super.draw(p);
        p.push();

        p.fill(200, 100, 100);
        p.rect((float)getX(), (float)getY(), sideLength, sideLength, 5f, 5f, 5f, 5f);

        p.pop();        
    }
    
}
