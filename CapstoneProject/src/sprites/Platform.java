package sprites;

import java.awt.*;
import processing.core.*;
import aviral.*;
import aviral.shapes.Line;
import processing.awt.*;
/**
 * The platform class represents a double precision platform that can be
 * displayed with processing
 * 
 * @author Aviral Vaidya
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
        length = (double) s.getPerimeter();
       // cloudImg = loadImage("platformcloud.png");
    }
    /**
     * loads images for platform
     * @param p surface on which image is loaded
     * @post images are loaded on the surface
     */
    public void loadAssets(PApplet p) {
	}
    /**
     * getter for first line of sprite
     * @return line 1 of sprite
     */
    public Line getL1() {
        return l1;
    }
    /**
     * getter for second line of sprite
     * @return line 2 of sprite
     */
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
        // p.strokeWeight(4);
        // p.color(255, 1, 1);
        // p.line((float)l1.getX()+2, (float)l1.getY()+2, (float)(l1.getX2()+2), (float)l1.getY2()+2);
        // p.line((float)getX(), (float)getY(), (float)(getX() + length),
        // (float)getY());
        l1.setStrokeColor(l1.getFillColor());
        l1.setStrokeWeight(4);
        l1.draw(p);
       // l2.draw(p);
       // loadAssets(p);
       // p.image(cloudImg, (float) this.getX(), (float) this.getY(), 50, 20);
    }

    /**
     * getter for length of line
     * @return length of line
     */
    public double getLength() {
        return length;
    }

}
