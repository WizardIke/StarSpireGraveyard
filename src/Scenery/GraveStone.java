package Scenery;

import Components.AlignedRectangleHitbox;
import Components.HitBox;
import CoreClasses.Main;
import Components.Faction;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Isaac on 16/01/2017.
 */
public class GraveStone implements StaticSceneryAlignedRectangle {
    private AlignedRectangleHitbox hitbox;

    public GraveStone(float minPosX, float minPosY, float maxPosX, float maxPosY){
        this.hitbox = new AlignedRectangleHitbox(this, minPosX, minPosY, maxPosX - minPosX, maxPosY - minPosY,
                Float.POSITIVE_INFINITY);
    }

    @Override
    public void render(Main main, Graphics2D graphics){
        graphics.setColor(Color.darkGray);
        graphics.fill(new Rectangle2D.Float(hitbox.position.x, hitbox.position.y, hitbox.width, hitbox.height));
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
