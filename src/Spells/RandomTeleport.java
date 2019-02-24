package Spells;

import Components.GameObject;
import Components.Position;
import Components.SpellBook;
import CoreClasses.Main;
import org.omg.PortableServer.POA;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 15/12/2016.
 */
public class RandomTeleport implements Spell {
    private Spell setPosition;
    private float cooldown;
    private float castTime;

    public RandomTeleport(float castTime, Spell setPosition) {
        this.castTime = castTime;
        this.setPosition = setPosition;
        cooldown = 0.0f;
    }

    public RandomTeleport(DataInputStream saveData) throws IOException {
        setPosition = SpellBook.loadSpell(saveData);
        this.castTime = saveData.readFloat();
        this.cooldown = saveData.readFloat();
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
        Position position = caster.getPosition();
        position.x = main.gameLevel.findRandomLocationX(main);
        position.y = main.gameLevel.findRandomLocationY(main);
        setPosition.cast(main, caster);
        cooldown = castTime;
    }

    @Override
    public float getCastTime() {
        return castTime;
    }

    @Override
    public float getCastRange() {
        return 0.0f;
    }

    @Override
    public void setCastTime(float castTime) {
        this.castTime = castTime;
    }

    @Override
    public void save(DataOutputStream saveData){
        try {
            saveData.writeInt(TalentTypes.RandomTeleport);
            setPosition.save(saveData);
            saveData.writeFloat(castTime);
            saveData.writeFloat(cooldown);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
