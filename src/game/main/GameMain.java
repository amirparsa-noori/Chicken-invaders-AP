package game.main;

import game.ui.MainMenuPanel;
import javax.swing.*;
import java.awt.*;

public class GameMain extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public GameMain() {
        setTitle("Chicken Invaders");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(new MainMenuPanel(this), "MainMenu");
        add(mainPanel);
        cardLayout.show(mainPanel, "MainMenu");
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameMain().setVisible(true);
            }
        });
    }
}