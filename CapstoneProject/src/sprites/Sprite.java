package sprites;

import java.awt.*;
import processing.core.PApplet;

public class Sprite {

    private double x, y;
    private double vx, vy;
    private Color c;
    private double ax, ay;
    private int lives, totalLives;
    

    public Sprite(double x, double y, double vx, double vy, Color c, double ax, double ay, int totalLives) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.c = c;
        this.ax = ax;
        this.ay = ay;
        this.totalLives = totalLives;
        lives = totalLives;
    }

    public Sprite(double x, double y) {
        this.x = x;
        this.y = y;
        c = new Color(100, 100, 100);
        vx = 0;
        vy = 0;
        ax = 0;
        ay = 0;
        totalLives = 1;
        lives = 1;
    }
    
    public void draw(PApplet p) {
        x += vx;
        y += vy;
        vx += ax;
        vy += ay;
    }

    public boolean isTouching(Sprite other) {
        return false;
    }

    public void moveTo(double x2, double y2) {
		x = x2;
        y = y2;
	}
	
	public void moveByAmount(double dx, double dy) {
		x += dx;
        y += dy;
	}

}
