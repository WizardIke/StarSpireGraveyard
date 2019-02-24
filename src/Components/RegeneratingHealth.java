package Components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 22/08/2017.
 */
public class RegeneratingHealth extends Health {
    public float regeneration;

    public RegeneratingHealth(Resistances resistances, float armorToughness, float maxHealth, float health,
                              final float regeneration) {
        super(resistances, armorToughness, maxHealth, health);
        this.regeneration = regeneration;
    }

    public RegeneratingHealth(DataInputStream saveData) throws IOException {
        super(saveData);
        regeneration = saveData.readFloat();
    }

    public final void update(float frameTime) {
        health += regeneration * frameTime;
        if(health > maxHealth) health = maxHealth;
    }

    @Override
    public void save(DataOutputStream saveData) throws IOException {
        super.save(saveData);
        saveData.writeFloat(regeneration);
    }
}
