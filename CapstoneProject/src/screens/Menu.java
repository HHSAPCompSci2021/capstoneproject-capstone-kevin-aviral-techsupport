package screens;

import java.awt.Point;
import java.awt.Rectangle;

import core.DrawingSurface;

public class Menu extends Screen {

	private DrawingSurface surface;
	private Rectangle start;
	private Rectangle quit;

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