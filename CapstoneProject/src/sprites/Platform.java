package sprites;

import java.awt.*;
import processing.core.*;
import aviral.shapes.Shape;
import aviral.shapes.Rectangle;

public class Platform extends Sprite {

    // creates a platform at a random location
    // represented by a line
    public Platform(Shape s, double vx, double vy) {
        super(s, vx, vy);
    }


    public void draw(PApplet p, Player player) {
        super.draw(p);
        p.fill(255);
        
    }

}
