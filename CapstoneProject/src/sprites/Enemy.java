package sprites;

import java.awt.*;

public class Enemy extends Sprite {


    public Enemy(double x, double y, double vx, double vy, Color c, double ax, double ay, int total) {
        super(x, y, vx, vy, c, ax, ay, total);
    }

    public void shoot(double targetX, double targetY) {
        // create new projectile with velocity in the direciton of target point

    }

    public void draw(Papplet p) {
        super.draw(p);
        p.fill(200, 100, 100);
        
    }
    
}
