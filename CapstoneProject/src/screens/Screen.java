package screens;
/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 * The screen is a superclass which cannot instantiated, and represents a double precision screen 
 * using processing java
 *
 */
public abstract class Screen {

	public final int DRAWING_WIDTH;
	public final int DRAWING_HEIGHT;
	/**
	 * creates a screen with a width and height
	 * @param width width of the screen
	 * @param height height of the screen
	 */
	public Screen(int width, int height) {
		this.DRAWING_WIDTH = width;
		this.DRAWING_HEIGHT = height;
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
		return this.DRAWING_WIDTH;
	}

	public int getH() {
		return this.DRAWING_HEIGHT;
	}

}
