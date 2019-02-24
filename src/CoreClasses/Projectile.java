package CoreClasses;

import Components.Category;
import Components.GameObject;
import Components.CircleHitbox;

/**
 * Created by Isaac on 14/01/2017.
 */
public interface Projectile extends GameObject{
    @Override
    default int getCategory() {
        return Category.Projectile;
    }
}
