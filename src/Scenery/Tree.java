package Scenery;

import Components.CircleHitbox;
import Components.Faction;
import Components.HitBox;
import Components.Position;
import CoreClasses.Main;


/**
 * Created by Isaac on 13/12/2016.
 */
public class Tree implements StaticScenery{
    protected CircleHitbox hitbox;

    public Tree(float x, float y, float radius) {
        this.hitbox = new CircleHitbox(this, new Position(x, y), radius, Float.POSITIVE_INFINITY);
    }

    @Override
    public void update(Main main, float frameTime) {
        main.renderables.add(this);
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

    @Override
    public Position getPosition() {
        return hitbox.position;
    }
}
