package Spells;

import Components.Category;
import Components.GameObject;
import Components.Resistances;
import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 14/10/2017.
 */
public class Bite implements Spell {
    private  float castTime;
    private float coolDown;
    private float damage;

    public Bite(float castTime, float damage) {
        this.castTime = castTime;
        this.coolDown = 0f;
        this.damage = damage;
    }

    public Bite(DataInputStream saveData) throws IOException {
        castTime = saveData.readFloat();
        coolDown = saveData.readFloat();
        damage = saveData.readFloat();
    }

    @Override
    public void update(Main main, float frameTime) {
        if(coolDown > 0f) coolDown -= frameTime;
    }

    @Override
    public void cast(Main main, GameObject caster, GameObject target) {
        if(target.getCategory() == Category.Creature) {
            if(coolDown <= 0.0f && target.getFaction().isEnemy(caster.getFaction())) {
                main.soundFX.bite.play();
                target.getHealth().takeDamage(main, caster, target, damage, Resistances.Type.piecing);
                caster.gainXp(damage * 0.1f);
                coolDown = castTime;
            }
        }
    }

    @Override
    public void save(DataOutputStream saveData) throws IOException {
        saveData.writeInt(TalentTypes.Bite);
        saveData.writeFloat(castTime);
        saveData.writeFloat(coolDown);
        saveData.writeFloat(damage);
    }
}
