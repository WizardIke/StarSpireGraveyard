package GameLevels;

import CoreClasses.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;

/**
 * Created by Isaac on 12/12/2016.
 */
public interface GameLevel {
    default void update(Main main, float dt) throws Exception {}
    default void render(Main main, Graphics2D graphics) {}
    default void mousePressed(MouseEvent event, Main main) throws Exception {}
    default void mouseReleased(MouseEvent event, Main main) {}
    default void mouseWheelMoved(MouseWheelEvent event, Main main) {}
    default void mouseMoved(MouseEvent event, Main main){}
    default void keyPressed(KeyEvent event, Main main) throws Exception {}
    default void keyReleased(KeyEvent event, Main main) {}
    default float findRandomLocationX(Main main) {return 0.0f;}
    default float findRandomLocationY(Main main) {return 0.0f;}
    default void save(DataOutputStream saveData) {}
    default void destroy(Main main) {}
    default void stopMusic(Main main){}
    default void startMusic(Main main){}

    default void windowClosing(WindowEvent e, Main main) {}
}
