package sprites;

import java.awt.*;
import processing.core.PApplet;
import processing.core.PImage;
import aviral.shapes.Circle;
import aviral.shapes.Rectangle;
import aviral.shapes.Shape;
/**
 * 
 * @author Kevin Ren
 *The Powerup class represents a powerup for the game that can be visualized using processing
 */
public class Powerup extends Sprite {

    // different types of powerups: 
    PImage t1, t2, t3;
    // type 1: add another life if health bar not full
    // type 2: add some more ammo
    // type 3: fully refill health bar
    private int type;
    private float sideLength;
    /**
     * creates a powerup object with the specs provided in the parameters
     * @param s shape of the powerup
     * @param vx x velocity of powerup
     * @param vy y velocity of powerup
     * @param type type of the powerup
     */
    public Powerup(Rectangle s, double vx, double vy, int type) {
        super(s, vx, vy);
        this.type = type;
        if (this.type > 3) this.type = (int)(Math.random()*2 + 1);
        sideLength = (float) (s.getPerimeter()/4);
    }
    /**
     * loads needed images for powerup
     * @param p surface on which images are loaded
     * @post image is loaded on the surface
     */
    public void loadAssets(PApplet p) {
        t1 = p.loadImage("assets" + fileSep + "red_potion.png");
        t2 = p.loadImage("assets" + fileSep + "bullet.png");
        t3 = p.loadImage("assets" + fileSep + "medkit.png");
    }
    /**
     * draws the powerup on the screen
     * @param p surface on which the powerup is drawn
     */
    public void draw(PApplet p) {
        super.draw(p);
        switch (type) {
            case 1:
                p.image(t1, (float)getX(), (float)getY(), sideLength, sideLength); break;
            case 2:
                p.image(t2, (float)getX(), (float)getY(), sideLength, sideLength); break;
            case 3:
                p.image(t3, (float)getX(), (float)getY(), sideLength, sideLength); break;
        }
    }
    /**
     * gets type of powerup
     * @return integer value representing type of powerup
     */
    public int getType() {
        return type;
    }
    
}
