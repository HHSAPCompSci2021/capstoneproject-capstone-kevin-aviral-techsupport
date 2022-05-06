package sprites;

import java.awt.*;
import processing.core.PApplet;
import aviral.shapes.Rectangle;
import aviral.shapes.Shape;

public class Sprite {

    private Shape s;
    private double vx, vy;
    private double ax, ay;
    private int lives, totalLives;

    public Sprite(Shape s, double vx, double vy, double ax, double ay, int totalLives) {
        this.s = s;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
        this.totalLives = totalLives;
        lives = totalLives;
    }

    public Sprite(Shape s, double vx, double vy) {
        this.s = s;
        this.vx = vx;
        this.vy = vy;
        ax = 0;
        ay = 0;
        totalLives = 1;
        lives = 1;
    }

    public Sprite(Shape s, double vx, double vy, double ax, double ay) {
        this.s = s;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
        totalLives = 1;
        lives = 1;
    }
    
    public void draw(PApplet p) {
        s.movePointBy(vx, vy);
        vx += ax;
        vy += ay;
    }

    public boolean isTouching(Sprite other) {
        // different shapes in this game:
        // rectangle 
        // line 
        // circle
        return false;
    }

}
