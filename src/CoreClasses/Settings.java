package CoreClasses;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Settings {
    public boolean fullScreen;
    public boolean relativeMovement;
    public boolean buttonSoundFx;
    public boolean musicOn;
    public String lastSavePath = "";
    public float screenSizeX, screenSizeY;
    public double screenLocationX, screenLocationY;
    public RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION,
            RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    Settings(){
        renderingHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        renderingHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        renderingHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        //renderingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        try {
            Scanner settingsReader = new Scanner(new File("data/settings.txt"));
            String line;
            String variable;
            while(settingsReader.hasNextLine())
            {
                line = settingsReader.nextLine();
                int pos;
                for(pos = 0; line.charAt(pos) != ' ' && line.charAt(pos) != '='; ++pos);
                variable = line.substring(0, pos);
                while(line.charAt(pos) == ' ' || line.charAt(pos) == '=') {++pos;}
                switch(variable) {
                    case "bFullScreen" :
                        fullScreen = Boolean.valueOf(line.substring(pos, line.indexOf(';')));
                        break;
                    case "sLastSavePath" :
                        lastSavePath = line.substring(pos, line.indexOf(';'));
                        break;
                    case "bRelativeMovement" :
                        relativeMovement = Boolean.valueOf(line.substring(pos, line.indexOf(';')));
                        break;
                    case "bButtonSoundFX" :
                        buttonSoundFx = Boolean.valueOf(line.substring(pos, line.indexOf(';')));
                        break;
                    case "bMusicOn" :
                        musicOn = Boolean.valueOf(line.substring(pos, line.indexOf(';')));
                        break;
                }
            }
            settingsReader.close();
        }
        catch(FileNotFoundException e) {
            try {
                updateSettingsFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if(fullScreen) {
            screenSizeX = 0;
            screenSizeY = 0;
            screenLocationX = 0;
            screenLocationY = 0;
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] gs = ge.getScreenDevices();
            for (GraphicsDevice curGs : gs)
            {
                DisplayMode mode = curGs.getDisplayMode();
                screenSizeX += mode.getWidth();
                screenSizeY += mode.getHeight();
            }
        }
        else {
            screenSizeX = 0;
            screenSizeY = 0;
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] gs = ge.getScreenDevices();
            for (GraphicsDevice curGs : gs)
            {
                DisplayMode mode = curGs.getDisplayMode();
                screenSizeX += mode.getWidth();
                screenSizeY += mode.getHeight();
            }
            screenSizeX *= 0.5;
            screenSizeY *= 0.5;
            screenLocationX = screenSizeX * 0.5;
            screenLocationY = screenSizeY * 0.5;
        }
    }

    public void updateSettingsFile() throws java.io.IOException {
        try(FileWriter writer = new FileWriter("data/settings.txt", false)) {
            writer.write("bFullScreen=" + String.valueOf(fullScreen) + ";\n");
            writer.write("sLastSavePath=" + lastSavePath + ";\n");
            writer.write("bRelativeMovement=" + String.valueOf(relativeMovement) + ";\n");
            writer.write("bButtonSoundFX=" + String.valueOf(buttonSoundFx) + ";\n");
            writer.write("bMusicOn=" + String.valueOf(musicOn) + ";");
            writer.close();
        }
    }
}
