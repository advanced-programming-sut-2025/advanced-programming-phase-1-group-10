package Models;

import Models.PlayerStuff.Gender;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String nickname;
    private String password;
    private String hashPassword;
    private String username;
    private Gender gender;
    private String email;
    private int pickQuestionNumber;
    private Map<Integer, Map<String, String>> pickQuestion ;
    private boolean stayLoggedIn = false;
    public int gold;
    public int games ;

    public User(String nickname, String password, String username, Gender gender) {
        this.password = password;
        this.hashPassword =generateSaltAndHashPassword(password);
        this.nickname = nickname;
        this.username = username;
        this.gender = gender;
        this.pickQuestion = new HashMap<>();
    }

    private String generateSaltAndHashPassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String saltString = Base64.getEncoder().encodeToString(salt);

        String hashedPassword = hashPassword(password, saltString);
        return saltString + ":" + hashedPassword;
    }

    private String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedPassword = password + salt;
            byte[] hash = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean authenticate(String password) {
        String salt = this.hashPassword.split(":")[0];
        String hashedPassword = hashPassword(password, salt);
        return this.hashPassword.split(":")[1].equals(hashedPassword);
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
