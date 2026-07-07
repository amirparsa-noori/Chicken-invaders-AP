package game.ui;

import game.db.DatabaseManager;
import game.main.GameMain;
import game.util.SoundManager;
import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private GameMain gameMain;
    private JCheckBox cbMusic, cbShoot, cbExplode, cbGameOver;

    public SettingsPanel(GameMain gameMain) {
        this.gameMain = gameMain;
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("SETTINGS", SwingConstants.CENTER);
        title.setForeground(Color.YELLOW);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        gbc.gridy = 0;
        add(title, gbc);

        cbMusic = createCheckBox("Background Music", SoundManager.musicEnabled);
        cbShoot = createCheckBox("Shoot Sound", SoundManager.shootEnabled);
        cbExplode = createCheckBox("Explosion Sound", SoundManager.explosionEnabled);
        cbGameOver = createCheckBox("Game Over / Win Sound", SoundManager.gameOverEnabled);

        gbc.gridy = 1; add(cbMusic, gbc);
        gbc.gridy = 2; add(cbShoot, gbc);
        gbc.gridy = 3; add(cbExplode, gbc);
        gbc.gridy = 4; add(cbGameOver, gbc);

        JButton saveBtn = new JButton("Save & Back");
        saveBtn.setFont(new Font("Arial", Font.BOLD, 18));
        saveBtn.addActionListener(e -> {
            SoundManager.musicEnabled = cbMusic.isSelected();
            SoundManager.shootEnabled = cbShoot.isSelected();
            SoundManager.explosionEnabled = cbExplode.isSelected();
            SoundManager.gameOverEnabled = cbGameOver.isSelected();

            if (!SoundManager.musicEnabled) SoundManager.stopMusic();
            else SoundManager.playMusic("assets/sound-effects/Chicken Invaders 2 Remastered OST - Main Theme.wav");

            if (gameMain.getCurrentUser() != null) {
                // آپدیت متغیرهای یوزر روی حافظه
                gameMain.getCurrentUser().sMusic = cbMusic.isSelected();
                gameMain.getCurrentUser().sShoot = cbShoot.isSelected();
                gameMain.getCurrentUser().sExplode = cbExplode.isSelected();
                gameMain.getCurrentUser().sGameOver = cbGameOver.isSelected();

                // ذخیره در دیتابیس
                DatabaseManager.updateSettings(gameMain.getCurrentUser().getUsername(),
                        cbMusic.isSelected(), cbShoot.isSelected(), cbExplode.isSelected(), cbGameOver.isSelected());
            }
            gameMain.showPanel("MainMenu");
        });
        gbc.gridy = 5; add(saveBtn, gbc);
    }

    private JCheckBox createCheckBox(String text, boolean selected) {
        JCheckBox cb = new JCheckBox(text, selected);
        cb.setForeground(Color.WHITE);
        cb.setBackground(Color.BLACK);
        cb.setFont(new Font("Arial", Font.PLAIN, 18));
        return cb;
    }

    public void loadUserSettings() {
        if (gameMain.getCurrentUser() != null) {
            SoundManager.musicEnabled = gameMain.getCurrentUser().sMusic;
            SoundManager.shootEnabled = gameMain.getCurrentUser().sShoot;
            SoundManager.explosionEnabled = gameMain.getCurrentUser().sExplode;
            SoundManager.gameOverEnabled = gameMain.getCurrentUser().sGameOver;
        }

        cbMusic.setSelected(SoundManager.musicEnabled);
        cbShoot.setSelected(SoundManager.shootEnabled);
        cbExplode.setSelected(SoundManager.explosionEnabled);
        cbGameOver.setSelected(SoundManager.gameOverEnabled);
    }
}