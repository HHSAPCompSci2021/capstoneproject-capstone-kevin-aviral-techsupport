package sprites;

import aviral.shapes.Shape;
import processing.core.PApplet;
import aviral.shapes.*;
import java.awt.*;

/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 *         The Player class represents a double precision player that can be
 *         visualized using the processing library
 *
 */
public class Player extends Sprite {

	private static final double g = 0.18;
	private int playerNum; // for multiplayer, 1 is on left, 2 is on right
	private float r;
	private int ammo;
	private long score;

	/**
	 * creates a player object
	 * 
	 * @param s          shape of player
	 * @param vx         x velocity of player
	 * @param vy         y velocity of player
	 * @param ax         x acceleration of player
	 * @param ay         y acceleration of player
	 * @param totalLives total lives of player
	 */
	public Player(Circle s, double vx, double vy, double ax, double ay, int totalLives) {
		super(s, vx, vy, ax, ay, totalLives);
		r = (float) s.getR();
		ammo = 10;
	}

	/**
	 * shoots a projectile to the left
	 * 
	 * @return projectile translated left
	 */
	public Projectile shootLeft() {
		if (ammo == 0)
			return null;
		ammo--;
		Circle circle = new Circle(getX() - 8, getY(), 2);
		System.out.println("left");
		return new Projectile(circle, -8, 0, 0, 0);
	}

	/**
	 * shoots a projectile to the right
	 * 
	 * @return projectile translated right
	 */
	public Projectile shootRight() {
		if (ammo == 0)
			return null;
		ammo--;
		Circle circle = new Circle(getX() + 8, getY(), 2);

		return new Projectile(circle, 8, super.getX() / Math.abs(super.getX()), super.getY() / Math.abs(super.getY()),
				0);
	}

	public void draw(PApplet p) {
		super.draw(p);
		final int WIDTH = 600;
		// check if player is out of bounds and have him appear on other side
		if (getX() >= WIDTH) {
			moveBy(-WIDTH, 0);
		} else if (getX() < 0) {
			moveBy(WIDTH, 0);
		}

		p.fill(255, 250, 251);
		// if player is partly off screen
		if (getX() + r >= WIDTH) {
			p.circle((float)(WIDTH - getX() - r), (float)getY(), 2*r);
		} else if (getX() - r <= 0) {
			p.circle((float)(WIDTH + getX() - r), (float)getY(), 2*r);
		}
		// System.out.println(getX() + " " + getY() + " " + r);
		p.circle((float) getX(), (float) getY(), 2 * r);
		int tx = 100 * (playerNum == 2 ? 5 : 1);
		p.text("Score: " + score, tx, 24);
		// make these graphic later
		p.text("Lives: " + getLives() + "/" + getTotalLives(), tx, 48);
		p.text("Ammo: " + ammo, tx, 64);
	}

	public int getPlayerNum() {
		return this.playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	/**
	 * getter for ammo
	 * 
	 * @return current ammo
	 */
	public int getAmmo() {
		return this.ammo;
	}

	/**
	 * sets ammo to a new value
	 * 
	 * @param ammo new ammo
	 */
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	/**
	 * getter for radius
	 * 
	 * @return radius
	 */
	public double getR() {
		return r;
	}

	public long getScore() {
		return this.score;
	}

	public void setScore(long score) {
		this.score = score;
	}

}
