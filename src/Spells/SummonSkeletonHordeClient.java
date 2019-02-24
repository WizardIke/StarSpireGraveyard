package Spells;

import Components.GameObject;
import CoreClasses.Main;
import Creatures.SkeletonClient;

import java.io.DataInputStream;

/**
 * Created by Ike on 30/01/2017.
 */
public class SummonSkeletonHordeClient extends SummonSkeletonHorde {

    public SummonSkeletonHordeClient(float castTime, int minSkeletonsPerGroup, int skeletonsPerGroupRange,
                                   int minNumGroups, int numGroupsRange){
        super(castTime, minSkeletonsPerGroup, skeletonsPerGroupRange, minNumGroups, numGroupsRange, TalentTypes.SummonSkeletonHordeClient);
    }

    public SummonSkeletonHordeClient(DataInputStream saveData){
        super(saveData, TalentTypes.SummonSkeletonHordeClient);
    }

    @Override
    public void handleMessage(Main main, DataInputStream networkData, GameObject caster) throws java.io.IOException{
        int numSkeletons = networkData.readInt();
        for(int i = 0; i < numSkeletons; ++i){
            new SkeletonClient(main, caster, 8.0f, 8.0f, networkData.readFloat(),
                    networkData.readFloat(), 0.15f);
        }
    }
}
