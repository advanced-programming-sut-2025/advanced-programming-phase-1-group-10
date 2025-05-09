package Views;

import Controllers.GameMenuControllers;
import Models.App;
import Models.Commands.GameMenuCommands;
import Models.PlayerStuff.Player;
import Models.Position;
import Models.Tile;

import java.util.Scanner;
import java.util.regex.Matcher;

import static Models.App.getInstance;

public class GameMenu implements AppMenu {

    GameMenuControllers controller = new GameMenuControllers();

    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;


        if ((matcher = GameMenuCommands.NEW_GAME.getMatcher(input)) != null) {
            System.out.println(controller.createGame(
                    getInstance().getCurrentUser().getUsername(),
                    matcher.group("username1").trim(),
                    matcher.group("username2").trim(),
                    matcher.group("username3").trim()
            ));
            if(getInstance().getCurrentGame() != null) {
                for (Player player : getInstance().getCurrentGame().getPlayers()) {
                    while (true) {
                        System.out.println("Enter farmType for " + player.getName() + " :");
                        String farmInput = scanner.nextLine().trim();
                        Matcher farmMatcher = GameMenuCommands.CHOOSE_MAP.getMatcher(farmInput);

                        if (farmMatcher != null) {
                            String mapNumber = farmMatcher.group("mapNumber").trim();
                            Position position = controller.chooseStartingPoint(
                                    getInstance().getCurrentGame().getPlayers().indexOf(player)
                            );
                            player.setFarm(controller.createFarm(mapNumber, position, getInstance().getCurrentGame()));
                            System.out.println("Farm created for " + player.getName() + " with map type " + mapNumber);
                            break;
                        } else {
                            System.out.println("Invalid input!");
                        }
                    }
                }
                System.out.println("All players have chosen their farms. Game starts!");
                for(Tile[] tiles : App.getInstance().getCurrentGame().getGameMap().getMap()){
                    for(Tile tile : tiles){
                        tile.printTile();
                    }
                    System.out.println();
                }

            }
        } else {
            System.out.println("Invalid command.");
        }
    }
}