package CoreClasses;

import Components.*;
import Creatures.*;
import GameLevels.*;
import Spells.FireBoltParticle;
import Spells.FireBoltParticleClient;
import Spells.FireBoltParticleHost;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Main implements Window.KeyEventDispatcher, MouseWheelListener, WindowListener,
        MouseMotionListener, MouseListener{
    public java.util.Random randomNumberGenerator = new java.util.Random();
    public final Settings settings = new Settings();
    public Font arial = new Font("Arial", Font.PLAIN, (int)(settings.screenSizeX * 0.03f));
    public Font courierNew = new Font("Courier New Bold", 1, (int)(settings.screenSizeX * 0.1f));
    public final Input input = new Input();
    public SoundFX soundFX;
    public Sprites sprites;
    public GameLevel gameLevel;
    public Matrix2d viewMatrix;
    public AffineTransform uiViewMatrix = new AffineTransform();
    public Clip currentMusic = null;
    public NetworkConnection networkConnection = null;
    public boolean survivalMode = false;
    public Window window;
    public GameObject player;
    public ArrayList<GameObject> gameObjects = new ArrayList<>(40);
    public ArrayList<GameObject> renderables = new ArrayList<>(40);


    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        try {
            sprites = new Sprites();
            gameLevel = new MainMenu(this);
            soundFX = new SoundFX();
        } catch (Exception e) {
            e.printStackTrace();
        }

        window = new Window((int) settings.screenSizeX, (int) settings.screenSizeY, (int) settings.screenLocationX,
                (int) settings.screenLocationY, "Star-Spire Graveyard", settings.fullScreen,
                this, this, this, this, this,
                new JPanel(true) {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D graphics = (Graphics2D)g;
                graphics.setRenderingHints(settings.renderingHints);
                gameLevel.render(Main.this, graphics);
            }
        });

        GameTimer timer = new GameTimer(Window.getRefreshRate());
        timer.start(this);
    }

    private static class GameTimer {
        private long oldTime;
        private long frameTime;

        public GameTimer(int framerate) {
            this.frameTime = 1000000000 / framerate;
        }

        public void start(Main main) {
            oldTime = System.nanoTime();
            long actualFrameTime = frameTime;
            while(true) {
                try {
                    final double deltaTime = actualFrameTime / 1000000000.0;
                    SwingUtilities.invokeAndWait(() -> {
                        try {
                            main.gameLevel.update(main, (float)deltaTime);
                            main.window.frame.getContentPane().repaint();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    });

                    long time = System.nanoTime();
                    actualFrameTime = System.nanoTime() - oldTime;
                    long waitTime = frameTime - actualFrameTime;
                    if(waitTime > 0) {
                        sleep(waitTime / 1000000, (int)(waitTime % 1000000));
                        time = System.nanoTime();
                        actualFrameTime = time - oldTime;
                    }
                    oldTime = time;
                    if(actualFrameTime > 50000000) actualFrameTime = 50000000;
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        public long measureTime() {
            long time = System.nanoTime();
            long passed = time - oldTime;
            oldTime = time;
            return passed;
        }
    }

    // Called whenever a key is pressed
    @Override
    public void keyPressed(KeyEvent event) {
        try {
            input.keyPressed(event);
            gameLevel.keyPressed(event, this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Called whenever a key is released
    @Override
    public void keyReleased(KeyEvent event) {
        input.keyReleased(event);
        gameLevel.keyReleased(event, this);
    }

    // Called whenever a key is pressed and immediately released

    @Override
    public void keyTyped(KeyEvent event) {
    }

    //-------------------------------------------------------
    // Mouse functions
    //-------------------------------------------------------

    // Called whenever a mouse button is clicked
    // (pressed and released in the same position)
    @Override
    public void mouseClicked(MouseEvent event) {
    }

    // Called whenever a mouse button is pressed
    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            this.input.mouse1Down = true;
        }
        try {
            gameLevel.mousePressed(event, this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Called whenever a mouse button is released
    @Override
    public void mouseReleased(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            this.input.mouse1Down = false;
        }
        gameLevel.mouseReleased(event, this);
    }

    // Called whenever the mouse cursor enters the game panel
    @Override
    public void mouseEntered(MouseEvent event) {
    }

    // Called whenever the mouse cursor leaves the game panel
    @Override
    public void mouseExited(MouseEvent event) {
    }

    // Called whenever the mouse is moved
    @Override
    public void mouseMoved(MouseEvent event) {
        if(gameLevel != null){
            this.gameLevel.mouseMoved(event, this);
        }
    }

    // Called whenever the mouse is moved with the mouse button held down
    @Override
    public void mouseDragged(MouseEvent event) {
    }

    @Override
    public void windowClosing(WindowEvent e){
        gameLevel.windowClosing(e, this);
        window.frame.dispose();
        System.exit(0);
    }

    @Override
    public void windowOpened(WindowEvent e){

    }

    @Override
    public void windowActivated(WindowEvent e){

    }

    @Override
    public void windowDeactivated(WindowEvent e){

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }


    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        gameLevel.mouseWheelMoved(event, this);
    }

    public void loadGame(File savePath) {
        try {
            DataInputStream saveData = new DataInputStream(new BufferedInputStream(new FileInputStream(savePath)));
            this.survivalMode = saveData.readBoolean();
            int gameObjectCount = saveData.readInt();
            gameObjects = new ArrayList<>(gameObjectCount);
            switch (saveData.readInt()) {
                case SaveType.NecromancerNPC:
                    gameObjects.add(new NecromancerNPC(this, saveData));
                    break;
                case SaveType.NecromancerClientEnemyPlayer:
                    gameObjects.add(new NecromancerClient(this, saveData));
                    break;
                case SaveType.FireMagePlayer:
                    player = new FireMagePlayer(this, saveData);
                    gameObjects.add(player);
                    break;
                case SaveType.FireMageClientPlayer:
                    gameLevel = new JoiningGameLevelLoading(this, gameLevel, saveData, gameObjectCount);
                    return;
                case SaveType.NecromancerPlayer:
                    player = new NecromancerPlayer(this, saveData);
                    gameObjects.add(player);
                    break;
                case SaveType.NecromancerHostPlayer:
                    gameLevel = new WaitingForOtherPlayerLevelLoading(this, gameLevel, saveData,
                            gameObjectCount);
                    return;
            }
            continueLoading(saveData, gameObjectCount);
        } catch (java.io.FileNotFoundException e) {
            settings.lastSavePath = "";
            try {
                settings.updateSettingsFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void continueLoading(DataInputStream saveData, int gameObjectCount) {
        try {
            for(int i = 1; i != gameObjectCount; ++i) {
                int debug = saveData.readInt();
                switch (debug) {
                    case SaveType.NecromancerNPC:
                        gameObjects.add(new NecromancerNPC(this, saveData));
                        break;
                    case SaveType.NecromancerClientEnemyPlayer:
                        gameObjects.add(new NecromancerClient(this, saveData));
                        break;
                    case SaveType.FireMageNPC:
                        gameObjects.add(new FireMageNPC(this, saveData));
                        break;
                    case SaveType.FireMageHostEnemyPlayer:
                        gameObjects.add(new FireMageHost(this, saveData));
                        break;
                    case SaveType.FireMagePlayer:
                        player = new FireMagePlayer(this, saveData);
                        gameObjects.add(player);
                        break;
                    case SaveType.Skeleton :
                        gameObjects.add(new Skeleton(this, saveData));
                        break;
                    case SaveType.SkeletonHost :
                        gameObjects.add(new SkeletonHost(this, saveData));
                        break;
                    case SaveType.SkeletonClient :
                        gameObjects.add(new SkeletonClient(this, saveData));
                        break;
                    case SaveType.FireBoltParticle:
                        gameObjects.add(new FireBoltParticle(this, saveData));
                        break;
                    case SaveType.FireBoltParticleHost:
                        gameObjects.add(new FireBoltParticleHost(this, saveData));
                        break;
                    case SaveType.FireBoltParticleClient:
                        gameObjects.add(new FireBoltParticleClient(this, saveData));
                        break;
                    case SaveType.End:
                        i = gameObjectCount - 1;
                        break;
                    default :
                        System.out.print("invalid GameObject");
                        System.exit(1);
                }
            }

            this.viewMatrix = new Matrix2d();
            Position playerPosition = this.player.getPosition();
            this.viewMatrix.setToTranslation((-playerPosition.x + 0.5) * this.settings.screenSizeX,
                    (-playerPosition.y + 0.5 * this.settings.screenSizeY / this.settings.screenSizeX) * this.settings.screenSizeX);

            final int debug2 = saveData.readInt();
            switch (debug2) {
                case SaveType.Level1:
                    this.gameLevel = new Level1(this);
                    break;
                case SaveType.PauseMenu:
                    this.gameLevel = new PauseMenu(saveData, this);
                    break;
            }
            saveData.close();
        }
        catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGame() {
        try (DataOutputStream saveData = new DataOutputStream(new FileOutputStream("data/saves/" + player.getName()))){
            saveData.writeBoolean(survivalMode);

            saveData.writeInt(gameObjects.size());
            for(GameObject gameObject : gameObjects){
                gameObject.save(this, saveData);
            }
            saveData.writeInt(SaveType.End);
            gameLevel.save(saveData);
        }
        catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void connectionLost(){
        saveGame();
        try {
            settings.updateSettingsFile();
            if(networkConnection != null){
                networkConnection.close();
                networkConnection = null;
            }
            gameLevel.destroy(this);
            gameLevel = new MainMenu(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void win() throws IOException {
        File saveFile = new File("data/saves/" + player.getName());
        saveFile.delete();
        settings.lastSavePath = "";
        settings.updateSettingsFile();
        soundFX.playerWalkingOnDirt.stop();
        if(settings.musicOn) {
            gameLevel.stopMusic(this);
        }
        gameLevel = new VictoryLevel(this);
    }

    public void loose() throws IOException {
        File saveFile = new File("data/saves/" + player.getName());
        saveFile.delete();
        settings.lastSavePath = "";
        settings.updateSettingsFile();
        soundFX.playerWalkingOnDirt.stop();
        if(settings.musicOn) {
            gameLevel.stopMusic(this);
        }
        gameLevel = new GameOver(this);
    }
}