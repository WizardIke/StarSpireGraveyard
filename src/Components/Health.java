package Components;

import CoreClasses.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static java.lang.Math.pow;

/**
 * Created by Isaac on 22/08/2017.
 */
public class Health {
    public Resistances resistances;
    public float armorToughness;
    public float maxHealth;
    public float health;


    public Health(Resistances resistances, float armorToughness, float maxHealth, float health) {
        this.resistances = resistances;
        this.armorToughness = armorToughness;
        this.maxHealth = maxHealth;
        this.health = health;
    }

    public Health(DataInputStream saveData) throws IOException {
        resistances = new Resistances(saveData);
        armorToughness = saveData.readFloat();
        maxHealth = saveData.readFloat();
        health = saveData.readFloat();
    }

    public void takeDamage(Main main, GameObject attacker, GameObject target, float amount, Resistances.Type type) {
        final float resistance = resistances.resistances[type.value];
        health = health - amount + resistance * (float)pow((1.0f / resistance * amount), armorToughness);
    }

    public void save(DataOutputStream saveData) throws IOException {
        resistances.save(saveData);
        saveData.writeFloat(armorToughness);
        saveData.writeFloat(maxHealth);
        saveData.writeFloat(health);
    }
}
