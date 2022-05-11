package screens;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.*;
import aviral.shapes.Circle;
import aviral.shapes.Line;
import core.DrawingSurface;
import sprites.*;

/**
 * 
 * @author Aviral Vaidya, Kevin Ren The game class represents a double precision
 *         game screen screen that can be represented using processing
 */
public class Game extends Screen {
	private static final double g = 0.18; 
	private DrawingSurface surface;
	private Rectangle screenRect;
	private Player player;
	private ArrayList<Platform> platforms;
	private ArrayList<Sprite> enemies; // this will store enemies AND projectiles
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

		for (int i = 0; i < 5; i++) {
			float len = 40;
			float lx = (float) (Math.random() * WIDTH), ly = (float) (Math.random() * HEIGHT);
			// dont spawn new lx and ly unless they are at least some distance away from
			// other platforms
			// using normal loop to prevent concurrent mod errors
			for (int j = 0; j < platforms.size(); j++) {
				double xdist = Math.abs(lx - platforms.get(j).getX()), ydist = Math.abs(ly - platforms.get(j).getY());
				double dist = Math.sqrt(xdist * xdist + ydist * ydist);
				while (dist < 150 || lx + len > WIDTH) {
					lx = (float) (Math.random() * WIDTH);
					ly = (float) (Math.random() * HEIGHT);
					xdist = Math.abs(lx - platforms.get(j).getX());
					ydist = Math.abs(ly - platforms.get(j).getY());
					dist = Math.sqrt(xdist * xdist + ydist * ydist);
				}
			}
			Line newLine = new Line(lx, ly, lx + len, ly);
			platforms.add(new Platform(newLine, 0, 0));
		}

		enemies = new ArrayList<>();
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
		for (Platform p : platforms) {
			p.draw(surface);
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
			player.shootLeft();
		} else if (surface.isPressed(KeyEvent.VK_E)) {
			player.shootRight();
		} else if (surface.isPressed(KeyEvent.VK_SPACE)) {
		// shoot
		}
		for (int i = 0; i < platforms.size(); i++) {
			if (player.isTouching(platforms.get(i))) {
				player.setVy(.8 * -player.getVy());
			}
		}
		
		if (player.getY() > HEIGHT) {
			player.moveBy(0, -HEIGHT);
			// keep this for now but get rid of when collisions are done. 
			player.setVy(1);
			platforms.clear();
			// using normal loop to prevent concurrent mod errors
			for (int i = 0; i < 5; i++) {
				float len = 40;
				float lx = (float) (Math.random() * WIDTH), ly = (float) (Math.random() * HEIGHT);
				// dont spawn new lx and ly unless they are at least some distance away from
				// other platforms
				for (int j = 0; j < platforms.size(); j++) {
					double xdist = Math.abs(lx - platforms.get(j).getX()),
							ydist = Math.abs(ly - platforms.get(j).getY());
					double dist = Math.sqrt(xdist * xdist + ydist * ydist);
					while (dist < 150 || lx + len > WIDTH) {
						lx = (float) (Math.random() * WIDTH);
						ly = (float) (Math.random() * HEIGHT);
						xdist = Math.abs(lx - platforms.get(j).getX());
						ydist = Math.abs(ly - platforms.get(j).getY());
						dist = Math.sqrt(xdist * xdist + ydist * ydist);
					}
				}
				Line newLine = new Line(lx, ly, lx + len, ly);
				platforms.add(new Platform(newLine, 0, 0));
			}
		}
		

		// check if player is out of bounds and have him appear on other side
		if (player.getX() >= WIDTH) {
			player.moveBy(-WIDTH, 0);
		} else if (player.getX() < 0) {
			player.moveBy(WIDTH, 0);
		}
		
	}

	public void addPlatform(Platform p) {
		platforms.add(p);
	}

	public void addEnemyOrProjectile(Sprite s) {
		enemies.add(s);
	}

}
