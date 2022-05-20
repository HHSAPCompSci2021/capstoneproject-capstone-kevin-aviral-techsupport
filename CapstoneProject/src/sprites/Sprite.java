package sprites;

import java.awt.*;
import processing.core.PApplet;
import aviral.shapes.Circle;
import aviral.shapes.Rectangle;
import aviral.shapes.Shape;

/**
 * 
 * @author Aviral Vaidya
 * The Sprite class represents a double precision sprite in Downfall which can be visualized using 
 * the processing library
 */

public class Sprite {
	/**
	 * file separator character
	 */
    public final static String fileSep = System.getProperty("file.separator");

    private Shape s;
    private double vx, vy;
    private double ax, ay;
    private int lives, totalLives;
    // only used in enemy class for now
    private long lastTime; 
    private boolean toggle;
    
    /**
     * Creates a sprite
     * @param s Shape of the sprite
     * @param vx x velocity of the sprite
     * @param vy y velocity of the sprite
     * @param ax x acceleration of the sprite
     * @param ay y acceleration of the sprite
     * @param totalLives total number of lives the sprite has
     */
    public Sprite(Shape s, double vx, double vy, double ax, double ay, int totalLives) {
        this.s = s;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
        this.totalLives = totalLives;
        lives = totalLives;
        lastTime = (long)Math.random()*60;
        toggle = true;
    }
    /**
     * alternate constructor for a sprite with just 1 life and no acceleration
     * @param s shape of the sprite
     * @param vx x velocity of the sprite
     * @param vy y velocity of the sprite
     */
    public Sprite(Shape s, double vx, double vy) {
        this.s = s;
        this.vx = vx;
        this.vy = vy;
        ax = 0;
        ay = 0;
        totalLives = 1;
        lives = 1;
        lastTime = (long)Math.random()*60;
        toggle = true;
    }
    /**
     * alternate constructor for a sprite with just 1 life
     * @param s shape of the sprite
     * @param vx x velocity of the sprite
     * @param vy y velocity of the sprite
     * @param ax x acceleration of the sprite
     * @param ay y acceleration of the sprite
     */
    public Sprite(Shape s, double vx, double vy, double ax, double ay) {
        this.s = s;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
        totalLives = 1;
        lives = 1;
        lastTime = (long)Math.random()*60;
        toggle = true;
    }
    /**
     * draws the sprite on the drawingsurface
     * @param p surface for sprite to be drawn on
     * @post Sprite is drawn on the surface
     */
    public void draw(PApplet p) {
        s.move(vx, vy);
        vx += ax;
        vy += ay;
    }
    /**
     * checks if sprite is touching another sprite
     * @param other other sprite this sprite is checked against
     * @return true if it is touching the other sprite, false otherwise
     */
    public boolean isTouching(Sprite other) {
        // different shapes in this game:
        // rectangle 
        // line 
        // true
    	return s.isPointInside(other.getX(), other.getY());
    }

    public long getLastTime() {
        return this.lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public boolean getBoolean() {
        return toggle;
    }

    public void setBoolean(boolean b) {
        toggle = b;
    }

    public Projectile shoot(double targetX, double targetY) {
        return null;
    }
    /**
     * moves a sprite a specific amount
     * @param dx distance moved in x
     * @param dy distance moved in y
     */
    public void moveBy(double dx, double dy) {
        s.move(dx, dy);
    }
    /**
     * getter for shape 
     * @return shape of sprite
     */
    public Shape getShape() {
    	return s;
    }
    /**
     * getter for x coordinate of sprite
     * @return x coordinate of sprite
     */
    public double getX() {
        return s.getX();
    }
    /**
     * 
     * getter for y coordinate of sprite
     * @return y coordinate of sprite
     */
    public double getY() {
        return s.getY();
    }
    /**
     * getter for x velocity of sprite
     * @return x velocity
     */
    public double getVx() {
        return this.vx;
    }
    /**
     * setter for x velocity of sprite
     * @param vx new x velocity
     */
    public void setVx(double vx) {
        this.vx = vx;
    }
    /**
     * getter for y velocity of sprite
     * @return y velocity
     */
    public double getVy() {
        return this.vy;
    }
    /**
     * setter for y velocity of sprite
     * @param vy new y velocity
     */
    public void setVy(double vy) {
        this.vy = vy;
    }
    /**
     * getter for x acceleration of sprite
     * @return x acceleration
     */
    public double getAx() {
        return this.ax;
    }
    /**
     * setter for x acceleration of sprite
     * @param ax new x acceleration
     */
    public void setAx(double ax) {
        this.ax = ax;
    }
    /**
     * getter for y acceleration of sprite
     * @return y acceleration 
     */
    public double getAy() {
        return this.ay;
    }
    /**
     * setter for y acceleration of sprite
     * @param ay new y acceleration
     */
    public void setAy(double ay) {
        this.ay = ay;
    }
    /**
     * getter for lives of sprite
     * @return number of lives
     */
    public int getLives() {
        return this.lives;
    }
    /**
     * setter for lives of sprite
     * @param lives new number of lives sprite has
     */
    public void setLives(int lives) {
        this.lives = lives;
    }
    /**
     * getter for total number of lives sprite has
     * @return total lives
     */
    public int getTotalLives() {
        return this.totalLives;
    }
    /**
     * setter for total lives of sprite
     * @param totalLives new total lives 
     */
    public void setTotalLives(int totalLives) {
        this.totalLives = totalLives;
    }

}
