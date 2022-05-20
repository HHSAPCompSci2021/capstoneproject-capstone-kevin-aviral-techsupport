package screens;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;
import core.DrawingSurface;
import processing.core.*;
import java.awt.event.KeyEvent;
/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 * The Help class represents a help menu for the game that can be drawn in processing
 *
 */
public class Help extends Screen {

    private DrawingSurface surface;
    private PImage controls;
    private float x1, y1;
    private float w1, h1;
    private PImage bg;
    private float x2, y2;
    private Rectangle back;
    private Color buttonColor;
    /**
     * Creates a new help menu
     * @param surface surface on which items are drawn and loaded on
     */
    public Help(DrawingSurface surface) {
        super(600, 800);
        this.surface = surface;
        int rw = 300, rh = 50;
        back = new Rectangle(WIDTH/2 - rw/2, HEIGHT - rh/2 - 70, rw, rh);
        buttonColor = new Color(55, 198, 166);
    }
    /**
     * Loads needed images
     * @post images are loaded on the surface
     */
    public void setup() {
		controls = surface.loadImage("assets" + fileSep + "instructions.png");
        w1 = 500;
        h1 = 300;
		x1 = WIDTH/2 - w1/2;
		y1 = 100;
		// sets background menu
        bg = surface.loadImage("assets" + fileSep + "northern_lights.jpg");
        x2 = -10;
        y2 = -10;
    }
    /**
     * Draws help menu
     * @post help menu is drawn on surface
     */
    @Override
    public void draw() {
        if (surface.isPressed(KeyEvent.VK_ESCAPE)) {
			surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
		}
        surface.image(bg, x2, y2, WIDTH + 50, HEIGHT + 50);
        surface.image(controls, x1, y1, w1, h1);
        surface.fill(buttonColor.getRGB());
		surface.rect(back.x, back.y, back.width, back.height, 10, 10, 10, 10);
		float buttonT = surface.textWidth("Return to Game Menu");
        surface.fill(20);
        surface.textSize(20);
        surface.text("Back", back.x + back.width/2 - buttonT/2, back.y + back.height/2);
    }
    @Override
    /**
     * Detects mouse input from console
     */
    public void mousePressed() {
        Point p = surface.actualToAssumed(new Point(surface.mouseX, surface.mouseY));
        if (back.contains(p)) 
        	surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
    }
    
}
