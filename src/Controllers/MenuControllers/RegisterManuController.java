package src.Controllers.MenuControllers;

import src.Models.App;
import src.Models.Commands.RegisterMenuCommands;
import src.Models.PlayerStuff.Gender;
import src.Models.Result;
import src.Models.User;
import java.security.SecureRandom;
import java.util.ArrayList;


public class RegisterManuController {

    public Result register(String username, String password, String confirmPassword,
                                    String nickname, String email, String gender){

        if(App.getUserByUserName(username) != null){
            return new Result(false, "username already taken!");
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

        boolean isRandomPassword = false;
        if(password.equalsIgnoreCase("random")){
            isRandomPassword = true;
            password = generateStrongPassword(12);
        }
        //TODO user confirmed the random password ?

        StringBuilder passwordResult = checkStrengthPassword(password);
        if(!passwordResult.isEmpty() && !isRandomPassword){
            return new Result(false, passwordResult.toString());
        }

        if(!password.contains(confirmPassword) && !isRandomPassword){
            return new Result(false, "confirm password doesn't match password!");
        }

        Gender usergender = Gender.Male;
        if(gender.equalsIgnoreCase("female")){
            usergender = Gender.Female;
        }

        User newuser = new User(nickname,password,username,usergender);
        App.addUser(newuser);
        return new Result(true, "user registered successfully!");
    }


    private boolean checkUsername(String username){
        return username.matches(RegisterMenuCommands.USERNAME.getPattern());
    }

    private boolean checkEmail(String email){
        return email.matches(RegisterMenuCommands.EMAIL.getPattern());
    }

    private boolean checkEmailTaken(String email){
        for (User user : App.users){
            if(user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    private StringBuilder  checkStrengthPassword(String password){
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
}