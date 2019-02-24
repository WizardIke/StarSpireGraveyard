package Creatures;

import Components.*;
import CoreClasses.*;
import Spells.Bite;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by Isaac on 14/12/2016.
 */
public class Skeleton implements Creature{
    private static final float biteTime = 1.0f;
    private static final float biteDamage = 10.0f;
    private static final float minMass = 3.0f;
    private static final float massRange = 4.0f;
    private static final float radius = 0.018f;
    private static final float animationLength = 0.4f;


    private GameObject master;
    private Health health;
    private TriggerHitBox hitbox;
    private Renderer renderer;
    private BasicAI ai;

    public Skeleton(Main main, GameObject master, float health, float maxHealth, float posX, float posY, float speed) {
        this.master = master;
        this.health = new SkeletonHealth(maxHealth, health);
        Bite bite = new Bite(biteTime, biteDamage);
        hitbox = new TriggerHitBox(this, bite, new Position(posX, posY), radius, (float)(Math.random() * massRange + minMass));
        ai = new BasicAI(speed);
        renderer = new Renderer(main.sprites.skeleton, animationLength);
        main.gameObjects.add(this);
    }

    public Skeleton(Main main, DataInputStream saveData) throws java.io.IOException {
        master =  main.gameObjects.get(saveData.readInt());
        health = new SkeletonHealth(saveData);
        hitbox = new TriggerHitBox(this, saveData);
        ai = new BasicAI(saveData);
        renderer = new Renderer(main.sprites.skeleton, animationLength);
    }

    @Override
    public void update(Main main, float frameTime) {
        renderer.update(main, frameTime, this);
        hitbox.spell.update(main, frameTime);
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
        saveData.writeInt(SaveType.Skeleton);
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
    public Faction getFaction(){
        return master.getFaction();
    }

    @Override
    public int getType() {
        return SaveType.Skeleton;
    }

    @Override
    public Position getPosition() {
        return hitbox.position;
    }

    @Override
    public Health getHealth() {
        return health;
    }
}
