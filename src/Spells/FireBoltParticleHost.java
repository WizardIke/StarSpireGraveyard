package Spells;

import Components.*;
import CoreClasses.Main;
import CoreClasses.Projectile;
import CoreClasses.SaveType;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static CoreClasses.NetworkMessageTypes.*;

/**
 * Created by Ike on 30/01/2017.
 */
public class FireBoltParticleHost implements Projectile {
    protected static final float mass = 0.5f;

    public GameObject caster;
    private TriggerHitBox hitbox;
    private ExplodeHost explode;

    public FireBoltParticleHost(Main main, float posX, float posY, float dirX, float dirY, float speed, float lifeTime,
                                GameObject caster, float damage, float radius){
        explode = new ExplodeHost(damage, lifeTime, dirX * speed, dirY * speed);
        hitbox = new TriggerHitBox(this, explode, new Position(posX, posY), radius, mass);
        this.caster = caster;
        main.gameObjects.add(this);
    }

    public FireBoltParticleHost(Main main, DataInputStream saveData) throws IOException {
        final int casterIndex = saveData.readInt();
        caster = main.gameObjects.get(casterIndex);
        hitbox = new TriggerHitBox(this, saveData);
        explode = (ExplodeHost)hitbox.spell;
    }

    @Override
    public void update(Main main, float dt){
        main.renderables.add(this);
        explode.timeLeft -= dt;
        if(explode.timeLeft < 0.0) {
            main.gameObjects.remove(this);
        }
        hitbox.position.x += explode.vX * dt;
        hitbox.position.y += explode.vY * dt;
    }

    @Override
    public void render(Main main, Graphics2D graphics){
        FireBoltRenderer.render(main, graphics, hitbox, explode.exploding);
    }

    @Override
    public void fullUpdate(final Main main, final DataOutputStream networkData)throws java.io.IOException {
        networkData.writeInt(14);
        networkData.write(gameObject);
        networkData.writeInt(main.gameObjects.indexOf(this));
        networkData.write(setPos);
        networkData.writeFloat(hitbox.position.x);
        networkData.writeFloat(hitbox.position.y);
    }

    @Override
    public void gainAwesomeness(Main main, int amount){
        caster.gainAwesomeness(main, amount);
    }

    @Override
    public void gainXp(float amount){
        caster.gainXp(amount);
    }

    @Override
    public Faction getFaction() {
        return caster.getFaction();
    }

    @Override
    public int getType() {
        return SaveType.FireBoltParticleHost;
    }

    @Override
    public void save(Main main, DataOutputStream saveData) throws IOException {
        saveData.writeInt(getType());
        saveData.writeInt(main.gameObjects.indexOf(caster));
        hitbox.save(saveData);
    }

    @Override
    public float getBottomPosY(){
        return hitbox.position.y + hitbox.radius;
    }

    @Override
    public HitBox getHitbox() {
        return hitbox;
    }
}
