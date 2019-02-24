package CoreClasses;

import java.awt.event.KeyEvent;

/**
 * Created by Isaac on 12/12/2016.
 */
public class Input {
    public boolean wDown = false, aDown = false, sDown = false, dDown = false, mouse1Down = false;

    public void keyPressed(KeyEvent event){
        switch(event.getKeyCode()) {
            case KeyEvent.VK_W:
                this.wDown = true;
                break;
            case KeyEvent.VK_S:
                this.sDown = true;
                break;
            case KeyEvent.VK_A:
                this.aDown = true;
                break;
            case KeyEvent.VK_D:
                this.dDown = true;
                break;
        }
    }

    public void keyReleased(KeyEvent event){
        switch(event.getKeyCode()){
            case KeyEvent.VK_W :
                this.wDown = false;
                break;
            case KeyEvent.VK_S :
                this.sDown = false;
                break;
            case KeyEvent.VK_A :
                this.aDown = false;
                break;
            case KeyEvent.VK_D :
                this.dDown = false;
                break;
        }
    }
}
