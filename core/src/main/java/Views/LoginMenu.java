package Views;


import Controllers.RegisterManuController;
import Models.App;
import Models.Commands.RegisterMenuCommands;
import Models.Result;
import Models.User;

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
        else if(input.equalsIgnoreCase("register")){
            HandleRegister(scanner);
        }
        else if((matcher = RegisterMenuCommands.PICK_QUESTION.getMatcher(input)) != null){
            HandlePickQuestion(matcher);
        }
        else if(input.equalsIgnoreCase("login")){
            HandleLogin(scanner);
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

    private void HandleRegister(Scanner scanner){
        String username, password, confirmPassword, nickname, email, gender;
        Result result;
        boolean isUsernameTaken;

        System.out.print("Enter username: ");
        username = scanner.nextLine().trim();
        System.out.print("Enter nickname: ");
        nickname = scanner.nextLine().trim();
        System.out.print("Enter email: ");
        email = scanner.nextLine().trim();
        System.out.print("Enter gender (male or female): ");
        gender = scanner.nextLine().trim();

        do {
            System.out.print("Enter password (or 'random' for a generated password): ");
            password = scanner.nextLine().trim();
            confirmPassword = password;

            if (password.equalsIgnoreCase("random")) {
                boolean passwordChosen = false;
                while (!passwordChosen) {
                    password = RegisterManuController.generateStrongPassword(12);
                    System.out.println("Generated random password: " + password);
                    System.out.print("Use this generated password? (yes or no): ");
                    String userInput = scanner.nextLine().trim();

                    if (userInput.equalsIgnoreCase("yes")) {
                        confirmPassword = password;
                        passwordChosen = true;
                    } else if (userInput.equalsIgnoreCase("no")) {
                        continue;
                    } else {
                        System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                    }
                }
            }
            else {
                System.out.print("Confirm password: ");
                confirmPassword = scanner.nextLine().trim();
            }

            result = controller.register(username, password, confirmPassword, nickname, email, gender);
            isUsernameTaken = false;

            if (!result.state() && result.message().contains("username already taken!")) {
                System.out.println(result.message());
                System.out.print("Do you want to use one of these suggestions? (Enter the username, or 'no' to try another username): ");
                String userInput = scanner.nextLine().trim();

                if (userInput.equalsIgnoreCase("no")) {
                    System.out.print("Enter a new username: ");
                    username = scanner.nextLine().trim();
                    isUsernameTaken = true;
                } else {
                    result = controller.register(userInput, password, confirmPassword, nickname, email, gender);
                    System.out.println(result.message());
                }
            } else {
                System.out.println(result.message());
            }
        } while (isUsernameTaken);

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

    private void HandleLogin(Scanner scanner){
        System.out.print("Enter username : ");
        String userName = scanner.nextLine().trim();
        User user = App.getInstance().getUserByUserName(userName);
        if(user == null){
            System.out.println("no user with this username exist!");
        }
        else {
            if(user.isStayLoggedIn()){
                System.out.println(controller.login(userName,user.getPassword(),"true").message());
            }
            else {
                System.out.print("Enter password : ");
                String password = scanner.nextLine().trim();
                System.out.print ("do you want stay login (yes or no) ? : ");
                String stay = scanner.nextLine().trim();
                if(stay.equalsIgnoreCase("yes"))
                    System.out.println(controller.login(userName,password,"true"));
                else System.out.println(controller.login(userName,password,""));
            }
        }
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