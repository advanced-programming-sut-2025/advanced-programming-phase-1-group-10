package Views;


import Controllers.GameMenuControllers;
import Models.Commands.GameMenuCommands;
import Models.Commands.RegisterMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements  AppMenu {

    GameMenuControllers controller = new GameMenuControllers();

    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;

        if((matcher = GameMenuCommands.NEW_GAME.getMatcher(input)) != null){

        } else {
            System.out.println("Invalid command");
        }
    }
}
