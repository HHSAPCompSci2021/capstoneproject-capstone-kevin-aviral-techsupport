package screens;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;
import core.DrawingSurface;
import processing.core.*;
import java.awt.event.KeyEvent;
/**
 * 
 * @author Kevin Ren
 * The Help class represents a help menu for the game that can be drawn in processing
 *
 */
public class Help extends Screen {

    private DrawingSurface surface;

    private PImage inst;
    private float x2, y2;

    private PImage bg;
    private float x1, y1;

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
        buttonColor = new Color(177, 178, 178);
    }
    /**
     * Loads needed images
     * @post images are loaded on the surface
     */
    public void setup() {
        // font name is artick
		inst = surface.loadImage("assets" + fileSep + "instructions.png");
        x1 = -10f;
		y1 = -10f;
        bg = surface.loadImage("assets" + fileSep + "stars.jpg");
        x2 = 0;
		y2 = 32;
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
        
        x1 = (float) (-10 - (surface.mouseX - WIDTH/2) * 0.03);
		y1 = (float) (-10 - (surface.mouseY - HEIGHT/2) * 0.03);
		surface.image(bg, x1, y1, WIDTH + 64f, HEIGHT + 64f);

        surface.filter(PApplet.BLUR, 2);
        
		x2 = (float) (-(surface.mouseX - WIDTH/2) * 0.05);
		y2 = (float) (-(surface.mouseY - HEIGHT/2) * 0.05);
		surface.image(inst, x2, y2, WIDTH, HEIGHT);
        
        surface.noStroke();
        boolean hover = false;
		Point p = surface.actualToAssumed(new Point(surface.mouseX, surface.mouseY));
		if (back.contains(p)) {
            surface.fill(buttonColor.getRGB(), 0);
            hover = true;
        } else surface.fill(buttonColor.getRGB(), 150);
        
		surface.rect(back.x, back.y, back.width, back.height, 10, 10, 10, 10);
        surface.cursor(hover ? PApplet.HAND : PApplet.ARROW);
        
		float buttonT = surface.textWidth("Return to Menu");
        surface.fill(201, 200, 200);
        surface.textSize(20);
        surface.text("Return to Menu", back.x + back.width/2 - buttonT/2, back.y + back.height/2);
    }
    @Override
    /**
     * Detects mouse input from console
     */
    public void mousePressed() {
        Point p = surface.actualToAssumed(new Point(surface.mouseX, surface.mouseY));
        if (back.contains(p)) surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
    }
    
}
