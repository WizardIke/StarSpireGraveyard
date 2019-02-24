package Components;

import CoreClasses.CollisionSystem;
import CoreClasses.Main;
import Spells.Spell;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 13/10/2017.
 */
public class TriggerHitBox extends CircleHitbox {
    public Spell spell;

    public TriggerHitBox(GameObject owner, Spell spell, Position position, float radius, float mass) {
        super(owner, position, radius, mass);
        this.spell = spell;
    }

    public void handleCollision(Main main, GameObject caster, GameObject other) {
        spell.cast(main, caster, other);
    }

    public TriggerHitBox(GameObject owner, DataInputStream saveData) throws java.io.IOException {
        super(owner, saveData);
        this.spell = SpellBook.loadSpell(saveData);
    }

    @Override
    public void save(DataOutputStream saveData) throws IOException {
        super.save(saveData);
        spell.save(saveData);
    }

    @Override
    public void collide(Main main, HitBox other) {
        other.collideWithTrigger(main, this);
    }

    @Override
    public void collideWithCircle(Main main, CircleHitbox other) {
        CollisionSystem.collideSkeletonAndCircle(main, this, other);
    }

    @Override
    public void collideWithAxisAlignedRect(Main main, AlignedRectangleHitbox other) {
        CollisionSystem.collideSkeletonAndAlignedRect(main, this, other);
    }

    @Override
    public void collideWithTrigger(Main main, TriggerHitBox other) {
        CollisionSystem.collideSkeletonAndSkeleton(main, other, this);
    }
}
