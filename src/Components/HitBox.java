package Components;

import CoreClasses.Main;

/**
 * Created by Isaac on 13/10/2017.
 */
public interface HitBox {
    void collide(Main main, HitBox other);
    void collideWithCircle(Main main, CircleHitbox other);
    void collideWithAxisAlignedRect(Main main, AlignedRectangleHitbox other);
    void collideWithTrigger(Main main, TriggerHitBox other);
}
