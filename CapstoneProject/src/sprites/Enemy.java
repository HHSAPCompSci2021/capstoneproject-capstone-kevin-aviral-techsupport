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
    }
    /**
     * Shoots a projectile towards x,y
     * @param targetX x target
     * @param targetY y target
     */ 
    public void shoot(double targetX, double targetY) {
        double pv = 3;
        double projVx = 0, projVy = 0;
        if (targetX > getX()) {
            projVx = pv;
        } else {
            projVx = -pv;
        }

        if (targetY > getY()) {
            projVy = pv;
        } else {
            projVy = -pv;
        }
        Circle circle = new Circle(getX() + projVx, getY() + projVy, 2);
        Projectile p = new Projectile(circle, projVx, projVy, 0, 0);
        // add to list of stuff on screen
        
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