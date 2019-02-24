package Components;

import CoreClasses.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static java.lang.Math.sqrt;

/**
 * Created by Isaac on 31/08/2017.
 */
public class PlayerMovement {
    public CircleHitbox hitbox;
    public float speed;

    public PlayerMovement(CircleHitbox hitbox, float speed) {
        this.hitbox = hitbox;
        this.speed = speed;
    }

    public PlayerMovement(CircleHitbox hitbox, DataInputStream saveData) throws IOException {
        this.hitbox = hitbox;
        speed = saveData.readFloat();
    }

    public final void update(Main main, float frameTime) {
        if (main.settings.relativeMovement) {
            if (main.input.wDown) {
                float diffX = ((float) MouseInfo.getPointerInfo().getLocation().x / main.settings.screenSizeX) - 0.5f -
                        (main.window.frame.getLocation().x - main.window.frame.getInsets().left) / main.settings.screenSizeX;
                float diffY = ((float) MouseInfo.getPointerInfo().getLocation().y / main.settings.screenSizeY) - 0.5f -
                        (main.window.frame.getLocation().y - main.window.frame.getInsets().top) / main.settings.screenSizeY;
                float length = (float)sqrt(diffX * diffX + diffY * diffY);
                float componentX = 0.0f, componentY = 0.0f;
                if (length != 0.0f) {
                    componentX = diffX / length;
                    componentY = diffY / length;
                }
                componentX *= speed * frameTime;
                componentY *= speed * frameTime;
                increasePos(main, componentX, componentY);
            }else if (main.input.sDown) {
                float diffX = ((float) MouseInfo.getPointerInfo().getLocation().x / main.settings.screenSizeX) - 0.5f -
                        (main.window.frame.getLocation().x - main.window.frame.getInsets().left) / main.settings.screenSizeX;
                float diffY = ((float) MouseInfo.getPointerInfo().getLocation().y / main.settings.screenSizeY) - 0.5f -
                        (main.window.frame.getLocation().y - main.window.frame.getInsets().top) / main.settings.screenSizeY;
                float length = (float)sqrt(diffX * diffX + diffY * diffY);
                float componentX = 0.0f, componentY = 0.0f;
                if (length != 0.0f) {
                    componentX = diffX / length;
                    componentY = diffY / length;
                }
                componentX *= -speed * frameTime;
                componentY *= -speed * frameTime;
                increasePos(main, componentX, componentY);
            }
            if (main.input.aDown) {
                float diffX = ((float) MouseInfo.getPointerInfo().getLocation().x / main.settings.screenSizeX) - 0.5f -
                        (main.window.frame.getLocation().x - main.window.frame.getInsets().left) / main.settings.screenSizeX;
                float diffY = ((float) MouseInfo.getPointerInfo().getLocation().y / main.settings.screenSizeY) - 0.5f -
                        (main.window.frame.getLocation().y - main.window.frame.getInsets().top) / main.settings.screenSizeY;
                float length = (float)sqrt(diffX * diffX + diffY * diffY);
                float componentX = 0.0f, componentY = 0.0f;
                if (length != 0.0f) {
                    componentX = diffX / length;
                    componentY = diffY / length;
                }
                componentX *= speed * frameTime;
                componentY *= speed * frameTime;
                float temp = componentX;
                componentX = componentY;
                componentY = -temp;
                increasePos(main, componentX, componentY);
            } else if (main.input.dDown) {
                float diffX = ((float) MouseInfo.getPointerInfo().getLocation().x / main.settings.screenSizeX) - 0.5f -
                        (main.window.frame.getLocation().x - main.window.frame.getInsets().left) / main.settings.screenSizeX;
                float diffY = ((float) MouseInfo.getPointerInfo().getLocation().y / main.settings.screenSizeY) - 0.5f -
                        (main.window.frame.getLocation().y - main.window.frame.getInsets().top) / main.settings.screenSizeY;
                float length = (float)sqrt(diffX * diffX + diffY * diffY);
                float componentX = 0.0f, componentY = 0.0f;
                if (length != 0.0f) {
                    componentX = diffX / length;
                    componentY = diffY / length;
                }
                componentX *= speed * frameTime;
                componentY *= speed * frameTime;
                float temp = componentX;
                componentX = -componentY;
                componentY = temp;
                increasePos(main, componentX, componentY);
            }
        } else {
            if (main.input.wDown && main.input.aDown) {
                float comY = speed * frameTime * 0.707106781186547f;
                float comX = speed * frameTime * 0.707106781186547f;
                increasePos(main, -comX, -comY);
            } else if (main.input.wDown && main.input.dDown) {
                float comY = speed * frameTime * 0.707106781186547f;
                float comX = speed * frameTime * 0.707106781186547f;
                increasePos(main, comX, -comY);
            } else if (main.input.sDown && main.input.aDown) {
                float comY = speed * frameTime * 0.707106781186547f;
                float comX = speed * frameTime * 0.707106781186547f;
                increasePos(main, -comX, comY);
            } else if (main.input.sDown && main.input.dDown) {
                float comY = speed * frameTime * 0.707106781186547f;
                float comX = speed * frameTime * 0.707106781186547f;
                increasePos(main, comX, comY);
            } else if (main.input.wDown) {
                float comY = speed * frameTime;
                increasePos(main, 0.0f, -comY);
            } else if (main.input.sDown) {
                float comY = speed * frameTime;
                increasePos(main, 0.0f, comY);
            } else if (main.input.aDown) {
                float comX = speed * frameTime;
                increasePos(main, -comX, 0.0f);
            } else if (main.input.dDown) {
                float comX = speed * frameTime;
                increasePos(main, comX, 0.0f);
            }
        }
    }

    private void increasePos(Main main, float x, float y){
        hitbox.position.x += x;
        hitbox.position.y += y;
        main.viewMatrix.translate(-x * main.settings.screenSizeX, -y * main.settings.screenSizeX);
    }

    public void keyReleased(Main main, KeyEvent event){
        switch(event.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                int numKeysDown = 0;
                if(main.input.aDown){
                    ++numKeysDown;
                }
                if(main.input.dDown){
                    ++numKeysDown;
                }
                if(main.input.sDown){
                    ++numKeysDown;
                }
                if(main.input.wDown){
                    ++numKeysDown;
                }
                if(numKeysDown == 0){
                    //main.soundFX.playerWalkingOnLeaves.stop();
                    main.soundFX.playerWalkingOnDirt.stop();
                }
                break;
        }
    }

    public void keyPressed(Main main, KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                int numKeysDown = 0;
                if(main.input.aDown){
                    ++numKeysDown;
                }
                if(main.input.dDown){
                    ++numKeysDown;
                }
                if(main.input.sDown){
                    ++numKeysDown;
                }
                if(main.input.wDown){
                    ++numKeysDown;
                }
                if(numKeysDown == 1){
                    //main.soundFX.playerWalkingOnLeaves.loop(Clip.LOOP_CONTINUOUSLY);
                    main.soundFX.playerWalkingOnDirt.play();
                }
                break;
        }
    }

    public void save(DataOutputStream saveData) throws IOException {
        saveData.writeFloat(speed);
    }
}
