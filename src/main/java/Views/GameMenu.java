package Views;

import Controllers.GameMenuControllers;
import Models.App;
import Models.Commands.GameMenuCommands;
import Models.PlayerStuff.Player;
import Models.Position;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {

    GameMenuControllers controller = new GameMenuControllers();

    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;


        if ((matcher = GameMenuCommands.NEW_GAME.getMatcher(input)) != null) {
            System.out.println(controller.createGame(
                    App.getInstance().getCurrentUser().getUsername(),
                    matcher.group("username1").trim(),
                    matcher.group("username2").trim(),
                    matcher.group("username3").trim()
            ));

            for (Player player : App.getInstance().getCurrentGame().getPlayers()) {
                while (true) {
                    System.out.println("Enter farmType for " + player.getName() + " :");
                    String farmInput = scanner.nextLine().trim();
                    Matcher farmMatcher = GameMenuCommands.CHOOSE_MAP.getMatcher(farmInput);

                    if (farmMatcher != null) {
                        String mapNumber = farmMatcher.group("MapNumber").trim();
                        Position position = controller.chooseStartingPoint(
                                App.getInstance().getCurrentGame().getPlayers().indexOf(player)
                        );
                        player.setFarm(controller.createFarm(mapNumber, position, App.getInstance().getCurrentGame()));
                        System.out.println("Farm created for " + player.getName() + " with map type " + mapNumber);
                        break;
                    } else {
                        System.out.println("Invalid input!");
                    }
                }
            }

            System.out.println("All players have chosen their farms. Game starts!");

        } else {
            System.out.println("Invalid command.");
        }
    }
}