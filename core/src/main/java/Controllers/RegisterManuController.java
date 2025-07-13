package Controllers;


import Models.*;
import Models.Commands.RegisterMenuCommands;
import Models.PlayerStuff.Gender;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RegisterManuController {

    public Result register(String username, String password, String confirmPassword,
                           String nickname, String email, String gender){

        if (App.getInstance().getUserByUserName(username) != null) {
            ArrayList<String> suggestions = suggestAlternativeUsernames(username);
            StringBuilder message = new StringBuilder("username already taken! Here are some suggestions:");

            for (String suggestion : suggestions) {
                message.append("\n- ").append(suggestion);
            }

            message.append("\nPlease choose one of these or try another username.");
            return new Result(false, message.toString());
        }

        if(!checkUsername(username)){
            return new Result(false, "username format is invalid!");
        }

        if(!checkEmail(email)){
            return new Result(false, "email format is invalid!");
        }

        if(!checkEmailTaken(email)){
            return new Result(false, "email already taken!");
        }

        if(password.equalsIgnoreCase("random")){
            password = generateStrongPassword(12);
            return new Result(false, "Generated random password: " + password);

        }

        StringBuilder passwordResult = checkStrengthPassword(password);
        if(!passwordResult.isEmpty()){
            return new Result(false, passwordResult.toString());
        }

        if(!password.contains(confirmPassword)){
            return new Result(false, "confirm password doesn't match password!");
        }

        Gender usergender = Gender.Male;
        if(gender.equalsIgnoreCase("female")){
            usergender = Gender.Female;
        }

        User newuser = new User(nickname,password,username,usergender);
        App.getInstance().addUser(newuser);
        newuser.setEmail(email);
        App.getInstance().setCurrentUser(newuser);
        SaveData.saveUsersToFile(App.getInstance().getUsers());
        return new Result(true, "user registered successfully!");
    }

    public ArrayList<String> suggestAlternativeUsernames(String username) {
        ArrayList<String> suggestions = new ArrayList<>();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 3; i++) {
            int randomNumber = 1 + random.nextInt(99);
            String suggestion = username + randomNumber;
            if (App.getInstance().getUserByUserName(suggestion) == null) {
                suggestions.add(suggestion);
            }
        }

        for (int i = 0; i < 3; i++) {
            int randomNumber = 1 + random.nextInt(99);
            String suggestion = username + "-" + randomNumber;
            if (App.getInstance().getUserByUserName(suggestion) == null) {
                suggestions.add(suggestion);
            }
        }

        return suggestions;
    }

    private boolean checkUsername(String username){
        return username.matches(RegisterMenuCommands.USERNAME.getPattern());
    }

    private boolean checkEmail(String email){
        return email.matches(RegisterMenuCommands.EMAIL.getPattern());
    }

    private boolean checkEmailTaken(String email){
        for (User user : App.getInstance().getUsers()){
            if(user.getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }

    private StringBuilder checkStrengthPassword(String password){
        boolean correctLength = password.length() > 8;

        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacters = false;
        String specialCharacters  = "!#$%^&*()=+{}[]|\\:;'\"<>?";

        for(Character character : password.toCharArray()) {
            if (Character.isLowerCase(character)) hasLowercase = true;
            else if (Character.isUpperCase(character)) hasUppercase = true;
            else if (Character.isDigit(character)) hasDigit = true;
            else if (specialCharacters.contains(String.valueOf(character))) hasSpecialCharacters = true;
        }

        StringBuilder result = new StringBuilder();
        if(!correctLength || !hasLowercase || !hasUppercase || !hasDigit || !hasSpecialCharacters){
            result.append("password is weak!");


            if(!correctLength){
                result.append("\n");
                result.append("password is too short!");
            }

            if(!hasLowercase){
                result.append("\npassword doesn't have lower case!");
            }

            if(!hasUppercase){
                result.append("\npassword doesn't have upper case!");
            }

            if(!hasDigit){
                result.append("\npassword doesn't have digit!");
            }

            if(!hasSpecialCharacters){
                result.append("\npassword doesn't have special characters!");
            }
        }
        return result;
    }

    public static String generateStrongPassword(int length) {
        if (length < 8) {
            length = 8;
        }

        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialCharacters = "?><,\"';:/|\\[]{}+=)(*&^%$#!";

        String allCharacters = upperCaseLetters + lowerCaseLetters + numbers + specialCharacters;

        SecureRandom random = new SecureRandom();
        char[] password = new char[length];

        password[0] = upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length()));
        password[1] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[2] = numbers.charAt(random.nextInt(numbers.length()));
        password[3] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));

        for (int i = 4; i < length; i++) {
            password[i] = allCharacters.charAt(random.nextInt(allCharacters.length()));
        }

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(length);
            char temp = password[i];
            password[i] = password[randomIndex];
            password[randomIndex] = temp;
        }

        return new String(password);
    }

    public String showSecurityQuestions(){
        StringBuilder questions = new StringBuilder();

        questions.append("List of security questions: (you can choose questions with **PickQuestion** command)\n");
        for(int i = 1; i < 11; i++){
            String temp = i + ". " + SecurityQuestions.getQuestionByNumber(i) + "\n";
            questions.append(temp);
        }
        return questions.toString();
    }

    public Result pickQuestion(int number, String answer){
        String question = SecurityQuestions.getQuestionByNumber(number);
        Map<String, String> questionWithAnswer = new HashMap<>();
        questionWithAnswer.put(question,answer);
        Map<Integer,Map<String, String>> userQuestion = new HashMap<>();
        userQuestion.put(number,questionWithAnswer);
        App.getInstance().getCurrentUser().setPickQuestion(userQuestion);
        App.getInstance().getCurrentUser().setPickQuestionNumber(number);
        SaveData.saveUsersToFile(App.getInstance().getUsers());
        return new Result(true, "you chose question number " + number + " with answer: " + answer + ".");
    }

    public Result login(String username, String password, String stayLoggedInStr){
        if(App.getInstance().getUserByUserName(username) == null){
            return new Result(false, "no user with this username exist!");
        }
        User user = App.getInstance().getUserByUserName(username);

        if(!user.getPassword().equals(password)){
            return new Result(false, "the password is incorrect!");
        }

        boolean stayLoggedIn = !stayLoggedInStr.isEmpty();
        user.setStayLoggedIn(stayLoggedIn);
        App.getInstance().setCurrentUser(user);
        SaveData.saveUsersToFile(App.getInstance().getUsers());
        return new Result(true, "the user with username " + "(" + username + ") " + "logged in successfully!");
    }

    public Result forgotPassword(String username){

        if(App.getInstance().getUserByUserName(username) == null){
            return new Result(false, "no user with this username exist!");
        }

        return new Result(true, "please answer the security question :");
    }

    public Result answerSecurityQuestion(String answer){

        int number = App.getInstance().getCurrentUser().getPickQuestionNumber();
        Map<String, String> questionWithAnswer = App.getInstance().getCurrentUser().getAnswerAndQuestionWithNumber(number);
        String question = SecurityQuestions.getQuestionByNumber(number);
        String correctAnswer = questionWithAnswer.get(question);

        if(!answer.equals(correctAnswer)){
            return new Result(false, "your answer doesn't match the correct answer!");
        }
        return new Result(true, "Do you want randomPassword or no ?");
    }

    public Result changePassword(String newPassword, boolean isRandom){
        if(isRandom){
            App.getInstance().getCurrentUser().setPassword(newPassword);
            return new Result(true, newPassword);
        }
        else {
            StringBuilder passwordResult = checkStrengthPassword(newPassword);
            if(!passwordResult.isEmpty()){
                return new Result(false, passwordResult.toString());
            }
            else{
                App.getInstance().getCurrentUser().setPassword(newPassword);
                return new Result(true, newPassword);
            }
        }
    }

    public Result enterMenu(String menuName){
        if(menuName.equalsIgnoreCase("main menu")){
            App.getInstance().setCurrentMenu(Menu.MainMenu);
            return new Result(true, "you are now in MAIN MENU.");
        }
        else
            return new Result(false, "you should go to MAIN MENU first.");
    }

    public String exitGame(){
        App.getInstance().setCurrentMenu(Menu.ExitMenu);
        return "Exiting game ...";
    }
}
