package sprites;

import aviral.shapes.Shape;
import processing.core.PApplet;
import aviral.shapes.*;
import java.awt.*;
/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 * The Player class represents a double precision player that can be visualized using the processing library
 *
 */
public class Player extends Sprite {

	private float r;

	/**
	 * creates a player object
	 * @param s shape of player
	 * @param vx x velocity of player
	 * @param vy y velocity of player
	 * @param ax x acceleration of player
	 * @param ay y acceleration of player
	 * @param totalLives
	 */
    public Player(Circle s, double vx, double vy, double ax, double ay, int totalLives) {
        super(s, vx, vy, ax, ay, totalLives);
		r = (float)s.getR();
    }

	public void draw(PApplet p) {
		super.draw(p);
		// System.out.println(getX() + " " + getY() + " " + r);
		p.circle((float)getX(), (float)getY(), r);
		p.text("Lives: " + getLives() + "/" + getTotalLives(), 500, 24); // TODO: make this vary with window size
	}

}

