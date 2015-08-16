package map.functionals;

import main.Game;
import mobs.Mob;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.awt.*;

/**
 * Created by stanners on 03/08/2015.
 */
public class Toilet extends Functional {

    public Toilet(Point location, int orientation){
        super(location, orientation);
        try {
            this.image = new Image("res/img/functionals/toilet.png"); // TODO make less static
        } catch (SlickException e) {
            System.err.println("Failed to load toilet image");
            System.exit(Game.EXIT_BAD);
        }
    }

    @Override
    public void use(Mob mob) {
        System.out.println("mob used toilet");
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics screen) { // TODO CONSIDER does this need to be overridden or can the superclass handle it?
        image.draw(32, 32);
//        screen.drawImage(image, (float) location.getX(), (float) location.getY());

    }
}
