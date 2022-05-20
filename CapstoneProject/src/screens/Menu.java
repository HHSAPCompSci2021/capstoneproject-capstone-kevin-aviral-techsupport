package screens;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;
import core.DrawingSurface;
import processing.core.*;

/**
 * 
 * @author Kevin Ren
 */
public class Menu extends Screen {

	private PImage bg;
	private float x1, y1;
	private PImage fg;
	private float x2, y2;

	private PFont font;
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
		buttonColor = new Color(172, 172, 172);
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
		font = surface.createFont("assets" + fileSep + "mangabey.otf", 22);
	}

	/**
	 * draws the menu
	 * 
	 * @post menu is drawn
	 */
	public void draw() {
		surface.cursor();

		x1 = (float) (-10 - (surface.mouseX - WIDTH/2) * 0.02);
		y1 = (float) (-10 - (surface.mouseY - HEIGHT/2) * 0.02);
		surface.image(bg, x1, y1, WIDTH + 64f, HEIGHT + 64f);

		x2 = (float) (-(surface.mouseX - WIDTH/2) * 0.07);
		y2 = (float) (-(surface.mouseY - HEIGHT/2) * 0.07);
		surface.image(fg, x2, y2, WIDTH, HEIGHT);
		
		// draw the buttons
		surface.noStroke();
		Point p = surface.actualToAssumed(new Point(surface.mouseX, surface.mouseY));
		if (start.contains(p)) surface.fill(buttonColor.getRGB(), 255);
		else surface.fill(buttonColor.getRGB(), 180);
		surface.rect(start.x, start.y, start.width, start.height, 10, 10, 10, 10);
		
		if (quit.contains(p)) surface.fill(buttonColor.getRGB(), 255);
		else surface.fill(buttonColor.getRGB(), 180);
		surface.rect(quit.x, quit.y, quit.width, quit.height, 10, 10, 10, 10);
		
		if (help.contains(p)) surface.fill(buttonColor.getRGB(), 255);
		else surface.fill(buttonColor.getRGB(), 180);
		surface.rect(help.x, help.y, help.width, help.height, 10, 10, 10, 10);
		// draw the text
		surface.fill(4, 20, 43);
		surface.textFont(font);
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
			surface.switchScreen(ScreenSwitcher.HELP_SCREEN);
		} else if (quit.contains(p)) {
			System.exit(0);
		}
	}

}