package sprites;

import java.awt.*;
import processing.core.*;
import aviral.shapes.Shape;
import aviral.shapes.*;

public class Projectile extends Sprite {

    public Projectile(Shape s, double vx, double vy, double ax, double ay, Color c, int totalLives) {
        super(s, vx, vy, ax, ay, c, totalLives);
    }

    public void draw(PApplet p, Player player) {
        super.draw(p);
        p.fill(255);
        
    }

    
}
