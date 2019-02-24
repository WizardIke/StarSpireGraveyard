package Spells;

import Components.GameObject;
import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 12/12/2016.
 */
public interface Spell {
    default float getCastTime() {return 0;}
    default void setCastTime(float castTime) {}
    default void update(Main main, float frameTime) {}
    default boolean isCastable(GameObject caster) {return true;}
    default float getCastRange() {return 0f;}
    //used when cast at a target e.g. when using the mouse
    default void cast(Main main, GameObject caster, float startPositionX, float startPositionY, float endPositionX, float endPositionY) {
        cast(main, caster);
    }
    default void cast(Main main, GameObject caster, GameObject target) {
        cast(main, caster);
    }
    //used when cast without a target e.g. when used as a hotkey
    default void cast(Main main, GameObject caster) {}
    default void save(DataOutputStream saveData) throws IOException {}
    default void handleMessage(Main main, DataInputStream messageData, GameObject caster) throws Exception {}
}
