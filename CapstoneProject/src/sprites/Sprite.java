package sprites;

import java.awt.*;
import processing.core.PApplet;
import aviral.shapes.*;
import aviral.shapes.Shape;

public class Sprite {

    private Shape s;
    private double vx, vy;
    private double ax, ay;
    private int lives, totalLives;

    public Sprite(Shape s, double vx, double vy, double ax, double ay, Color c, int totalLives) {
        this.s = s;
        this.vx = vx;
        this.vy = vy;
        this.totalLives = totalLives;
        lives = totalLives;
    }

    public Sprite(Color c) {
        // ???
        vx = 0;
        vy = 0;
        ax = 0;
        ay = 0;
        totalLives = 1;
        lives = 1;
    }
    
    public void draw(PApplet p) {
        s.movePointBy(vx, vy);
        vx += ax;
        vy += ay;
    }

    public boolean isTouching(Sprite other) {
        return false;
    }

}
