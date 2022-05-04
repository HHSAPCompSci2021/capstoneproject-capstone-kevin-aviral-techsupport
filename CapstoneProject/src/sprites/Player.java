package sprites;


import java.awt.*;

public class Player extends Sprite {
	private double x, y;
	private double vx, vy;
	private Color c;
	private double ax, ay;
	private int lives, totalLives;
	
	
	
    public Player(double x, double y, double vx, double vy, Color c, double ax, double ay, int totalLives) {
    	super(x, y, vx, vy, c, ax, ay, totalLives);
        
    }
    
    
}

