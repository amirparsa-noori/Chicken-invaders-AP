package game.main;

import game.db.DatabaseManager;
import game.model.User;
import game.ui.*;
import game.util.SoundManager;
import javax.swing.*;
import java.awt.*;

public class GameMain extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private User currentUser;

    private GamePanel gamePanel;
    private StorePanel storePanel;
    private SettingsPanel settingsPanel;
    private HighScorePanel highScorePanel;

    public GameMain() {
        setTitle("Chicken Invaders");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        gamePanel = new GamePanel(this);
        storePanel = new StorePanel(this);
        settingsPanel = new SettingsPanel(this);
        highScorePanel = new HighScorePanel(this);

        mainPanel.add(new MainMenuPanel(this), "MainMenu");
        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new HowToPlayPanel(this), "HowToPlay");
        mainPanel.add(gamePanel, "Game");
        mainPanel.add(storePanel, "Store");
        mainPanel.add(settingsPanel, "Settings");
        mainPanel.add(highScorePanel, "HighScores");

        add(mainPanel);
        cardLayout.show(mainPanel, "MainMenu");

        SoundManager.playMusic("assets/sound-effects/Chicken Invaders 2 Remastered OST - Main Theme.wav");
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
        if (panelName.equals("Game")) {
            SoundManager.stopMusic();
            gamePanel.startGame();
        } else if (panelName.equals("Store")) {
            storePanel.updateStore();
        } else if (panelName.equals("HighScores")) {
            highScorePanel.loadScores();
        } else if (panelName.equals("Settings")) {
            settingsPanel.loadUserSettings();
        } else if (panelName.equals("MainMenu")) {
            SoundManager.playMusic("assets/sound-effects/Chicken Invaders 2 Remastered OST - Main Theme.wav");
        }
    }

    public void setCurrentUser(User user) { this.currentUser = user; }
    public User getCurrentUser() { return currentUser; }

    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();
        SwingUtilities.invokeLater(() -> new GameMain().setVisible(true));
    }
}