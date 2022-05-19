package sprites;

import java.awt.*;
import processing.core.PApplet;
import aviral.shapes.Circle;
import aviral.shapes.Rectangle;
import aviral.shapes.Shape;

public class Powerup extends Sprite {

    // different types of powerups: 
    // type 1: add another life if healthbar not full
    private int type;
    private float sideLength;

    public Powerup(Rectangle s, double vx, double vy, int type) {
        super(s, vx, vy);
        this.type = type;
        sideLength = (float) (s.getPerimeter()/4);
    }

    public void draw(PApplet p) {
        super.draw(p);
        p.push();

        p.fill(200, 200, 210);
        p.rect((float)getX(), (float)getY(), sideLength, sideLength, 5f, 5f, 5f, 5f);

        p.pop();
    }

    public int getType() {
        return type;
    }
    
}
