package screens;
/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 * The screen is a superclass which cannot instantiated, and represents a double precision screen 
 * using processing java
 *
 */
public abstract class Screen {

	private final int DRAWING_WIDTH, DRAWING_HEIGHT;
	
	public Screen(int width, int height) {
		this.DRAWING_WIDTH = width;
		this.DRAWING_HEIGHT = height;
	}
	
	public void setup() {
		
	}
	
	public void draw() {
		
	}
	
	public void mousePressed() {
		
	}
	
	public void mouseMoved() {
		
	}
	
	public void mouseDragged() {
		
	}
	
	public void mouseReleased() {
		
	}
	
}
