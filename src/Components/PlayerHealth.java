package Components;

import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Isaac on 24/08/2017.
 */
public class PlayerHealth extends RegeneratingHealth {
    public PlayerHealth(final Resistances resistances, float armorToughness, float maxHealth, float health,
                        final float regeneration) {
        super(resistances, armorToughness, maxHealth, health, regeneration);
    }

    public PlayerHealth(DataInputStream saveData) throws IOException {
        super(saveData);
    }

    public void takeDamage(Main main, GameObject attacker, GameObject target, float amount, Resistances.Type type) {
        super.takeDamage(main, attacker, target, amount,type);
        if(health < 0.0f) {
            attacker.gainAwesomeness(main, 500);
            try {
                main.loose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
