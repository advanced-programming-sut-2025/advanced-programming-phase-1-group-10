package src.Models;

import java.util.ArrayList;

public class App {

    private static App instance;

    private App() {
        games = new ArrayList<>();
        users = new ArrayList<>();
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    private final Menu currentMenu = Menu.LoginMenu;
    private ArrayList<User> users;
    private ArrayList<Game> games;
    private Game currentGame;

    public User getUserByUserName(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void addGame(Game game) {
        games.add(game);
    }
}
