package sprites;

import java.awt.*;

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
    }

    public void draw() {
        x += vx;
        y += vy;
        vx += ax;
        vy += ay;
    }

    public boolean isTouching() {
        return false;
    }

    public void moveTo(double x2, double y2) {

    }

}
