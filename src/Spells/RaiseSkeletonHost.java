package Spells;

import Components.GameObject;
import CoreClasses.Main;
import CoreClasses.NetworkMessageTypes;
import Creatures.SkeletonHost;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static CoreClasses.NetworkMessageTypes.castSpell;
import static CoreClasses.NetworkMessageTypes.raiseSkeleton;

/**
 * Created by Ike on 30/01/2017.
 */
public class RaiseSkeletonHost extends RaiseSkeleton {

    public RaiseSkeletonHost(float castTime){
        super(castTime, TalentTypes.RaiseSkeletonHost);
    }

    public RaiseSkeletonHost(DataInputStream saveData){
        super(saveData, TalentTypes.RaiseSkeletonHost);
    }

    @Override
    public void cast(Main main, GameObject caster, float x, float y, float posX, float posY) {
        new SkeletonHost(main, caster, 8.0f, 8.0f, posX, posY,
                0.15f);
        cooldown = castTime;

        final DataOutputStream networkOut = main.networkConnection.getNetworkOut();
        try {
            networkOut.writeInt(15);
            networkOut.writeByte(NetworkMessageTypes.gameObject);
            networkOut.writeInt(main.gameObjects.indexOf(caster));
            networkOut.writeByte(castSpell);
            networkOut.writeByte(raiseSkeleton);
            networkOut.writeFloat(posX);
            networkOut.writeFloat(posY);
        } catch (IOException e) {
            main.connectionLost();
        }
    }
}
