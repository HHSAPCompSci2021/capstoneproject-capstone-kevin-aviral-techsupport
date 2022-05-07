package screens;

import java.awt.Point;
import java.awt.Rectangle;

import core.DrawingSurface;
/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 * The menu class represents a double precision game menu that can be displayed with processing
 */
public class Menu extends Screen {

	private DrawingSurface surface;
	private Rectangle start;
	private Rectangle quit;
	/**
	 * Creates a menu object
	 * @param surface surface the menu drawn on
	 */
	public Menu(DrawingSurface surface) {
        super(600, 750);
		this.surface = surface;
		start = new Rectangle(600/2-100, 600/2-50, 500, 550);
		quit = new Rectangle(600/2-100, 600/2-50, 600, 650);
	}
	
	public void draw() {
		// set background image
		// draw the rect for each button
		// label the buttons
	}

	public void mousePressed() {
		// switch screen if start button pressed
	}

}