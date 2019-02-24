package Scenery;

import CoreClasses.Main;
import java.awt.*;

/**
 * Created by Isaac on 2/02/2017.
 */
public class Tree1 extends Tree {
    private static final float width = 0.1258f;
    private static final float height = 0.1681f;

    public Tree1(float posX, float posY){
        super(posX + 0.5f * width, posY + 0.8f * height, width * 0.16f);
    }

    @Override
    public void render(Main main, Graphics2D graphics){
        graphics.drawImage(main.sprites.tree1, (int)(main.settings.screenSizeX * (hitbox.position.x - 0.5f * width)),
                (int)(main.settings.screenSizeX * (hitbox.position.y - 0.8f * height)),
                (int)(main.settings.screenSizeX * width), (int)(main.settings.screenSizeX * height), null);
    }
}
