package Scenery;

import Components.CircleHitbox;
import Components.GameObject;
import Components.Category;

/**
 * Created by Isaac on 13/12/2016.
 */
public interface StaticScenery extends GameObject {
    @Override
    default float getBottomPosY(){
        CircleHitbox circle = (CircleHitbox)getHitbox();
        return circle.position.y + circle.radius;
    }

    @Override
    default int getCategory() {
        return Category.Object;
    }
}
