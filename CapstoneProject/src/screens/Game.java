package screens;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import core.DrawingSurface;
import sprites.*;
/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 * The game class represents a double precision game screen screen that can be represented 
 * using processing 
 */
public class Game extends Screen {

    private DrawingSurface surface;
    private Rectangle screenRect;
    private Player player;
    private List<Platform> platforms;
    private List<Enemy> enemies;
    // todo: deal with list for sprites

    /**
     * Creates a new game object
     * @param surface the window
     */
    public Game(DrawingSurface surface) {
        super(800,600);
		this.surface = surface;

    }
    /**
     * Draws the game screen
     * @post The game screen is drawn
     */
    public void draw() {
        
    }

    
}
