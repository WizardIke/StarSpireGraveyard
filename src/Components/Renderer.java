package Components;

import CoreClasses.Main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Isaac on 24/08/2017.
 */
public class Renderer {
    private float animationLength;
    private float animationTime;
    private BufferedImage[] images;

    public Renderer(BufferedImage[] images, float animationLength) {
        this.images = images;
        this.animationLength = animationLength;
        animationTime = 0.0f;
    }

    public void render(Main main, Graphics2D graphics, Position pos, float radius, float targetDirX, float targetDirY,
                       boolean moving) {
        int frameNum = 0;

        if(moving) {
            frameNum = (int)(animationTime / animationLength * 9.0);
            if (targetDirX > 0.70711) {
                // Moving Right
                frameNum += 27;
            } else if (targetDirX < -0.70711) {
                // Moving Left
                frameNum += 9;
            }else if (targetDirY > 0.70711) {
                // Moving Down
                frameNum += 18;
            } // Moving Up
        }

        graphics.drawImage(images[frameNum], (int)((pos.x - radius * 2) * main.settings.screenSizeX),
                (int)((pos.y - radius * 2) * main.settings.screenSizeX), (int)((radius * 4) * main.settings.screenSizeX),
                (int)((radius * 4)  * main.settings.screenSizeX), null);
    }

    public void update(Main main, float frameTime, GameObject ths) {
        main.renderables.add(ths);
        animationTime += frameTime;
        if(animationTime >= animationLength) animationTime = 0.0f;
    }
}
