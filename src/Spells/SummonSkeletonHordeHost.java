package Spells;

import Components.GameObject;
import CoreClasses.Main;
import Creatures.SkeletonHost;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static CoreClasses.NetworkMessageTypes.castSpell;
import static CoreClasses.NetworkMessageTypes.summonSkeletonHorde;

/**
 * Created by Ike on 30/01/2017.
 */
public class SummonSkeletonHordeHost extends SummonSkeletonHorde {

    public SummonSkeletonHordeHost(float castTime, int minSkeletonsPerGroup, int skeletonsPerGroupRange,
                                   int minNumGroups, int numGroupsRange){
        super(castTime, minSkeletonsPerGroup, skeletonsPerGroupRange, minNumGroups, numGroupsRange, TalentTypes.SummonSkeletonHordeHost);
    }

    public SummonSkeletonHordeHost(DataInputStream saveData){
        super(saveData, TalentTypes.SummonSkeletonHordeHost);
    }

    @Override
    public void cast(Main main, GameObject caster) {
        int numGroups = main.randomNumberGenerator.nextInt(numGroupsRange) + minNumGroups;
        int numSkeletons;
        float posX, posY;
        for(int i = 0; i < numGroups; ++i) {
            posX = main.gameLevel.findRandomLocationX(main);
            posY = main.gameLevel.findRandomLocationY(main);
            numSkeletons = main.randomNumberGenerator.nextInt(skeletonsPerGroupRange) + minSkeletonsPerGroup;

            final DataOutputStream networkOut = main.networkConnection.getNetworkOut();
            try {
                networkOut.writeInt(10 + numSkeletons * 8);
                networkOut.writeInt(caster.getType());
                networkOut.write(castSpell);
                networkOut.write(summonSkeletonHorde);
                networkOut.writeInt(numSkeletons);
            } catch (IOException e) {
                main.connectionLost();
            }
            for(int j = 0; j < numSkeletons; ++j) {
                float x = posX + main.randomNumberGenerator.nextFloat() * 0.1f - 0.05f;
                float y = posY + main.randomNumberGenerator.nextFloat() * 0.1f - 0.05f;
                try {
                    networkOut.writeFloat(x);
                    networkOut.writeFloat(y);
                } catch (IOException e) {
                    main.connectionLost();
                }
                new SkeletonHost(main, caster, 8.0f, 8.0f, x, y, 0.15f);
            }
        }
        cooldown = castTime;
    }
}
