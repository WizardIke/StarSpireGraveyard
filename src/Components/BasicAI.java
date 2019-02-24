package Components;

import CoreClasses.AI;
import CoreClasses.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 14/10/2017.
 */
public class BasicAI {
    public float targetDirX, targetDirY;
    protected float speed;

    public BasicAI(float speed) {
        this.speed = speed;
    }

    public BasicAI(DataInputStream saveData) throws IOException {
        speed = saveData.readFloat();
    }

    public void update(Main main, float frameTime, GameObject ths, Position position) {
        AI.Target target = AI.findTarget(ths, main.gameObjects);
        if(target != null){
            targetDirX = target.dispacementX / target.distance;
            targetDirY = target.dispacementY / target.distance;
            position.x += targetDirX * speed * frameTime;
            position.y += targetDirY * speed * frameTime;
        }
    }

    public void save(DataOutputStream saveData) throws IOException {
        saveData.writeFloat(speed);
    }
}
