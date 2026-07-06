package game.util;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;

public class AssetManager {
    public static Image background;
    public static Image[] planes = new Image[7];
    public static Image bullet;
    public static Image explosion;
    public static Image normalChicken;
    public static Image fastChicken;
    public static Image zigzagChicken;
    public static Image shooterChicken;
    public static Image egg;

    static {
        try {
            background = ImageIO.read(new File("assets/background/background.jpg"));
            for (int i = 0; i < 7; i++) {
                planes[i] = ImageIO.read(new File("assets/airplan/" + (i + 1) + ".png"));
            }
            bullet = ImageIO.read(new File("assets/airplan/shot.png"));
            explosion = ImageIO.read(new File("assets/airplan/Explosion.png"));
            normalChicken = ImageIO.read(new File("assets/chicken/normal_chicken.png"));
            fastChicken = ImageIO.read(new File("assets/chicken/fast_chicken.png"));
            zigzagChicken = ImageIO.read(new File("assets/chicken/zigzag_chicken.png"));
            shooterChicken = ImageIO.read(new File("assets/chicken/shooter_chicken.png"));
            egg = ImageIO.read(new File("assets/chicken/egg.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}