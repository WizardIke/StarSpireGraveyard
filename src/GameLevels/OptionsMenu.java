package GameLevels;

import CoreClasses.Main;
import CoreClasses.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;

/**
 * Created by Isaac on 12/12/2016.
 */
public class OptionsMenu implements GameLevel {
    private static final float titlePosX = 0.19f;
    private static final float titlePosY = 0.18f;

    private static final float backBoxPosX = 0.15f;
    private static final float backBoxPosY = 0.9f;
    private static final float backBoxWidth = 0.182f;
    private static final float backBoxHeight = 0.0648f;

    private static final float maxDispacement = 0.45f;

    private static final float optionsStartHeight = 0.3f;

    private Settings settings;
    private boolean wasFullScreen;
    private double dispacementY = 0.0;
    private GameLevel previousGameLevel;
    private Font courierNew;
    private boolean backButtonSelected = false;

    public OptionsMenu(Main main, GameLevel previousGameLevel){
        this.settings = main.settings;
        this.wasFullScreen = main.settings.fullScreen;
        this.previousGameLevel = previousGameLevel;
        courierNew = new Font("Courier New Bold", 1, (int) (main.settings.screenSizeX * 0.1));
    }

    @Override
    public void render(Main main, Graphics2D graphics) {
        graphics.setBackground(Color.black);
        graphics.clearRect(0, 0,(int)(settings.screenSizeX), (int)(settings.screenSizeY));
        graphics.setColor(Color.red);
        graphics.setFont(courierNew);
        graphics.drawString("STAY ALIVE", main.settings.screenSizeX * titlePosX, main.settings.screenSizeY * titlePosY);

        graphics.setFont(main.arial);
        graphics.setColor(Color.white);
        if(main.settings.fullScreen){
            graphics.drawString("Fullscreen: on", (int)(settings.screenSizeX * 0.2),
                    (int)(settings.screenSizeY * optionsStartHeight + dispacementY));
        }
       else{
            graphics.drawString("Fullscreen: off", (int)(settings.screenSizeX * 0.2),
                    (int)(settings.screenSizeY * optionsStartHeight + dispacementY));
        }

        if(main.settings.relativeMovement) {
            graphics.drawString("Keyboard movement direction:  Relative", (int)(settings.screenSizeX * 0.2),
                    (int)(settings.screenSizeY * (optionsStartHeight + 0.1f) + dispacementY));
        }
        else {
            graphics.drawString("Keyboard movement direction:  Absolute", (int)(settings.screenSizeX * 0.2),
                    (int)(settings.screenSizeY * (optionsStartHeight + 0.1f) + dispacementY));
        }
        if(main.settings.buttonSoundFx){
            graphics.drawString("Button sound effects: on", (int)(settings.screenSizeX * 0.2),
                    (int)(settings.screenSizeY * (optionsStartHeight + 0.2f) + dispacementY));
        }
        else{
            graphics.drawString("Button sound effects: off", (int)(settings.screenSizeX * 0.2),
                    (int)(settings.screenSizeY * (optionsStartHeight + 0.2f) + dispacementY));
        }
        if(main.settings.musicOn){
            graphics.drawString("Music: on", (int)(settings.screenSizeX * 0.2),
                    (int)(settings.screenSizeY * (optionsStartHeight + 0.3f) + dispacementY));
        }
        else{
            graphics.drawString("Music: off", (int)(settings.screenSizeX * 0.2),
                    (int)(settings.screenSizeY * (optionsStartHeight + 0.3f) + dispacementY));
        }

        if(backButtonSelected){
            graphics.fillRect((int) (main.settings.screenSizeX * backBoxPosX), (int) (main.settings.screenSizeY * backBoxPosY),
                    (int)(backBoxWidth * main.settings.screenSizeX), (int)(backBoxHeight * main.settings.screenSizeY));
            graphics.setColor(Color.black);
            graphics.drawString("Back", (int)(main.settings.screenSizeX * 0.2), (int)(main.settings.screenSizeY * 0.95));
        }
        else{
            graphics.drawString("Back", (int)(main.settings.screenSizeX * 0.2), (int)(main.settings.screenSizeY * 0.95));
        }
    }

    @Override
    public void mousePressed(MouseEvent event, Main main) {
        if(event.getX() > (settings.screenSizeX * 0.20) && event.getX() < (settings.screenSizeX * 0.20) +  //back
                 main.settings.screenSizeX * 0.088 &&
                event.getY() > settings.screenSizeY * 0.95  - main.arial.getSize() && event.getY() < (settings.screenSizeY * 0.95)) {
            if(wasFullScreen != main.settings.fullScreen) {
                resizeScreen(main);
            }
            try {
                main.settings.updateSettingsFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            main.gameLevel = this.previousGameLevel;
        }
        else if(event.getX() > (settings.screenSizeX * 0.20) && event.getX() < (settings.screenSizeX * 0.20) +  //fullscreen
                main.settings.screenSizeX * 0.23 &&
                event.getY() > settings.screenSizeY * optionsStartHeight + dispacementY  - main.arial.getSize() &&
                event.getY() < settings.screenSizeY * optionsStartHeight + dispacementY) {
            main.settings.fullScreen = !main.settings.fullScreen;
        }
        else if(event.getX() > (settings.screenSizeX * 0.20) && event.getX() < (settings.screenSizeX * 0.20) +  //relative movement
                main.settings.screenSizeX * 0.54 &&
                event.getY() > settings.screenSizeY * (optionsStartHeight + 0.1f) + dispacementY  - main.arial.getSize() &&
                event.getY() < settings.screenSizeY * (optionsStartHeight + 0.1f) + dispacementY) {
            main.settings.relativeMovement = !main.settings.relativeMovement;
        }
        else if(event.getX() > (settings.screenSizeX * 0.20f) && event.getX() < (settings.screenSizeX * 0.20f) +  //button sfx
                main.settings.screenSizeX * 0.33f &&
                event.getY() > settings.screenSizeY * (optionsStartHeight + 0.2f) + dispacementY  - main.arial.getSize() &&
                event.getY() < settings.screenSizeY * (optionsStartHeight + 0.2f) + dispacementY) {
            main.settings.buttonSoundFx = !main.settings.buttonSoundFx;
        }
        else if(event.getX() > (settings.screenSizeX * 0.20f) && event.getX() < (settings.screenSizeX * 0.20f) +  //musicOn
                main.settings.screenSizeX * 0.15f &&
                event.getY() > settings.screenSizeY * (optionsStartHeight + 0.3f) + dispacementY  - main.arial.getSize() &&
                event.getY() < settings.screenSizeY * (optionsStartHeight + 0.3f) + dispacementY) {
            if(main.settings.musicOn){
                previousGameLevel.stopMusic(main);
                main.settings.musicOn = false;
            }
            else{
                main.settings.musicOn = true;
                previousGameLevel.startMusic(main);
                main.settings.musicOn = true;
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event, Main main) {
        dispacementY -= event.getPreciseWheelRotation();
        if(dispacementY < 0.0) {
            dispacementY = 0.0;
        }
        else if(dispacementY > settings.screenSizeY * maxDispacement) {
            dispacementY = settings.screenSizeY * maxDispacement;
        }
    }

    private void resizeScreen(Main main) {
        if(main.settings.fullScreen) {
            main.settings.screenSizeX = 0;
            main.settings.screenSizeY = 0;
            main.settings.screenLocationX = 0;
            main.settings.screenLocationY = 0;
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] gs = ge.getScreenDevices();
            for (GraphicsDevice curGs : gs)
            {
                DisplayMode mode = curGs.getDisplayMode();
                main.settings.screenSizeX += mode.getWidth();
                main.settings.screenSizeY += mode.getHeight();
            }

            main.window.frame.dispose();
            main.window.frame.setUndecorated(true);
            main.window.frame.setBounds((int)main.settings.screenLocationX, (int)main.settings.screenLocationY,
                    (int)main.settings.screenSizeX, (int)main.settings.screenSizeY);
            main.window.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            main.window.frame.setVisible(true);
            main.arial = main.arial.deriveFont((float) (settings.screenSizeX * 0.03));
            main.courierNew = main.courierNew.deriveFont(settings.screenSizeX * 0.1f);
        }
        else {
            main.settings.screenSizeX *= 0.5;
            main.settings.screenSizeY *= 0.5;
            main.settings.screenLocationX = main.settings.screenSizeX * 0.5;
            main.settings.screenLocationY = main.settings.screenSizeY * 0.5;

            main.window.frame.dispose();
            main.window.frame.setUndecorated(false);
            main.window.frame.pack();
            Insets insets = main.window.frame.getInsets();
            main.window.frame.setBounds((int)main.settings.screenLocationX, (int)main.settings.screenLocationY,
                    (int)main.settings.screenSizeX + insets.left + insets.right,
                    (int)main.settings.screenSizeY + insets.top + insets.bottom);
            main.window.frame.setExtendedState(JFrame.NORMAL);
            main.window.frame.setVisible(true);
            main.arial = main.arial.deriveFont((settings.screenSizeX * 0.03f));
            main.courierNew = main.courierNew.deriveFont(settings.screenSizeX * 0.1f);
        }
    }

    @Override
    public void keyPressed(KeyEvent event, Main main) {
        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            if(!backButtonSelected){
                backButtonSelected = true;
                if(main.settings.buttonSoundFx){
                    main.soundFX.beep.play();
                }
            }
        }

        else if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            if (backButtonSelected) {
                main.gameLevel = previousGameLevel;
            }
        }
    }

    @Override
    public void mouseMoved (MouseEvent event, Main main) {
        double mouseX = event.getX();
        double mouseY = event.getY();
        if (((mouseX >= (main.settings.screenSizeX * backBoxPosX) &&
                mouseX <= (main.settings.screenSizeX * backBoxPosX) + backBoxWidth * main.settings.screenSizeX &&
                mouseY >= (main.settings.screenSizeY * backBoxPosY) &&
                mouseY <= (main.settings.screenSizeY * backBoxPosY) + backBoxHeight * main.settings.screenSizeY))) {
            if(!backButtonSelected){
                backButtonSelected = true;
                if(main.settings.buttonSoundFx){
                    main.soundFX.beep.play();
                }
            }
        }
        else {
            backButtonSelected = false;  //don't play sound
        }
    }
}
