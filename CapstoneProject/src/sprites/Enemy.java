package sprites;

import processing.core.*;
import java.awt.*;
import aviral.shapes.Shape;
import aviral.shapes.*;

public class Enemy extends Sprite {


    public Enemy(Shape s, double vx, double vy, double ax, double ay) {
        super(s, vx, vy, ax, ay);
    }

    public void shoot(double targetX, double targetY) {
        // create new projectile with velocity in the direciton of target point
        
        // Projectile p = new Projectile()
    }

    public void draw(PApplet p) {
        super.draw(p);
        p.fill(200, 100, 100);

    }
    
}
