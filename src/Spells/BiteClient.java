package Spells;

import Components.Category;
import Components.GameObject;
import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 14/10/2017.
 */
public class BiteClient implements Spell{
    private  float castTime;
    private float coolDown;

    public BiteClient(float castTime) {
        this.castTime = castTime;
        this.coolDown = 0f;
    }

    public BiteClient(DataInputStream saveData) throws IOException {
        castTime = saveData.readFloat();
        coolDown = saveData.readFloat();
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
                coolDown = castTime;
            }
        }
    }

    @Override
    public void save(DataOutputStream saveData) throws IOException {
        saveData.writeInt(TalentTypes.BiteClient);
        saveData.writeFloat(castTime);
        saveData.writeFloat(coolDown);
    }
}
