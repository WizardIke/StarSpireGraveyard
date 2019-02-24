package Spells;

import Components.GameObject;
import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 12/12/2016.
 */
public class FireBoltSpell implements Spell {
    protected static final float radius = 0.01f;

    protected int talentType;
    protected float speed;
    protected float castTime;
    protected float cooldown;
    protected float lifeTime;
    protected float damage;

    public FireBoltSpell(float speed, float castTime, float range, float damage) {
        this(speed, castTime, range, damage, TalentTypes.FireBoltSpell);
    }

    protected FireBoltSpell(float speed, float castTime, float range, float damage, int talentType) {
        this.talentType = talentType;
        this.speed = speed;
        this.castTime = castTime;
        this.lifeTime = range / speed;
        this.cooldown = 0.0f;
        this.damage = damage;
    }

    public FireBoltSpell(DataInputStream saveData){
        this(saveData, TalentTypes.FireBoltSpell);
    }

    protected FireBoltSpell(DataInputStream saveData, int talentType){
        this.talentType = talentType;
        try {
            this.speed = saveData.readFloat();
            this.castTime = saveData.readFloat();
            this.lifeTime = saveData.readFloat();
            this.cooldown = saveData.readFloat();
            this.damage = saveData.readFloat();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Main main, float frameTime) {
        cooldown -= frameTime;
    }

    @Override
    public boolean isCastable(GameObject caster) {
        return cooldown <= 0.0;
    }

    @Override
    public void cast(Main main, GameObject caster, float startPosX, float startPosY, float endPosX, float endPosY) {
        main.soundFX.FireboltCastSound.play(6.0f);
        float dirX = endPosX - startPosX;
        float dirY = endPosY - startPosY;
        float distance = (float)Math.sqrt(dirX * dirX + dirY * dirY);
        dirX /= distance;
        dirY /= distance;
        new FireBoltParticle(main, startPosX + dirX * radius,
                startPosY + dirY * radius, radius, dirX, dirY, speed, lifeTime, caster, damage);
        cooldown = castTime;
    }

    @Override
    public void cast(Main main, GameObject caster) {}

    @Override
    public void setCastTime(float castTime) {
        this.castTime = castTime;
    }

    @Override
    public float getCastTime() {
        return this.castTime;
    }

    @Override
    public float getCastRange() {
        return 1.0f;
    }

    @Override
    public void save(DataOutputStream saveData){
        try {
            saveData.writeInt(talentType);
            saveData.writeFloat(speed);
            saveData.writeFloat(castTime);
            saveData.writeFloat(lifeTime);
            saveData.writeFloat(cooldown);
            saveData.writeFloat(damage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
