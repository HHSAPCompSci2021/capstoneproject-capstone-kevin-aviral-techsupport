package sprites;

import java.awt.*;
import processing.core.*;

public class Projectile extends Sprite {

    public Projectile(double x, double y, double vx, double vy, Color c) {
    	super(x, y, vx, vy, c, 0, 0, 1);

    }

    public void draw(PApplet p, Player player) {
        super.draw(p);
        p.fill(255);
        
    }

    
}
