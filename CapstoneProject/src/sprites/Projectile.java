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

	/**
	 * Creates a projectile
	 * @param s shape of projectile
	 * @param vx x velocity of projectile
	 * @param vy y velocity of projectile
	 * @param ax x acelleration of projectile
	 * @param ay y acceleration of projectile
	 */
    public Projectile(Circle s, double vx, double vy, double ax, double ay) {
        super(s, vx, vy, ax, ay);
        r = (float) s.getRadius();
    }
    /**
     * draws projetile
     * @param p  surface to be drawn on
     * @post projectile is drawn on surface
     */
    public void draw(PApplet p) {
        super.draw(p);
        p.fill(249, 255, 135);
        p.circle((float)getX(), (float)getY(), 2*r);
        
    }
    /**
     * getter for radius of projectile
     * @return radius of projectile
     */
    public float getR() {
        return r;
    }

}
