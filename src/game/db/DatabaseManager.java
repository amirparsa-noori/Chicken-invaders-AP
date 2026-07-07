package game.db;

import game.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:game.db";

    public static void initializeDatabase() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();

            // جدول کاربران
            String sqlUsers = "CREATE TABLE IF NOT EXISTS users (" +
                    "username TEXT PRIMARY KEY, password TEXT NOT NULL, " +
                    "highest_score INTEGER DEFAULT 0, last_level INTEGER DEFAULT 1, " +
                    "active_plane INTEGER DEFAULT 1, s_music BOOLEAN DEFAULT 1, " +
                    "s_shoot BOOLEAN DEFAULT 1, s_explode BOOLEAN DEFAULT 1, s_gameover BOOLEAN DEFAULT 1)";
            stmt.execute(sqlUsers);


            String sqlHistory = "CREATE TABLE IF NOT EXISTS game_history (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, " +
                    "score INTEGER, level INTEGER, play_date DATETIME DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(sqlHistory);

            stmt.close(); conn.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static boolean registerUser(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            String checkSql = "SELECT username FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            if (checkStmt.executeQuery().next()) { conn.close(); return false; }

            String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, username); insertStmt.setString(2, password);
            insertStmt.executeUpdate(); conn.close(); return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static User loginUser(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            pstmt.setString(1, username); pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("username"), rs.getInt("highest_score"),
                        rs.getInt("last_level"), rs.getInt("active_plane"),
                        rs.getBoolean("s_music"), rs.getBoolean("s_shoot"),
                        rs.getBoolean("s_explode"), rs.getBoolean("s_gameover"));
                conn.close(); return user;
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }


    public static void saveGameRecord(String username, int score, int level) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO game_history (username, score, level) VALUES (?, ?, ?)");
            pstmt.setString(1, username);
            pstmt.setInt(2, score);
            pstmt.setInt(3, level);
            pstmt.executeUpdate();


            PreparedStatement getHigh = conn.prepareStatement("SELECT highest_score FROM users WHERE username = ?");
            getHigh.setString(1, username);
            if (getHigh.executeQuery().getInt("highest_score") < score) {
                PreparedStatement updateHigh = conn.prepareStatement("UPDATE users SET highest_score = ?, last_level = ? WHERE username = ?");
                updateHigh.setInt(1, score); updateHigh.setInt(2, level); updateHigh.setString(3, username);
                updateHigh.executeUpdate();
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void updateActivePlane(String username, int planeId) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET active_plane = ? WHERE username = ?");
            pstmt.setInt(1, planeId); pstmt.setString(2, username);
            pstmt.executeUpdate(); conn.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void updateSettings(String username, boolean m, boolean s, boolean e, boolean go) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET s_music=?, s_shoot=?, s_explode=?, s_gameover=? WHERE username=?");
            pstmt.setBoolean(1, m); pstmt.setBoolean(2, s); pstmt.setBoolean(3, e); pstmt.setBoolean(4, go);
            pstmt.setString(5, username);
            pstmt.executeUpdate(); conn.close();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    public static ArrayList<String> getTopScores() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            ResultSet rs = conn.createStatement().executeQuery("SELECT username, MAX(score) as max_score, play_date FROM game_history GROUP BY username ORDER BY max_score DESC LIMIT 10");
            int rank = 1;
            while(rs.next()) {
                list.add(rank + ". " + rs.getString("username") + " | Score: " + rs.getInt("max_score") + " | Date: " + rs.getString("play_date"));
                rank++;
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}