package Spells;

import Components.GameObject;
import CoreClasses.Main;
import Creatures.SkeletonClient;

import java.io.DataInputStream;

/**
 * Created by Ike on 30/01/2017.
 */
public class RaiseSkeletonClient extends RaiseSkeleton{

    public RaiseSkeletonClient(float castTime){
        super(castTime, TalentTypes.RaiseSkeletonClient);
    }

    public RaiseSkeletonClient(DataInputStream saveData){
        super(saveData, TalentTypes.RaiseSkeletonClient);
    }

    @Override
    public void cast(Main main, GameObject caster, float x, float y, float posX, float posY) {
        new SkeletonClient(main, caster, 8.0f, 8.0f, posX, posY,
                0.15f);
        cooldown = castTime;
    }
}
