package Scenery;

import CoreClasses.Main;
import java.awt.*;

/**
 * Created by Isaac on 2/02/2017.
 */
public class Tree2 extends Tree {
    private static final float width = 0.08387097f;
    private static final float height = 0.12611111f;


    public Tree2( float posX, float posY){
        super(posX + 0.5f * width, posY + height * 0.95f, 0.15f * width);
    }

    @Override
    public void render(Main main, Graphics2D graphics){
        graphics.drawImage(main.sprites.tree2, (int)(main.settings.screenSizeX * (hitbox.position.x - 0.5f * width)),
                (int)(main.settings.screenSizeX * (hitbox.position.y - height * 0.95f)),
                (int)(main.settings.screenSizeX * width), (int)(main.settings.screenSizeX * height), null);
    }
}
