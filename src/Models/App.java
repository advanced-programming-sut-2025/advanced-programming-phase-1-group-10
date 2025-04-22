package src.Models;

import java.util.ArrayList;

public class App {
    private final Menu currentMenu = Menu.LoginMenu;
    public static ArrayList<User> users;
    private ArrayList<Game> games;


    public static User getUserByUserName(String username){
        for(User user : App.users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public static void addUser(User user){
        users.add(user);
    }
}
