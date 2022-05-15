package core;

import java.awt.Point;
import java.util.ArrayList;

import processing.core.*;
import screens.*;

public class DrawingSurface extends PApplet implements ScreenSwitcher {

	public float ratioX, ratioY;

	private ArrayList<Integer> keys;
	private PImage img;
	private float ix, iy;
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
		img = loadImage("downfall_title1.jpeg");
		ix = 0;
		iy = 0;
	}

	public void draw() {
		ratioX = (float) width / activeScreen.WIDTH;
		ratioY = (float) height / activeScreen.HEIGHT;

		push();
		// background(201, 205, 222);
		image(img, ix, iy, activeScreen.WIDTH, activeScreen.HEIGHT);
		if (0 < mouseX && mouseX < activeScreen.WIDTH) {
			// System.out.println("move");
			ix += (mouseX - activeScreen.WIDTH / 2) / 100;
		}

		scale(ratioX, ratioY);
		activeScreen.draw();

		pop();
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
