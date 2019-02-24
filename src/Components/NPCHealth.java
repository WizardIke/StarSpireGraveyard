package Components;

import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Isaac on 29/08/2017.
 */
public class NPCHealth extends RegeneratingHealth {
    public NPCHealth(final Resistances resistances, float armorToughness, float maxHealth, float health,
                        final float regeneration) {
        super(resistances, armorToughness, maxHealth, health, regeneration);
    }

    public NPCHealth(DataInputStream saveData) throws IOException {
        super(saveData);
    }

    @Override
    public void takeDamage(Main main, GameObject attacker, GameObject target, float amount, Resistances.Type type) {
        if(!main.survivalMode){
           super.takeDamage(main, attacker, target, amount, type);

            if (health <= 0.0) {
                attacker.gainAwesomeness(main, 500);
                try {
                    main.win();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
