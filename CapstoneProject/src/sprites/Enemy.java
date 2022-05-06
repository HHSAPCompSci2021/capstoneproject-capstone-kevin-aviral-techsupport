package sprites;
import processing.core.*;
import java.awt.*;

public class Enemy extends Sprite {


    public Enemy(double x, double y, Color c) {
        super(x, y, c);
        
    }

    public void shoot(double targetX, double targetY) {
        // create new projectile with velocity in the direciton of target point

    }

    public void draw(PApplet p) {
        super.draw(p);
        p.fill(200, 100, 100);

    }
    
}
