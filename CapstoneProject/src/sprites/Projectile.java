package sprites;

import java.awt.*;
import processing.core.*;
import aviral.shapes.Shape;
import aviral.shapes.*;
/**
 * Double precision Projectile which can be drawin using processing
 * @author Aviral Vaidya
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
        if (fromPlayer) {
            p.image(getVx() < 0 ? left : right, (float)getX(), (float)getY(), 8*r, 2*r);
        } else {
            p.fill(249, 255, 135);
            p.noStroke();
            p.circle((float)getX(), (float)getY(), 2*r);
        }
    }
    /**
     * getter for radius of projectile
     * @return radius of projectile
     */
    public float getR() {
        return r;
    }

}
