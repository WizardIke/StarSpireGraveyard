package Creatures;

import Components.*;
import CoreClasses.*;
import Spells.BiteClient;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import static CoreClasses.NetworkMessageTypes.*;

/**
 * Created by Ike on 30/01/2017.
 */

public class SkeletonClient implements Creature {
    private static final float animationLength = 0.4f;
    protected static final float radius = 0.018f;
    protected static final float minMass = 3.0f;
    protected static final float massRange = 4.0f;
    public static final float biteTime = 1.0f;

    private GameObject master;
    private Health health;
    private TriggerHitBox hitbox;
    private Renderer renderer;
    private BasicAI ai;
    private BiteClient bite = new BiteClient(biteTime);

    public SkeletonClient(Main main, GameObject master, float health,
                          float maxHealth, float posX, float posY, float speed){
        this.master = master;
        this.health = new SkeletonHealth(maxHealth, health);
        hitbox = new TriggerHitBox(this, bite, new Position(posX, posY), radius, (float)(Math.random() * massRange + minMass));
        ai = new BasicAI(speed);
        renderer = new Renderer(main.sprites.skeleton, animationLength);
        main.gameObjects.add(this);
    }

    public SkeletonClient(Main main, DataInputStream saveData) throws java.io.IOException {
        master =  main.gameObjects.get(saveData.readInt());
        health = new SkeletonHealth(saveData);
        this.hitbox = new TriggerHitBox(this, saveData);
        ai = new BasicAI(saveData);
        renderer = new Renderer(main.sprites.skeleton, animationLength);
    }

    @Override
    public void handleMessage(Main main, DataInputStream networkData) throws java.io.IOException{
        int message = networkData.readByte();
        switch(message){
            case setX:
                hitbox.position.x = networkData.readFloat();
                break;
            case setY:
                hitbox.position.y = networkData.readFloat();
                break;
            case setPos :
                hitbox.position.x = networkData.readFloat();
                hitbox.position.y = networkData.readFloat();
                break;
            case setHealth :
                health.health = networkData.readFloat();
                break;
            case die:
                main.gameObjects.remove(this);
                break;
            default :
                main.connectionLost();
                break;
        }
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
        saveData.writeInt(SaveType.SkeletonClient);
        saveData.writeInt(main.gameObjects.indexOf(master));
        health.save(saveData);
        hitbox.save(saveData);
        ai.save(saveData);
    }

    @Override
    public void gainXp(float amount){}

    @Override
    public void gainAwesomeness(Main main, int amount){}

    @Override
    public int getType() {
        return SaveType.SkeletonClient;
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
