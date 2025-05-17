package Models;

import Models.PlayerStuff.Gender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String nickname;
    private String password;
    private String username;
    private Gender gender;
    private String email;
    private int pickQuestionNumber;
    private Map<Integer, Map<String, String>> pickQuestion ;
    private boolean stayLoggedIn = false;
    public int gold;
    public int games ;

    public User(String nickname, String password, String username, Gender gender) {
        this.nickname = nickname;
        this.password = password;
        this.username = username;
        this.gender = gender;
        this.pickQuestion = new HashMap<>();
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }

    public int getPickQuestionNumber() {
        return pickQuestionNumber;
    }

    public void setPickQuestionNumber(int pickQuestionNumber) {
        this.pickQuestionNumber = pickQuestionNumber;
    }

    public Map<String, String> getAnswerAndQuestionWithNumber(int number){
        return pickQuestion.get(number);
    }

    public Map<Integer, Map<String, String>> getPickQuestion() {
        return pickQuestion;
    }

    public void setPickQuestion(Map<Integer, Map<String, String>> pickQuestion) {
        this.pickQuestion = pickQuestion;
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
}
