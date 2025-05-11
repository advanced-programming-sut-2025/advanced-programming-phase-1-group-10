package Views;

import Controllers.CheatCodeControllers;
import Controllers.GameController;
import Models.App;
import Models.Commands.CheatCodeCommands;
import Models.Commands.GameCommands;
import Models.Commands.GameMenuCommands;
import Models.Position;
import Models.Result;
import Models.Tile;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameLauncher implements AppMenu{

    GameController controller = new GameController();
    CheatCodeControllers cheatCodeController = new CheatCodeControllers();

    @Override
    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;

        if((matcher = GameCommands.BUILD_MAINTENANCE.getMatcher(input)) != null){
            String name = matcher.group("buildingName");
            if(!name.equalsIgnoreCase("coop") && !name.equalsIgnoreCase("barn")){
                System.out.println("you can just build coop or barn, so enter the name coop or barn.");
                return;
            }
            String x = matcher.group("x");
            String y = matcher.group("y");
            int X = Integer.parseInt(x);
            int Y = Integer.parseInt(y);
            Position position = new Position(X,Y);
            Result result = null;
            if(name.equalsIgnoreCase("coop")){
                result = controller.createCoop(position, App.getInstance().getCurrentGame());
            }
            else if(name.equalsIgnoreCase("barn")){
                result = controller.createBarn(position,App.getInstance().getCurrentGame());
            }
            System.out.println(result);
        }
        else if((matcher = GameCommands.PRINT_MAP.getMatcher(input)) != null){
            System.out.println(controller.printMap());
        } else if((matcher = GameCommands.WALK.getMatcher(input)) != null){
            System.out.println(controller.walkPlayer(new Position(
               Integer.parseInt(matcher.group("x")),
               Integer.parseInt(matcher.group("y"))
            )));
        } else if((matcher = GameCommands.SHOW_ENERGY.getMatcher(input)) != null){
            System.out.println(controller.showEnergy());
        } else if((matcher = CheatCodeCommands.SET_ENERGY.getMatcher(input)) != null){
            System.out.println(cheatCodeController.setEnergy(matcher.group("value")));
        } else if ((matcher = CheatCodeCommands.SET_ENERGY_UNLIMITED.getMatcher(input)) != null) {
            System.out.println(cheatCodeController.setUnlimitedEnergy());
        } else if((matcher = GameCommands.BUY_ANIMAL.getMatcher(input)) != null){
            String animal = matcher.group("animal");
            String name = matcher.group("name");
            System.out.println(controller.buyAnimal(animal,name).message());
        } else if((matcher = GameCommands.PET_ANIMAL.getMatcher(input)) != null){
            String name = matcher.group("name");
            System.out.println(controller.petAnimals(name).message());
        } else if((matcher = CheatCodeCommands.SET_ANIMAL_FRIENDSHIP.getMatcher(input)) != null){
            String name = matcher.group("animalName");
            String amount = matcher.group("amount");
            System.out.println(cheatCodeController.setAnimalFriendShip(name,amount));
        } else if((matcher = GameCommands.ANIMLAS.getMatcher(input)) != null){
            System.out.println(controller.showAnimals().message());
        } else if((matcher = GameCommands.SHOW_INVENTORY.getMatcher(input)) != null){
            System.out.println(controller.showInventory());
        }
        else{
            System.out.println("invalid command.");
        }

    }
}
