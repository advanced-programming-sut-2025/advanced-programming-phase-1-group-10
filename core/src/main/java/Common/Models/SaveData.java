package Common.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SaveData {
    private static final String USERS_FILE_PATH = "users.json";

    public static ArrayList<User> loadUsersFromFile() {
        Gson gson = new Gson();
        Path path = Paths.get(USERS_FILE_PATH);

        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try {
            String json = new String(Files.readAllBytes(path));
            TypeToken<ArrayList<User>> typeToken = new TypeToken<ArrayList<User>>() {
            };
            ArrayList<User> users = gson.fromJson(json, typeToken.getType());
            if (users == null)
                return new ArrayList<>();
            return users;
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void saveUsersToFile(List<User> users) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(users);

        try {
            Files.write(Paths.get(USERS_FILE_PATH), json.getBytes());
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
        }
    }

    public static void ensureFileExists() {
        File file = new File(USERS_FILE_PATH);
        try {
            if (file.createNewFile()) {
                saveUsersToFile(new ArrayList<>());
            } else {
                System.out.print("");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e.getMessage());
        }
    }
}
