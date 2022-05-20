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

    private float r;
    private PImage left;
    private PImage right;
    private boolean fromPlayer;

	/**
	 * Creates a projectile
	 * @param s shape of projectile
	 * @param vx x velocity of projectile
	 * @param vy y velocity of projectile
	 * @param ax x acelleration of projectile
	 * @param ay y acceleration of projectile
     * @param fromPlayer if this projectile was shot by a player
	 */
    public Projectile(Circle s, double vx, double vy, double ax, double ay, boolean fromPlayer) {
        super(s, vx, vy, ax, ay);
        r = (float)s.getRadius();
        this.fromPlayer = fromPlayer;
    }

    public void loadAssets(PApplet p) {
        // we only need to load the images if the player shoots the projectile
        left = p.loadImage("assets" + fileSep + "shootleft.png");
        right = p.loadImage("assets" + fileSep + "shootright.png");
    }
    /**
     * draws projetile
     * @param p  surface to be drawn on
     * @post projectile is drawn on surface
     */
    public void draw(PApplet p) {
        super.draw(p);
        p.fill(249, 255, 135);
        if (fromPlayer) {
            // draw the image
            p.image(getVx() < 0 ? left : right, (float)getX(), (float)getY(), 8*r, 2*r);
        } else {
            p.circle((float)getX(), (float)getY(), 2*r);
        }
    }
    /**
     * Checks if it is in contact with a sprite
     * @param other other enemy to check if is touching
     * @return returns true if projectile is hitting a sprite, false otherwise
     */
    public boolean isTouching(Enemy other) {
    	// loop through arraylist of sprites and if it is hitting a nonplatform remove it and 
    	// decrease teh lives of that sprite by one.
        
    	return false; 
    }
    /**
     * getter for radius of projectile
     * @return radius of projectile
     */
    public float getR() {
        return r;
    }

}
