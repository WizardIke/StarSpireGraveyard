package Components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 1/09/2017.
 */
public class Position {
    public float x;
    public float y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Position(DataInputStream saveData) throws IOException {
        x = saveData.readFloat();
        y = saveData.readFloat();
    }

    public void save(DataOutputStream saveData) throws IOException {
        saveData.writeFloat(x);
        saveData.writeFloat(y);
    }
}
