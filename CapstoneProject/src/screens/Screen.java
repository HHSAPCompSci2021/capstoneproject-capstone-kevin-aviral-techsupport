package screens;
/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 * The screen is a superclass which cannot instantiated, and represents a double precision screen 
 * using processing java
 *
 */
public abstract class Screen {

	public final int WIDTH;
	public final int HEIGHT;
	/**
	 * creates a screen with a width and height
	 * @param width width of the screen
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

	public int getW() {
		return this.WIDTH;
	}

	public int getH() {
		return this.HEIGHT;
	}

}
