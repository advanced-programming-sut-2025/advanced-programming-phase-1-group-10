package src.Models;

import java.util.ArrayList;

public class User {
    private String name;
    private String nickname;
    private String password;
    private String username;
    private App.Gender gender;

    private ArrayList<String> questions = new ArrayList<>();

    public User(String name, String nickname, String password, String username, App.Gender gender, ArrayList<String> questions) {
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.username = username;
        this.gender = gender;
        this.questions = questions;
    }


}
