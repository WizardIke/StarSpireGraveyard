package CoreClasses;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Window {
    public JFrame frame;

    public interface KeyEventDispatcher extends java.awt.KeyEventDispatcher, KeyListener {
        @Override
        default boolean dispatchKeyEvent(KeyEvent e) {
            switch (e.getID()) {
                case KeyEvent.KEY_PRESSED:
                    keyPressed(e);
                    return false;
                case KeyEvent.KEY_RELEASED:
                    keyReleased(e);
                    return false;
                case KeyEvent.KEY_TYPED:
                    keyTyped(e);
                    return false;
                default:
                    return false; // do not consume the event
            }
        }
    }

    public void setWindowSize(final int width, final int height) {
        SwingUtilities.invokeLater(() -> {
            // Resize the window (insets are just the boarders that the Operating System puts on the board)
            Insets insets = frame.getInsets();
            frame.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
            frame.getContentPane().setSize(width, height);
        });
    }

    private void setupWindow(final int width, final int height, final int locationX, final int locationY, final String title,
                            final boolean fullScreen, final java.awt.KeyEventDispatcher keyEventDispatcher,
                             MouseWheelListener mouseWheelListener, WindowListener windowListener, MouseListener mouseListener,
                             MouseMotionListener mouseMotionListener, JPanel gamePanel) {
        frame = new JFrame(title);

        frame.setLocation(locationX, locationY);
        //WindowListener will take care of closing
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setContentPane(gamePanel);
        if(fullScreen) {
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        gamePanel.addMouseListener(mouseListener);
        gamePanel.addMouseMotionListener(mouseMotionListener);
        gamePanel.addMouseWheelListener(mouseWheelListener);
        frame.addWindowListener(windowListener);

        // Register a key event dispatcher to get a turn in handling all
        // key events, independent of which component currently has the focus
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);

        // Resize the window (insets are just the boarders that the Operating System puts on the board)
        gamePanel.setPreferredSize(new Dimension(width, height));
        frame.pack();

        frame.setVisible(true);
    }

    public Window(final int screenWidth, final int screenHeight, final int locationX, final int locationY,
                  final String title, final boolean fullScreen,
                  KeyEventDispatcher keyEventDispatcher, MouseWheelListener mouseWheelListener, WindowListener windowListener,
                  MouseListener mouseListener, MouseMotionListener mouseMotionListener, JPanel gamePanel) {
        // Create window
        SwingUtilities.invokeLater(() -> setupWindow(screenWidth, screenHeight, locationX, locationY, title, fullScreen, keyEventDispatcher,
                    mouseWheelListener, windowListener, mouseListener, mouseMotionListener, gamePanel));
    }

    public static int getRefreshRate() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] graphicsDevices = ge.getScreenDevices();

        DisplayMode dm = graphicsDevices[0].getDisplayMode();
        int min = dm.getRefreshRate();
        if (min == DisplayMode.REFRESH_RATE_UNKNOWN) {
            min = 60;
        }
        for(int i = 1; i < graphicsDevices.length; ++i){
            dm = graphicsDevices[i].getDisplayMode();

            int refreshRate = dm.getRefreshRate();
            if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
                refreshRate = 60;
            }
            if(refreshRate < min) min = refreshRate;
        }
        return min;
    }
}