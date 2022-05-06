package sprites;

import java.awt.*;
import processing.core.PApplet;
import aviral.shapes.Rectangle;
import aviral.shapes.Shape;

/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 * The Sprite class represents a double precision sprite in Downfall which can be visualized using 
 * the processing library
 */

public class Sprite {

    private Shape s;
    private double vx, vy;
    private double ax, ay;
    private int lives, totalLives;
    
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
    }
    /**
     * draws the sprite on the drawingsurface
     * @param p surface for sprite to be drawn on
     * @post Sprite is drawn on the surface
     */
    public void draw(PApplet p) {
        s.movePointBy(vx, vy);
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
        // circle
        return false;
    }

}
