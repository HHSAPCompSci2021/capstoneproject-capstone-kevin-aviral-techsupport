package sprites;

import java.awt.*;
import processing.core.PApplet;
import processing.core.PImage;
import aviral.shapes.Circle;
import aviral.shapes.Rectangle;
import aviral.shapes.Shape;

public class Powerup extends Sprite {

    // different types of powerups: 
    PImage t1, t2, t3;
    // type 1: add another life if health bar not full
    // type 2: add some more ammo
    // type 3: fully refill health bar
    private int type;
    private float sideLength;

    public Powerup(Rectangle s, double vx, double vy, int type) {
        super(s, vx, vy);
        this.type = type;
        sideLength = (float) (s.getPerimeter()/4);
    }

    public void loadAssets(PApplet p) {
        t1 = p.loadImage("assets" + fileSep + "red_potion.png");
        t2 = p.loadImage("assets" + fileSep + "bullet.png");
        t3 = p.loadImage("assets" + fileSep + "medkit.png");
    }

    public void draw(PApplet p) {
        super.draw(p);
        p.push();

        p.fill(200, 200, 210);
        switch (type) {
            case 1:
                p.image(t1, (float)getX(), (float)getY(), sideLength, sideLength); break;
            case 2:
                p.image(t2, (float)getX(), (float)getY(), sideLength, sideLength); break;
            case 3:
                p.image(t3, (float)getX(), (float)getY(), sideLength, sideLength); break;
        }

        p.fill(0);
        p.text(type, (float)getX() + 10, (float)getY() + sideLength);

        p.pop();
    }

    public int getType() {
        return type;
    }
    
}
