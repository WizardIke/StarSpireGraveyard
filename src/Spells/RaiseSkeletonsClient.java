package Spells;

import Components.GameObject;
import CoreClasses.Main;
import Creatures.SkeletonClient;
import java.io.DataInputStream;

/**
 * Created by Ike on 30/01/2017.
 */
public class RaiseSkeletonsClient extends RaiseSkeletons {
    public RaiseSkeletonsClient(float castTime, int minSkeletons, int maxSkeletons){
        super(castTime, minSkeletons, maxSkeletons, TalentTypes.RaiseSkeletonsClient);
    }

    public RaiseSkeletonsClient(DataInputStream saveData){
        super(saveData, TalentTypes.RaiseSkeletonsClient);
    }

    @Override
    public void cast(Main main, GameObject caster, float x, float y, float posX, float posY) {}

    @Override
    public void handleMessage(Main main, DataInputStream networkIn, GameObject caster) throws java.io.IOException{
        int numSkeletons = networkIn.readInt();
        for(int i = 0; i < numSkeletons; ++i){
            new SkeletonClient(main, caster, 8.0f, 8.0f, networkIn.readFloat(),
                    networkIn.readFloat(), 0.15f);
        }
    }
}
