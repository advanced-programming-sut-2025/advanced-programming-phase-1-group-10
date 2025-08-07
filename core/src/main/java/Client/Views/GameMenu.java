package Client.Views;

import Common.Models.Farm;
import Common.Models.Position;
import Client.Controllers.GameMenuControllers;
import Common.Models.Commands.GameMenuCommands;
import Common.Models.PlayerStuff.Player;

import java.util.Scanner;
import java.util.regex.Matcher;

import static Common.Models.App.getInstance;

public class GameMenu implements AppMenu {

    GameMenuControllers controller = new GameMenuControllers();

    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;


        if ((matcher = GameMenuCommands.NEW_GAME.getMatcher(input)) != null) {
            //System.out.println(controller.createGame(
            //        getInstance().getCurrentUser().getUsername(),
            //        matcher.group("username1").trim(),
           //         matcher.group("username2").trim(),
            //        matcher.group("username3").trim()
            //));
            if (getInstance().getCurrentGame() != null) {
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
                            //Create farm
                            Farm farm = controller.createFarm(mapNumber, position, getInstance().getCurrentGame());
                            //Set player postion left-up of the house.
                            Position playerPostion = controller.getPlaceByName(farm.getPlaces(), "House").getPosition();
                            player.setPosition(new Position(0,0));
                            player.getPosition().setX(playerPostion.getX() - 1);
                            player.getPosition().setY(playerPostion.getY() - 1);
                            controller.getTileByPosition(player.getPosition()).setPerson(player);
                            //controller.putRandomMineral(farm,4);
                            //controller.putRandomForagingPlanet(farm,10);
                            //Give farm to player
                            player.setFarm(farm);
                            //Set up Realation with players
                            controller.setUpFriendShip(player);
                            System.out.println("Farm created for " + player.getName() + " with map type " + mapNumber);
                            break;
                        } else {
                            System.out.println("Invalid input!");
                        }
                    }
                }
                //App.getInstance().setCurrentMenu(Menu.GameLauncher);
                System.out.println("All players have chosen their farms. Game starts!");
            }
        } else {
            System.out.println("Invalid command.");
        }
    }
}
