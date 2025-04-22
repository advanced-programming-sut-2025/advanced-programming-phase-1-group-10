package src.Models;

import src.Models.PlayerStuff.Gender;

import java.util.ArrayList;

public class User {
    private String nickname;
    private String password;
    private String username;
    private Gender gender;
    private String email;



    private ArrayList<String> questions = new ArrayList<>();

    public User(String nickname, String password, String username, Gender gender) {
        this.nickname = nickname;
        this.password = password;
        this.username = username;
        this.gender = gender;
        this.questions = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
