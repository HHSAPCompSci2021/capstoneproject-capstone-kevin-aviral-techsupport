package screens;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;
import core.DrawingSurface;
import processing.core.*;

/**
 * 
 * @author Aviral Vaidya, Kevin Ren The menu class represents a double precision
 *         game menu that can be displayed with processing
 */
public class Menu extends Screen {

	private PImage bg;
	private float x1, y1;
	private PImage fg;
	private float x2, y2;

	private Color buttonColor;
	private DrawingSurface surface;
	private Rectangle start;
	private Rectangle help;
	private Rectangle quit;

	/**
	 * Creates a menu object
	 * 
	 * @param surface surface the menu drawn on
	 */
	public Menu(DrawingSurface surface) {
		super(600, 800);
		this.surface = surface;
		int rw = 300, rh = 60;
		start = new Rectangle(WIDTH/2 - rw/2, HEIGHT/2 - rh/2 + 150, rw, rh);
		help = new Rectangle(WIDTH/2 - rw/2, HEIGHT/2 + rh + 150, rw, rh);
		quit = new Rectangle(WIDTH/2 - rw/2, HEIGHT/2 + 5*rh/2 + 150, rw, rh);
		buttonColor = new Color(236, 181, 176);
	}

	/**
	 * sets up needed images on the screen
	 * 
	 * @post images are loaded on the screen
	 */
	public void setup() {
		bg = surface.loadImage("assets" + fileSep + "mountain.jpg");
		x1 = -10f;
		y1 = -10f;
		// font name is calamandria
		fg = surface.loadImage("assets" + fileSep + "downfall_text.png");
		x2 = -0;
		y2 = 32;
	}

	/**
	 * draws the menu
	 * 
	 * @post menu is drawn
	 */
	public void draw() {
		x1 = (float) (-10 - (surface.mouseX - WIDTH/2) * 0.02);
		y1 = (float) (-10 - (surface.mouseY - HEIGHT/2) * 0.02);
		surface.image(bg, x1, y1, WIDTH + 64f, HEIGHT + 64f);

		x2 = (float) (-(surface.mouseX - WIDTH/2) * 0.07);
		y2 = (float) (-(surface.mouseY - HEIGHT/2) * 0.07);
		surface.image(fg, x2, y2, WIDTH, HEIGHT);
		
		// draw the buttons
		surface.fill(buttonColor.getRGB());
		surface.rect(start.x, start.y, start.width, start.height, 10, 10, 10, 10);
		surface.rect(quit.x, quit.y, quit.width, quit.height, 10, 10, 10, 10);
		surface.rect(help.x, help.y, help.width, help.height, 10, 10, 10, 10);
		// draw the text
		surface.fill(4, 20, 43);
		surface.textSize(16);
		float textWidth1 = surface.textWidth("Start");
		float textWidth2 = surface.textWidth("Help");
		float textWidth3 = surface.textWidth("Quit");
		surface.text("Start", start.x + start.width/2 - textWidth1/2, start.y + start.height/2);
		surface.text("Help", help.x + help.width/2 - textWidth2/2, help.y + help.height/2);
		surface.text("Quit", quit.x + quit.width/2 - textWidth3/2, quit.y + quit.height/2);
	}

	/**
	 * sets color of the screen
	 * 
	 * @param c color
	 */
	public void setColor(Color c) {
		buttonColor = c;
	}

	/**
	 * client mouse input detection
	 */
	public void mousePressed() {
		// check if button was clicked
		Point p = surface.actualToAssumed(new Point(surface.mouseX, surface.mouseY));
		if (start.contains(p)) {
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
		} else if (help.contains(p)) {
			System.out.println("help clicked");
			surface.switchScreen(ScreenSwitcher.HELP_SCREEN);
		} else if (quit.contains(p)) {
			System.exit(0);
		}
	}

	private boolean between(int x, int a, int b) {
		return a < x && x < b;
	}

}