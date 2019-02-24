package Components;

import CoreClasses.Main;
import CoreClasses.SaveType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by Isaac on 23/08/2017.
 */
public interface GameObject {
    default Health getHealth() {return null;}
    default String getName() {return null;}
    default Position getPosition() {return null;}
    default HitBox getHitbox() {return null;}
    default Faction getFaction() {return Faction.Unaligned;}
    default int getType() {return SaveType.Unknown;}
    default int getCategory() {return Category.Unknown;}

    default void render(Main main, Graphics2D graphics) {}
    default void gainXp(float amount) {}
    default void gainAwesomeness(Main main, int amount) {}
    default int getAwesomeness(){return 0;}
    default float getBottomPosY() {return 0;}
    default void handleMessage(Main main, DataInputStream networkData) throws Exception {}
    default void fullUpdate(Main main, final DataOutputStream networkData) throws java.io.IOException {}
    default void update(Main main, float frameTime) throws java.lang.Exception {}
    default void save(Main main, DataOutputStream saveData) throws java.io.IOException {}
    default void mousePressed(Main main, MouseEvent event) {}
    default void mouseReleased(Main main, MouseEvent event) {}
    default void keyPressed(Main main, KeyEvent event){}
    default void keyReleased(Main main, KeyEvent event){}
}
