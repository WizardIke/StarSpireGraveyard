package Spells;

import Components.*;
import CoreClasses.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 20/12/2016.
 */
public class FireBoltParticle implements Projectile {
    protected static final float mass = 0.5f;

    private GameObject caster;
    private TriggerHitBox hitbox;
    private Explode explode;


    public FireBoltParticle(Main main, float posX, float posY, float radius, float dirX, float dirY, float speed, float lifeTime,
                            final GameObject caster, float damage) {
        explode = new Explode(damage, lifeTime, dirX * speed, dirY * speed);
        hitbox = new TriggerHitBox(this, explode, new Position(posX, posY), radius, mass);
        this.caster = caster;
        main.gameObjects.add(this);
    }

    public FireBoltParticle(final Main main, final DataInputStream saveData) throws IOException {
        final int casterIndex = saveData.readInt();
        caster = main.gameObjects.get(casterIndex);
        hitbox = new TriggerHitBox(this, saveData);
        explode = (Explode)hitbox.spell;
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
    public HitBox getHitbox() {
        return hitbox;
    }

    @Override
    public void save(Main main, DataOutputStream saveData) throws IOException {
        saveData.writeInt(getType());
        saveData.writeInt(main.gameObjects.indexOf(caster));
        hitbox.save(saveData);
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
        return SaveType.FireBoltParticle;
    }

    @Override
    public float getBottomPosY(){
        return hitbox.position.y + hitbox.radius;
    }
}
