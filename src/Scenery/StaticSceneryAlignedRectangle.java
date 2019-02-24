package Scenery;

import Components.AlignedRectangleHitbox;
import Components.GameObject;
import Components.Category;

/**
 * Created by Isaac on 16/01/2017.
 */
public interface StaticSceneryAlignedRectangle extends GameObject {

    @Override
    default float getBottomPosY(){
        return ((AlignedRectangleHitbox)getHitbox()).position.y;
    }

    @Override
    default int getCategory() {
        return Category.Object;
    }
}
