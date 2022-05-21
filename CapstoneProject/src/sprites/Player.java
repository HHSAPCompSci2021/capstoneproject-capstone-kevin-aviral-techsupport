package sprites;

import aviral.shapes.Shape;
import processing.core.PApplet;
import static processing.core.PApplet.*;
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

	private double djRate = 3;
	private double fireRate = 0.55;

	private double lastJump;
	private double lastShot;

	private PImage ball;
	private PImage lightOn, lightOff;
	private PImage bulletIcon;
	private double time;
	private Circle s;
	private int playerNum; // for multiplayer, 1 is on left, 2 is on right
	private float r;
	private int ammo;
	private int score;
	private boolean visible;

	private float angle;
	private float w;
	private float alpha;

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
		this.s = s;
		r = (float) (float) s.getRadius();
		ammo = 5;
		visible = true;
		time = 0;
		lastJump = -9999;
		lastShot = -9999;
		
		w = 0;
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
		ball = p.loadImage("assets" + fileSep + "8ball.png");
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
		setScore((int) Math.max(getY() * 2, score)); // *2 to compensate for going up
		setVx(getVx() * 0.981);

		if (time/60 >= 40) {
			fireRate = 0.15;
			djRate = 1;
		} else if (time/60 >= 20) {
			fireRate = 0.25;
			djRate = 1.5;
		}
		if (time/60 >= 10) {
			fireRate = 0.35;
			djRate = 2;
		} else if (time/60 >= 5) {
			fireRate = 0.45;
			djRate = 2.5;
		}
		if (w != 0) angle = (float) ((w > 0 ? 2 : -2)*PI*time/30);
		// actually draw the player
		p.push();
		if (visible) {
			p.noStroke();
			p.ellipseMode(CENTER);
			p.ellipse((float) s.getX(), (float) s.getY(), (float) getR()*2+4, (float) getR()*2+4);
			p.imageMode(CENTER);
			/*
			if (getX() - r >= WIDTH) {
				s.move((WIDTH - getX() - r), (float) getY());
				s.setStrokeColor(s.getFillColor());
				s.draw(p);
				p.fill(Color.black.getRGB());
				p.text("8", (float) s.getX() - 4, (float) s.getY() + 4);
			} else if (getX() + r <= 0) {
				s.move(WIDTH + getX(), getY());
				s.setStrokeColor(s.getFillColor());
				s.draw(p);
				p.fill(Color.black.getRGB());
				p.text("8", (float) s.getX() - 4, (float) s.getY() + 4);
			}
			*/
			p.translate((float) getX(), (float) getY());
			p.rotate(angle);
			p.image(ball, 0, 0, (float) getR()*2, (float) getR()*2);
		}
		p.pop();
		// draw the icons
		p.push();
		int tx = (int) (10 * (playerNum == 2 ? 50 : 1));
		int incr = 48;
		int which = 0;
		for (int ix = tx; which < 3; ix += incr, which++) {
			p.image((getLives() > which) ? lightOn : lightOff, ix, -6, incr, incr);
		}
		p.fill(255, 255, 255);
		p.text("Score: " + score, tx, incr + 24);
		incr = 16;
		which = 0;
		for (int ix = (int) (WIDTH - 30); which < ammo; ix -= (incr + 5), which++) {
			p.image(bulletIcon, ix, 5, incr, incr * 4.20f);
		}
		// bar should be full (halfway up screen) when double jump is available
		float percent = (float) Math.min(100, (time - lastJump)/(60 * djRate/100));
		p.strokeWeight(10);
		if (percent < 100) {
			p.stroke(126, 158, 122); // dull green
		} else {
			p.stroke(10, 220, 120); // charged green
		}
		p.line(0, HEIGHT, 0, Math.max(400, HEIGHT - 400 * percent/100));
		// bar should be full when ready to fire
		percent = (float) Math.min(100, (time - lastShot)/(60 * fireRate/100));
		if (percent < 100) {
			p.stroke(180, 130, 50); // dull yellow
		} else {
			p.stroke(255, 200, 31); // charged yellow
		}
		if (ammo > 0) p.line(WIDTH, HEIGHT, WIDTH, Math.max(400, HEIGHT - 400 * percent/100));
		p.pop();
	}

	public void setW(float w) {
		this.w = w;
	}

	/**
	 * Gets the freqeucny for player doube jump.
	 * 
	 * @return The minimum value, in seconds, between double jumps of the player.
	 */
	public double getFreq() {
		return djRate;
	}

	public double getFireRate() {
		return fireRate;
	}

	/**
	 * Gives the player an upward velocity and saves the time that this happens
	 */
	public void jump() {
		setVy(-5);
		lastJump = time;
	}

	/**
	 * Gives the player a downward velocity and saves the time that this happens
	 */
	public void jumpDown() {
		setVy(3);
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
		Circle circle = new Circle(getX() - 25d, getY(), 8);
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
		Circle circle = new Circle(getX() + 25d, getY(), 8);
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
	 * 
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
	 * 
	 * @return this player's score
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Sets the score of this player
	 * 
	 * @param l new score
	 */
	public void setScore(int l) {
		this.score = l;
	}

}
