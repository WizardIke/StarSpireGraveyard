package Spells;

import Components.GameObject;
import Components.Health;
import Components.Resistances;
import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static CoreClasses.NetworkMessageTypes.explode;
import static CoreClasses.NetworkMessageTypes.gameObject;

/**
 * Created by Isaac on 14/10/2017.
 */
public class ExplodeHost extends Explode {
    public ExplodeHost(float damage, float lifeTime, float vX, float vY) {
        super(damage, lifeTime, vX, vY);
    }

    public ExplodeHost(DataInputStream saveData) throws IOException {
        super(saveData);
    }

    @Override
    public void cast(Main main, GameObject caster, GameObject target) {
        if(!exploding && !target.getFaction().isAlly(caster.getFaction())){
            main.soundFX.explosion.play(5.0f);
            Health health = target.getHealth();
            if(health != null) {
                health.takeDamage(main, caster, target, damage, Resistances.Type.fire);
                caster.gainXp(damage * 0.1f);
            }
            timeLeft = 0.2f;
            exploding = true;
            vX = 0.0f;
            vY = 0.0f;

            final DataOutputStream networkData = main.networkConnection.getNetworkOut();
            try {
                networkData.writeInt(6);
                networkData.write(gameObject);
                networkData.writeInt(main.gameObjects.indexOf(caster));
                networkData.write(explode);
            } catch (IOException e) {
                main.connectionLost();
            }
        }
    }

    public void save(DataOutputStream saveData) {
        try {
            saveData.writeInt(TalentTypes.ExplodeHost);
            saveData.writeFloat(vX);
            saveData.writeFloat(vY);
            saveData.writeFloat(timeLeft);
            saveData.writeFloat(damage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
