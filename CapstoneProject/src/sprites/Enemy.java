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
 * The Enemy class represents a double precision enemy which can be
 * represented using processing
 */
public class Enemy extends Sprite {

	public static float sideLength;
    private int firePattern;

	/**
	 * Creates an enemy
	 * @param s  shape of enemy
	 * @param vx x velocity of enemy
	 * @param vy y velocity of enemy
	 * @param ax x acceleration of enemy
	 * @param ay y acceleration of enemy
	 */
	public Enemy(Rectangle s, double vx, double vy, double ax, double ay) {
		super(s, vx, vy, ax, ay);
		sideLength = (float) s.getPerimeter() / 4;
		double a = Math.random();
		if (a >= 0.8) {
			firePattern = 1; 
		} else {
			firePattern = 0;
		}
	}

	/**
	 * Shoots a projectile towards x,y
	 * 
	 * @param targetX x target
	 * @param targetY y target
	 * @return The projectile shot by this enemy.
	 */
	public Projectile shoot(double targetX, double targetY) {
        if (getBoolean() == false) return null;

		double projVx = 0.025 * (targetX - getX());
		double projVy = 0.025 * (targetY - getY());

		Circle circle = new Circle();
		circle.setRadius(6);

		if (projVx > 0) {
			circle.setX(getX() + projVx + sideLength);
		} else {
			circle.setX(getX() + projVx - 16);
		}

		if (projVy > 0) {
			circle.setY(getY() + projVy + sideLength);
		} else {
			circle.setY(getY() + projVy - 16);
		}

		return new Projectile(circle, projVx, projVy, 0, 0, false);
	}

	/**
	 * draws enemy
	 * 
	 * @param p surface to be drawn on
	 */
	public void draw(PApplet p) {
		// set up surface
		super.draw(p);
		p.push();
		// color
		p.fill(200, 100, 100);
		p.rect((float) getX(), (float) getY(), sideLength, sideLength, 5f, 5f, 5f, 5f);

		p.pop();
	}
	/**
	 * getter for the fire pattern of the enemy
	 * @return integer value of the fire pattern of the enemy
	 */
    public int getFirePattern() {
        return this.firePattern;
    }
    /**
     * setter for the fire pattern of the enemy
     * @param firePattern sets fire pattern to the argued integer
     */
    public void setFirePattern(int firePattern) {
        this.firePattern = firePattern;
    }

}
