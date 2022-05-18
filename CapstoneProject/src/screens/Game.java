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
	
	private double scrollBy; // how fast everything scrolls up
	private double border;

	private PImage bg;
	private float x1, y1;
	private PImage gameOverText;

	private DrawingSurface surface;
	private Rectangle screenRect;
	private Player player;
	// 0 if vert, 1 if right, 2 if left
	private ArrayList<Pair<Platform, Integer>> platforms;
	private ArrayList<Platform> horizontal;
	private ArrayList<Sprite> enemies; // and projectiles
	
	private long time; // keep track of game time
	private long fireTime; // time of previous shooting from player
	private long hitTime; // previous time player got hit
	private long gameOver;

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
		
		border = 0;
		generatePlatforms(HEIGHT/2, HEIGHT, 5);
		
		enemies = new ArrayList<>();
		// spawn the enemies
		Rectangle erect = new Rectangle(200, 200, 30, 30);
		enemies.add(new Enemy(erect, 0, 0, 0, 0));
		
		System.out.println("NEW GAME");
		player = new Player(new Circle(WIDTH / 2, 0, 23), 0, 0, 0, g, 3);
		time = 0;
		fireTime = -9999;
		hitTime = -9999;
		gameOver = -1;
		scrollBy = -1.5;
	}
	
	public void setup() {
		player.loadAssets(this.surface);
		bg = surface.loadImage("assets" + fileSep + "stars.jpg");
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
		if (surface.isPressed(KeyEvent.VK_Q) && time/60 > fireTime/60 + 0.5) {
			if (player.getAmmo() > 0) {
				enemies.add(player.shootLeft());
				fireTime = time;
			}
		} 
		if (surface.isPressed(KeyEvent.VK_E) && time/60 > fireTime/60 + 0.5) {
			if (player.getAmmo() > 0) {
				enemies.add(player.shootRight());
				fireTime = time;
			}
		}
		// if (time%10 == 0) System.out.println(hitTime + " " + time + " " + ((time - hitTime)/60 > 0.3));
		if (gameOver > 0) {
			surface.textSize(64f);
			surface.fill(249, 255, 135);
			
			String message = "Score: " + player.getScore();
			surface.text(message, WIDTH/2 - surface.textWidth(message)/2, 550);
			if (surface.isPressed(KeyEvent.VK_SPACE)) {
				surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
			}
			return;
		}
		// for ALL "living" sprites, check if they are dead
		time++;
		if (time%60 == 0) {
			if (enemies.size() > 0) {
				if (enemies.get(0) != null) {
					enemies.add(enemies.get(0).shoot(player.getX(), player.getY()));
				}
			}
		}
		surface.background(20, 20, 64);
		// platforms drawn
		for (Pair<Platform, Integer>p : platforms) {
			if (p.first.getX() >= WIDTH || p.first.getX() <= 0) {
				p.first.setVx(-p.first.getVx());
			}
			p.first.moveBy(0, scrollBy);
			p.first.draw(surface);
		}
		// sus loop（enemies are drawn）
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i) == null) continue;
			if (enemies.get(i) instanceof Projectile) {
				// check if projecitle hit anything or is out of bounds
				double x = enemies.get(i).getX(), y = enemies.get(i).getY();
				if (x < 0 || x > WIDTH || y < 0 || y > HEIGHT) {
					enemies.remove(i--);
					continue;
				}
				for (int j = 0; j < enemies.size(); j++) {
					if (enemies.get(j) == null || enemies.get(j) instanceof Projectile) continue;
					if (enemies.get(i).isTouching(enemies.get(j))) {
						System.out.println("hit enemy");
						enemies.get(j).setLives(enemies.get(j).getLives()-1);
					}
				}
			} 
			if (enemies.get(i) instanceof Enemy) {
				if (enemies.get(i).getLives() <= 0) {
					System.out.println("enemy dead ");
					enemies.remove(i--);
					continue;
				}
			}
			// if player is hit, also grant temporary invincibility
			if (!(time/60 > hitTime/60 + 0.2)) System.out.println(player.getY() + " invincible");
			// TODO: make player flicker when invincible
			if (time/60 > hitTime/60 + 0.2 && player.getShape().isPointInside(enemies.get(i).getX(), enemies.get(i).getY())) {
				System.out.println(enemies.get(i) + " hit player");
				player.setLives(player.getLives()-1);
				hitTime = time;
			}
			enemies.get(i).moveBy(0, scrollBy);
			enemies.get(i).draw(surface);
		}
		// player platform collisions
		for (Pair<Platform, Integer> p : platforms) {
			if (!(player.getY() + player.getR() >= p.first.getY())) {
				continue;
			}
			// this might fix phasing
			if (player.isTouching(p.first) && p.second == 0 && player.getVy() > 0) {
				System.out.println("collide ");
				player.moveBy(player.getVx(), -player.getVy());
				player.setVy(-3);
			}
			if (player.isTouching(p.first) && p.second == 1) {
				System.out.println("collide ");
				player.moveBy(player.getVx(), -player.getVy());
				player.setVx(-5/Math.sqrt(2));
				player.setVy(-5/Math.sqrt(2));
			}
			if (player.isTouching(p.first) && p.second == 2) {
				System.out.println("collide ");
				player.moveBy(player.getVx(), -player.getVy());
				player.setVx(5/Math.sqrt(2));
				player.setVy(-5/Math.sqrt(2));
			}
		}
		if (player.getLives() <= 0) {
			surface.image(gameOverText, WIDTH/2 - 500/2, 100, 500, 100);
			if (gameOver == -1) gameOver = time;
			return;
		}
		if (player.getY() > HEIGHT) {
			// TODO: animation for falling down
			player.setLives(0);
		}
		border += scrollBy;
		// still has to fix scoring past this point
		if (border <= 0) {
			System.out.println("reset");
			generatePlatforms(HEIGHT, 2*HEIGHT, 10);
			border = HEIGHT;
		}
		surface.push();
		surface.stroke(69);
		surface.line(0, (float)border, WIDTH, (float)border);
		surface.pop();
		player.moveBy(0, scrollBy);
		player.draw(surface);

	}

	private void generatePlatforms(float min, float max, int num) {
		for (int i = 0; i < num; i++) {
			float lx = (float) (Math.random() * WIDTH);
			float ly = (float) (Math.random() * (max - min)) + min;
			int tries = 0;
			while (tooClose(lx, ly, 200) || lx > WIDTH - len || ly == HEIGHT || ly == 0) {
				if (tries > 15) break;
				lx = (float) (Math.random() * WIDTH);
				ly = (float) (Math.random() * (max - min)) + min;
				tries++;
			}
			// 50% chance of having velocity (for testing purposes, 50% is a bit big)
			double a = Math.random();
			int vx = 0, vy = 0;
			// if (a >= 0.8) {
			// 	vx = (int)(Math.random()*5 + 2);
			// }

			// 50% chance of being angular
			a = Math.random();
			if (a >= 0.5) {
				Line newLine1 = new Line(lx, ly, lx + len, ly);
				Line newLine2 = new Line(lx + len, ly, lx, ly);
				newLine1.setFillColor(new Color(253, 254, 255));
				newLine2.setFillColor(new Color(253, 254, 255));
				horizontal.add(new Platform(newLine1, vx, vy));
				horizontal.add(new Platform(newLine2, vx, vy));
				platforms.add(new Pair<Platform, Integer>(new Platform(newLine1, vx, vy), 0));
				platforms.add(new Pair<Platform, Integer>(new Platform(newLine2, vx, vy), 0));
			} else {
				double b = Math.random(); 
				if (b >= .5) {
					//int angle = (int) Math.random() * 180;
					Line newLine1 = new Line(lx, ly, (float) (lx + len / Math.sqrt(2)), (float) (ly - len/Math.sqrt(2)));
					Line newLine2 = new Line((float) (lx + len / Math.sqrt(2)), (float) (ly - len/Math.sqrt(2)), lx, ly);
					newLine1.setFillColor(new Color(253, 254, 255));
					newLine2.setFillColor(new Color(253, 254, 255));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine1, vx, vy), 1));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine2, vx, vy), 1));
				} else {
					//int angle = (int) Math.random() * 180;
					Line newLine1 = new Line(lx, ly, (float) (lx + len / Math.sqrt(2)), (float) (ly + len/Math.sqrt(2)));
					Line newLine2 = new Line ((float) (lx + len / Math.sqrt(2)), (float) (ly + len/Math.sqrt(2)), lx, ly);
					newLine1.setFillColor(new Color(253, 254, 255));
					newLine2.setFillColor(new Color(253, 254, 255));
					platforms.add(new Pair<Platform, Integer> (new Platform(newLine1, vx, vy), 2));
					platforms.add(new Pair<Platform, Integer> (new Platform(newLine2, vx, vy), 2));
				}
			}
		}

	}

	private boolean tooClose(double lx, double ly, double radius) {
		// given a point (lx, ly), determine if it is radius away from ALL existing
		// platforms
		for (Pair<Platform, Integer> p : platforms) {
			double xdist = Math.abs(lx - p.first.getX());
			double ydist = Math.abs(ly - p.first.getY());
			if (Math.sqrt(xdist * xdist + ydist * ydist) < radius) {
				return true;
			}
		}
		return false;
	}

	/**
	 * adds and enemy or projectile to the list of things to be added to the screen
	 * @param s sprite to be added
	 */
	public void addEnemyOrProjectile(Sprite s) {
		enemies.add(s);
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
}
