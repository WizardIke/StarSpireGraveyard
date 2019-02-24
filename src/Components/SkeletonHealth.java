package Components;

import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Isaac on 28/08/2017.
 */
public class SkeletonHealth extends Health {
    protected static final float armorToughness = 0.1f;
    protected static final float startingFireResistance = 0.2f;
    protected static final float startingColdResistance = 1.25f;
    protected static final float startingLightningResistance = 0.3f;
    protected static final float startingArcaneResistance = 0.2f;
    protected static final float startingBludgeoningResistance = 0.1f;
    protected static final float startingPiecingResistance = 1.4f;
    protected static final float startingSlashingResistance = 0.2f;

    protected static final int awesomenessForKill = 1;


    public SkeletonHealth(float maxHealth, float health) {
        super(new Resistances(startingFireResistance, startingColdResistance, startingLightningResistance,
                        startingArcaneResistance, startingBludgeoningResistance, startingPiecingResistance, startingSlashingResistance),
                armorToughness, maxHealth, health);
    }

    public SkeletonHealth(DataInputStream saveData) throws IOException {
        super(saveData);
    }

    public void takeDamage(Main main, GameObject attacker, GameObject target, float amount, Resistances.Type type) {
        super.takeDamage(main, attacker, target, amount, type);
        if(health < 0.0f) {
            main.gameObjects.remove(target);
            attacker.gainAwesomeness(main, awesomenessForKill);
        }
    }
}
