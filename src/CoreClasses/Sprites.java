package CoreClasses;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Isaac on 1/02/2017.
 */
public class Sprites {
    public BufferedImage[] fireMage, necromancer, skeleton;
    public BufferedImage fireBall, explosion;
    public BufferedImage tree1, tree2, tree4;

    public Sprites() throws IOException{
        loadFireBoltImages();
        loadTrees();
        loadSkeletonImages();
        loadFireMageImages();
        loadNecromancerImages();
    }

    private void loadSkeletonImages() throws IOException{
        skeleton = new BufferedImage[36];
        BufferedImage imageSheet = ImageIO.read(new File("data/graphics/skeletonspritesheet.png"));

        for(int i = 0; i < 9; i++) {
            skeleton[i] = imageSheet.getSubimage((i*64), 512, 64, 64);
            skeleton[i + 9] = imageSheet.getSubimage((i*64), 576, 64, 64);
            skeleton[i + 18] = imageSheet.getSubimage((i*64), 640, 64, 64);
            skeleton[i + 27] = imageSheet.getSubimage((i*64), 704, 64, 64);
        }
    }

    private void loadFireMageImages() throws IOException {
        fireMage = new BufferedImage[36];
        BufferedImage imageSheet = ImageIO.read(new File("data/graphics/wizard_hat_mage.png"));
        for(int i = 0; i < 9; i++) {
            fireMage[i] = imageSheet.getSubimage((i*64), 512, 64, 64);
            fireMage[i + 9] = imageSheet.getSubimage((i*64), 576, 64, 64);
            fireMage[i + 18] = imageSheet.getSubimage((i*64), 640, 64, 64);
            fireMage[i + 27] = imageSheet.getSubimage((i*64), 704, 64, 64);
        }
    }

    private void loadNecromancerImages() throws IOException {
        necromancer = new BufferedImage[36];
        BufferedImage imageSheet = ImageIO.read(new File("data/graphics/wizard_hat_necro.png"));
        for(int i = 0; i < 9; i++) {
            necromancer[i] = imageSheet.getSubimage((i*64), 512, 64, 64);
            necromancer[i + 9] = imageSheet.getSubimage((i*64), 576, 64, 64);
            necromancer[i + 18] = imageSheet.getSubimage((i*64), 640, 64, 64);
            necromancer[i + 27] = imageSheet.getSubimage((i*64), 704, 64, 64);
        }
    }

    private void loadFireBoltImages() throws IOException{
        fireBall = ImageIO.read(new File("data/graphics/sun2.bmp"));
        BufferedImage explosionSheet = ImageIO.read(new File("data/graphics/exp2_0.png"));
        explosion = explosionSheet.getSubimage(0, 0, 64, 64);
    }

    private void loadTrees() throws IOException{
        BufferedImage terranAtlas = ImageIO.read(new File("data/graphics/terrain_atlas.png"));
        tree1 = terranAtlas.getSubimage(928,896, 96, 128);
        tree2 = terranAtlas.getSubimage(864,928, 64, 96);

        tree4 = terranAtlas.getSubimage(960,0, 64, 160);
    }
}
