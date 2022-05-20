package sprites;

import aviral.shapes.Shape;
import processing.core.PApplet;
import processing.core.PImage;
import aviral.shapes.*;
import java.awt.*;

/**
 * 
 * @author Aviral Vaidya, Kevin Ren The Player class represents a double
 *         precision player that can be visualized using the processing library
 *
 */
public class Player extends Sprite {
	
	private final double djRate = 2.5; // double jump every 2.5s
	private final double fireRate = 0.45;

	private double lastJump;
	private double lastShot;

	private PImage lightOn, lightOff;
	private PImage bulletIcon;
	private double time;

	private int playerNum; // for multiplayer, 1 is on left, 2 is on right
	private float r;
	private int ammo;
	private int score;
	private boolean visible;

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
		visible = true;
		time = 0;
		lastJump = -9999;
		lastShot = -9999;
	}

	/**
	 * loads images for lives left
	 * 
	 * @param p surface on which images are loaded
	 * @post images are loaded on the surface
	 */
	public void loadAssets(PApplet p) {
		lightOn = p.loadImage("assets" + fileSep + "lantern_on.png");
		lightOff = p.loadImage("assets" + fileSep + "lantern_off.png");
		bulletIcon = p.loadImage("assets" + fileSep + "bulleticon.png");
	}

	public void draw(PApplet p) {
		super.draw(p);
		time++;
		final float WIDTH = 600;
		final float HEIGHT = 800;
		// check if player is out of bounds and have him appear on other side
		if (getX() >= WIDTH) {
			moveBy(-WIDTH, 0);
		} else if (getX() < 0) {
			moveBy(WIDTH, 0);
		}
		setScore((int) Math.max(getY()*2, score)); // *2 to compensate for going up
		setVx(getVx()*0.981);
		// actually draw the player
		if (visible) {
			// if player is partly off screen
			p.fill(255, 250, 251);
			if (getX() - r >= WIDTH) {
				p.circle((float) (WIDTH - getX() - r), (float) getY(), 2 * r);
			} else if (getX() + r <= 0) {
				p.circle((float) (WIDTH + getX()), (float) getY(), 2 * r);
			}
			p.circle((float) getX(), (float) getY(), 2 * r);
		}
		// draw the icons
		p.push();
		int tx = (int) (10 * (playerNum == 2 ? 50 : 1));
		int incr = 48;
		int which = 0;
		for (int ix = tx; which < 3; ix += incr, which++) {
			p.image((getLives() > which) ? lightOn : lightOff, ix, -6, incr, incr);
		}
		p.text("Score: " + score, tx, incr+24);
		incr = 16;
		which = 0;
		for (int ix = (int) (WIDTH - 30); which < ammo; ix -= (incr+5), which++) {
			p.image(bulletIcon, ix, 5, incr, incr*4.20f);
		}
		// bar should be full (halfway up screen) when double jump is available
		float percent = (float) Math.min(100, (time - lastJump)/(60*djRate/100));
		p.strokeWeight(10);
		if (percent < 100) {
			p.stroke(126, 158, 122); // dull green
		} else {
			p.stroke(10, 220, 120); // charged green
		}
		p.line(0, HEIGHT, 0, Math.max(400, HEIGHT - 400*percent/100));
		// bar should be full when ready to fire
		percent = (float) Math.min(100, (time - lastShot)/(60*fireRate/100));
		if (percent < 100) {
			p.stroke(190, 140, 57); // dull yellow
		} else {
			p.stroke(250, 193, 31); // charged yellow
		}
		if (ammo > 0) p.line(WIDTH, HEIGHT, WIDTH, Math.max(400, HEIGHT - 400*percent/100));
		p.pop();
	}

	/**
	 * Gets the freqeucny for player doube jump.
	 * @return The minimum value, in seconds, between double jumps of the player.
	 */
	public double getFreq() {
		return djRate;
	}

	public double getFireRate() {
		return fireRate;
	}

	/**
	 *	Gives the player an upward velocdoubleity and saves the time that this happens
	 */
	public void jump() {
		setVy(-5);
		lastJump = time;
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
		lastShot = time;
		Circle circle = new Circle(getX() - 32d, getY(), 8);
		return new Projectile(circle, -20, 0, 0, 0, true);
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
		lastShot = time;
		Circle circle = new Circle(getX() + 32d, getY(), 8);
		return new Projectile(circle, 20, 0, 0, 0, true);
	}

	/**
	 * Setter for player visibility
	 * 
	 * @param isVisible visibility of sprite
	 */
	public void setVisible(boolean isVisible) {
		visible = isVisible;
	}

	/**
	 * Toggles player visibility on the PApplet.
	 */
	public void toggleVisible() {
		visible = !visible;
	}

	/**
	 * Gets player number
	 * 
	 * @return The player number
	 */
	public int getPlayerNum() {
		return this.playerNum;
	}

	/**
	 * Sets the player number.
	 * @param playerNum Player number
	 */
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

	/**
	 * Gets the score of this player
	 * @return this player's score
	 */
	public long getScore() {
		return this.score;
	}

	/**
	 * Sets the score of this player
	 * @param score new score
	 */
	public void setScore(int score) {
		this.score = score;
	}

}
