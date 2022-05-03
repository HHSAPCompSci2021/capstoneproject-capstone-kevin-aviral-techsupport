package core;

import java.awt.Point;
import java.util.ArrayList;

import processing.core.PApplet;
import screens.FirstScreen;
import screens.Screen;
import screens.ScreenSwitcher;
import screens.SecondScreen;

public class DrawingSurface extends PApplet implements ScreenSwitcher {

    public float ratioX, ratioY;

    private ArrayList<Integer> keys;

    private ArrayList<Screen> screens;
    private Screen activeScreen;

    public DrawingSurface() {
        keys = new ArrayList<Integer>();
        screens = new ArrayList<Screen>();
		screens.add(new FirstScreen(this));
		screens.add(new SecondScreen(this));
		
		activeScreen = screens.get(0);
    }

    public void draw() {

    }

    public void keyPressed() {
		keys.add(keyCode);
		if (key == ESC) key = 0;
	}

	public void keyReleased() {
		while(keys.contains(keyCode)) keys.remove(new Integer(keyCode));
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
		return new Point((int)(assumed.getX()*ratioX), (int)(assumed.getY()*ratioY));
	}

	public Point actualToAssumed(Point actual) {
		return new Point((int)(actual.getX()/ratioX) , (int)(actual.getY()/ratioY));
	}

	@Override
	public void switchScreen(int i) {
		activeScreen = screens.get(i);
	}
    
}
