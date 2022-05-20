package screens;

import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import aviral.shapes.*;
import core.*;
import processing.core.*;
import sprites.*;

/**
 * 
 * @author Aviral Vaidya, Kevin Ren The game class represents a double precision
 *         game screen screen that can be represented using processing
 */
public class Game extends Screen {

	private static final double g = 0.1;
	private static final float len = 40;
	private static final int maxePop = 2;

	private double scrollBy; // how fast everything scrolls up
	private double border;

	private PImage background;
	private float x1, y1;
	private PImage gameOverText;

	private DrawingSurface surface;
	private Rectangle screenRect;
	private Player player;
	// 0 if vert, 1 if right, 2 if left
	private ArrayList<Pair<Platform, Integer>> platforms;
	private ArrayList<Platform> horizontal;
	private ArrayList<Sprite> enemies; // 30 by 30 (also store projectiles)
	private ArrayList<Powerup> powerups; // 25 by 25

	private long time; // keep track of game time
	private long fireTime; // time of previous shooting from player
	private long hitTime; // previous time player got hit
	private long lastJump; // time of last double jump
	private long deathTime;
	private boolean show = true;

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

		border = 0;
		generatePlatforms(HEIGHT / 2, HEIGHT, 5);
		spawnEnemies(HEIGHT / 2, HEIGHT, 3);

		System.out.println("NEW GAME");
		player = new Player(new Circle(WIDTH / 2, 48, 24), 0, 0, 0, g, 3);
		time = 0;
		fireTime = -9999;
		hitTime = -9999;
		lastJump = -9999;
		deathTime = -1;
		scrollBy = -2d;
	}

	public void setup() {
		player.loadAssets(this.surface);
		background = surface.loadImage("assets" + fileSep + "moon.jpg");
		x1 = 0;
		y1 = 0;
		gameOverText = surface.loadImage("assets" + fileSep + "gameover.png");
	}

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
		}
		if (surface.isPressed(KeyEvent.VK_RIGHT) || surface.isPressed(KeyEvent.VK_D)) {
			player.moveBy(4, 0);
		}
		if (surface.isPressed(KeyEvent.VK_Q) && time / 60 > fireTime / 60 + player.getFireRate()) {
			if (player.getAmmo() > 0) {
				enemies.add(player.shootLeft());
				enemies.get(enemies.size() - 1).loadAssets(surface);
				fireTime = time;
			}
		}
		if (surface.isPressed(KeyEvent.VK_E) && time / 60 > fireTime / 60 + player.getFireRate()) {
			if (player.getAmmo() > 0) {
				enemies.add(player.shootRight());
				enemies.get(enemies.size() - 1).loadAssets(surface);
				fireTime = time;
			}
		}
		if (surface.isPressed(KeyEvent.VK_W) || surface.isPressed(KeyEvent.VK_UP)) {
			if (time / 60 - lastJump / 60 >= player.getFreq()) {
				player.jump();
				lastJump = time;
			}
		}

		time++;
		if (time % 60 == 0) {
			System.out.println(enemies.size() + " enemies");
			System.out.println(platforms.size() + " platforms\n");
		}
		surface.background(20, 20, 64);
		// platforms drawn
		for (int i = 0; i < platforms.size(); i++) {
			if (platforms.get(i) == null || platforms.get(i).first.getY() < 0) {
				platforms.remove(i--);
				continue;
			}
			if (platforms.get(i).first.getX() >= WIDTH || platforms.get(i).first.getX() <= 0) {
				platforms.get(i).first.setVx(-platforms.get(i).first.getVx());
			}
			platforms.get(i).first.moveBy(0, scrollBy);
			platforms.get(i).first.draw(surface);
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
				// check if projcetile hit an enemy
				for (int j = 0; j < enemies.size(); j++) {
					if (enemies.get(j) == null || enemies.get(j) instanceof Projectile)
						continue;
					if (enemies.get(j).getShape().isPointInside(x, y)) {
						System.out.println("bullet hit enemy");
						// player.setScore(player.getScore() + 200);
						enemies.get(j).setLives(enemies.get(j).getLives() - 1);
					}
				}
				// check if projectile hit a powerup
				for (int j = 0; j < powerups.size(); j++) {
					if (powerups.get(j) == null)
						continue;
					if (powerups.get(j).getShape().isPointInside(x, y)) {
						System.out.println("bullet hit powerup");
						applyPowerup(powerups.get(j));
						powerups.remove(j--);
						continue;
					}
				}
			}
			if (enemies.get(i) instanceof Enemy) {
				if (y <= HEIGHT)
					enemies.get(i).setLastTime(time);
				// check for death
				if (enemies.get(i).getLives() <= 0) {
					System.out.println("enemy dead ");
					enemies.remove(i--);
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
			if (time / 60 > hitTime / 60 + 0.3
					&& player.getShape().isPointInside(enemies.get(i).getX(), enemies.get(i).getY())) {
				System.out.println(enemies.get(i) + " hit player");
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
					for (int j = horizontal.size() - 1; j >= 0; j--) {
						horizontal.remove(j);
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
		// player platform collisions
		for (int i = 0; i < platforms.size(); i++) {
			// cant collide from below
			if (player.getR() + player.getY() < platforms.get(i).first.getY())
				continue;
			if (player.isTouching(platforms.get(i).first) && platforms.get(i).second == 0 && player.getVy() > 0) {
				player.moveBy(player.getVx(), -player.getVy());
				// vertical so no need to multiply by - 1 because there is only one direction
				player.setVy(-3.4);
			}
			if (player.isTouching(platforms.get(i).first) && platforms.get(i).second == 2) {
				player.moveBy(player.getVx(), -player.getVy());
				player.setVx(3.4 / Math.sqrt(2));
				player.setVy(-3.4 / Math.sqrt(2));
			}
			if (player.isTouching(platforms.get(i).first) && platforms.get(i).second == 1) {
				player.moveBy(player.getVx(), -player.getVy());
				player.setVx(-3.4 / Math.sqrt(2));
				player.setVy(-3.4 / Math.sqrt(2));
			}
		}

		if (player.getLives() <= 0) {
			if (time / 60 - deathTime / 60 < 5)
				scrollBy = -75;
			else
				scrollBy = 0;
			surface.push();
			surface.image(gameOverText, WIDTH / 2 - 500 / 2, 100, 500, 100);
			surface.textSize(64f);
			surface.fill(249, 255, 135);
			String message = "Score: " + player.getScore();
			surface.text(message, WIDTH / 2 - surface.textWidth(message) / 2, 550);
			surface.pop();
			if (surface.isPressed(KeyEvent.VK_SPACE)) {
				surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
			}
			return;
		}
		if (player.getY() > HEIGHT) {
			deathTime = time;
			player.setLives(0);
		} else if (time / 60 > hitTime / 60 + 0.3 && player.getY() <= 0) {
			player.moveBy(0, player.getY() + 25);
			player.setVy(1);
			player.setLives(player.getLives() - 1);
			hitTime = time;
		}
		border += scrollBy;
		// still has to fix scoring past this point
		// respawn the entities
		if (border <= 0) {
			generatePlatforms(HEIGHT, 2 * HEIGHT, 10);
			spawnEnemies(HEIGHT, 2 * HEIGHT, 4);
			spawnPowerups(HEIGHT, 2 * HEIGHT, (int) (Math.random() * 4));
			border = HEIGHT;
		}
		surface.push();
		// signify that ceiling damages the player
		surface.stroke(251, 175, 59);
		surface.strokeWeight(4);
		surface.line(0, 0, WIDTH, 0);

		surface.pop();

		player.moveBy(0, scrollBy);
		scrollBy = -Math.max(Math.abs(-2d), Math.abs(player.getVy()) / 3);
		if (time / 60 - hitTime / 60 <= 0.3) {
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

	// change num to getE() when funciton is working
	private void spawnPowerups(float min, float max, int num) {
		for (int i = 0; i < num; i++) {
			float sx = (float) (Math.random() * WIDTH);
			float sy = (float) (Math.random() * (max - min)) + min;
			int tries = 0;
			while (tooClose(sx, sy, 300)) {
				if (tries > 10)
					break;
				sx = (float) (Math.random() * WIDTH);
				sy = (float) (Math.random() * (max - min)) + min;
				tries++;
			}
			Rectangle rect = new Rectangle(sx, sy, 25, 25);
			Powerup pu = new Powerup(rect, 0, 0, (int) (Math.random() * 3) + 1);
			pu.loadAssets(surface);
			powerups.add(pu);
		}
	}

	// change num to getE() when funciton is working
	private void spawnEnemies(float min, float max, int num) {
		for (int i = 0; i < getE(); i++) {
			float sx = (float) (Math.random() * WIDTH);
			float sy = (float) (Math.random() * (max - min)) + min;
			int tries = 0;
			while (tooClose(sx, sy, 400)) {
				if (tries > 15)
					break;
				sx = (float) (Math.random() * WIDTH);
				sy = (float) (Math.random() * (max - min)) + min;
				tries++;
			}
			// spawn the enemies
			Rectangle rect = new Rectangle(sx, sy, 30, 30);
			enemies.add(new Enemy(rect, 0, 0, 0, 0));
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
			// 10% chance of having velocity (for testing purposes, 50% is a bit big)
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
					horizontal.add(new Platform(newLine1, .5, vy));
					horizontal.add(new Platform(newLine2, .5, vy));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine1, .5, vy), 0));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine2, .5, vy), 0));
				} else {
					horizontal.add(new Platform(newLine1, vx, vy));
					horizontal.add(new Platform(newLine2, vx, vy));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine1, vx, vy), 0));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine2, vx, vy), 0));
				}

			} else {
				double b = Math.random();
				if (b >= .5) {
					// int angle = (int) Math.random() * 180;
					Line newLine1 = new Line(lx, ly, (float) (lx + len / Math.sqrt(2)),
							(float) (ly - len / Math.sqrt(2)));
					Line newLine2 = new Line((float) (lx + len / Math.sqrt(2)), (float) (ly - len / Math.sqrt(2)), lx,
							ly);
					newLine1.setFillColor(new Color(253, 254, 255));
					newLine2.setFillColor(new Color(253, 254, 255));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine1, vx, vy), 1));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine2, vx, vy), 1));
				} else {
					// int angle = (int) Math.random() * 180;
					Line newLine1 = new Line(lx, ly, (float) (lx + len / Math.sqrt(2)),
							(float) (ly + len / Math.sqrt(2)));
					Line newLine2 = new Line((float) (lx + len / Math.sqrt(2)), (float) (ly + len / Math.sqrt(2)), lx,
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
					&& (platforms.get(i).first.getX() > WIDTH - len || platforms.get(i).first.getX() < len)) {
				platforms.remove(i);
			}
		}
		for (int i = horizontal.size() - 1; i >= 0; i--) {
			if (horizontal.get(i).getX() > WIDTH - len || horizontal.get(i).getX() < len) {
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

	// use logistic equation proportional to time to have adaptive difficulty enemy
	// generation
	private int getE() {
		return (int) (maxePop * (Math.log(time / 60)));
	}
}
