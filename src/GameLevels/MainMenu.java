package GameLevels;

import CoreClasses.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.Color.*;

/**
 * Created by Isaac on 12/12/2016.
 */
public class MainMenu implements GameLevel {
    private static final float continuePosX = 0.43f;
    private static final float continuePosY = 0.37f;
    private static final float continueWidth = 0.15f;

    private static final float newGamePosX = 0.43f;
    private static final float newGamePosY = 0.43f;
    private static final float newGameWidth = 0.16f;

    private static final float titlePosX = 0.19f;
    private static final float titlePosY = 0.18f;


    private BufferedImage background;
    private int hoveringOver = 5;


    public MainMenu(Main main) throws java.io.IOException {
        if(main.settings.musicOn && main.currentMusic == null){
            startMusic(main);
        }

        background = ImageIO.read(new File("data/graphics/skeletonhandrepainted2.png"));
    }

    @Override
    public void render(Main main, Graphics2D graphics) {
        graphics.setBackground(Color.black);
        graphics.clearRect(0, 0, (int)(main.settings.screenSizeX), (int)(main.settings.screenSizeY));
        graphics.setFont(main.courierNew);
        graphics.setColor(RED);
        /*or dif name*/
        graphics.drawString("STAY ALIVE", main.settings.screenSizeX * titlePosX, main.settings.screenSizeY * titlePosY);
        graphics.setColor(white);

        graphics.drawImage(background, (int) (main.settings.screenSizeX * 0.294f), (int) (main.settings.screenSizeY * 0.2f),
                (int)(0.427f * main.settings.screenSizeX), (int)(0.777778f * main.settings.screenSizeY), null);


        graphics.setFont(main.arial);
        if(main.settings.lastSavePath.equals("")) {
            if(hoveringOver == 0){
                graphics.fillRect((int) (main.settings.screenSizeX * 0.38), (int) (main.settings.screenSizeY * 0.32),
                        (int)(0.26f * main.settings.screenSizeX), (int)(0.06f * main.settings.screenSizeY));
                graphics.setColor(Color.red);
                graphics.drawString("Continue", main.settings.screenSizeX * continuePosX,
                        main.settings.screenSizeY * continuePosY);
                graphics.setColor(white);
            }
            else{
                graphics.setColor(Color.red);
                graphics.drawString("Continue", main.settings.screenSizeX * continuePosX,
                        main.settings.screenSizeY * continuePosY);
                graphics.setColor(white);
            }
        }
        else {
            if(hoveringOver == 0){
                graphics.fillRect((int) (main.settings.screenSizeX * 0.38), (int) (main.settings.screenSizeY * 0.32),
                        (int)(0.26f * main.settings.screenSizeX), (int)(0.06f * main.settings.screenSizeY));
                graphics.setColor(black);
                graphics.drawString("Continue", main.settings.screenSizeX * continuePosX,
                        main.settings.screenSizeY * continuePosY);
                graphics.setColor(Color.darkGray);
                graphics.setColor(white);
            }
            else{
                graphics.drawString("Continue", main.settings.screenSizeX * continuePosX,
                        main.settings.screenSizeY * continuePosY);
            }
        }
        if(hoveringOver == 1){
            graphics.fillRect((int) (main.settings.screenSizeX * 0.38), (int) (main.settings.screenSizeY * 0.38),
                    (int)(0.26f * main.settings.screenSizeX), (int)(0.06f * main.settings.screenSizeY));
            graphics.setColor(black);
            graphics.drawString("New Game", main.settings.screenSizeX * newGamePosX, main.settings.screenSizeY * newGamePosY);
            graphics.setColor(white);
        }
        else{
            graphics.drawString("New Game", main.settings.screenSizeX * newGamePosX, main.settings.screenSizeY * newGamePosY);
        }


        if(hoveringOver == 2){
            graphics.fillRect((int) (main.settings.screenSizeX * 0.38), (int) (main.settings.screenSizeY * 0.44),
                    (int)(0.26f * main.settings.screenSizeX), (int)(0.06f * main.settings.screenSizeY));
            graphics.setColor(black);
            graphics.drawString("Load", main.settings.screenSizeX * 0.43f, main.settings.screenSizeY * 0.49f);
            graphics.setColor(white);
        }
        else{
            graphics.drawString("Load", main.settings.screenSizeX * 0.43f, main.settings.screenSizeY * 0.49f);
        }
        if(hoveringOver == 3){
            graphics.fillRect((int) (main.settings.screenSizeX * 0.38), (int) (main.settings.screenSizeY * 0.50),
                    (int)(0.26f * main.settings.screenSizeX), (int)(0.06f * main.settings.screenSizeY));
            graphics.setColor(black);
            graphics.drawString("Options", main.settings.screenSizeX * 0.43f, main.settings.screenSizeY * 0.55f);
            graphics.setColor(white);
        }
        else{
            graphics.drawString("Options", main.settings.screenSizeX * 0.43f, main.settings.screenSizeY * 0.55f);
        }
        if(hoveringOver == 4){
            graphics.fillRect((int) (main.settings.screenSizeX * 0.38), (int) (main.settings.screenSizeY * 0.56),
                    (int)(0.26f * main.settings.screenSizeX), (int)(0.06f * main.settings.screenSizeY));
            graphics.setColor(black);
            graphics.drawString("Quit", main.settings.screenSizeX * 0.43f, main.settings.screenSizeY * 0.61f);
            graphics.setColor(white);
        }
        else{
            graphics.drawString("Quit", main.settings.screenSizeX * 0.43f, main.settings.screenSizeY * 0.61f);
        }
    }

    @Override
    public void mousePressed(MouseEvent event, Main main) throws IOException {
        if(event.getX() > (main.settings.screenSizeX * continuePosX) && event.getX() < (main.settings.screenSizeX * continuePosX) +
                main.settings.screenSizeX * continueWidth &&
                event.getY() > main.settings.screenSizeY * continuePosY - main.arial.getSize() &&
                event.getY() < main.settings.screenSizeY * continuePosY) {
            if(!main.settings.lastSavePath.equals("")) {
                main.loadGame(new File(main.settings.lastSavePath));
            }
        }
        else if(event.getX() > (main.settings.screenSizeX * newGamePosX) && event.getX() < (main.settings.screenSizeX * newGamePosX) +
                main.settings.screenSizeX * newGameWidth &&
                event.getY() > main.settings.screenSizeY * newGamePosY - main.arial.getSize() &&
                event.getY() < main.settings.screenSizeY * newGamePosY) {
            main.gameLevel = new CharacterCreation(main, this);
        }
        else if(event.getX() > (main.settings.screenSizeX * 0.43f) && event.getX() < (main.settings.screenSizeX * 0.43f) +
                main.settings.screenSizeX * 0.11f &&
                event.getY() > main.settings.screenSizeY * 0.49f - main.arial.getSize() && event.getY() < main.settings.screenSizeY * 0.49f){
            main.gameLevel = new LoadSelection(main, this);
        }
        else if(event.getX() > (main.settings.screenSizeX * 0.43f) && event.getX() < (main.settings.screenSizeX * 0.43f) +
                main.settings.screenSizeX * 0.105f &&
                event.getY() > main.settings.screenSizeY * 0.55f - main.arial.getSize() && event.getY() < main.settings.screenSizeY * 0.55f) {
            main.gameLevel = new OptionsMenu(main, this);
        }
        else if(event.getX() > (main.settings.screenSizeX * 0.43f) && event.getX() < (main.settings.screenSizeX * 0.43f) +
                main.settings.screenSizeX * 0.16f &&
                event.getY() > main.settings.screenSizeY * 0.61f - main.arial.getSize() && event.getY() < main.settings.screenSizeY * 0.61f) {
            main.window.frame.dispatchEvent(new WindowEvent(main.window.frame, WindowEvent.WINDOW_CLOSING));
            main.currentMusic.stop();
            main.currentMusic.close();
        }
    }

    @Override
    public void keyPressed(KeyEvent event, Main main) throws IOException {
        if (event.getKeyCode() == KeyEvent.VK_UP) {
            --hoveringOver;
            if(hoveringOver == -1){
                hoveringOver = 4;
            }
            if(main.settings.buttonSoundFx){
                main.soundFX.beep.play();
            }
        }
        else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            ++hoveringOver;
            if(hoveringOver >= 5){
                hoveringOver = 0;
            }
            if(main.settings.buttonSoundFx){
                main.soundFX.beep.play();
            }
        }

        else if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            if (hoveringOver == 0) {
                main.loadGame(new File(main.settings.lastSavePath));
            } else if (hoveringOver == 1) {
                main.gameLevel = new CharacterCreation(main, this);
            } else if (hoveringOver == 2) {
                main.gameLevel = new LoadSelection(main, this);
            } else if (hoveringOver == 3) {
                main.gameLevel = new OptionsMenu(main, this);
            } else if (hoveringOver == 4){
                main.window.frame.dispatchEvent(new WindowEvent(main.window.frame, WindowEvent.WINDOW_CLOSING));
                main.currentMusic.stop();
                main.currentMusic.close();
            }
        }
    }

    @Override
    public void mouseMoved (MouseEvent event, Main main) {
        double mouseX = event.getX();
        double mouseY = event.getY();
        if (((mouseX >= (main.settings.screenSizeX * 0.38) &&
                mouseX <= (main.settings.screenSizeX * 0.38) + 0.26f * main.settings.screenSizeX &&
                mouseY >= (main.settings.screenSizeY * 0.32) &&
                mouseY <= (main.settings.screenSizeY * 0.32) + 0.06f * main.settings.screenSizeY))) {
            if(hoveringOver != 0){
                hoveringOver = 0;
                if(main.settings.buttonSoundFx){
                    main.soundFX.beep.play();
                }
            }
        } else if (((mouseX >= (main.settings.screenSizeX * 0.38) &&
                mouseX <= (main.settings.screenSizeX * 0.38) + 0.26f * main.settings.screenSizeX &&
                mouseY >= (main.settings.screenSizeY * 0.38) &&
                mouseY <= (main.settings.screenSizeY * 0.38) + 0.06f * main.settings.screenSizeY))) {
            if(hoveringOver != 1){
                hoveringOver = 1;
                if(main.settings.buttonSoundFx){
                    main.soundFX.beep.play();
                }
            }
        } else if (((mouseX >= (main.settings.screenSizeX * 0.38) &&
                mouseX <= (main.settings.screenSizeX * 0.38) + 0.26f * main.settings.screenSizeX &&
                mouseY >= (main.settings.screenSizeY * 0.44) &&
                mouseY <= (main.settings.screenSizeY * 0.44) + 0.06f * main.settings.screenSizeY))) {
            if(hoveringOver != 2){
                hoveringOver = 2;
                if(main.settings.buttonSoundFx){
                    main.soundFX.beep.play();
                }
            }
        } else if (((mouseX >= (main.settings.screenSizeX * 0.38) &&
                mouseX <= (main.settings.screenSizeX * 0.38) + 0.26f * main.settings.screenSizeX &&
                mouseY >= (main.settings.screenSizeY * 0.50) &&
                mouseY <= (main.settings.screenSizeY * 0.50) + 0.06f * main.settings.screenSizeY))) {
            if(hoveringOver != 3){
                hoveringOver = 3;
                if(main.settings.buttonSoundFx){
                    main.soundFX.beep.play();
                }
            }
        } else if (((mouseX >= (main.settings.screenSizeX * 0.38) &&
                mouseX <= (main.settings.screenSizeX * 0.38) + 0.26f * main.settings.screenSizeX &&
                mouseY >= (main.settings.screenSizeY * 0.56) &&
                mouseY <= (main.settings.screenSizeY * 0.56) + 0.06f * main.settings.screenSizeY))) {
            if(hoveringOver != 4){
                hoveringOver = 4;
                if(main.settings.buttonSoundFx){
                    main.soundFX.beep.play();
                }
            }
        } else {
            hoveringOver = 5;  //don't play sound
        }
    }

    @Override
    public void stopMusic(Main main){
        if(main.currentMusic != null){
            main.currentMusic.stop();
            main.currentMusic.close();
            main.currentMusic = null;
        }
    }

    @Override
    public void startMusic(Main main){
        try {
            main.currentMusic = AudioSystem.getClip();
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("data/music/Heroic_Demise_New_.wav"));
            main.currentMusic.open(audio);
            audio.close();
            main.currentMusic.setLoopPoints(44100 * 2, -1);
            main.currentMusic.setFramePosition(44100 * 2);
            main.currentMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
