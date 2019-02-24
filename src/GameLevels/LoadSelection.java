package GameLevels;

import CoreClasses.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;

/**
 * Created by Isaac on 18/12/2016.
 */
public class LoadSelection implements GameLevel{
    private static final float backBoxPosX = 0.15f;
    private static final float backBoxPosY = 0.9f;
    private static final float backBoxWidth = 0.182f;
    private static final float backBoxHeight = 0.0648f;


    private String[] saveNames;
    private File[] saveFiles;
    private double[] nameLengths;
    private double dispacementY = 0.0;
    private double minDisY, maxDisY;
    private GameLevel previousGameLevel;
    private boolean backButtonSelected = false;

    public LoadSelection(Main main, GameLevel previousGameLevel) {
        File dir = new File("data/saves");
        saveFiles = dir.listFiles();
        saveNames = new String[saveFiles.length];
        nameLengths = new double[saveFiles.length];
        for(int i = 0; i < saveFiles.length; ++i) {
            saveNames[i] = saveFiles[i].getName();
            nameLengths[i] = main.window.frame.getGraphics().getFontMetrics(main.arial).stringWidth(saveNames[i]);
        }
        minDisY = saveNames.length * main.settings.screenSizeX * 0.04 > 0.8 * main.settings.screenSizeY ?
                0.8 * main.settings.screenSizeY - saveNames.length * main.settings.screenSizeX * 0.04 : 0.0;

        maxDisY = saveNames.length * main.settings.screenSizeX * 0.04 < 0.8 * main.settings.screenSizeY ?
                0.8 * main.settings.screenSizeY - saveNames.length * main.settings.screenSizeX * 0.04 : 0.0;
        this.previousGameLevel = previousGameLevel;
    }

    @Override
    public void render(Main main, Graphics2D graphics) {
        graphics.setBackground(Color.black);
        graphics.clearRect(0, 0, (int)main.settings.screenSizeX, (int)main.settings.screenSizeY);
        graphics.setFont(main.arial);
        graphics.setColor(Color.white);

        for(int i = 0; i < saveNames.length; ++i) {
            double y = main.settings.screenSizeX * 0.04 * i + dispacementY;
            if(y >= 0.0 && y <= 0.76 * main.settings.screenSizeY) {
                graphics.drawString(saveNames[i], (int)(main.settings.screenSizeX * 0.2),
                        (int)(main.settings.screenSizeY * 0.1 + y));
            }
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
        if(event.getX() > ( main.settings.screenSizeX * 0.20) && event.getX() < ( main.settings.screenSizeX * 0.20) +  //back
                main.settings.screenSizeX * 0.088 &&
                event.getY() >  main.settings.screenSizeY * 0.95  -  main.arial.getSize() &&
                event.getY() < ( main.settings.screenSizeY * 0.95)) {
            main.gameLevel = this.previousGameLevel;
        }
        for(int i = 0; i < saveNames.length; ++i) {
            if(event.getX() > ( main.settings.screenSizeX * 0.20) && event.getX() < ( main.settings.screenSizeX * 0.20) +
                    nameLengths[i] &&
                    event.getY() >  (main.settings.screenSizeY * 0.1 + main.settings.screenSizeX * 0.04 * i + dispacementY)
                            -  main.arial.getSize() &&
                    event.getY() < (main.settings.screenSizeY * 0.1 + main.settings.screenSizeX * 0.04 * i + dispacementY)) {
                main.settings.lastSavePath = "data/saves/" + saveNames[i];
                main.loadGame(saveFiles[i]);
                break;
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event, Main main) {
        dispacementY -= event.getPreciseWheelRotation();
        if(dispacementY < minDisY) {
            dispacementY = minDisY;
        }
        else if(dispacementY > maxDisY) {
            dispacementY = maxDisY;
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
                main.gameLevel = this.previousGameLevel;
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
