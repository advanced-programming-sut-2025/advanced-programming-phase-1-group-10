package Models;

import Models.PlayerStuff.Player;

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

    private Menu currentMenu = Menu.LoginMenu;
    private final ArrayList<User> users;
    private final ArrayList<Game> games;

    private User currentUser;
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

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
}

