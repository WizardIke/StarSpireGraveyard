package Scenery;

import Components.AlignedRectangleHitbox;
import Components.Faction;
import Components.HitBox;

/**
 * Created by Isaac on 1/02/2017.
 */
public class GraveStoneHitBoxRect implements StaticSceneryAlignedRectangle {
    private AlignedRectangleHitbox hitbox;

    public GraveStoneHitBoxRect(float minPosX, float minPosY, float maxPosX, float maxPosY){
        this.hitbox = new AlignedRectangleHitbox(this, minPosX, minPosY, maxPosX - minPosX, maxPosY - minPosY,
                Float.POSITIVE_INFINITY);
    }

    @Override
    public HitBox getHitbox()
    {
        return hitbox;
    }

    @Override
    public Faction getFaction() {
        return Faction.Unaligned;
    }
}
