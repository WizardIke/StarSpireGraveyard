package Creatures;

import Components.Category;
import Components.GameObject;
import Components.CircleHitbox;

/**
 * Created by Isaac on 14/12/2016.
 */
public interface Creature extends GameObject {
    @Override
    default float getBottomPosY()
    {
        CircleHitbox circle = (CircleHitbox)getHitbox();
        return circle.position.y + circle.radius;
    }

    @Override
    default int getCategory() {
        return Category.Creature;
    }
}
