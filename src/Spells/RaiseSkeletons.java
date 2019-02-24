package Spells;

import Components.GameObject;
import CoreClasses.Main;
import Creatures.Skeleton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 16/12/2016.
 */
public class RaiseSkeletons implements Spell {
    protected int talentType;
    protected float cooldown;
    protected float castTime;
    protected int minSkeletons;
    protected int maxSkeletons;

    public RaiseSkeletons(float castTime, int minSkeletons, int maxSkeletons) {
        this(castTime, minSkeletons, maxSkeletons, TalentTypes.RaiseSkeletons);
    }

    protected RaiseSkeletons(float castTime, int minSkeletons, int maxSkeletons, int talentType) {
        this.talentType = talentType;
        this.castTime = castTime;
        this.maxSkeletons = maxSkeletons;
        this.minSkeletons = minSkeletons;
        cooldown = 0.0f;
    }

    public RaiseSkeletons(DataInputStream saveData){
        this(saveData, TalentTypes.RaiseSkeletons);
    }

    protected RaiseSkeletons(DataInputStream saveData, int talentType){
        this.talentType = talentType;
        try {
            this.castTime = saveData.readFloat();
            this.cooldown = saveData.readFloat();
            this.minSkeletons = saveData.readInt();
            this.maxSkeletons = saveData.readInt();
        } catch (Exception e) {
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
        int numSkeletons = main.randomNumberGenerator.nextInt(maxSkeletons + 1 - minSkeletons) + minSkeletons;
        for(int j = 0; j < numSkeletons; ++j) {
            new Skeleton(main, caster, 8.0f, 8.0f,
                    posX + main.randomNumberGenerator.nextFloat() * 0.1f - 0.05f,
                    posY + main.randomNumberGenerator.nextFloat() * 0.1f - 0.05f,
                    0.15f);
        }
        cooldown = castTime;
    }

    @Override
    public void cast(Main main, GameObject caster) {}

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
            saveData.writeInt(minSkeletons);
            saveData.writeInt(maxSkeletons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}