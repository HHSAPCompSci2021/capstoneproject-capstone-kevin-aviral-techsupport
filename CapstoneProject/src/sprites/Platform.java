package sprites;

import java.awt.*;
import processing.core.*;
import aviral.*;
import aviral.shapes.Line;
/**
 * The platform class represents a double precision platform that can be
 * displayed with processing
 * 
 * @author Aviral
 *
 */
public class Platform extends Sprite {

    // creates a platform at a random location
    // represented by a line
    private double length;
    private Line l1;
    private Line l2; 

    /**
     * creates a platform object at a random location
     * 
     * @param s  shape of the platform
     * @param vx x velocity of platform
     * @param vy y velocity of platform
     */
    public Platform(Line s, double vx, double vy) {
        super(s, vx, vy);
        this.l1 = s;
        this.l2 = s;
        length = (double) s.getPerimeter();
    }

    public void draw(PApplet p, Player player) {

    }

    public Line getL1() {
        return l1;
    }
    
    public Line getL2(){
    	return l2; 
    }

    /**
     * draws the platform
     * 
     * @param p surface to draw on
     * @post platform is drawn on the surface
     */
    public void draw(PApplet p) {
        super.draw(p);
        p.push();
        p.stroke(255, 255, 2555);
        p.strokeWeight(10);
        // p.line((float)getX(), (float)getY(), (float)(getX() + length),
        // (float)getY());
        p.strokeWeight(10);
        l1.draw(p);
       // l2.draw(p);
        p.pop();
    }

    public double getLength() {
        return length;
    }

}
