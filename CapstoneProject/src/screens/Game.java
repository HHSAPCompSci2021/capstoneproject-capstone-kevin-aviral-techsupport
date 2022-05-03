package screens;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import core.DrawingSurface;
import sprites.*;

public class Game extends Screen {

    private DrawingSurface surface;
    private Rectangle screenRect;
    private Player player;
    private List<Platform> platforms;
    private List<Enemy> enemies;

    public Game(DrawingSurface surface) {
        super(800,600);
		this.surface = surface;

    }

    public void draw() {
        
    }

    
}
