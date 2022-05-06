package sprites;

import aviral.shapes.Shape;
import aviral.shapes.*;
import java.awt.*;

public class Player extends Sprite {
	
    public Player(Shape s, double vx, double vy, double ax, double ay, Color c, int totalLives) {
        super(s, vx, vy, ax, ay, c, totalLives);
    }

}

