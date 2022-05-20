package core;

import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import screens.*;
import processing.core.*;

public class DrawingSurface extends PApplet implements ScreenSwitcher {
	public float ratioX;
	public float ratioY;

	private ArrayList<Integer> keys;
	private ArrayList<Screen> screens;
	private Screen activeScreen;

	public DrawingSurface() {
		keys = new ArrayList<Integer>();
		screens = new ArrayList<Screen>();
		screens.add(new Menu(this));
		screens.add(new Game(this));
		screens.add(new Help(this));
		activeScreen = screens.get(0);
	}

	public void setup() {
		for (int i = 0; i < screens.size(); i++) {
			screens.get(i).setup();
		}
	}

	public void draw() {
		ratioX = (float) width / activeScreen.WIDTH;
		ratioY = (float) height / activeScreen.HEIGHT;

		scale(ratioX, ratioY);

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
		if (i == GAME_SCREEN) {
			screens.set(i, new Game(this));
			screens.get(i).setup();
		}
	}

}
