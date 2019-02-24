package Spells;

import Components.GameObject;
import CoreClasses.Main;

import java.io.DataInputStream;

/**
 * Created by Ike on 30/01/2017.
 */
public class FireBoltSpellHost extends FireBoltSpell {

    public FireBoltSpellHost(float speed, float castTime, float range, float damage){
        super(speed, castTime, range, damage, TalentTypes.FireBoltSpellHost);
    }

    public FireBoltSpellHost(DataInputStream saveData){
        super(saveData, TalentTypes.FireBoltSpellHost);
    }

    @Override
    public void cast(Main main, GameObject caster, float posX, float posY, float dirX, float dirY) {
        main.soundFX.FireboltCastSound.play(6.0f);
        new FireBoltParticleHost(main, posX + dirX * radius,
                posY + dirY * radius, dirX, dirY, speed, lifeTime, caster,
                damage, radius);

        cooldown = castTime;
    }
}
