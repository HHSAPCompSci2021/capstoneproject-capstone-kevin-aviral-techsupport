package screens;

import java.awt.Point;
import java.awt.Rectangle;

import core.DrawingSurface;
import processing.core.*;

/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 *         The menu class represents a double precision game menu that can be
 *         displayed with processing
 */
public class Menu extends Screen {

	private PFont font;
	private DrawingSurface surface;
	private Rectangle start;
	private Rectangle quit;

	/**
	 * Creates a menu object
	 * 
	 * @param surface surface the menu drawn on
	 */
	public Menu(DrawingSurface surface) {
		super(600, 800);
		this.surface = surface;
		int rw = 300, rh = 80;
		start = new Rectangle(WIDTH / 2 - rw / 2, HEIGHT / 2 - rh / 2 + 150, rw, rh);
		quit = new Rectangle(WIDTH / 2 - rw / 2, HEIGHT / 2 + rh + 150, rw, rh);
	}

	public void draw() {
		// surface.background(255, 255, 255);
		// draw the buttons
		surface.fill(236, 181, 176);
		surface.rect(start.x, start.y, start.width, start.height, 10, 10, 10, 10);
		surface.rect(quit.x, quit.y, quit.width, quit.height, 10, 10, 10, 10);
		// draw the text
		surface.fill(4, 20, 43);
		surface.textSize(16);
		float textWidth1 = surface.textWidth("START");
		float textWidth2 = surface.textWidth("QUIT");
		surface.text("START", start.x + start.width / 2 - textWidth1 / 2, start.y + start.height / 2);
		surface.text("QUIT", quit.x + quit.width / 2 - textWidth2 / 2, quit.y + quit.height / 2);
	}

	public void mousePressed() {
		// check if button was clicked
		Point p = surface.actualToAssumed(new Point(surface.mouseX, surface.mouseY));
		if (start.contains(p)) {
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
		} else if (quit.contains(p)) {
			System.exit(0);
		}
	}

}