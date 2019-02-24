package Creatures;

import Components.*;
import CoreClasses.Main;
import CoreClasses.NetworkMessageTypes;
import CoreClasses.SaveType;
import Spells.Bite;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static CoreClasses.NetworkMessageTypes.*;

/**
 * Created by Ike on 30/01/2017.
 */
public class SkeletonHost implements Creature{
    private static final float biteTime = 1.0f;
    private static final float biteDamage = 10.0f;
    private static final float minMass = 3.0f;
    private static final float massRange = 4.0f;
    private static final float radius = 0.018f;
    private static final float animationLength = 0.4f;

    private GameObject master;
    private SkeletonHealthHost health;
    private TriggerHitBox hitbox;
    private Renderer renderer;
    private BasicAI ai;
    private Bite bite = new Bite(biteTime, biteDamage);

    public SkeletonHost(Main main, GameObject master, float health, float maxHealth, float posX, float posY, float speed){
        this.master = master;
        this.health = new SkeletonHealthHost(maxHealth, health);
        hitbox = new TriggerHitBox(this, bite, new Position(posX, posY), radius, (float)(Math.random() * massRange + minMass));
        ai = new BasicAI(speed);
        renderer = new Renderer(main.sprites.skeleton, animationLength);
        main.gameObjects.add(this);
    }

    public SkeletonHost(Main main, final DataInputStream saveData) throws java.io.IOException {
        master =  main.gameObjects.get(saveData.readInt());
        health = new SkeletonHealthHost(saveData);
        hitbox = new TriggerHitBox(this, saveData);
        ai = new BasicAI(saveData);
        renderer = new Renderer(main.sprites.skeleton, animationLength);
    }

    @Override
    public void fullUpdate(Main main, final DataOutputStream networkOut) throws java.io.IOException{
        networkOut.writeInt(14);
        networkOut.writeByte(NetworkMessageTypes.gameObject);
        networkOut.writeInt(main.gameObjects.indexOf(this));
        networkOut.writeByte(setPos);
        networkOut.writeFloat(hitbox.position.x);
        networkOut.writeFloat(hitbox.position.y);
    }

    @Override
    public int getType() {
        return SaveType.SkeletonHost;
    }

    @Override
    public void update(Main main, float frameTime) {
        renderer.update(main, frameTime, this);
        bite.update(main, frameTime);
        ai.update(main, frameTime, this, hitbox.position);
    }

    @Override
    public void render(Main main, Graphics2D graphics) {
        renderer.render(main, graphics, hitbox.position, hitbox.radius, ai.targetDirX, ai.targetDirY, true);
    }

    @Override
    public HitBox getHitbox()
    {
        return hitbox;
    }

    @Override
    public void save(Main main, DataOutputStream saveData) throws java.io.IOException {
        saveData.writeInt(SaveType.SkeletonHost);
        saveData.writeInt(main.gameObjects.indexOf(master));
        health.save(saveData);
        hitbox.save(saveData);
        ai.save(saveData);
    }

    @Override
    public void gainXp(float amount){
        master.gainXp(amount);
    }

    @Override
    public void gainAwesomeness(Main main, int amount){
        master.gainAwesomeness(main, amount);
    }

    @Override
    public Position getPosition() {
        return hitbox.position;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public Faction getFaction(){
        return master.getFaction();
    }
}