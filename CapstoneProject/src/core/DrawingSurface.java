package core;

import java.awt.Point;
import java.util.ArrayList;

import processing.core.*;
import screens.*;

public class DrawingSurface extends PApplet implements ScreenSwitcher {

	public float ratioX, ratioY;

	private ArrayList<Integer> keys;
	private PImage bg;
	private float x1, y1;
	private PImage fg;
	private float x2, y2;
	private ArrayList<Screen> screens;
	private Screen activeScreen;

	public DrawingSurface() {
		keys = new ArrayList<Integer>();
		screens = new ArrayList<Screen>();
		screens.add(new Menu(this));
		screens.add(new Game(this));
		activeScreen = screens.get(0);
	}

	public void setup() {
		// setup the images
		bg = loadImage("mountain.jpg");
		x1 = -10f;
		y1 = -10f;
		fg = loadImage("downfall_text.png");
		x2 = -0;
		y2 = 32;
	}

	public void draw() {
		ratioX = (float) width / activeScreen.WIDTH;
		ratioY = (float) height / activeScreen.HEIGHT;
		if (activeScreen instanceof Menu) {
			push();
			x1 = (float) (-10 - (mouseX - activeScreen.WIDTH / 2) * 0.02);
			y1 = (float) (-10 - (mouseY - activeScreen.HEIGHT / 2) * 0.02);
			image(bg, x1, y1, activeScreen.WIDTH + 64f, activeScreen.HEIGHT + 64f);
			System.out.println(x1);

			x2 = (float) (-(mouseX - activeScreen.WIDTH / 2) * 0.07);
			y2 = (float) (-(mouseY - activeScreen.HEIGHT / 2) * 0.07);
			image(fg, x2, y2, activeScreen.WIDTH, activeScreen.HEIGHT);
			scale(ratioX, ratioY);
			pop();
		}
		activeScreen.draw();
	}

	public void keyPressed() {
		keys.add(keyCode);
		if (key == ESC)
			key = 0;
	}

	public void keyReleased() {
		while (keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}

	public void mousePressed() {
		activeScreen.mousePressed();
	}

	public void mouseMoved() {
		activeScreen.mouseMoved();
	}

	public void mouseDragged() {
		activeScreen.mouseDragged();
	}

	public void mouseReleased() {
		activeScreen.mouseReleased();
	}

	public Point assumedToActual(Point assumed) {
		return new Point((int) (assumed.getX() * ratioX), (int) (assumed.getY() * ratioY));
	}

	public Point actualToAssumed(Point actual) {
		return new Point((int) (actual.getX() / ratioX), (int) (actual.getY() / ratioY));
	}

	@Override
	/**
	 * switches screen to screen i
	 * 
	 * @post screen is switched to screen i
	 */
	public void switchScreen(int i) {
		activeScreen = screens.get(i);
	}

}
