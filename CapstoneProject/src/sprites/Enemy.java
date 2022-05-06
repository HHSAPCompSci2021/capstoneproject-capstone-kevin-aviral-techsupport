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
        double pv = 3;
        double projVx = 0, projVy = 0;
        if (targetX > getX()) {
            projVx = pv;
        } else {
            projVx = -pv;
        }

        if (targetY > getY()) {
            projVy = pv;
        } else {
            projVy = -pv;
        }
        Circle circle = new Circle(getX() + projVx, getY() + projVy, 2);
        Projectile p = new Projectile(circle, projVx, projVy, 0, 0);
        // add to list of stuff on screen
    }

    public void draw(PApplet p) {
        super.draw(p);
        p.fill(200, 100, 100);

    }
    
}
