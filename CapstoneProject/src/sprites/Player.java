package sprites;

import aviral.shapes.Shape;
import processing.core.PApplet;
import processing.core.PImage;
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

	private PImage lightOn, lightOff;

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
		r = (float) s.getRadius();
		ammo = 5;
	}

	public void loadAssets(PApplet p) {
		lightOn = p.loadImage("assets" + fileSep + "lantern_on.png");
		lightOff = p.loadImage("assets" + fileSep + "lantern_off.png");
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
		Circle circle = new Circle(getX() - 32d, getY(), 8);
		return new Projectile(circle, -6, 0, 0, 0);
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
		Circle circle = new Circle(getX() + 32d, getY(), 8);
		return new Projectile(circle, 6, 0, 0, 0);
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
		setScore((long) Math.max(getY()*2, score)); // *2 to compensate for going up
		p.fill(255, 250, 251);
		// if player is partly off screen
		if (getX() - r >= WIDTH) {
			p.circle((float) (WIDTH - getX() - r), (float) getY(), 2*r);
		} else if (getX() + r <= 0) {
			p.circle((float) (WIDTH + getX()), (float) getY(), 2*r);
		}
		setVx(getVx()*0.985);
		// System.out.println(getX() + " " + getY() + " " + r);
		p.circle((float) getX(), (float) getY(), 2 * r);
		int tx = (int) (10 * (playerNum == 2 ? 50 : 1));
		// make these graphic later
		// figure this out
		int incr = 48;
		int which = 0;
		for (int ix = tx; which < 3; ix += incr, which++) {
			p.image((getLives() > which) ? lightOn : lightOff, ix, -6, incr, incr);
		}
		p.text("Score: " + score, tx, incr+24);
		p.text("Ammo: " + ammo, tx, incr+64);
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
