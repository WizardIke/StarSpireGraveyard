package Components;

import CoreClasses.Main;
import CoreClasses.NetworkMessageTypes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static CoreClasses.NetworkMessageTypes.die;
import static CoreClasses.NetworkMessageTypes.setHealth;

/**
 * Created by Isaac on 31/08/2017.
 */
public class PlayerHealthHost extends RegeneratingHealth {

    public PlayerHealthHost(final Resistances resistances, float armorToughness, float maxHealth, float health,
                                 final float regeneration) {
        super(resistances,armorToughness, maxHealth, health, regeneration);
    }

    public PlayerHealthHost(DataInputStream saveData) throws IOException {
        super(saveData);
    }

    @Override
    public void takeDamage(Main main, GameObject attacker, GameObject target, float amount, Resistances.Type type) {
        super.takeDamage(main, attacker, target, amount,type);

        final DataOutputStream networkOut = main.networkConnection.getNetworkOut();
        try {
            networkOut.writeInt(10);
            networkOut.writeByte(NetworkMessageTypes.gameObject);
            networkOut.writeInt(main.gameObjects.indexOf(target));
            networkOut.writeByte(setHealth);
            networkOut.writeFloat(health);
        } catch (IOException e) {
            main.connectionLost();
        }

        if (health <= 0.0f) {
            try {
                networkOut.writeInt(6);
                networkOut.writeByte(NetworkMessageTypes.gameObject);
                networkOut.writeInt(main.gameObjects.indexOf(target));
                networkOut.write(die);
            } catch (IOException e) {
                main.connectionLost();
            }
            attacker.gainAwesomeness(main, 500);
            try {
                main.loose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
