package screens;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.*;
import java.util.*;

import aviral.shapes.Circle;
import core.DrawingSurface;
import sprites.*;

/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 *         The game class represents a double precision game screen screen that
 *         can be represented
 *         using processing
 */
public class Game extends Screen {

    private DrawingSurface surface;
    private Rectangle screenRect;
    private Player player;
    private ArrayList<Platform> platforms;
    private ArrayList<Sprite> enemies; // this will store enemies AND projectiles
                                    // easier to check for collisions

    /**
     * Creates a new game object
     * 
     * @param surface the window
     */
    public Game(DrawingSurface surface) {
        super(800, 600);
        this.surface = surface;
        screenRect = new Rectangle(0, 0, WIDTH, HEIGHT);
        // TODO: spawn the platforms and enemies
        platforms = new ArrayList<>();
        enemies = new ArrayList<>();
        // spawn the player
        player = new Player(new Circle(WIDTH / 2, HEIGHT / 2, 32), 0, 0, 0, 0, 3);
    }

    /**
     * Draws the game screen
     * 
     * @post The game screen is drawn
     */
    public void draw() {
        surface.background(36, 150, 177);
        // draw all the sprites
        for (Platform p : platforms) {
            p.draw(surface);
        }
        for (Sprite s : enemies) {
            s.draw(surface);
        }
        player.draw(surface);
        // check for key presses and player input
        if (surface.isPressed(KeyEvent.VK_ESCAPE)) {
            surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
        } else if (surface.isPressed(KeyEvent.VK_LEFT) || surface.isPressed(KeyEvent.VK_A)) {
            player.moveBy(-4, 0);
        } else if (surface.isPressed(KeyEvent.VK_RIGHT) || surface.isPressed(KeyEvent.VK_D)) {
            player.moveBy(4, 0);
        } else if (surface.isPressed(KeyEvent.VK_Q)) {
            player.shootLeft();
        } else if (surface.isPressed(KeyEvent.VK_E)) {
            player.shootRight();
        }
    }

    public void addPlatform(Platform p) {
        platforms.add(p);
    }

    public void addEnemyOrProjectile(Sprite s) {
        enemies.add(s);
    }

}
