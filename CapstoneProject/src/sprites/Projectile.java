package sprites;

import java.awt.*;
import processing.core.*;
import aviral.shapes.Shape;
import aviral.shapes.*;

public class Projectile extends Sprite {

    public Projectile(Shape s, double vx, double vy, double ax, double ay) {
        super(s, vx, vy, ax, ay, 1);
    }

    public void draw(PApplet p, Player player) {
        super.draw(p);
        p.fill(255);
        
    }

}
