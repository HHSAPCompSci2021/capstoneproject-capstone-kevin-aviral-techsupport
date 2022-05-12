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

		// randomly generate all platforms and make them seem random
		for (int i = 0; i < 5; i++) {
			float len = 40;
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
				int angle = 0; 
				while (!(angle >= 0 && angle <= 60) || ! (angle >= 135 && angle <= 180)) {
					angle = (int) ((int) 180 * Math.random());
				}
				Line newLine = Line.lineFromAngle(lx, ly, angle, len);
				platforms.add(new Platform(newLine, 0, 0));
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
			System.out.println("Q pressed");
			if (player.getAmmo() > 0) {
				enemies.add(player.shootLeft());
			}
		} else if (surface.isPressed(KeyEvent.VK_E)) {
			System.out.println("E pressed");
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
		
		
	}

	private boolean tooClose(double lx, double ly, double radius) {
		// given a point (lx, ly), determine if it is radius away from ALL existing
		// platforms
		for (Platform p : platforms) {
			double xdist = Math.abs(lx - p.getX());
			double ydist = Math.abs(ly - p.getY());
			if (Math.sqrt(xdist * xdist + ydist * ydist) < radius) {
				return true;
			}
		}
		return false;
	}

	public void addPlatform(Platform p) {
		platforms.add(p);
	}

	public void addEnemyOrProjectile(Sprite s) {
		enemies.add(s);
	}

}
