package game.ui;

import game.db.DatabaseManager;
import game.main.GameMain;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HighScorePanel extends JPanel {
    private GameMain gameMain;
    private DefaultListModel<String> listModel;

    public HighScorePanel(GameMain gameMain) {
        this.gameMain = gameMain;
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("HIGH SCORES", SwingConstants.CENTER);
        title.setForeground(Color.YELLOW);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        JList<String> scoreList = new JList<>(listModel);
        scoreList.setBackground(Color.DARK_GRAY);
        scoreList.setForeground(Color.WHITE);
        scoreList.setFont(new Font("Arial", Font.BOLD, 20));
        scoreList.setFixedCellHeight(40);
        ((DefaultListCellRenderer)scoreList.getCellRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane(scoreList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        scrollPane.getViewport().setBackground(Color.BLACK);
        add(scrollPane, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back to Menu");
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.addActionListener(e -> gameMain.showPanel("MainMenu"));
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void loadScores() {
        listModel.clear();
        ArrayList<String> scores = DatabaseManager.getTopScores();
        for (String s : scores) {
            listModel.addElement(s);
        }
    }
}