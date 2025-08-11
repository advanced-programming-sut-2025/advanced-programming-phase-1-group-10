package Common.Models;

import Common.Models.PlayerStuff.Gender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaveData {
    private static final String USERS_FILE_PATH = "users.json";
    private static final String DB_FILE_PATH = "users.db";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + DB_FILE_PATH);
    }

    public static void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nickname TEXT,
                password TEXT,
                hashPassword TEXT,
                username TEXT,
                gender TEXT,
                email TEXT,
                pickQuestionNumber INTEGER,
                pickQuestionJson TEXT,
                stayLoggedIn INTEGER,
                gold INTEGER,
                games INTEGER,
                avatarPath TEXT
            )
        """;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    // ====== JSON ======
    public static ArrayList<User> loadUsersFromFile() {
        try {
            if (!Files.exists(Paths.get(USERS_FILE_PATH))) {
                return new ArrayList<>();
            }
            String json = new String(Files.readAllBytes(Paths.get(USERS_FILE_PATH)));
            var type = new TypeToken<ArrayList<User>>() {}.getType();
            ArrayList<User> users = gson.fromJson(json, type);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void saveUsersToFile(List<User> users) {
        try {
            Files.write(Paths.get(USERS_FILE_PATH), gson.toJson(users).getBytes());
            saveUsersToDatabase(users);
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
        }
    }

    public static void ensureFileExists() {
        try {
            File file = new File(USERS_FILE_PATH);
            if (file.createNewFile()) {
                saveUsersToFile(new ArrayList<>());
            }
        } catch (IOException e) {
            System.err.println("Error creating JSON file: " + e.getMessage());
        }

        createTableIfNotExists();

        if (isDatabaseEmpty()) {
            ArrayList<User> jsonUsers = loadUsersFromFile();
            if (!jsonUsers.isEmpty()) {
                saveUsersToDatabase(jsonUsers);
            }
        }
    }

    // ====== SQLite ======
    private static boolean isDatabaseEmpty() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users")) {
            return rs.getInt(1) == 0;
        } catch (SQLException e) {
            return true;
        }
    }

    public static void saveUsersToDatabase(List<User> users) {
        String deleteSQL = "DELETE FROM users";
        String insertSQL = """
            INSERT INTO users(
                nickname, password, hashPassword, username, gender, email,
                pickQuestionNumber, pickQuestionJson, stayLoggedIn, gold, games, avatarPath
            ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            conn.setAutoCommit(false);
            stmt.execute(deleteSQL);

            for (User u : users) {
                pstmt.setString(1, u.getNickname());
                pstmt.setString(2, u.getPassword());
                pstmt.setString(3, u.getHashPassword());
                pstmt.setString(4, u.getUsername());
                pstmt.setString(5, u.getGender() != null ? u.getGender().name() : null);
                pstmt.setString(6, u.getEmail());
                pstmt.setInt(7, u.getPickQuestionNumber());
                pstmt.setString(8, gson.toJson(u.getPickQuestion()));
                pstmt.setInt(9, u.isStayLoggedIn() ? 1 : 0);
                pstmt.setInt(10, u.gold);
                pstmt.setInt(11, u.games);
                pstmt.setString(12, u.getAvatarPath());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit();

        } catch (SQLException e) {
            System.err.println("Error saving to database: " + e.getMessage());
        }
    }

    public static ArrayList<User> loadUsersFromDatabase() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User u = new User(
                    rs.getString("nickname"),
                    rs.getString("password"),
                    rs.getString("username"),
                    rs.getString("gender") != null ? Gender.valueOf(rs.getString("gender")) : null
                );
                u.setHashPassword(rs.getString("hashPassword"));
                u.setEmail(rs.getString("email"));
                u.setPickQuestionNumber(rs.getInt("pickQuestionNumber"));
                u.setPickQuestion(gson.fromJson(rs.getString("pickQuestionJson"),
                    new TypeToken<java.util.Map<Integer, java.util.Map<String, String>>>(){}.getType()));
                u.setStayLoggedIn(rs.getInt("stayLoggedIn") == 1);
                u.gold = rs.getInt("gold");
                u.games = rs.getInt("games");
                u.setAvatarPath(rs.getString("avatarPath"));
                users.add(u);
            }

        } catch (SQLException e) {
            System.err.println("Error loading from database: " + e.getMessage());
        }
        return users;
    }
}
