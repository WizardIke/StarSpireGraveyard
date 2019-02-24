package Spells;

import Components.GameObject;
import Components.Health;
import Components.Resistances;
import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 14/10/2017.
 */
public class Explode implements Spell{
    public boolean exploding;
    protected float damage;
    public float timeLeft;
    public float vX, vY;

    public Explode(float damage, float lifeTime, float vX, float vY) {
        exploding = false;
        this.damage = damage;
        this.timeLeft = lifeTime;
        this.vX = vX;
        this.vY = vY;
    }

    public Explode(DataInputStream saveData) throws IOException {
        this.vX = saveData.readFloat();
        this.vY = saveData.readFloat();
        this.timeLeft = saveData.readFloat();
        this.damage = saveData.readFloat();
    }

    @Override
    public void cast(Main main, GameObject caster, GameObject target) {
        if(!exploding && !target.getFaction().isAlly(caster.getFaction())){
            main.soundFX.explosion.play(5.0f);
            final Health health = target.getHealth();
            if(health != null) {
                health.takeDamage(main, caster, target, damage, Resistances.Type.fire);
                caster.gainXp(damage * 0.1f);
            }
            timeLeft = 0.2f;
            exploding = true;
            vX = 0.0f;
            vY = 0.0f;
        }
    }

    public void save(DataOutputStream saveData) {
        try {
            saveData.writeInt(TalentTypes.Explode);
            saveData.writeFloat(vX);
            saveData.writeFloat(vY);
            saveData.writeFloat(timeLeft);
            saveData.writeFloat(damage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
