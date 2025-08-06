package Client.Controllers;

import Common.Models.*;
import Common.Models.Commands.ProfileMenuCommands;

import java.security.SecureRandom;
import java.util.ArrayList;

public class ProfileMenuControllers {

    public Result changeUsername(String username){

        if(!username.matches(ProfileMenuCommands.USERNAME.getPattern())){
            return new Result(false, "username format is invalid!");
        }

        if (App.getInstance().getUserByUserName(username) != null &&
                !App.getInstance().getUserByUserName(username).equals(App.getInstance().getCurrentUser())) {

            ArrayList<String> suggestions = suggestAlternativeUsernames(username);
            StringBuilder message = new StringBuilder("username already taken! Here are some suggestions:");

            for (String suggestion : suggestions) {
                message.append("\n- ").append(suggestion);
            }

            message.append("\nPlease choose one of these or try another username.");
            return new Result(false, message.toString());
        }

            User currentUser = App.getInstance().getCurrentUser();

        if(currentUser.getUsername().equals(username)){
            return new Result(false, "the new username is the same as the previous username!");
        }
        currentUser.setUsername(username);
        SaveData.saveUsersToFile(App.getInstance().getUsers());
        return new Result(true, "the username changed successfully to " + username + "!");
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

    public Result changeNickName(String nickname){

        User currentUser = App.getInstance().getCurrentUser();
        if(currentUser.getNickname().equals(nickname)){
            return new Result(false, "the new nickname is the same as the previous nickname!");
        }

        currentUser.setNickname(nickname);
        return new Result(true, "the nickname changed successfully to " + nickname + "!");
    }

    public Result changeEmail(String email){

        User currentUser = App.getInstance().getCurrentUser();

        if(!email.matches(ProfileMenuCommands.EMAIL.getPattern())){
            return new Result(false, "email format is invalid!");
        }

        if(currentUser.getEmail().equals(email)){
            return new Result(false, "the new email is the same as the previous email!");
        }

        if(!checkEmailTaken(email)){
            return new Result(false, "email already taken!");
        }

        currentUser.setEmail(email);
        SaveData.saveUsersToFile(App.getInstance().getUsers());
        return new Result(true, "the email changed successfully to " + email + "!");
    }

    private boolean checkEmailTaken(String email){
        for (User user : App.getInstance().getUsers()){
            if(user.getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }

    public Result changePassword(String newPassword, String oldPassword){

        User currentUser = App.getInstance().getCurrentUser();

        StringBuilder passwordResult = checkStrengthPassword(newPassword);
        if(!passwordResult.isEmpty()){
            return new Result(false, passwordResult.toString());
        }

        if(newPassword.equals(oldPassword)){
            return new Result(false, "the new password is the same as the previous password!");
        }

        if(!currentUser.getPassword().equals(oldPassword)){
            return new Result(false, "the oldPassword us incorrect!");
        }

        currentUser.setPassword(newPassword);
        SaveData.saveUsersToFile(App.getInstance().getUsers());
        return new Result(true, "the password changed successfully to " + newPassword + "!");
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

    public Result userInfo(){

        User currentUser = App.getInstance().getCurrentUser();

        StringBuilder info = new StringBuilder();
        info.append("USER NAME : ");
        info.append(currentUser.getUsername());
        info.append("\n");
        info.append("NICKNAME : ");
        info.append(currentUser.getNickname());
        info.append("\n");
        info.append("golds : ");
        info.append(App.getInstance().getCurrentUser().gold);
        info.append("\n");
        info.append("game played : ");
        info.append(App.getInstance().getCurrentUser().games);
        return new Result(true, info.toString());
    }

    public Result enterMenu(String menuName){
        if(menuName.equalsIgnoreCase("main menu")){
            App.getInstance().setCurrentMenu(Menu.MainMenu);
            return new Result(true, "you are now in MAIN MENU.");
        }
        else
            return new Result(false, "you should go to MAIN MENU first.");
    }
}
