package Scenery;

import CoreClasses.Main;
import java.awt.*;

/**
 * Created by Isaac on 2/02/2017.
 */
public class Tree4 extends Tree {
    private static final float width = 0.08387097f;
    private static final float height = 0.21018519f;

    public Tree4(float posX, float posY){
        super(posX - 0.5f * width, posY - 0.95f * height, 0.25f * width);
    }

    @Override
    public void render(Main main, Graphics2D graphics){
        graphics.drawImage(main.sprites.tree4, (int)(main.settings.screenSizeX * (hitbox.position.x - 0.5f * width)),
                (int)(main.settings.screenSizeX * (hitbox.position.y - 0.95f * height)),
                (int)(main.settings.screenSizeX * width), (int)(main.settings.screenSizeX * height), null);
    }
}
