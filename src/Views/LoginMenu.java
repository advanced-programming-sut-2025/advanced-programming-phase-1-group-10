package src.Views;

import src.Controllers.MenuControllers.RegisterManuController;
import src.Models.App;
import src.Models.Commands.RegisterMenuCommands;
import src.Models.Menu;
import src.Models.Result;

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
                System.out.print("Your choice: ");
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
    }
}