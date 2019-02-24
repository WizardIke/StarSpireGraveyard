package Components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 31/08/2017.
 */
public class Movement {
    public CircleHitbox hitbox;
    public float speed;

    public Movement(CircleHitbox hitbox, float speed) {
        this.hitbox = hitbox;
        this.speed = speed;
    }

    public Movement(GameObject onwer, DataInputStream saveData) throws IOException {
        hitbox = new CircleHitbox(onwer, saveData);
        speed = saveData.readFloat();
    }

    public void save(DataOutputStream saveData) throws IOException {
        hitbox.save(saveData);
        saveData.writeFloat(speed);
    }
}
