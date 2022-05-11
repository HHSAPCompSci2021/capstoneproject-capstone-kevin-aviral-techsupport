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
	private int ammo;

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
		ammo = 5;
    }

	public Projectile shootLeft() {
		if (ammo == 0) return null;
		ammo--;
		Circle circle = new Circle(getX() - 8, getY(), 2);
		System.out.println("left");
        return new Projectile(circle, - 8, 0, 0, 0);
	}

	public Projectile shootRight() {
		if (ammo == 0) return null;
		ammo--;
		Circle circle = new Circle(getX() + 8, getY(), 2);
		System.out.println("right");
        return new Projectile(circle, 8, 0, 0, 0);
	}

	public void draw(PApplet p) {
		super.draw(p);
		// System.out.println(getX() + " " + getY() + " " + r);
		p.fill(255, 250, 251);
		p.circle((float)getX(), (float)getY(), 2*r);
		p.text("Lives: " + getLives() + "/" + getTotalLives(), 500, 24);
	}

	public int getAmmo() {
		return this.ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public double getR() {
		return r;
	}

}
