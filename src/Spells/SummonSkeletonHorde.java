package Spells;

import Components.GameObject;
import CoreClasses.Main;
import Creatures.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 16/12/2016.
 */
public class SummonSkeletonHorde implements Spell {
    protected int talentType;
    protected float castTime;
    protected float cooldown;
    protected int minSkeletonsPerGroup, skeletonsPerGroupRange, minNumGroups, numGroupsRange;

    public SummonSkeletonHorde(float castTime, int minSkeletonsPerGroup, int skeletonsPerGroupRange,
                               int minNumGroups, int numGroupsRange) {
        this(castTime, minSkeletonsPerGroup, skeletonsPerGroupRange, minNumGroups, numGroupsRange, TalentTypes.SummonSkeletonHorde);
    }

    protected SummonSkeletonHorde(float castTime, int minSkeletonsPerGroup, int skeletonsPerGroupRange,
                               int minNumGroups, int numGroupsRange, int talentType) {
        this.talentType = talentType;
        this.castTime = castTime;
        this.minSkeletonsPerGroup = minSkeletonsPerGroup;
        this.skeletonsPerGroupRange = skeletonsPerGroupRange;
        this.minNumGroups = minNumGroups;
        this.numGroupsRange = numGroupsRange;
        this.cooldown = 0.0f;
    }

    public SummonSkeletonHorde(DataInputStream saveData){
        this(saveData, TalentTypes.SummonSkeletonHorde);
    }

    protected SummonSkeletonHorde(DataInputStream saveData, int talentType){
        this.talentType = talentType;
        try {
            this.castTime = saveData.readFloat();
            this.cooldown = saveData.readFloat();
            this.minSkeletonsPerGroup = saveData.readInt();
            this.skeletonsPerGroupRange = saveData.readInt();
            this.minNumGroups = saveData.readInt();
            this.numGroupsRange = saveData.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public float getCastTime() {
        return castTime;
    }

    @Override
    public void setCastTime(float castTime) {
        this.castTime = castTime;
    }

    @Override
    public float getCastRange() {
        return 0;
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
    public void cast(Main main, GameObject caster, float startX, float startY, float posX, float posY) {
        cast(main, caster);
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
            for(int j = 0; j < numSkeletons; ++j) {
                new Skeleton(main, caster, 8.0f, 8.0f,
                        posX + main.randomNumberGenerator.nextFloat() * 0.1f - 0.05f,
                        posY + main.randomNumberGenerator.nextFloat() * 0.1f - 0.05f,
                        0.15f);
            }
        }
        cooldown = castTime;
    }

    @Override
    public void save(DataOutputStream saveData){
        try {
            saveData.writeInt(talentType);
            saveData.writeFloat(castTime);
            saveData.writeFloat(cooldown);
            saveData.writeInt(minSkeletonsPerGroup);
            saveData.writeInt(skeletonsPerGroupRange);
            saveData.writeInt(minNumGroups);
            saveData.writeInt(numGroupsRange);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
