package game.util;

import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {
    public static boolean musicEnabled = true;
    public static boolean shootEnabled = true;
    public static boolean explosionEnabled = true;
    public static boolean gameOverEnabled = true;

    private static Clip bgMusic;

    public static void playMusic(String filePath) {
        if (!musicEnabled) return;
        try {
            if (bgMusic != null && bgMusic.isRunning()) bgMusic.stop();

            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.out.println("❌ ارور: فایل آهنگ پیدا نشد -> " + filePath);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile);
            bgMusic = AudioSystem.getClip();
            bgMusic.open(audioIn);
            bgMusic.loop(Clip.LOOP_CONTINUOUSLY); // پخش مداوم برای منو
        } catch (Exception e) {
            System.out.println("Warning: Cannot play music: " + filePath);
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if (bgMusic != null && bgMusic.isRunning()) {
            bgMusic.stop();
        }
    }

    public static void playSound(String filePath, String type) {
        if (type.equals("shoot") && !shootEnabled) return;
        if (type.equals("explosion") && !explosionEnabled) return;
        if (type.equals("gameover") && !gameOverEnabled) return;

        try {
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.out.println("❌ ارور: فایل افکت صوتی پیدا نشد -> " + filePath);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            // رفع باگ قطع شدن صدا: بستن کلیپ از حافظه به محض پایان یافتن صدا --> این بررسی بشه
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            clip.start();
        } catch (Exception e) {
            System.out.println("Warning: Cannot play sound effect: " + filePath);
            e.printStackTrace();
        }
    }
}