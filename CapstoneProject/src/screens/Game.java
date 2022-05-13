package screens;

import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import aviral.shapes.Circle;
import aviral.shapes.Line;
import aviral.shapes.Rectangle;
import core.DrawingSurface;
import sprites.*;

/**
 * 
 * @author Aviral Vaidya, Kevin Ren The game class represents a double precision
 *         game screen screen that can be represented using processing
 */
public class Game extends Screen {

	private static final double g = 0.18; 
	private static final float len = 40;
	private DrawingSurface surface;
	private Rectangle screenRect;
	private Player player;
	// 0 if vert, 1 if right, 2 if left
	private ArrayList<Pair<Platform, Integer>> platforms;
	private ArrayList<Sprite> enemies;
	// this will store enemies AND projectiles
										// easier to check for collisions
	
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

		// randomly generate all platforms and make them seem random
		for (int i = 0; i < 8; i++) {
			final float len = 40;
			float lx = (float) (Math.random() * WIDTH), ly = (float) (Math.random() * HEIGHT);
			while (tooClose(lx, ly, 200) || lx > WIDTH - len) {
				lx = (float) (Math.random() * WIDTH);
				ly = (float) (Math.random() * HEIGHT);
			}
			// 50% chance of having velocity (for testing purposes, 50% is a bit big)
			double a = Math.random();
			int vx = 0, vy = 0;
			// if (a >= 0.5) {
			// 	vx = (int)(Math.random()*5 + 5);
			// 	System.out.println("x velocity " + vx);
			// }

			// 50% chance of being angular
			a = Math.random();
			if (a >= 0.5) {
				Line newLine = new Line(lx, ly, lx + len, ly);
				platforms.add(new Pair<Platform, Integer>(new Platform(newLine, vx, vy), 0));
			} else {
				double b = Math.random(); 
				if (b >= .5) {
					int angle = (int) Math.random() * 180;
					Line newLine = new Line(lx, ly, (float) (lx + len / Math.sqrt(2)), (float) (ly - len/Math.sqrt(2)));
					platforms.add(new Pair<Platform, Integer>(new Platform(newLine, vx, vy), 1));
				} else {
					int angle = (int) Math.random() * 180;
					Line newLine = new Line(lx, ly, (float) (lx + len / Math.sqrt(2)), (float) (ly + len/Math.sqrt(2)));
					platforms.add(new Pair<Platform, Integer> (new Platform(newLine, vx, vy), 2));
				}
			}
			
		}

		enemies = new ArrayList<>();

		Rectangle erect = new Rectangle(200, 200, 30, 30);
		enemies.add(new Enemy(erect, 0, 0, 0, 0));
		// spawn the player
		player = new Player(new Circle(WIDTH / 2, HEIGHT / 2, 16), 0, 0, 0, g, 3);
	}

	/**
	 * Draws the game screen
	 * 
	 * @post The game screen is drawn
	 */
	public void draw() {
		surface.background(36, 150, 177);
		// draw all the sprites
		for (Pair<Platform, Integer>p : platforms) {
			p.first.draw(surface);
		}
		for (Sprite s : enemies) {
			s.draw(surface);
		}
		player.draw(surface);
		// check for key presses and player input
		if (surface.isPressed(KeyEvent.VK_ESCAPE)) {
			surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
		} else if (surface.isPressed(KeyEvent.VK_LEFT) || surface.isPressed(KeyEvent.VK_A)) {
			player.moveBy(-4, 0);
		} else if (surface.isPressed(KeyEvent.VK_RIGHT) || surface.isPressed(KeyEvent.VK_D)) {
			player.moveBy(4, 0);
		} else if (surface.isPressed(KeyEvent.VK_Q)) {
			// System.out.println("Q pressed");
			if (player.getAmmo() > 0) {
				enemies.add(player.shootLeft());
			}
		} else if (surface.isPressed(KeyEvent.VK_E)) {
			// System.out.println("E pressed");
			if (player.getAmmo() > 0) {
				enemies.add(player.shootRight());
			}
		}

		// check if player is out of bounds and have him appear on other side
		if (player.getX() >= WIDTH) {
			player.moveBy(-WIDTH, 0);
		} else if (player.getX() < 0) {
			player.moveBy(WIDTH, 0);
		}

		player.setScore((long)Math.max(player.getY(), player.getScore()));
		
		for (Pair<Platform, Integer> p : platforms) {
			if (player.isTouching(p.first) && p.second == 0) {
				player.setVy(-2);
			}
			if (player.isTouching(p.first) && p.second == 1) {
				player.setVx(-Math.sqrt(2));
				player.setVy(-Math.sqrt(2));
			}
			if (player.isTouching(p.first) && p.second == 2) {
				player.setVx(Math.sqrt(2));
				player.setVy(-Math.sqrt(2));
			}
		}
		if (player.getY() >= HEIGHT) {
			player.moveBy(0, -HEIGHT);
			player.setVy(2);
			
			// TODO re randomize tiles
			/*
			for (int i = 0; i < 8; i++) {
				float lx = (float) (Math.random() * WIDTH), ly = (float) (Math.random() * HEIGHT);
				while (tooClose(lx, ly, 200) || lx > WIDTH - len) {
					lx = (float) (Math.random() * WIDTH);
					ly = (float) (Math.random() * HEIGHT);
				}
				double a = Math.random();
				if (a >= 0.5) {
					Line newLine = new Line(lx, ly, lx + len, ly);
					platforms.add(new Platform(newLine, 0, 0));
				} else {
					double b = Math.random(); 
					if (b >= .5) {
						int angle = (int) Math.random() * 180;
						Line newLine = new Line(lx, ly, (float) (lx + len / Math.sqrt(2)), (float) (ly - len/Math.sqrt(2)));
						platforms.add(new Platform(newLine, 0, 0));
					} else {
						int angle = (int) Math.random() * 180;
						Line newLine = new Line(lx, ly, (float) (lx + len / Math.sqrt(2)), (float) (ly + len/Math.sqrt(2)));
						platforms.add(new Platform(newLine, 0, 0));
					}
				}
				
			}
			*/
			
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
