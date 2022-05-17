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
	private DrawingSurface surface;
	private Rectangle screenRect;
	private Player player;
	// 0 if vert, 1 if right, 2 if left
	private ArrayList<Pair<Platform, Integer>> platforms;
	private ArrayList<Platform> horizontal;
	private ArrayList<Sprite> enemies; // and projectiles

	private long time;
	private long prevTime;
	private Platform test;
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
		
		Line lin = new Line(100, 500, 100+len, 500);
		lin.setStrokeColor(new Color(210, 50, 69));;
		test = new Platform(lin, 0, 0);
		generatePlatforms();
		enemies = new ArrayList<>();

		// spawn the enemies
		Rectangle erect = new Rectangle(200, 200, 30, 30);
		enemies.add(new Enemy(erect, 0, 0, 0, 0));
		
		player = new Player(new Circle(WIDTH / 2, HEIGHT / 2, 16), 0, 0, 0, g, 3);
		time = 0;
		prevTime = 0;
	}
	
	public void setup() {
		player.loadAssets(this.surface);
	}

	/**
	 * Draws the game screen
	 * 
	 * @post The game screen is drawn
	 */
	public void draw() {
		// for ALL "living" sprites, check if they are dead
		time++;
		if (time%60 == 0) {
			System.out.println(time/60+ " " + prevTime/60);
			if (enemies.size() != 0) {
				enemies.add(enemies.get(0).shoot(player.getX(), player.getY()));
			}
		}
		surface.background(36, 150, 177);
		// draw all the sprites
		for (Pair<Platform, Integer>p : platforms) {
			if (p.first.getX() >= WIDTH || p.first.getX() <= 0) {
				p.first.setVx(-p.first.getVx());
			}
			p.first.draw(surface);
		}
		// sus loop
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i) == null) continue;
			if (enemies.get(i) instanceof Projectile) {
				// check if projecitle hit anything
				for (int j = 0; j < enemies.size(); j++) {
					if (enemies.get(j) == null || enemies.get(j) instanceof Projectile) continue;
					if (enemies.get(i).isTouching(enemies.get(j))) {
						enemies.get(j).setLives(enemies.get(j).getLives()-1);
					}
				}
				if (enemies.get(i).isTouching(player)) {
					System.out.println("player hit");
					player.setLives(player.getLives()-1);
				}
				double x = enemies.get(i).getX(), y = enemies.get(i).getY();
				if (x < 0 || x > WIDTH || y < 0 || y > HEIGHT) {
					enemies.remove(i--);
					continue;
				}
			} else {
				// check if enemy is dead
				System.out.println("enemy with " + enemies.get(i).getLives());
				if (enemies.get(i).getLives() <= 0) {
					enemies.remove(i--);
					continue;
				}
			}
			enemies.get(i).draw(surface);
		}
		player.draw(surface);
		if (player.getLives() <= 0) {
			// death
			System.out.println("YOU DIED");
		}
		test.draw(surface);
		// check for key presses and player input
		if (surface.isPressed(KeyEvent.VK_ESCAPE)) {
			surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
		} 
		if (surface.isPressed(KeyEvent.VK_LEFT) || surface.isPressed(KeyEvent.VK_A)) {
			player.moveBy(-4, 0);
		} 
		if (surface.isPressed(KeyEvent.VK_RIGHT) || surface.isPressed(KeyEvent.VK_D)) {
			player.moveBy(4, 0);
		} 
		if (surface.isPressed(KeyEvent.VK_Q) && time/60 > prevTime/60 + 1) {
			if (player.getAmmo() > 0) {
				enemies.add(player.shootLeft());
				prevTime = time;
			}
		} 
		if (surface.isPressed(KeyEvent.VK_E) && time/60 > prevTime/60 + 1) {
			if (player.getAmmo() > 0) {
				enemies.add(player.shootRight());
				prevTime = time;
			}
		}

		if (player.isTouching(test)) {
			System.out.println("test success");
		}

		for (Pair<Platform, Integer> p : platforms) {
			if (player.isTouching(p.first) && p.second == 0) {
				System.out.println("collide ");
				player.setVy(-2);
			}
			if (player.isTouching(p.first) && p.second == 1) {
				System.out.println("collide ");
				player.moveBy(player.getVx(), -player.getVy());
				player.setVx(-Math.sqrt(2));
				player.setVy(-Math.sqrt(2));
			}
			if (player.isTouching(p.first) && p.second == 2) {
				System.out.println("collide ");
				player.moveBy(player.getVx(), -player.getVy());
				player.setVx(Math.sqrt(2));
				player.setVy(-Math.sqrt(2));
			}
		}
		if (player.getY() >= HEIGHT) {
			player.moveBy(0, -HEIGHT);
			player.setVy(2);
			
			// TODO re randomize tiles
			/*
			generatePlatforms();
			*/
			
			
		}
		
	}

	private void generatePlatforms() {
		// randomly generate all platforms on this screen and the screen below and make them seem random
		for (int i = 0; i < 20; i++) {
			final float len = 40;
			float lx = (float) (Math.random() * WIDTH), ly = (float) (Math.random() * 2 * HEIGHT);
			while (tooClose(lx, ly, 200) || lx > WIDTH - len) {
				lx = (float) (Math.random() * WIDTH);
				ly = (float) (Math.random() * 2 *HEIGHT);
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
				horizontal.add(new Platform(newLine1, vx, vy));
				horizontal.add(new Platform(newLine2, vx, vy));
				platforms.add(new Pair<Platform, Integer>(new Platform(newLine1, vx, vy), 0));
				platforms.add(new Pair<Platform, Integer>(new Platform(newLine1, vx, vy), 0));
			} else {
				double b = Math.random(); 
				if (b >= .5) {
					//int angle = (int) Math.random() * 180;
					Line newLine1 = new Line(lx, ly, (float) (lx + len / Math.sqrt(2)), (float) (ly - len/Math.sqrt(2)));
					Line newLine2 = new Line((float) (lx + len / Math.sqrt(2)), (float) (ly - len/Math.sqrt(2)), lx, ly);
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine1, vx, vy), 1));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine2, vx, vy), 1));
				} else {
					//int angle = (int) Math.random() * 180;
					Line newLine1 = new Line(lx, ly, (float) (lx + len / Math.sqrt(2)), (float) (ly + len/Math.sqrt(2)));
					Line newLine2 = new Line ((float) (lx + len / Math.sqrt(2)), (float) (ly + len/Math.sqrt(2)), lx, ly);
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
	
	private static class Pair<F, S> {
		F first;
		S second;
		
		public Pair(F fv, S sv) {
			first = fv;
			second = sv;
		}
	}
}
