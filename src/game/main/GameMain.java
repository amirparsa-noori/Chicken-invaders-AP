package game.main;

import game.db.DatabaseManager;
import game.model.User;
import game.ui.GamePanel;
import game.ui.LoginPanel;
import game.ui.MainMenuPanel;
import javax.swing.*;
import java.awt.*;

public class GameMain extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private User currentUser;
    private GamePanel gamePanel;

    public GameMain() {
        setTitle("Chicken Invaders");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        gamePanel = new GamePanel(this);
        mainPanel.add(new MainMenuPanel(this), "MainMenu");
        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(gamePanel, "Game");
        add(mainPanel);
        cardLayout.show(mainPanel, "MainMenu");
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
        if (panelName.equals("Game")) {
            gamePanel.startGame();
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameMain().setVisible(true);
            }
        });
    }
}