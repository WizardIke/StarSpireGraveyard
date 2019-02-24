package Spells;

import Components.GameObject;
import CoreClasses.Main;
import Creatures.Skeleton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 15/12/2016.
 */
public class RaiseSkeleton implements Spell {
    protected int talentType;
    protected float cooldown;
    protected float castTime;

    public RaiseSkeleton(float castTime) {
        this(castTime, TalentTypes.RaiseSkeleton);
    }

    protected RaiseSkeleton(float castTime, int talentType) {
        this.talentType = talentType;
        this.castTime = castTime;
        this.cooldown = 0.0f;
    }

    public RaiseSkeleton(DataInputStream saveData){
        this(saveData, TalentTypes.RaiseSkeleton);
    }

    protected RaiseSkeleton(DataInputStream saveData, int talentType){
        this.talentType = talentType;
        try {
            this.castTime = saveData.readFloat();
            this.cooldown = saveData.readFloat();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isCastable(GameObject caster) {
        return cooldown <= 0.0;
    }

    @Override
    public void update(Main main, float frameTime) {
        cooldown -= frameTime;
    }

    @Override
    public void cast(Main main, GameObject caster, float x, float y, float posX, float posY) {
        new Skeleton(main, caster, 8.0f, 8.0f, posX, posY,
                        0.15f);
        cooldown = castTime;
    }

    @Override
    public void cast(Main main, GameObject caster){}

    @Override
    public float getCastTime() {
        return castTime;
    }

    @Override
    public void setCastTime(float time) {
        this.castTime = time;
    }

    @Override
    public float getCastRange() {
        return 0.15f;
    }

    @Override
    public void save(DataOutputStream saveData){
        try {
            saveData.writeInt(talentType);
            saveData.writeFloat(castTime);
            saveData.writeFloat(cooldown);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
