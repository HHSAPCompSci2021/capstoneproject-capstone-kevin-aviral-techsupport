package screens;

import processing.core.PApplet;
import java.awt.Color;

/**
 * 
 * @author Kevin Ren
 *
 */
public abstract class Screen {

	public final static String fileSep = System.getProperty("file.separator");
	public final int WIDTH;
	public final int HEIGHT;

	/**
	 * creates a screen with a width and height
	 * 
	 * @param width  width of the screen
	 * @param height height of the screen
	 */
	public Screen(int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
	}

	/**
	 * sets up the window
	 */
	public void setup() {

	}

	/**
	 * draws the screen
	 */
	public void draw() {

	}

	/**
	 * detects client mouse input via clicks
	 */
	public void mousePressed() {

	}

	/**
	 * detects client mouse input via mouse movement
	 */
	public void mouseMoved() {

	}

	/**
	 * detects client mouse input via dragging
	 */
	public void mouseDragged() {

	}

	/**
	 * detects client mouse input releases
	 */
	public void mouseReleased() {

	}

	/**
	 * getter for width
	 * 
	 * @return width of screen
	 */
	public int getW() {
		return this.WIDTH;
	}

	/**
	 * getter for height of screen
	 * 
	 * @return height of screen
	 */
	public int getH() {
		return this.HEIGHT;
	}

	/**
	 * tostring for debugging
	 */
	public String toString() {
		return WIDTH + " " + HEIGHT;
	}

	/**
	 * sets color of the screen
	 * 
	 * @param c color
	 */
	public void setColor(Color c) {

	}

}
