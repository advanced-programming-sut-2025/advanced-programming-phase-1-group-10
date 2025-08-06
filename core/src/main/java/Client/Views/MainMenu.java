package Client.Views;

import Server.Controllers.MainMenuControllers;
import Common.Models.Commands.MainMenuCommands;
import Common.Models.Result;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu {

    MainMenuControllers controller = new MainMenuControllers();

    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;

        if((matcher = MainMenuCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null){
            System.out.println("you are now in MainMenu!");
        }
        else if((matcher = MainMenuCommands.MENU_ENTER.getMatcher(input)) != null){
            HandleEnterMenu(matcher);
        }
        else if((matcher = MainMenuCommands.USER_LOGOUT.getMatcher(input)) != null){
            HandleLogout();
        }
        else
            System.out.println("invalid command!");
    }

    private void HandleEnterMenu(Matcher matcher){
        String menuName = matcher.group("menuname");
        Result result = controller.enterMenu(menuName);
        System.out.println(result.message());
    }

    private void HandleLogout(){
        Result result = controller.logout();
        System.out.println(result.message());
    }
}
