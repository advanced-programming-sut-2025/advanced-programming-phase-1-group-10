package src.Models;

import java.util.ArrayList;

public class App {
    private static final Menu currentMenu = Menu.LoginMenu;
    public static ArrayList<User> users = new ArrayList<>();
    private ArrayList<Game> games;
    private static User currentUser = null;


    public static User getUserByUserName(String username){
        for(User user : App.users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }

    public static void addUser(User user){
        users.add(user);
    }
}
