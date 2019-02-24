package Components;

import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static CoreClasses.NetworkMessageTypes.gameObject;
import static CoreClasses.NetworkMessageTypes.die;
import static CoreClasses.NetworkMessageTypes.setHealth;

public class SkeletonHealthHost extends SkeletonHealth {
    public SkeletonHealthHost(float maxHealth, float health) {
        super(maxHealth, health);
    }
    public SkeletonHealthHost(DataInputStream saveData) throws IOException {
        super(saveData);
    }

    @Override
    public void takeDamage(final Main main, final GameObject attacker, final GameObject target, final float amount,
                           final Resistances.Type type) {
        super.takeDamage(main, attacker, target, amount, type);
        target.gainXp(amount * 0.1f);

        final DataOutputStream networkOut = main.networkConnection.getNetworkOut();

        try {
            networkOut.writeInt(10);
            networkOut.writeByte(gameObject);
            networkOut.writeInt(main.gameObjects.indexOf(target));
            networkOut.writeByte(setHealth);
            networkOut.writeFloat(health);
        } catch (IOException e) {
            main.connectionLost();
        }
        if(health <= 0.0) {
            try {
                networkOut.writeInt(6);
                networkOut.writeByte(gameObject);
                networkOut.writeInt(main.gameObjects.indexOf(target));
                networkOut.writeByte(die);
            } catch (IOException e) {
                main.connectionLost();
            }

            main.gameObjects.remove(target);
            attacker.gainAwesomeness(main, awesomenessForKill);
        }
    }
}
