package src.Models;

import src.Models.PlayerStuff.Gender;

import java.util.ArrayList;

public class User {
    private String name;
    private String nickname;
    private String password;
    private String username;
    private Gender gender;

    private ArrayList<String> questions = new ArrayList<>();

    public User(String name, String nickname, String password, String username, Gender gender, ArrayList<String> questions) {
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.username = username;
        this.gender = gender;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }
}
