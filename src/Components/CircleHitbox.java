package Components;

import CoreClasses.CollisionSystem;
import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by Isaac on 13/12/2016.
 */
public class CircleHitbox implements HitBox {
    public Position position;
    public float radius, mass;
    public GameObject owner;

    public CircleHitbox(GameObject owner, Position position, float radius, float mass)
    {
        this.owner = owner;
        this.position = position;
        this.radius = radius;
        this.mass = mass;
    }

    public CircleHitbox(GameObject owner, DataInputStream saveData) throws java.io.IOException {
        position = new Position(saveData);
        radius = saveData.readFloat();
        mass = saveData.readFloat();
        this.owner = owner;
    }

    public void save(DataOutputStream saveData) throws java.io.IOException {
        position.save(saveData);
        saveData.writeFloat(radius);
        saveData.writeFloat(mass);
    }

    @Override
    public void collide(Main main, HitBox other) {
        other.collideWithCircle(main, this);
    }

    @Override
    public void collideWithCircle(Main main, CircleHitbox other) {
        CollisionSystem.collideCircleAndCircle(main, other, this);
    }

    @Override
    public void collideWithAxisAlignedRect(Main main, AlignedRectangleHitbox other) {
        CollisionSystem.collideCircleAndAlignedRect(main, this, other);
    }

    @Override
    public void collideWithTrigger(Main main, TriggerHitBox other) {
        CollisionSystem.collideSkeletonAndCircle(main, other, this);
    }
}
