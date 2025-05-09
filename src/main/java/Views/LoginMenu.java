package Views;


import Controllers.RegisterManuController;
import Models.Commands.RegisterMenuCommands;
import Models.Result;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;


public class LoginMenu implements AppMenu {

    RegisterManuController controller = new RegisterManuController();

    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;

        if((matcher = RegisterMenuCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null){
            System.out.println("you are now in LoginMenu!");
        }
        else if ((matcher = RegisterMenuCommands.REGISTER.getMatcher(input)) != null){
            HandleRegister(matcher,scanner);
        }
        else if((matcher = RegisterMenuCommands.PICK_QUESTION.getMatcher(input)) != null){
            HandlePickQuestion(matcher);
        }
        else if((matcher = RegisterMenuCommands.LOGIN.getMatcher(input)) != null){
            HandleLogin(matcher);
        }
        else if((matcher = RegisterMenuCommands.MENU_EXIT.getMatcher(input)) != null){
            HandleExitGame();
        }
        else if((matcher = RegisterMenuCommands.FORGET_PASSWORD.getMatcher(input)) != null){
            HandleForgotPassword(matcher,scanner);
        }
        else if((matcher = RegisterMenuCommands.MENU_ENTER.getMatcher(input)) != null){
            HandleEnterMenu(matcher);
        }
        else
            System.out.println("invalid command!");
    }

    private void HandleRegister(Matcher matcher, Scanner scanner){
        String username = matcher.group("username");
        String password = matcher.group("password");
        String confirmPassword = matcher.group("passwordconfirm");
        String nickname = matcher.group("nickname");
        String email = matcher.group("email");
        String gender = matcher.group("gender");

        Result result = controller.register(username, password, confirmPassword, nickname, email, gender);
        boolean isUsernameTaken = false;

        if (!result.state() && result.message().contains("username already taken! Here are some suggestions:")) {
            System.out.println(result.message());
            System.out.print("Do you want to use one of these suggestions? (Enter the username): \n");
            String userInput = scanner.nextLine();
             result = controller.register(userInput, password, confirmPassword, nickname, email, gender);
             System.out.println(result.message());
             isUsernameTaken = true;
        }
        if (!result.state() && result.message().contains("Generated random password:")) {
            System.out.println(result.message());
            String userInput;
            String generatedPassword;

            do {
                System.out.print("Your choice (yes or no) : ");
                userInput = scanner.nextLine().trim();

                if (userInput.equalsIgnoreCase("yes")) {
                    generatedPassword = result.message().split(":")[1].trim().split("\n")[0];
                    result = controller.register(username, generatedPassword,generatedPassword, nickname, email, gender);
                    System.out.println(result.message());
                    break;
                } else if (userInput.equalsIgnoreCase("no")) {
                    result = controller.register(username, "random", "random", nickname, email, gender);
                    System.out.println(result.message());
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            } while (!userInput.equalsIgnoreCase("yes"));
        }
        else if(!isUsernameTaken){
            System.out.println(result.message());
        }
        if(result.state()){
            System.out.println(controller.showSecurityQuestions());
        }
    }

    private void HandlePickQuestion(Matcher matcher){
        String questionNumber = matcher.group("questionnumber");
        String answer = matcher.group("answer");
        String answerConfirm = matcher.group("answerconfirm");

        int number = Integer.parseInt(questionNumber);;

        if(number < 1 || number > 10){
            System.out.println("The question number must be between 1 and 10 .");
            return;
        }

        if(!answer.equals(answerConfirm)){
            System.out.println("the (answerconfirm) doesn't match answer!");
            return;
        }

        Result result = controller.pickQuestion(number,answer);
        System.out.println(result.message());
    }

    private void HandleLogin(Matcher matcher){
        String username = matcher.group("username");
        String password = matcher.group("password");
        String stayLoggedInStr = Optional.ofNullable(matcher.group("stayLoggedIn")).orElse("default_value");

        Result result = controller.login(username,password,stayLoggedInStr);
        System.out.println(result.message());
    }

    private void HandleForgotPassword(Matcher matcher , Scanner scanner){
        String username = matcher.group("username");
        Result result1 = controller.forgotPassword(username);
        System.out.println(result1.message());
        String answer;
        Result result2;
        if(result1.state()){
            answer = scanner.nextLine();
            result2 = controller.answerSecurityQuestion(answer);
        }
        else
            return;
        String randomPass = "";
        if(result2 != null && result2.state()){
            System.out.println(result2.message());
            randomPass = scanner.nextLine();
        }
        else {
            System.out.println(result2.message());
            return;
        }
        String password;
        boolean isRandom;
        if(randomPass.equalsIgnoreCase("yes")){
            password = RegisterManuController.generateStrongPassword(10);
            isRandom = true;
            System.out.println(controller.changePassword(password,isRandom).message());
        }
        else {
            System.out.println("Please enter the password: ");
            password = scanner.nextLine();
            Result result3 = controller.changePassword(password,false);
            System.out.println(result3.message());
            if(!result3.state()){
                System.out.println("please enter a new strong password: ");
                password = scanner.nextLine();
                System.out.println(controller.changePassword(password,false).message());
            }
        }
    }

    private void HandleEnterMenu(Matcher matcher){
        String menuName = matcher.group("menuname");

        Result result = controller.enterMenu(menuName);
        System.out.println(result.message());
    }

    private void HandleExitGame(){
        System.out.println(controller.exitGame());
    }
}