package screens;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.util.*;
import aviral.shapes.*;
import core.*;
import processing.core.*;
import static processing.core.PApplet.*;
import sprites.*;

/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 *         The game class represents a double precision game screen screen that
 *         can be represented using processing
 */
public class Game extends Screen {

	private static final double g = 0.1;
	private static final float len = 40;
	private static final int maxePop = 2;

	private double scrollBy; // how fast everything scrolls up
	private double border;

	private PImage gameOverText;
	private DrawingSurface surface;
	private Rectangle screenRect;
	private Player player;
	// 0 if vert, 1 if right, 2 if left
	private ArrayList<Pair<Platform, Integer>> platforms;
	private ArrayList<Platform> horizontal;
	private ArrayList<Sprite> enemies; // also stores projectiles
	private ArrayList<Powerup> powerups;
	private ArrayList<Enemy> dead;
	private ArrayList<Platform> falling;

	private float time; // keep track of game time
	private float fireTime; // time of previous shooting from player
	private float hitTime; // previous time player got hit
	private float lastJump; // time of last double jump
	private float deathTime;
	private boolean show = true;
	private boolean hideCursor = false;
	
	/**
	 * Creates a new game object
	 * 
	 * @param surface the window
	 */
	public Game(DrawingSurface surface) {
		super(600, 800);
		this.surface = surface;
		screenRect = new Rectangle(0, 0, WIDTH, HEIGHT);
		platforms = new ArrayList<>();
		horizontal = new ArrayList<>();
		enemies = new ArrayList<>();
		powerups = new ArrayList<>();
		dead = new ArrayList<>();
		falling = new ArrayList<>();
		
		border = 0;
		generatePlatforms(HEIGHT/2, HEIGHT, 5);
		
		player = new Player(new Circle(WIDTH/2, 48, 24), 0, 0, 0, g, 3);
		time = 0;
		fireTime = -9999;
		hitTime = -9999;
		lastJump = -9999;
		deathTime = -1;
		scrollBy = -2d;
	}
	
	public void setup() {

		player.loadAssets(this.surface);
		gameOverText = surface.loadImage("assets" + fileSep + "gameover.png");
	}
	
	private Color top;	
	private Color bot;
	private int dtr = 12, dtg = 21, dtb = 46;

	/**
	 * Draws the game screen
	 * 
	 * @post The game screen is drawn
	 */
	public void draw() {
		// check for keypresses
		if (surface.isPressed(KeyEvent.VK_ESCAPE)) {
			surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
		}
		if (surface.isPressed(KeyEvent.VK_LEFT) || surface.isPressed(KeyEvent.VK_A)) {
			player.moveBy(-4, 0);
			hideCursor = true;
		}
		if (surface.isPressed(KeyEvent.VK_RIGHT) || surface.isPressed(KeyEvent.VK_D)) {
			player.moveBy(4, 0);
			hideCursor = true;
		}
		if (surface.isPressed(KeyEvent.VK_Q) && (time - fireTime)/60 >= player.getFireRate()) {
			if (player.getAmmo() > 0) {
				enemies.add(player.shootLeft());
				enemies.get(enemies.size() - 1).loadAssets(surface);
				fireTime = time;
			}
			hideCursor = true;
		}
		if (surface.isPressed(KeyEvent.VK_E) && (time - fireTime)/60 >= player.getFireRate()) {
			if (player.getAmmo() > 0) {
				enemies.add(player.shootRight());
				enemies.get(enemies.size() - 1).loadAssets(surface);
				fireTime = time;
			}
			hideCursor = true;
		}
		if (surface.isPressed(KeyEvent.VK_W) || surface.isPressed(KeyEvent.VK_UP)) {
			if ((time - lastJump)/60 >= player.getFreq()) {
				player.jump();
				lastJump = time;
			}
			hideCursor = true;
		}
		if (surface.isPressed(KeyEvent.VK_S) || surface.isPressed(KeyEvent.VK_DOWN)) {
			if ((time - lastJump)/60 >= player.getFreq()) {
				player.jumpDown();
				lastJump = time;
			}
			hideCursor = true;
		}

		if (hideCursor)
			surface.noCursor();
		else
			surface.cursor();

		time++;

		if (player.getLives() >= 3) {
			top = new Color(80, 130, 211);	
			bot = new Color(25, 25, 72);
		} else if (player.getLives() == 2) {
			top = new Color(69, 108, 175);
			bot = new Color(20, 20, 64);
		} else if (player.getLives() == 1) {
			top = new Color(48, 65, 126);
			bot = new Color(12, 21, 47);
		} else if (player.getLives() <= 0) {
			if (dtr > 0 && dtg > 0 && dtb > 0) {
				top = new Color(dtr, dtg, dtb);
				bot = new Color(0, 0, 0);
			}
			if (time%6 == 0) {
				dtr -= 0.02;
				dtg -= 0.02;
				dtb -= 0.03;
			}
		}
		setGradient(0, 0, WIDTH, HEIGHT/3, top, bot);
		// falling enemies
		for (int i = 0; i < dead.size(); i++) {
			if (dead.get(i).getY() > HEIGHT) {
				dead.remove(i--);
				continue;
			}
			dead.get(i).fall(surface);
		}
		// falling platforms
		for (int i = 0; i < falling.size(); i++) {
			if (falling.get(i).getY() > HEIGHT) {
				falling.remove(i--);
				continue;
			}
			falling.get(i).draw(surface);
		}
		// sus loop（enemies are drawn）
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i) == null) {
				enemies.remove(i--);
				continue;
			}
			double x = enemies.get(i).getX(), y = enemies.get(i).getY();
			// check if it is out of bounds
			if (x < 10 || x > WIDTH - 10 || y < 5) {
				enemies.remove(i--);
				continue;
			}
			if (enemies.get(i) instanceof Projectile) {
				if (y > HEIGHT) {
					enemies.remove(i--);
					continue;
				}
				// check if projectile hit an enemy
				for (int j = 0; j < enemies.size(); j++) {
					if (enemies.get(j) == null || enemies.get(j) instanceof Projectile)
						continue;
					if (enemies.get(j).getShape().isPointInside(x, y)) {
						// enemy is now dead
						enemies.get(j).setLives(enemies.get(j).getLives() - 1);
						enemies.get(j).setVx(((enemies.get(i).getVx() > 0) ? 1 : -1) * Math.random() * 2 + 0.5);
					}
				}
				// check if projectile hit a powerup
				for (int j = 0; j < powerups.size(); j++) {
					if (powerups.get(j) == null) {
						powerups.remove(j--);
						continue;
					} else if (powerups.get(j).getShape().isPointInside(x, y)) {
						applyPowerup(powerups.get(j));
						powerups.remove(j--);
					}
				}
			}
			if (enemies.get(i) instanceof Enemy) {
				if (y <= HEIGHT)
					enemies.get(i).setLastTime(time);
				// check for death
				if (enemies.get(i).getLives() <= 0) {
					player.setScore(player.getScore()+200);
					dead.add((Enemy) enemies.remove(i--));
					dead.get(dead.size() - 1).setAy(5 * g);
					continue;
				}
				// different attack patterns
				if (((Enemy) enemies.get(i)).getFirePattern() == 1 && time % 6 == 0) {
					enemies.add(enemies.get(i).shoot(player.getX(), player.getY()));
					enemies.get(i).setLastTime(time);
					if (time % 120 == 0) {
						enemies.get(i).setBoolean(true);
					} else if (time % 10 == 0) {
						enemies.get(i).setBoolean(false);
					}
				} else if (time % 90 == i * 10) {
					enemies.get(i).setBoolean(true);
					enemies.add(enemies.get(i).shoot(player.getX(), player.getY()));
					enemies.get(i).setLastTime(time);
				}
			}
			// grant temporary invincibility if player is hit or shot
			if (time/60 > hitTime/60 + 0.3
					&& player.getShape().isPointInside(enemies.get(i).getX(), enemies.get(i).getY())) {
				player.setLives(player.getLives() - 1);
				hitTime = time;
				if (player.getLives() == 0) {
					for (int j = platforms.size() - 1; j >= 0; j--) {
						platforms.remove(j);
					}
					for (int j = enemies.size() - 1; j >= 0; j--) {
						enemies.remove(j);
					}
					for (int j = powerups.size() - 1; j >= 0; j--) {
						powerups.remove(j);
					}
				}
			}
			if (enemies.size() > 0) {
				enemies.get(i).moveBy(0, scrollBy);
				enemies.get(i).draw(surface);
			}

		}
		// powerups drawn
		for (int i = 0; i < powerups.size(); i++) {
			if (player.getShape().isPointInside(powerups.get(i).getX(), powerups.get(i).getY())) {
				applyPowerup(powerups.get(i));
				powerups.remove(i--);
				continue;
			}
			powerups.get(i).moveBy(0, scrollBy);
			powerups.get(i).draw(surface);
		}
		// platforms drawn and player platform collisions
		for (int i = 0; i < platforms.size(); i++) {
			if (platforms.get(i) == null || platforms.get(i).first.getY() < 0) {
				platforms.remove(i--);
				continue;
			}
			platforms.get(i).first.moveBy(0, scrollBy);
			platforms.get(i).first.draw(surface);
			// cant collide from below
			if (player.getR() + player.getY() < platforms.get(i).first.getY())
				continue;
			if (player.isTouching(platforms.get(i).first) && platforms.get(i).second == 0 && player.getVy() > 0) {
				player.moveBy(player.getVx(), -player.getVy());
				if (player.getVx() != 0) player.setW(player.getVx() > 0 ? 1 : -1);
				// vertical so no need to multiply by - 1 because there is only one direction
				player.setVy(-3.4);
			} else if (player.isTouching(platforms.get(i).first) && platforms.get(i).second == 3
			&& player.getVy() > 0) {
				player.moveBy(player.getVx(), -player.getVy());
				if (player.getVx() != 0) player.setW(player.getVx() > 0 ? 1 : -1);
				player.setVy(-3.4);
				player.setScore(player.getScore()+100);
				falling.add(platforms.get(i).first);
				falling.get(falling.size() - 1).setAy(5 * g);
				platforms.remove(i--);
			} else if (player.isTouching(platforms.get(i).first) && platforms.get(i).second == 2) {
				player.moveBy(player.getVx(), -player.getVy());
				player.setW(1);
				player.setVx(3.4/Math.sqrt(2));
				player.setVy(-3.4/Math.sqrt(2));
			} else if (player.isTouching(platforms.get(i).first) && platforms.get(i).second == 1) {
				player.setW(-1);
				player.moveBy(player.getVx(), -player.getVy());
				player.setVx(-3.4/Math.sqrt(2));
				player.setVy(-3.4/Math.sqrt(2));
			}
		}

		if (player.getLives() <= 0) {
			if (time/60 - deathTime/60 < 5)
				scrollBy = -75;
			else
				scrollBy = 0;
			surface.push();
			surface.image(gameOverText, WIDTH/2 - 500/2, 100, 500, 100);
			surface.textSize(64f);
			surface.fill(85, 81, 91);
			String message = "Score: " + player.getScore();
			surface.text(message, WIDTH/2 - surface.textWidth(message)/2, 550);
			message = "[ESC] to continue";
			surface.textSize(48f);
			surface.text(message, WIDTH/2 - surface.textWidth(message)/2, 650);
			surface.pop();
			if (surface.isPressed(KeyEvent.VK_ESCAPE)) {
				surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
			}
			return;
		}

		if (player.getY() > HEIGHT) {
			deathTime = time;
			player.setLives(0);
		} else if (time/60 > hitTime/60 + 0.3 && player.getY() <= 0) {
			player.moveBy(0, player.getY() + 2 * player.getR());
			player.setVy(1.5);
			player.setLives(player.getLives() - 1);
			hitTime = time;
		}
		Color threeL = Color.WHITE;
		Color oneL = new Color(255, 114, 111);
		Color twoL = new Color(255, 174, 66);
		if (player.getLives() == 3) {
			player.getShape().setFillColor(threeL);
		} 
		if (player.getLives() == 2) {
			player.getShape().setFillColor(twoL);
		}
		if (player.getLives()  == 1) {
			player.getShape().setFillColor(oneL);
		}
		border += scrollBy;
		// still has to fix scoring past this point
		// respawn the entities
		if (border <= 0) {
			generatePlatforms(HEIGHT, 2 * HEIGHT, 10);
			spawnEnemies(HEIGHT, 2 * HEIGHT);
			spawnPowerups(HEIGHT, 2 * HEIGHT, (int) (Math.random() * 4));
			border = HEIGHT;
		}
		player.moveBy(0, scrollBy);
		scrollBy = -Math.max(Math.abs(-2d), Math.abs(player.getVy())/2);
		if (time/60 - hitTime/60 <= 0.3) {
			if (time % 5 == 0)
				player.toggleVisible();
		} else {
			player.setVisible(true);
		}
		player.draw(surface);
	}

	private void applyPowerup(Powerup item) {
		item.setLives(0);
		switch (item.getType()) {
			case 1:
				if (player.getLives() < 3)
					player.setLives(player.getLives() + 1);
				break;
			case 2:
				player.setAmmo(player.getAmmo() + 3);
				break;
			case 3:
				player.setLives(3);
				break;
		}
	}

	// change num to getE() when function is working
	private void spawnPowerups(float min, float max, int num) {
		for (int i = 0; i < num; i++) {
			float sx = (float) (Math.random() * WIDTH);
			float sy = (float) (Math.random() * (max - min)) + min;
			int tries = 0;
			while (tooClose(sx, sy, 300)) {
				if (tries > 10)
					break;
				// takes a while
				sx = (float) (Math.random() * WIDTH);
				sy = (float) (Math.random() * (max - min)) + min;
				tries++;
			}
			Rectangle rect = new Rectangle(sx, sy, 25, 25);
			Powerup pu = new Powerup(rect, 0, 0, (int) (Math.random() * 4) + 1);
			pu.loadAssets(surface);
			powerups.add(pu);
		}
	}

	// change num to getE() when funciton is working
	private void spawnEnemies(float min, float max) {
		for (int i = 0; i < getE(false); i++) {
			float sx = (float) (Math.random() * WIDTH);
			float sy = (float) (Math.random() * (max - min)) + min;
			int tries = 0;
			while (tooClose(sx, sy, 400)) {
				if (!(tries <= 16))
					break;
				// this takes too much time sometimes so have a counter
				sx = (float) (Math.random() * WIDTH);
				sy = (float) (Math.random() * (max - min)) + min;
				tries++;
			}
			// spawn the enemies
			Rectangle rect = new Rectangle(sx, sy, 40, 40);
			enemies.add(new Enemy(rect, 0, 0, 0, 0));
			enemies.get(enemies.size() - 1).loadAssets(surface);
		}
	}

	private void generatePlatforms(float min, float max, int num) {
		for (int i = 0; i < num; i++) {
			float lx = (float) (Math.random() * WIDTH);
			float ly = (float) (Math.random() * (max - min)) + min;
			int tries = 0;
			while (tooClose(lx, ly, 200) || lx > WIDTH - len || ly == HEIGHT || ly == 0) {
				if (tries > 15)
					break;
				lx = (float) (Math.random() * WIDTH);
				ly = (float) (Math.random() * (max - min)) + min;
				tries++;
			}
			double a = Math.random();
			int vx = 0, vy = 0;
			/*
			 * if (a >= 0.9) { vx = (int)(Math.random()*5 + 2); }
			 */
			// 50% chance of being angular
			if (a >= 0.5) {
				Line newLine1 = new Line(lx, ly, lx + len, ly);
				Line newLine2 = new Line(lx + len, ly, lx, ly);
				newLine1.setFillColor(new Color(253, 254, 255));
				newLine2.setFillColor(new Color(253, 254, 255));
				double c = Math.random();
				if (c <= 0.2) {
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine1, .5, vy), 0));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine2, .5, vy), 0));
				} else {
					double d = Math.random();
					if (d <= 0.1) {
						newLine1 = new Line(lx, ly, lx + len/2, ly);
						newLine2 = new Line(lx + len/2, ly, lx, ly);
						newLine1.setFillColor(new Color(0, 254, 0));
						newLine2.setFillColor(new Color(0, 254, 0));
						platforms.add(new Pair<Platform, Integer>(new Platform(newLine1, vx, vy), 3));
						System.out.println("disappearing added");

					} else {
						newLine1 = new Line(lx, ly, lx + len, ly);
						newLine2 = new Line(lx + len, ly, lx, ly);
						newLine1.setFillColor(new Color(253, 254, 255));
						newLine2.setFillColor(new Color(253, 254, 255));
						platforms.add(new Pair<Platform, Integer>(new Platform(newLine1, vx, vy), 0));
						platforms.add(new Pair<Platform, Integer>(new Platform(newLine2, vx, vy), 0));
					}

				}

			} else {
				double b = Math.random();
				if (b >= .5) {
					// int angle = (int) Math.random() * 180;
					Line newLine1 = new Line(lx, ly, (float) (lx + len/Math.sqrt(2)),
							(float) (ly - len/Math.sqrt(2)));
					Line newLine2 = new Line((float) (lx + len/Math.sqrt(2)), (float) (ly - len/Math.sqrt(2)), lx,
							ly);
					newLine1.setFillColor(new Color(253, 254, 255));
					newLine2.setFillColor(new Color(253, 254, 255));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine1, vx, vy), 1));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine2, vx, vy), 1));
				} else {
					// int angle = (int) Math.random() * 180;
					Line newLine1 = new Line(lx, ly, (float) (lx + len/Math.sqrt(2)),
							(float) (ly + len/Math.sqrt(2)));
					Line newLine2 = new Line((float) (lx + len/Math.sqrt(2)), (float) (ly + len/Math.sqrt(2)), lx,
							ly);
					newLine1.setFillColor(new Color(253, 254, 255));
					newLine2.setFillColor(new Color(253, 254, 255));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine1, vx, vy), 2));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine2, vx, vy), 2));
				}
			}
		}
		for (int i = platforms.size() - 1; i >= 0; i--) {
			if (platforms.get(i).second == 0
					&& (platforms.get(i).first.getX() >= WIDTH - len || platforms.get(i).first.getX() <= len)) {
				platforms.remove(i);
			}
		}
		for (int i = horizontal.size() - 1; i >= 0; i--) {
			if (horizontal.get(i).getX() >= WIDTH - len || horizontal.get(i).getX() <= len) {
				horizontal.remove(i);
			}
		}
	}

	private boolean tooClose(double lx, double ly, double radius) {
		// given a point (lx, ly), determine if it is radius away from ALL existing
		// platforms
		for (int i = 0; i < platforms.size(); i++) {
			double xdist = Math.abs(lx - platforms.get(i).first.getX());
			double ydist = Math.abs(ly - platforms.get(i).first.getY());
			if (Math.sqrt(xdist * xdist + ydist * ydist) < radius) {
				return true;
			}
		}
		return false;
	}
	/**
	 * detects user mouse movement
	 */
	public void mouseMoved() {
		if (hideCursor)
			hideCursor = false;
	}

	private void setGradient(int x, int y, float w, float h, Color c1, Color c2) {
		surface.background(c2.getRGB());
		surface.noFill();
		for (int i = y; i <= y + h; i++) {
			float inter = map(i, y, y + h, 0, 1);
			Color c = new Color(surface.lerpColor(c1.getRGB(), c2.getRGB(), inter));
			surface.stroke(c.getRGB());
			surface.line(x, i, x + w, i);
		}
	}

	/**
	 * adds and enemy or projectile to the list of things to be added to the screen
	 * 
	 * @param s sprite to be added
	 */
	public void addEnemyOrProjectile(Sprite s) {
		enemies.add(s);
	}

	/**
	 * returns boolean value based on player visibility
	 * 
	 * @return true if it is shown, otherwise false
	 */
	public boolean drawPlayer() {
		return show;
	}

	// keeps track of platform orientations
	private static class Pair<F, S> {
		F first;
		S second;

		public Pair(F fv, S sv) {
			first = fv;
			second = sv;
		}
	}

	// enemy population to have adaptive difficulty
	private int getE(boolean islog) {
		if (!islog) {
			if ((int) (maxePop * (Math.log(time/60))) <= maxePop * 4) {
				return (int) (maxePop * (Math.log(time/60)));
			} else {
				return (int) (maxePop * 4);
			}
		} else {
			int a;
			a = 5;
			a *= (1 + 120 * Math.exp(time/60));
			return a;
		}
	}
}
