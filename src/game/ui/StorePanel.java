package game.ui;

import game.db.DatabaseManager;
import game.main.GameMain;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StorePanel extends JPanel {
    private GameMain gameMain;

    public StorePanel(GameMain gameMain) {
        this.gameMain = gameMain;
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
    }

    public void updateStore() {
        removeAll();
        if (gameMain.getCurrentUser() == null) return;

        int highestScore = gameMain.getCurrentUser().getHighestScore();

        JLabel title = new JLabel("STORE - Your Highest Score: " + highestScore, SwingConstants.CENTER);
        title.setForeground(Color.YELLOW);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel itemsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        itemsPanel.setBackground(Color.BLACK);
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        String[] names = {"Default", "Fast", "Heavy", "Sniper"};
        int[] costs = {0, 5000, 8000, 10000};

        for (int i = 0; i < 4; i++) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(Color.DARK_GRAY);

            JLabel nameLabel = new JLabel(names[i] + " (Cost: " + costs[i] + ")", SwingConstants.CENTER);
            nameLabel.setForeground(Color.WHITE);
            card.add(nameLabel, BorderLayout.NORTH);

            int planeId = i + 1;
            JButton btn = new JButton();
            if (highestScore >= costs[i]) {
                if (gameMain.getCurrentUser().getActivePlane() == planeId) {
                    btn.setText("Equipped");
                    btn.setEnabled(false);
                } else {
                    btn.setText("Select");
                    btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            gameMain.getCurrentUser().setActivePlane(planeId);
                            DatabaseManager.updateActivePlane(gameMain.getCurrentUser().getUsername(), planeId);
                            updateStore();
                        }
                    });
                }
            } else {
                btn.setText("Locked");
                btn.setEnabled(false);
            }
            card.add(btn, BorderLayout.SOUTH);
            itemsPanel.add(card);
        }

        add(itemsPanel, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back to Menu");
        backBtn.addActionListener(e -> gameMain.showPanel("MainMenu"));
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
}