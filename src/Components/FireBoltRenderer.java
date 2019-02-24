package Components;

import CoreClasses.Main;

import java.awt.*;

/**
 * Created by Isaac on 14/10/2017.
 */
public class FireBoltRenderer {
    public static void render(Main main, Graphics2D graphics, CircleHitbox hitbox, Boolean exploding){
        if(exploding) {
            graphics.drawImage(main.sprites.explosion, (int)((hitbox.position.x - hitbox.radius * 3) * main.settings.screenSizeX),
                    (int)((hitbox.position.y - hitbox.radius * 3)  * main.settings.screenSizeX),
                    (int)((hitbox.radius * 6)  * main.settings.screenSizeX),
                    (int)((hitbox.radius * 6)  * main.settings.screenSizeX), null);
        }
        else{
            graphics.drawImage(main.sprites.fireBall, (int)((hitbox.position.x - hitbox.radius) * main.settings.screenSizeX),
                    (int)((hitbox.position.y - hitbox.radius) * main.settings.screenSizeX),
                    (int)((hitbox.radius * 2.0) * main.settings.screenSizeX),
                    (int)((hitbox.radius * 2.0) * main.settings.screenSizeX), null);
        }
    }
}
