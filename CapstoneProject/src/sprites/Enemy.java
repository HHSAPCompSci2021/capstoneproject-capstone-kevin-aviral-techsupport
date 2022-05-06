package sprites;

import processing.core.*;
import java.awt.*;
import aviral.shapes.Shape;
import aviral.shapes.*;

public class Enemy extends Sprite {


    public Enemy(Shape s, double vx, double vy, double ax, double ay, Color c, int totalLives) {
        super(s, vx, vy, ax, ay, c, totalLives);
    }

    public void shoot(double targetX, double targetY) {
        // create new projectile with velocity in the direciton of target point

    }

    public void draw(PApplet p) {
        super.draw(p);
        p.fill(200, 100, 100);

    }
    
}
