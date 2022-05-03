package sprites;
import java.awt.*;

public class Sprite {
    private double x, y;
    private double xv, yv;
    private Color c;
    private double ax, ay;
    
    public Sprite (double x, double y, double xv, double yv, Color c, double ax, double ay) {
    	this.x = x;
    	this.y = y;
    	this.xv = xv;
    	this.yv = yv;
    	this.c = c;
    	this.ax = ax;
    	this.ay = ay;
    }
    
    public void draw () {
    	
    }
    
    public boolean isTouching () {
    	return false; 
    }
    
    public void moveTo (double x2, double y2) {
    	
    }
}
