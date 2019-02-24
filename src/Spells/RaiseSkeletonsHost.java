package Spells;

import Components.GameObject;
import CoreClasses.Main;
import Creatures.SkeletonHost;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static CoreClasses.NetworkMessageTypes.castSpell;
import static CoreClasses.NetworkMessageTypes.raiseSkeletons;

/**
 * Created by Ike on 30/01/2017.
 */
public class RaiseSkeletonsHost extends RaiseSkeletons {

    public RaiseSkeletonsHost(float castTime, int minSkeletons, int maxSkeletons){
        super(castTime, minSkeletons, maxSkeletons, TalentTypes.RaiseSkeletonsHost);
    }

    public RaiseSkeletonsHost(DataInputStream saveData){
        super(saveData, TalentTypes.RaiseSkeletonsHost);
    }

    @Override
    public void cast(Main main, GameObject caster, float startX, float startY, float posX, float posY) {
        int numSkeletons = main.randomNumberGenerator.nextInt(maxSkeletons + 1 - minSkeletons) + minSkeletons;
        final DataOutputStream networkOut = main.networkConnection.getNetworkOut();
        try {
            networkOut.writeInt(10 + 8 * numSkeletons);
            networkOut.writeInt(caster.getType());
            networkOut.write(castSpell);
            networkOut.write(raiseSkeletons);
            networkOut.writeInt(numSkeletons);

            for(int j = 0; j < numSkeletons; ++j) {
                float x = posX + main.randomNumberGenerator.nextFloat() * 0.1f - 0.05f;
                float y = posY + main.randomNumberGenerator.nextFloat() * 0.1f - 0.05f;
                networkOut.writeFloat(x);
                networkOut.writeFloat(y);
                new SkeletonHost(main, caster, 8.0f, 8.0f,
                        x, y, 0.15f);
            }
            cooldown = castTime;
        } catch (IOException e) {
            main.connectionLost();
        }
    }
}
