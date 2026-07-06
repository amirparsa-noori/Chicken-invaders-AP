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
    public static Image boss1;
    public static Image boss2;

    // پاورآپ‌ها
    public static Image powerAddFire;
    public static Image powerRapidFire;
    public static Image powerFreeze;
    public static Image powerExtraLife;
    public static Image powerShield;

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

            boss1 = ImageIO.read(new File("assets/chicken/boss1.png"));
            boss2 = ImageIO.read(new File("assets/chicken/boss2.png"));

            powerAddFire = ImageIO.read(new File("assets/powerup1/add_shot.png"));
            powerRapidFire = ImageIO.read(new File("assets/powerup1/fast_shot.png"));
            powerFreeze = ImageIO.read(new File("assets/powerup1/freeze.png"));
            powerExtraLife = ImageIO.read(new File("assets/powerup1/heal.png"));
            powerShield = ImageIO.read(new File("assets/powerup1/sheild.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}