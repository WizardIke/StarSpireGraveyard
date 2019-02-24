package Spells;

import Components.*;
import CoreClasses.Main;
import CoreClasses.NetworkMessageTypes;
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
public class FireBoltParticleClient implements Projectile {

    public GameObject caster;
    private Explode explode;
    protected CircleHitbox hitbox;

    public FireBoltParticleClient(Main main, float posX, float posY, float dirX, float dirY, float speed, float lifeTime,
                                  GameObject caster, float damage, float radius){
        hitbox = new CircleHitbox(this, new Position(posX, posY), radius, FireBoltParticle.mass);
        explode = new Explode(damage, lifeTime, dirX * speed, dirY * speed);
        this.caster = caster;
        main.gameObjects.add(this);
    }

    public FireBoltParticleClient(Main main, DataInputStream saveData) throws IOException {
        final int casterIndex = saveData.readInt();
        this.caster = main.gameObjects.get(casterIndex);
        explode = (Explode)SpellBook.loadSpell(saveData);
        this.hitbox = new CircleHitbox(this, saveData);
    }

    @Override
    public void update(Main main, float dt){
        main.renderables.add(this);
        if(explode.exploding){
            explode.timeLeft -= dt;
            if(explode.timeLeft <= 0.0){
                main.gameObjects.remove(this);
            }
        }
        hitbox.position.x += explode.vX * dt;
        hitbox.position.y += explode.vY * dt;
    }

    @Override
    public void handleMessage(Main main, DataInputStream networkData) throws java.io.IOException{
        int message = networkData.read();
        switch(message){
            case NetworkMessageTypes.explode:
                if(!explode.exploding){
                    main.soundFX.explosion.play(5.0f);
                    explode.timeLeft = 0.2f;
                    explode.exploding = true;
                    explode.vX = 0.0f;
                    explode.vY = 0.0f;
                }
                break;
            case setPos :
                this.hitbox.position.x = networkData.readFloat();
                this.hitbox.position.y = networkData.readFloat();
                break;
            default :
                main.connectionLost();
                break;
        }
    }

    @Override
    public void gainXp(float amount){}

    @Override
    public void gainAwesomeness(Main main, int amount){}

    @Override
    public int getType() {
        return SaveType.FireBoltParticleClient;
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
        explode.save(saveData);
        hitbox.save(saveData);
    }

    @Override
    public Faction getFaction() {
        return caster.getFaction();
    }

    @Override
    public float getBottomPosY(){
        return hitbox.position.y + hitbox.radius;
    }
}
