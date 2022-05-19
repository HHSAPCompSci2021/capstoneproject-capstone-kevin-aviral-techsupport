package screens;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;
import core.DrawingSurface;
import processing.core.*;
import java.awt.event.KeyEvent;

public class Help extends Screen {

    private DrawingSurface surface;
    
    private PImage controls;
    private float x1, y1;
    private float w1, h1;
    
    private PImage bg;
    private float x2, y2;

    private Rectangle back;
    private Color buttonColor;

    public Help(DrawingSurface surface) {
        super(600, 800);
        this.surface = surface;
        int rw = 300, rh = 50;
        back = new Rectangle(WIDTH/2 - rw/2, HEIGHT - rh/2 - 70, rw, rh);
        buttonColor = new Color(55, 198, 166);
    }

    public void setup() {
		controls = surface.loadImage("assets" + fileSep + "instructions.png");
        w1 = 500;
        h1 = 300;
		x1 = WIDTH/2 - w1/2;
		y1 = 100;
        bg = surface.loadImage("assets" + fileSep + "northern_lights.jpg");
        x2 = -10;
        y2 = -10;
    }

    public void draw() {
        if (surface.isPressed(KeyEvent.VK_ESCAPE)) {
			surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
		}
        surface.image(bg, x2, y2, WIDTH + 50, HEIGHT + 50);
        surface.image(controls, x1, y1, w1, h1);

        surface.fill(buttonColor.getRGB());
		surface.rect(back.x, back.y, back.width, back.height, 10, 10, 10, 10);
		float textWidth1 = surface.textWidth("Back");
        surface.fill(20);
        surface.textSize(20);
        surface.text("Back", back.x + back.width/2 - textWidth1/2, back.y + back.height/2);
    }

    public void mousePressed() {
        Point p = surface.actualToAssumed(new Point(surface.mouseX, surface.mouseY));
        if (back.contains(p)) {
            System.out.println("back");
            surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
        }
    }
    
}
