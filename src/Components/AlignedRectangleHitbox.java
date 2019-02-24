package Components;

import CoreClasses.CollisionSystem;
import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by Isaac on 16/01/2017.
 */
public class AlignedRectangleHitbox implements HitBox {
    public Position position;
    public float width, height, mass;
    public GameObject owner;

    public AlignedRectangleHitbox(GameObject owner, float positionX, float positionY, float width, float height, float mass)
    {
        this.owner = owner;
        position = new Position(positionX, positionY);
        this.width = width;
        this.height = height;
        this.mass = mass;
    }

    public AlignedRectangleHitbox(GameObject owner, DataInputStream saveData) throws java.io.IOException {
        position = new Position(saveData.readFloat(), saveData.readFloat());
        width = saveData.readFloat();
        height = saveData.readFloat();
        this.owner = owner;
    }

    public void save(DataOutputStream saveData) throws java.io.IOException {
        saveData.writeFloat(position.x);
        saveData.writeFloat(position.y);
        saveData.writeFloat(width);
        saveData.writeFloat(height);
    }

    @Override
    public void collide(Main main, HitBox other) {
        other.collideWithAxisAlignedRect(main, this);
    }

    @Override
    public void collideWithCircle(Main main, CircleHitbox other) {
        CollisionSystem.collideCircleAndAlignedRect(main, other, this);
    }

    @Override
    public void collideWithAxisAlignedRect(Main main, AlignedRectangleHitbox other) {
        CollisionSystem.collideAlignedRectAndAlignedRect(main, other, this);
    }

    @Override
    public void collideWithTrigger(Main main, TriggerHitBox other) {
        CollisionSystem.collideSkeletonAndAlignedRect(main, other, this);
    }
}
