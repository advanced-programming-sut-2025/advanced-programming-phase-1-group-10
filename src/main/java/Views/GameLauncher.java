package Views;

import Controllers.CheatCodeControllers;
import Controllers.GameController;
import Models.*;
import Models.Commands.CheatCodeCommands;
import Models.Commands.GameCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameLauncher implements AppMenu{

    GameController controller = new GameController();
    CheatCodeControllers cheatCodeController = new CheatCodeControllers();

    //TODO Notify players about new messages sent to 'em and move it to HistoryArray

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
        } else if((matcher = GameCommands.EQUIP_TOOL.getMatcher(input)) != null){
            System.out.println(controller.equipTool(matcher.group("toolName")));
        } else if((matcher = GameCommands.SHOW_CURRENT_TOOL.getMatcher(input)) != null){
            System.out.println(controller.showCurrentTool());
        } else if((matcher = GameCommands.SHOW_AVALIABLE_TOOL.getMatcher(input)) != null){
            System.out.println(controller.showAvaliableTools());
        } else if((matcher = GameCommands.TOOL_USE.getMatcher(input)) != null){
            System.out.println(controller.useTool(
                    matcher.group("direction")
            ));
        } else if((matcher = GameCommands.SHEPHERD_ANIMAL.getMatcher(input)) != null){
            String name = matcher.group("name");
            String x = matcher.group("x");
            String y = matcher.group("y");
            int X = Integer.parseInt(x);
            int Y = Integer.parseInt(y);
            Position position = new Position(X,Y);
            System.out.println(controller.shepherdAnimals(name,position));
        } else if((matcher = GameCommands.FEED_ANIMAL_WITH_HAY.getMatcher(input)) != null){
            System.out.println(controller.feedAnimalWithHay(
                    matcher.group("name")
            ));
        } else if((matcher = GameCommands.SELL_ANIMAL.getMatcher(input)) != null){
            System.out.println(controller.sellAnimal(
                    matcher.group("name")
            ));
        } else if((matcher =  GameCommands.PRINT_PART_OF_MAP.getMatcher(input)) != null){
            System.out.println(controller.parintPartialMap(
                    matcher.group("x"),
                    matcher.group("y"),
                    matcher.group("size")
            ));
        } else if((matcher = GameCommands.MEET_NPC.getMatcher(input)) != null){
            System.out.println(controller.meetNPC(matcher.group("npcName")));
        } else if((matcher = GameCommands.GIFT_NPC.getMatcher(input)) != null){
            System.out.println(controller.sendGift(
                    matcher.group("npcName"),
                    matcher.group("item")
            ));
        } else if((matcher = GameCommands.SHOW_NPC_FRIENDSHIP.getMatcher(input)) != null){
            System.out.println(controller.showNPClist());
        } else if((matcher = GameCommands.SHOW_FRINEDSHIP.getMatcher(input)) != null){
            System.out.println(controller.showFriendship());
        } else if((matcher = GameCommands.TALK_TO_PLAYER.getMatcher(input)) != null){
            System.out.println(controller.talkToPlayer(
                    matcher.group("username"),
                    matcher.group("message")
            ));
        } else if((matcher = GameCommands.TALK_HISTORY.getMatcher(input)) != null){
            System.out.println(controller.talkHistory(matcher.group("username")));
        }
        else{
            System.out.println("invalid command.");
        }
    }
}
