package Spells;

import Components.GameObject;
import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import static CoreClasses.NetworkMessageTypes.castSpell;
import static CoreClasses.NetworkMessageTypes.fireBolt;
import static CoreClasses.NetworkMessageTypes.gameObject;

/**
 * Created by Ike on 30/01/2017.
 */
public class FireBoltSpellClient extends FireBoltSpell {

    public FireBoltSpellClient(float speed, float castTime, float range, float damage){
        super(speed, castTime, range, damage, TalentTypes.FireBoltSpellClient);
    }

    public FireBoltSpellClient(DataInputStream saveData){
        super(saveData, TalentTypes.FireBoltSpellClient);
    }

    @Override
    public void cast(Main main, GameObject caster, float startPosX, float startPosY, float endPosX, float endPosY) {
        main.soundFX.FireboltCastSound.play(6.0f);
        float dirX = endPosX - startPosX;
        float dirY = endPosY - startPosY;
        float distance = (float)Math.sqrt(dirX * dirX + dirY * dirY);
        dirX /= distance;
        dirY /= distance;
        new FireBoltParticleClient(main, startPosX + dirX * radius,
                startPosY + dirY * radius, dirX, dirY, speed, lifeTime, caster,
                damage, radius);
        cooldown = castTime;

        DataOutputStream networkOut = main.networkConnection.getNetworkOut();

        try {
            networkOut.writeInt(15);
            networkOut.writeByte(gameObject);
            networkOut.writeInt(main.gameObjects.indexOf(caster));
            networkOut.write(castSpell);
            networkOut.write(fireBolt);
            networkOut.writeFloat(dirX);
            networkOut.writeFloat(dirY);
        } catch (java.io.IOException e) {
            main.connectionLost();
        }
    }

    @Override
    public void handleMessage(Main main, DataInputStream messageData, GameObject caster) throws Exception {
        main.soundFX.FireboltCastSound.play(6.0f);
        new FireBoltParticleClient(main, messageData.readFloat(),
                messageData.readFloat(), messageData.readFloat(), messageData.readFloat(), speed, lifeTime,
                caster, damage, radius);
    }
}
