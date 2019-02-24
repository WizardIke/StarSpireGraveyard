package GameLevels;


import Components.Position;
import CoreClasses.*;
import Creatures.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * Created by Isaac on 12/12/2016.
 */
public class CharacterCreation implements GameLevel {
    private static final float nameBoxPosX = 0.34f;
    private static final float nameBoxPosY = 0.25f;
    private static final float nameBoxWidth = 0.4f;
    private static final float nameLabelPosX = 0.25f;
    private static final float nameLabelPosY = 0.25f;

    private static final float finishButtonPosX = 0.8f;
    private static final float finishButtonPosY = 0.95f;
    private static final float finishButtonWidth = 0.088f;

    public static final float finishBoxPosX = 0.75f;
    public static final float finishBoxPosY = 0.9f;
    public static final float finishBoxWidth = 0.182f;
    public static final float finishBoxHeight = 0.0648f;

    private static final float backButtonPosX = 0.2f;
    private static final float backButtonPosY = 0.95f;
    private static final float backButtonWidth = 0.088f;

    private static final float backButtonBoxPosX = 0.15f;
    private static final float backButtonBoxPosY = 0.9f;
    private static final float backButtonBoxWidth = 0.182f;
    private static final float backButtonBoxHeight = 0.0648f;

    private static final float singleMultiPlayerButtonPosX = 0.25f;
    private static final float singleMultiPlayerButtonPosY = 0.65f;
    private static final float singleMultiPlayerButtonWidth = 0.16f;
    private static final float ipBoxPosX = 0.38f;
    private static final float ipBoxPosY = 0.75f;
    private static final float ipBoxWidth = 0.36f;
    private static final float ipLabelPosX = 0.25f;
    private static final float ipLabelPosY = 0.75f;
    private static final float survivalModeButtonPosX = 0.25f;
    private static final float survivalModeButtonPosY = 0.75f;
    private static final float survivalModeButtonWidth = 0.27f;

    private static final float classLabelPosX = 0.25f;
    private static final float classLabelPosY = 0.35f;

    private static final float fireMageIconPosX = 0.25f;
    private static final float fireMageIconPosY = 0.38f;
    private static final float fireMageIconWidthHeight = 0.2f;

    private static final float titlePosX = 0.19f;
    private static final float titlePosY = 0.18f;

    private static final float skeletonPosX = 0.6f;
    private static final float skeletonPosY = 0.3f;
    private static final float skeletonWidth = 0.155f;
    private static final float skeletonHeight = 0.28f;

    private static final double skeletonAnimationMaxTime = 4.0;


    private GameLevel previousGameLevel;
    public TextBox nameBox;
    private int playerType = SaveType.FireMagePlayer;
    private boolean singlePlayer = true;
    public TextBox ipBox;
    private String yourIP = "Unknown";
    private Rectangle.Float fireMageIconOutline;
    private Rectangle.Float necromancerIconOutline;
    private Font smallFont;
    public boolean hostNotFound = false;
    private boolean nameNotFound = false;
    private int hoveringOver = 3;
    private double skeletonAnimationTime = skeletonAnimationMaxTime * 0.5;
    private BufferedImage[] skeletonSpritesheet;
    private JoiningGameLevel joiningGameLevel;

    public CharacterCreation(Main main, GameLevel previousGameLevel) throws java.io.IOException {
        this.previousGameLevel = previousGameLevel;
        this.joiningGameLevel = new JoiningGameLevel(this);
        this.smallFont = main.arial.deriveFont(main.arial.getSize() * 0.60f);
        this.skeletonSpritesheet = new BufferedImage[28];
        BufferedImage temp = ImageIO.read(new File("data/graphics/skeletonspritesheet.png"));
        for(int y = 0; y < 4; ++y){
            for(int x = 0; x < 7; ++x){
                this.skeletonSpritesheet[y * 7 + x] = temp.getSubimage(x * 64, y * 64, 64, 64);
            }
        }
        this.fireMageIconOutline = new Rectangle.Float(main.settings.screenSizeX * fireMageIconPosX,
                main.settings.screenSizeY * fireMageIconPosY,
                main.settings.screenSizeY * fireMageIconWidthHeight,
                main.settings.screenSizeY * fireMageIconWidthHeight);
        this.necromancerIconOutline = new Rectangle.Float(main.settings.screenSizeX * fireMageIconPosX +
                main.settings.screenSizeY * fireMageIconWidthHeight * 1.1f,
                main.settings.screenSizeY * fireMageIconPosY,
                main.settings.screenSizeY * fireMageIconWidthHeight,
                main.settings.screenSizeY * fireMageIconWidthHeight);
        this.nameBox = new TextBox(main.settings.screenSizeX * nameBoxPosX,
                (main.settings.screenSizeY) * nameBoxPosY + main.arial.getSize() * 0.2f,
                main.settings.screenSizeX * nameBoxWidth, (float)main.arial.getSize() * 1.2f);
        this.ipBox = new TextBox(main.settings.screenSizeX * ipBoxPosX,
                (main.settings.screenSizeY) * ipBoxPosY + main.arial.getSize() * 0.2f,
                main.settings.screenSizeX * ipBoxWidth, (float)main.arial.getSize() * 1.2f);


        //find IP
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp() || networkInterface.isVirtual() ||
                        networkInterface.isPointToPoint())
                    continue;

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                if(addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    yourIP = address.getHostAddress();
                } //else{yourIP = "Unknown";}
            }
        } catch (SocketException e) {
            //yourIP = "Unknown;
        }
        main.gameObjects.clear();
    }

    @Override
    public void update(Main main, float dt) {
        nameBox.update(dt);
        ipBox.update(dt);
        skeletonAnimationTime += dt;
        if(skeletonAnimationTime >= skeletonAnimationMaxTime){
            skeletonAnimationTime = 0.0;
        }
    }

    @Override
    public void render(Main main, Graphics2D graphics) {
        graphics.setBackground(Color.black);
        graphics.clearRect(0, 0, (int)main.settings.screenSizeX, (int)main.settings.screenSizeY);
        graphics.setFont(main.courierNew);
        graphics.setColor(Color.red);
        graphics.drawString("STAY ALIVE", main.settings.screenSizeX * titlePosX, main.settings.screenSizeY * titlePosY);
        graphics.setColor(Color.white);
        graphics.setFont(main.arial);
        if(hoveringOver == 0){
            graphics.fillRect((int)(main.settings.screenSizeX * backButtonBoxPosX), (int)(main.settings.screenSizeY * backButtonBoxPosY),
                    (int)(main.settings.screenSizeX * backButtonBoxWidth), (int)(main.settings.screenSizeY * backButtonBoxHeight));
            graphics.setColor(Color.black);
            graphics.drawString("Back", main.settings.screenSizeX * backButtonPosX,
                    main.settings.screenSizeY * backButtonPosY);
            graphics.setColor(Color.white);
        }
        else{
            graphics.drawString("Back", main.settings.screenSizeX * backButtonPosX,
                    main.settings.screenSizeY * backButtonPosY);
        }
        if(hoveringOver == 1){
            graphics.fillRect((int)(main.settings.screenSizeX * finishBoxPosX), (int)(main.settings.screenSizeY * finishBoxPosY),
                    (int)(main.settings.screenSizeX * finishBoxWidth), (int)(main.settings.screenSizeY * finishBoxHeight));
            graphics.setColor(Color.black);
            graphics.drawString("Finish", main.settings.screenSizeX * finishButtonPosX,
                    main.settings.screenSizeY * finishButtonPosY);
            graphics.setColor(Color.white);
        }
        else{
            graphics.drawString("Finish", main.settings.screenSizeX * finishButtonPosX,
                    main.settings.screenSizeY * finishButtonPosY);
        }

        if(nameNotFound){
            graphics.setColor(Color.red);
            graphics.drawString("Name: ", main.settings.screenSizeX * nameLabelPosX,
                    main.settings.screenSizeY * nameLabelPosY);
            nameBox.render(graphics);
            graphics.setColor(Color.white);
        }
        else{
            graphics.drawString("Name: ", main.settings.screenSizeX * nameLabelPosX,
                    main.settings.screenSizeY * nameLabelPosY);
            nameBox.render(graphics);
        }
        graphics.drawString("Class:", main.settings.screenSizeX * classLabelPosX,
                main.settings.screenSizeY * classLabelPosY);
        graphics.setFont(smallFont);
        if(playerType == SaveType.FireMagePlayer){
            graphics.setColor(Color.blue);
        }
        graphics.draw(fireMageIconOutline);
        graphics.drawString("Fire Mage", main.settings.screenSizeX * fireMageIconPosX +
                0.15f * fireMageIconWidthHeight * main.settings.screenSizeY,
                main.settings.screenSizeY * fireMageIconPosY +
                        fireMageIconWidthHeight * main.settings.screenSizeY * 0.95f);
        if(playerType == SaveType.NecromancerPlayer){
            graphics.setColor(Color.blue);
        }
        else{
            graphics.setColor(Color.white);
        }
        graphics.draw(necromancerIconOutline);
        graphics.drawString("Necromancer", main.settings.screenSizeX * fireMageIconPosX +
                        main.settings.screenSizeY * fireMageIconWidthHeight * 1.11f,
                main.settings.screenSizeY * fireMageIconPosY +
                        fireMageIconWidthHeight * main.settings.screenSizeY * 0.95f);
        if(playerType == SaveType.NecromancerPlayer){
            graphics.setColor(Color.white);
        }
        graphics.setFont(main.arial);
        if(singlePlayer){
            graphics.drawString("Single Player", main.settings.screenSizeX * singleMultiPlayerButtonPosX,
                    main.settings.screenSizeY * singleMultiPlayerButtonPosY);
            graphics.drawString("Survival mode   " + String.valueOf(main.survivalMode),
                    main.settings.screenSizeX * survivalModeButtonPosX, main.settings.screenSizeY * survivalModeButtonPosY);
        }
        else{
            graphics.drawString("Multi Player", main.settings.screenSizeX * singleMultiPlayerButtonPosX,
                    main.settings.screenSizeY * singleMultiPlayerButtonPosY);
            if(playerType == SaveType.FireMagePlayer){
                if(hostNotFound){
                    graphics.setColor(Color.red);
                    graphics.drawString("Host's IP:", main.settings.screenSizeX * ipLabelPosX,
                            main.settings.screenSizeY * ipLabelPosY);
                    ipBox.render(graphics);
                    graphics.setColor(Color.white);
                }
                else{
                    graphics.drawString("Host's IP:", main.settings.screenSizeX * ipLabelPosX,
                            main.settings.screenSizeY * ipLabelPosY);
                    ipBox.render(graphics);
                }
            }
            else{
                graphics.drawString("Your IP: " + yourIP, main.settings.screenSizeX * ipLabelPosX,
                        main.settings.screenSizeY * ipLabelPosY);
            }
        }
        graphics.drawImage(skeletonSpritesheet[(int)(skeletonAnimationTime / skeletonAnimationMaxTime * 28.0)],
                (int)(skeletonPosX * main.settings.screenSizeX), (int)(skeletonPosY * main.settings.screenSizeY),
                (int)(main.settings.screenSizeX * skeletonWidth), (int)(main.settings.screenSizeY * skeletonHeight), null);
    }

    @Override
    public void mousePressed(MouseEvent event, Main main) throws IOException {
        if(event.getX() > (main.settings.screenSizeX * finishButtonPosX) &&  //finish
                event.getX() < (main.settings.screenSizeX * finishButtonPosX) + main.settings.screenSizeX * finishButtonWidth &&
                event.getY() > main.settings.screenSizeY * finishButtonPosY - main.arial.getSize()
                && event.getY() < (main.settings.screenSizeY * finishButtonPosY)) {
            activateFinishButton(main);
        }
        else if(event.getX() > (main.settings.screenSizeX * singleMultiPlayerButtonPosX) && //single or multi player
                event.getX() < (main.settings.screenSizeX * singleMultiPlayerButtonPosX) +
                        main.settings.screenSizeX * singleMultiPlayerButtonWidth &&
                event.getY() > main.settings.screenSizeY * singleMultiPlayerButtonPosY - main.arial.getSize() &&
                event.getY() < (main.settings.screenSizeY * singleMultiPlayerButtonPosY)) {
            this.singlePlayer = !this.singlePlayer;
        }
        else if(singlePlayer && (event.getX() > (main.settings.screenSizeX * survivalModeButtonPosX) && //survival mode
                event.getX() < (main.settings.screenSizeX * survivalModeButtonPosX) +
                        main.settings.screenSizeX * survivalModeButtonWidth &&
                event.getY() > main.settings.screenSizeY * survivalModeButtonPosY - main.arial.getSize() &&
                event.getY() < (main.settings.screenSizeY * survivalModeButtonPosY))) {
            main.survivalMode = !main.survivalMode;
        }
        else if(event.getX() > (main.settings.screenSizeX * fireMageIconPosX) && //mage icon
                event.getX() < (main.settings.screenSizeX * fireMageIconPosX) +
                        main.settings.screenSizeY * fireMageIconWidthHeight &&
                event.getY() > main.settings.screenSizeY * fireMageIconPosY &&
                event.getY() < (main.settings.screenSizeY * fireMageIconPosY + fireMageIconWidthHeight *
                        main.settings.screenSizeY)) {
            this.playerType = SaveType.FireMagePlayer;
        }
        else if(event.getX() > (main.settings.screenSizeX * fireMageIconPosX + 1.1f * fireMageIconWidthHeight * //necromancer icon
                main.settings.screenSizeY) &&
                event.getX() < (main.settings.screenSizeX * fireMageIconPosX + 1.1f * fireMageIconWidthHeight *
                        main.settings.screenSizeY) +
                        main.settings.screenSizeY * fireMageIconWidthHeight &&
                event.getY() > main.settings.screenSizeY * fireMageIconPosY &&
                event.getY() < (main.settings.screenSizeY * fireMageIconPosY + fireMageIconWidthHeight *
                        main.settings.screenSizeY)) {
            this.playerType = SaveType.NecromancerPlayer;
        }
        else if(event.getX() > (main.settings.screenSizeX * backButtonPosX) &&  //back
                event.getX() < (main.settings.screenSizeX * backButtonPosX) + main.settings.screenSizeX * backButtonWidth &&
                event.getY() > main.settings.screenSizeY * backButtonPosY - main.arial.getSize() &&
                event.getY() < (main.settings.screenSizeY * backButtonPosY)) {
            main.gameLevel = this.previousGameLevel;
        }
        nameBox.mousePressed(event);
        if(nameBox.active && nameNotFound){
            nameNotFound = false;
        }
        ipBox.mousePressed(event);
        if(ipBox.active){
            hostNotFound = false;
        }
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent event, Main main) throws IOException {
        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            if(hoveringOver != 0){
                hoveringOver = 0;
                if(main.settings.buttonSoundFx){
                    main.soundFX.beep.play();
                }
            }
        }
        else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(hoveringOver != 1){
                hoveringOver = 1;
                if(main.settings.buttonSoundFx){
                    main.soundFX.beep.play();
                }
            }
        }

        else if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            if (hoveringOver == 0) {
                main.gameLevel = this.previousGameLevel;
            }
            else {
                activateFinishButton(main);
            }
        }
        if(!nameBox.active && event.getKeyCode() == KeyEvent.VK_ENTER) {
            activateFinishButton(main);
        }
        nameBox.keyPressed(event);
        ipBox.keyPressed(event);
    }

    private void activateFinishButton(Main main) throws IOException {
        if(nameBox.text.equals("") || (playerType == SaveType.FireMagePlayer && !singlePlayer && ipBox.text.equals(""))){
            if(nameBox.text.equals("")) {
                nameNotFound = true;
            }
            if(playerType == SaveType.FireMagePlayer && !singlePlayer && ipBox.text.equals("")){
                hostNotFound = true;
            }
        }
        else{
            if(playerType == SaveType.FireMagePlayer && singlePlayer){
                try {
                    new NecromancerNPC(main, 0.0f, 0.0f);
                    main.player = new FireMagePlayer(main, nameBox.text, 0.5f,
                            0.5f);
                    startGame(main);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(playerType == SaveType.NecromancerPlayer && singlePlayer){
                try {
                    main.player = new NecromancerPlayer(main, nameBox.text, 0.5f, 0.5f);
                    new FireMageNPC(main, 0.0f, 0.0f);
                    startGame(main);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(playerType == SaveType.FireMagePlayer){
                main.gameLevel = joiningGameLevel;
                joiningGameLevel.activate(main);
            }
            else if(playerType == SaveType.NecromancerPlayer){
                main.gameLevel = new WaitingForOtherPlayerLevel(main, this);
            }
        }
    }

    public void startGame(Main main) throws java.io.IOException{
        main.settings.lastSavePath = "data/saves/" + nameBox.text;
        main.settings.updateSettingsFile();
        main.viewMatrix = new Matrix2d();
        Position playerPosition = main.player.getPosition();
        main.viewMatrix.setToTranslation((-playerPosition.x + 0.5) * main.settings.screenSizeX,
                (-playerPosition.y + 0.5 * main.settings.screenSizeY / main.settings.screenSizeX) * main.settings.screenSizeX);
        main.gameLevel = new Level1(main);
    }

    @Override
    public void mouseMoved (MouseEvent event, Main main) {
        double mouseX = event.getX();
        double mouseY = event.getY();
        if (((mouseX >= (main.settings.screenSizeX * backButtonBoxPosX) &&
                mouseX <= (main.settings.screenSizeX * backButtonBoxPosX) + backButtonBoxWidth * main.settings.screenSizeX &&
                mouseY >= (main.settings.screenSizeY * backButtonBoxPosY) &&
                mouseY <= (main.settings.screenSizeY * backButtonBoxPosY) + backButtonBoxHeight * main.settings.screenSizeY))) {
            if(hoveringOver != 0){
                hoveringOver = 0;
                if(main.settings.buttonSoundFx){
                    main.soundFX.beep.play();
                }
            }
        } else if (((mouseX >= (main.settings.screenSizeX * finishBoxPosX) &&
                mouseX <= (main.settings.screenSizeX * finishBoxPosX) + finishBoxWidth * main.settings.screenSizeX &&
                mouseY >= (main.settings.screenSizeY * finishBoxPosY) &&
                mouseY <= (main.settings.screenSizeY * finishBoxPosY) + finishBoxHeight * main.settings.screenSizeY))) {
            if(hoveringOver != 1){
                hoveringOver = 1;
                if(main.settings.buttonSoundFx){
                    main.soundFX.beep.play();
                }
            }
        } else {
            hoveringOver = 3;  //don't play sound
        }
    }
}
