package Views;

import Controllers.MenuControllers.ProfileMenuControllers;
import Models.Commands.ProfileMenuCommands;
import Models.Result;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu {
    ProfileMenuControllers controllers = new ProfileMenuControllers();

    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;

        if((matcher = ProfileMenuCommands.CHANGE_USERNAME.getMatcher(input)) != null){
            HandleChangeUsername(matcher,scanner);
        }
        else if((matcher = ProfileMenuCommands.CHANGE_NICKNAME.getMatcher(input)) != null){
            HandleChangeNickName(matcher);
        }
        else if((matcher = ProfileMenuCommands.CHANGE_EMAIL.getMatcher(input)) != null){
            HandleChangeEmail(matcher);
        }
        else if((matcher = ProfileMenuCommands.CHANGE_PASSWORD.getMatcher(input)) != null){
            HandleChangePassword(matcher);
        }
        else if((matcher = ProfileMenuCommands.USER_INFO.getMatcher(input)) != null){
            HandleUserInfo();
        }
        else
            System.out.println("invalid command");
    }

    private void HandleChangeUsername(Matcher matcher, Scanner scanner){
        String username = matcher.group("username");

        Result result = controllers.changeUsername(username);

        if (!result.state() && result.message().contains("username already taken! Here are some suggestions:")) {
            System.out.println(result.message());
            System.out.print("Do you want to use one of these suggestions? (Enter the username): \n");
            String userInput = scanner.nextLine();
            result = controllers.changeUsername(userInput);
            System.out.println(result.message());
        }

        else {
            System.out.println(result.message());
        }
    }

    private void HandleChangeNickName(Matcher matcher){
        String nickname = matcher.group("nickname");

        Result result = controllers.changeNickName(nickname);
        System.out.println(result.message());
    }

    private void HandleChangePassword(Matcher matcher){
        String newPass = matcher.group("newpassword");
        String oldPass = matcher.group("oldpassword");

        Result result = controllers.changePassword(newPass,oldPass);
        System.out.println(result.message());
    }

    private void HandleChangeEmail(Matcher matcher){
        String email = matcher.group("email");

        Result result = controllers.changeEmail(email);
        System.out.println(result.message());
    }

    private void HandleUserInfo(){
        Result result = controllers.userInfo();
        System.out.println(result.message());
    }
}
