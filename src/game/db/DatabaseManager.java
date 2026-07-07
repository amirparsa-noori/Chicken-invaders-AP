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
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "username TEXT PRIMARY KEY, " +
                    "password TEXT NOT NULL, " +
                    "highest_score INTEGER DEFAULT 0, " +
                    "last_level INTEGER DEFAULT 1, " +
                    "active_plane INTEGER DEFAULT 1, " +
                    "s_music BOOLEAN DEFAULT 1, " +
                    "s_shoot BOOLEAN DEFAULT 1, " +
                    "s_explode BOOLEAN DEFAULT 1, " +
                    "s_gameover BOOLEAN DEFAULT 1)";
            stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean registerUser(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            String checkSql = "SELECT username FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                rs.close(); checkStmt.close(); conn.close();
                return false;
            }
            String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.executeUpdate();
            insertStmt.close(); conn.close();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static User loginUser(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getString("username"), rs.getInt("highest_score"),
                        rs.getInt("last_level"), rs.getInt("active_plane"),
                        rs.getBoolean("s_music"), rs.getBoolean("s_shoot"),
                        rs.getBoolean("s_explode"), rs.getBoolean("s_gameover")
                );
                rs.close(); pstmt.close(); conn.close();
                return user;
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public static void updateScore(String username, int newScore) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            String sql = "UPDATE users SET highest_score = ? WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newScore); pstmt.setString(2, username);
            pstmt.executeUpdate(); pstmt.close(); conn.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void updateActivePlane(String username, int planeId) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            String sql = "UPDATE users SET active_plane = ? WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, planeId); pstmt.setString(2, username);
            pstmt.executeUpdate(); pstmt.close(); conn.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void updateSettings(String username, boolean m, boolean s, boolean e, boolean go) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            String sql = "UPDATE users SET s_music=?, s_shoot=?, s_explode=?, s_gameover=? WHERE username=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, m); pstmt.setBoolean(2, s); pstmt.setBoolean(3, e); pstmt.setBoolean(4, go);
            pstmt.setString(5, username);
            pstmt.executeUpdate(); pstmt.close(); conn.close();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    public static ArrayList<String> getTopScores() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username, highest_score FROM users ORDER BY highest_score DESC LIMIT 10");
            int rank = 1;
            while(rs.next()) {
                list.add(rank + ". " + rs.getString("username") + " - Score: " + rs.getInt("highest_score"));
                rank++;
            }
            rs.close(); stmt.close(); conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}