package GameLevels;

import Components.GameObject;
import Components.Health;
import CoreClasses.*;
import Scenery.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Isaac on 12/12/2016.
 */
public class Level1 implements GameLevel{
    private static final float backgroundWidth = 2.6f;
    private static final float backgroundHeight = 2.27f;
    private static final float backgroundPosX = (0.5f - backgroundWidth * 0.5f);
    private static final float backgroundPosY = (0.5f- backgroundHeight * 0.5f);


    private Music music;
    private BufferedImage background;
    private StaticScenery[] staticScenery = new StaticScenery[8];
    private StaticSceneryAlignedRectangle[] staticSceneryRectangles = new StaticSceneryAlignedRectangle[24];

    public Level1(Main main) throws java.io.IOException {
        loadMap();
        Collections.addAll(main.gameObjects, staticScenery);
        Collections.addAll(main.gameObjects, staticSceneryRectangles);

        if(main.currentMusic != null){
            main.currentMusic.stop();
            main.currentMusic.close();
            main.currentMusic = null;
            this.music = new Music(new File("data/music/level1"));
            this.music.start();
        }
    }

    private void loadMap() throws java.io.IOException{
        this.background = ImageIO.read(new File("data/graphics/map.png"));

        this.staticSceneryRectangles[0] = new GraveStoneHitBoxRect((0.209677f * backgroundWidth + backgroundPosX),
                (0.1481f * backgroundHeight + backgroundPosY),
                (0.29f * backgroundWidth + backgroundPosX),
                (0.1852f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[1] = new GraveStoneHitBoxRect((0.209677f * backgroundWidth + backgroundPosX),
                (0.222222222f * backgroundHeight + backgroundPosY),
                (0.29f * backgroundWidth + backgroundPosX),
                (0.2593f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[2] = new GraveStoneHitBoxRect((0.209677f * backgroundWidth + backgroundPosX),
                (0.4259f * backgroundHeight + backgroundPosY),
                (0.29f * backgroundWidth + backgroundPosX),
                (0.463f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[3] = new GraveStoneHitBoxRect((0.209677f * backgroundWidth + backgroundPosX),
                (0.5f * backgroundHeight + backgroundPosY),
                (0.29f * backgroundWidth + backgroundPosX),
                (0.537f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[4] = new GraveStoneHitBoxRect((0.209677f * backgroundWidth + backgroundPosX),
                (0.685f * backgroundHeight + backgroundPosY),
                (0.29f * backgroundWidth + backgroundPosX),
                (0.7222f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[5] = new GraveStoneHitBoxRect((0.209677f * backgroundWidth + backgroundPosX),
                (0.75926f * backgroundHeight + backgroundPosY),
                (0.29f * backgroundWidth + backgroundPosX),
                (0.79629f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[6] = new GraveStoneHitBoxRect((0.3387f * backgroundWidth + backgroundPosX),
                (0.1481f * backgroundHeight + backgroundPosY),
                (0.41935f * backgroundWidth + backgroundPosX),
                (0.1852f * backgroundHeight + backgroundPosY));


        this.staticSceneryRectangles[7] = new GraveStoneHitBoxRect((0.3387f * backgroundWidth + backgroundPosX),
                (0.22222f * backgroundHeight + backgroundPosY),
                (0.41935f * backgroundWidth + backgroundPosX),
                (0.25926f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[8] = new GraveStoneHitBoxRect((0.3387f * backgroundWidth + backgroundPosX),
                (0.2962963f * backgroundHeight + backgroundPosY),
                (0.41935f * backgroundWidth + backgroundPosX),
                (0.3333333f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[9] = new GraveStoneHitBoxRect((0.3387f * backgroundWidth + backgroundPosX),
                (0.37037037f * backgroundHeight + backgroundPosY),
                (0.41935f * backgroundWidth + backgroundPosX),
                (0.40740741f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[10] = new GraveStoneHitBoxRect((0.3387f * backgroundWidth + backgroundPosX),
                (0.53703704f * backgroundHeight + backgroundPosY),
                (0.41935f * backgroundWidth + backgroundPosX),
                (0.57407407f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[11] = new GraveStoneHitBoxRect((0.3387f * backgroundWidth + backgroundPosX),
                (0.61111111f * backgroundHeight + backgroundPosY),
                (0.41935f * backgroundWidth + backgroundPosX),
                (0.64814815f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[12] = new GraveStoneHitBoxRect((0.3387f * backgroundWidth + backgroundPosX),
                (0.68518519f * backgroundHeight + backgroundPosY),
                (0.41935f * backgroundWidth + backgroundPosX),
                (0.72222222f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[13] = new GraveStoneHitBoxRect((0.3387f * backgroundWidth + backgroundPosX),
                (0.7962963f * backgroundHeight + backgroundPosY),
                (0.41935f * backgroundWidth + backgroundPosX),
                (0.8333333f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[14] = new GraveStoneHitBoxRect((0.5f * backgroundWidth + backgroundPosX),
                (0.14814815f * backgroundHeight + backgroundPosY),
                (0.79032258f * backgroundWidth + backgroundPosX),
                (0.18518519f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[15] = new GraveStoneHitBoxRect((0.5f * backgroundWidth + backgroundPosX),
                (0.24074074f * backgroundHeight + backgroundPosY),
                (0.62903226f * backgroundWidth + backgroundPosX),
                (0.27777778f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[16] = new GraveStoneHitBoxRect((0.6612032f * backgroundWidth + backgroundPosX),
                (0.24074074f * backgroundHeight + backgroundPosY),
                (0.79032258f * backgroundWidth + backgroundPosX),
                (0.27777778f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[17] = new GraveStoneHitBoxRect((0.5f * backgroundWidth + backgroundPosX),
                (0.37037037f * backgroundHeight + backgroundPosY),
                (0.62903226f * backgroundWidth + backgroundPosX),
                (0.40740741f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[18] = new GraveStoneHitBoxRect((0.6612032f * backgroundWidth + backgroundPosX),
                (0.37037037f * backgroundHeight + backgroundPosY),
                (0.79032258f * backgroundWidth + backgroundPosX),
                (0.40740741f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[19] = new GraveStoneHitBoxRect((0.5f * backgroundWidth + backgroundPosX),
                (0.44444444f * backgroundHeight + backgroundPosY),
                (0.75925926f * backgroundWidth + backgroundPosX),
                (0.48148148f * backgroundHeight + backgroundPosY));

        //walls
        this.staticSceneryRectangles[20] = new GraveStoneHitBoxRect((-100.0f * backgroundWidth + backgroundPosX),
                (-100.0f * backgroundHeight + backgroundPosY),
                (0.18548387f * backgroundWidth + backgroundPosX),
                (100.0f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[21] = new GraveStoneHitBoxRect((0.81451613f * backgroundWidth + backgroundPosX),
                (-100.0f * backgroundHeight + backgroundPosY),
                (100.0f * backgroundWidth + backgroundPosX),
                (100.0f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[22] = new GraveStoneHitBoxRect((-100.0f * backgroundWidth + backgroundPosX),
                (-100.0f * backgroundHeight + backgroundPosY),
                (100.0f * backgroundWidth + backgroundPosX),
                (0.14814815f * backgroundHeight + backgroundPosY));

        this.staticSceneryRectangles[23] = new GraveStoneHitBoxRect((-100.0f * backgroundWidth + backgroundPosX),
                (0.85185185f * backgroundHeight + backgroundPosY),
                (100.0f * backgroundWidth + backgroundPosX),
                (100.0f * backgroundHeight + backgroundPosY));


        this.staticScenery[0] = new Tree1((0.19354839f * backgroundWidth + backgroundPosX),
                (0.24074074f * backgroundHeight + backgroundPosY));

        this.staticScenery[1] = new Tree1((0.35483871f * backgroundWidth + backgroundPosX),
                (0.7037037f * backgroundHeight + backgroundPosY));

        this.staticScenery[2] = new Tree1((0.69354839f * backgroundWidth + backgroundPosX),
                (0.74074074f * backgroundHeight + backgroundPosY));


        this.staticScenery[3] = new Tree2((0.246f * backgroundWidth + backgroundPosX),
                (0.333f * backgroundHeight + backgroundPosY));

        this.staticScenery[4] = new Tree2((0.359f * backgroundWidth + backgroundPosX),
                (0.407f * backgroundHeight + backgroundPosY));

        this.staticScenery[5] = new Tree2((0.214f * backgroundWidth + backgroundPosX),
                (0.504f * backgroundHeight + backgroundPosY));


        this.staticScenery[6] = new Tree4((0.750f * backgroundWidth + backgroundPosX),
                (0.599f * backgroundHeight + backgroundPosY));

        this.staticScenery[7] = new Tree4((0.61f * backgroundWidth + backgroundPosX),
                (0.688f * backgroundHeight + backgroundPosY));
    }

    @Override
    public void update(final Main main, final float frameTime) throws Exception {
        main.renderables.clear();
        for(int i = main.gameObjects.size() - 1; i >= 0; --i) {
            main.gameObjects.get(i).update(main, frameTime);
        }

        if (main.networkConnection != null) {
            main.networkConnection.update(main, frameTime);
        }

        //collisions
        CollisionSystem.update(main);
    }

    @Override
    public void render(Main main, Graphics2D graphics) {
        graphics.clearRect(0, 0, (int)main.settings.screenSizeX, (int)main.settings.screenSizeY);
        graphics.setTransform(main.viewMatrix);
        graphics.drawImage(background, (int)(backgroundPosX * main.settings.screenSizeX),
                (int)(backgroundPosY * main.settings.screenSizeX),
                (int)(backgroundWidth * main.settings.screenSizeX),
                (int)(backgroundHeight * main.settings.screenSizeX), null);

        main.renderables.sort((GameObject o1, GameObject o2) -> {
                if(o1.getBottomPosY() > o2.getBottomPosY()){
                    return 1;
                }
                else if (o1.getBottomPosY() == o2.getBottomPosY()){
                    return 0;
                }
                return -1;
            });

        for(GameObject renderable : main.renderables){
            renderable.render(main, graphics);
        }

        graphics.setTransform(main.uiViewMatrix);
        graphics.setColor(Color.red);
        Health playerHealth = main.player.getHealth();
        graphics.fillRect((int)(0.012f * main.settings.screenSizeX), (int)(main.settings.screenSizeY * 0.01826),
                (int)(playerHealth.health / playerHealth.maxHealth *
                main.settings.screenSizeX * 0.50), (int)(0.0185f * main.settings.screenSizeY));

        graphics.setColor(Color.white);
        graphics.drawString("Your Score: " + main.player.getAwesomeness(), main.settings.screenSizeX * 0.8f,
                main.settings.screenSizeY * 0.03826f);
    }

    @Override
    public void mousePressed(MouseEvent event, Main main) {
        main.player.mousePressed(main, event);
    }

    @Override
    public void mouseReleased(MouseEvent event, Main main) {
        main.player.mouseReleased(main, event);
    }

    @Override
    public void keyPressed(KeyEvent event, Main main) {
        switch(event.getKeyCode()){
            case KeyEvent.VK_TAB :
                main.gameLevel = new PauseMenu(main, this);
                break;
        }
        main.player.keyPressed(main, event);
    }

    @Override
    public void keyReleased(KeyEvent event, Main main) {
        main.player.keyReleased(main, event);
    }

    @Override
    public float findRandomLocationX(Main main) {
        return main.randomNumberGenerator.nextFloat() * backgroundWidth * 0.61290323f +
                backgroundWidth * 0.19354839f + backgroundPosX;
    }

    @Override
    public float findRandomLocationY(Main main) {
        return main.randomNumberGenerator.nextFloat() * backgroundHeight * 0.7037037f +
                backgroundHeight * 0.14814815f + backgroundPosY;
    }

    @Override
    public void save(DataOutputStream saveData) {
        try {
            saveData.writeInt(SaveType.Level1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy(Main main){
        main.soundFX.playerWalkingOnDirt.stop();
        main.gameObjects.removeAll(Arrays.asList(staticScenery));
        main.gameObjects.removeAll(Arrays.asList(staticSceneryRectangles));
        if(this.music != null){
            this.music.close();
        }
    }

    @Override
    public void stopMusic(Main main){
        music.close();
        music = null;
    }

    @Override
    public void startMusic(Main main){
        music = new Music(new File("data/music/level1"));
        music.start();
    }

    @Override
    public void windowClosing(WindowEvent e, Main main){
        if(main.networkConnection == null){
            main.gameLevel = new PauseMenu(main, this);
        }
        main.saveGame();
    }
}
