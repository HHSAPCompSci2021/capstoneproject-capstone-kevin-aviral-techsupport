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
    /**
     * loads images needed for projectile on papplet 
     * @param p surface on which images are loaded
     * @post images are loaded on surface
     */
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
        p.push();
        p.imageMode(PApplet.CENTER);
        if (fromPlayer) {
            p.tint(255, 255, 37);
            p.image(getVx() < 0 ? left : right, (float)getX(), (float)getY(), 8*r, 2*r); 
        } else {
            float angle = (float) (Math.atan(getVy()/getVx()));
            p.translate((float)getX(), (float)getY());
            p.rotate(angle);
            p.image(getVx() < 0 ? left : right, 0, 0, 6*r, 2*r);        
        }
        p.pop();
    }
    /**
     * getter for radius of projectile
     * @return radius of projectile
     */
    public float getR() {
        return r;
    }

}
