package Views;

import Controllers.CheatCodeControllers;
import Controllers.GameController;
import Models.*;
import Models.Commands.CheatCodeCommands;
import Models.Commands.GameCommands;
import Models.DateTime.DateTimeManager;
import Models.Weather.WeatherManagement;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameLauncher implements AppMenu{

    GameController controller = new GameController();
    CheatCodeControllers cheatCodeController = new CheatCodeControllers();

    //TODO Notify players about new messages sent to 'em and move it to HistoryArray.
    //TODO Notify players about the gifts that are recievecd.
    //TODO Notify players about new trade sended.

    @Override
    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;


        if(App.getInstance().getCurrentGame().getCurrentPlayer().getEnergy().getEnergyAmount() <=0){
            App.getInstance().getCurrentGame().getCurrentPlayer().setFainted(true);
        }

        if(App.getInstance().getCurrentGame().getCurrentPlayer().isFainted() && !input.matches(GameCommands.NEXT_TURN.getPattern())){
            System.out.println("You are faint and can't do anything, please write (next turn)");
            return;
        }

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
        } else if((matcher = GameCommands.PRODUCES.getMatcher(input)) != null){
            System.out.println(controller.showAnimalProducts().message());
        }
        else if((matcher = GameCommands.MEET_NPC.getMatcher(input)) != null){
            System.out.println(controller.meetNPC(matcher.group("npcName")));
        } else if((matcher = GameCommands.GIFT_NPC.getMatcher(input)) != null){
            System.out.println(controller.sendGift(
                    matcher.group("npcName"),
                    matcher.group("item")
            ));
        } else if((matcher = GameCommands.SHOW_NPC_FRIENDSHIP.getMatcher(input)) != null){
            System.out.println(controller.showNPClist());
        } else if((matcher = GameCommands.FISHING_POLE.getMatcher(input)) != null){
            System.out.println(controller.fishing(
                    matcher.group("fishingpole")
            ));
        } else if((matcher = GameCommands.SHOW_FRINEDSHIP.getMatcher(input)) != null){
            System.out.println(controller.showFriendship());
        } else if((matcher = GameCommands.TALK_TO_PLAYER.getMatcher(input)) != null){
            System.out.println(controller.talkToPlayer(
                    matcher.group("username"),
                    matcher.group("message")
            ));
        } else if((matcher = GameCommands.TALK_HISTORY.getMatcher(input)) != null){
            System.out.println(controller.talkHistory(matcher.group("username")));
        } else if((matcher = GameCommands.SEND_GIFT.getMatcher(input)) != null){
            System.out.println(controller.sendGiftToPlayer(
                    matcher.group("username").trim(),
                    matcher.group("item").trim(),
                    matcher.group("amount").trim()
            ));
        } else if((matcher = GameCommands.SHOW_GIFT_LIST.getMatcher(input)) != null){
            System.out.println(controller.showRecievedGifts());
        } else if((matcher = GameCommands.RATE_GIFT.getMatcher(input)) != null){
            System.out.println(controller.rateGift(
                    matcher.group("giftNumber"),
                    matcher.group("rate")
            ));
        } else if((matcher = GameCommands.SHOW_TIME.getMatcher(input)) != null){
            System.out.println(DateTimeManager.showGameTime(
                    App.getInstance().getCurrentGame()
            ));
        } else if((matcher = GameCommands.SHOW_DATETIME.getMatcher(input)) != null){
            System.out.println(DateTimeManager.showGameDateTime(
                    App.getInstance().getCurrentGame()
            ));
        } else if((matcher = GameCommands.SHOW_DATE.getMatcher(input)) != null){
            System.out.println(DateTimeManager.showGameDate(
                    App.getInstance().getCurrentGame()
            ));
        } else if((matcher = GameCommands.SHOW_DAY_OF_WEEK.getMatcher(input)) != null){
            System.out.println(DateTimeManager.showDayOfWeek(
                    App.getInstance().getCurrentGame()
            ));
        } else if((matcher = GameCommands.SHOW_WEATHER.getMatcher(input)) != null){
            System.out.println(WeatherManagement.showWeather());
        } else if((matcher = GameCommands.SHOW_WEATHER_FORECAST.getMatcher(input)) != null){
            System.out.println(WeatherManagement.showWeatherForecast());
        } else if((matcher = GameCommands.COLLECT_PRODUCTS.getMatcher(input)) != null){
            System.out.println(controller.collectAnimalProducts(
                    matcher.group("name")
            ));
        } else if((matcher = GameCommands.GIFT_HISTORY.getMatcher(input)) != null){
            System.out.println(controller.showGiftHistory(
                    matcher.group("username")
            ));
        } else if((matcher = GameCommands.HUG_EACH_OHTER.getMatcher(input)) != null){
            System.out.println(controller.hugEachOther(
                    matcher.group("username")
            ));
        } else if((matcher = GameCommands.GIVE_FLOWER.getMatcher(input)) != null){
            System.out.println(controller.giveFlower(
                    matcher.group("username")
            ));
        } else if((matcher = GameCommands.CRAFT_INFO.getMatcher(input)) != null){
            System.out.println(controller.craftInfo(
                    matcher.group("name")
            ));
        } else if((matcher = GameCommands.PLANT.getMatcher(input)) != null){
            System.out.println(controller.plant(
                    matcher.group("seed"),
                    matcher.group("direction")
            ));
        } else if((matcher = GameCommands.SHOW_PLANT.getMatcher(input)) != null){
            System.out.println(controller.showPlant(
                    matcher.group("x"),
                    matcher.group("y")
            ));
        } else if((matcher = GameCommands.START_TRADE.getMatcher(input)) != null){
            System.out.println(controller.startTrade());
        } else if((matcher = GameCommands.TRADE_OFFER.getMatcher(input)) != null){
            System.out.println(controller.tradeOffer(
                    matcher.group("username"),
                    matcher.group("item").trim(),
                    matcher.group("amount"),
                    matcher.group("price")
            ));
        } else if((matcher = GameCommands.TRADE_REQUEST.getMatcher(input)) != null){
            System.out.println(controller.tradeRequest(
               matcher.group("username"),
               matcher.group("item").trim(),
               matcher.group("amount"),
               matcher.group("targetItem").trim(),
               matcher.group("targetAmount")
            ));
        } else if((matcher = GameCommands.LIST_TRADE.getMatcher(input)) != null){
            System.out.println(controller.listTrade());
        } else if ((matcher = GameCommands.RESPOND_TRADE.getMatcher(input)) != null){
            System.out.println(controller.responseTrade(
                    matcher.group("state"),
                    matcher.group("id")
            ));
        } else if((matcher = GameCommands.TRADE_HISTORY.getMatcher(input)) != null){
            System.out.println(controller.tradeHistory());
        } else if((matcher = GameCommands.FERTILIZER.getMatcher(input)) != null){
            System.out.println(controller.fertilize(
                    matcher.group("fertilizer"),
                    matcher.group("direction")
            ));
        } else if((matcher = GameCommands.HOW_MACH_WATER.getMatcher(input)) != null){
            System.out.println(controller.howMuchWater());
        } else if((matcher = GameCommands.HELP_READING_MAP.getMatcher(input)) != null){
            System.out.println(controller.helpReadingMap());
        } else if((matcher = GameCommands.SHOW_SEASON.getMatcher(input)) != null){
            System.out.println(DateTimeManager.showSeason(App.getInstance().getCurrentGame()));
        } else if((matcher = GameCommands.EXIT_GAME.getMatcher(input)) != null){
            System.out.println(controller.exitGame());
        } else if((matcher = GameCommands.NEXT_TURN.getMatcher(input)) != null){
            System.out.println(controller.nextTurn());
        } else if((matcher = CheatCodeCommands.THOR_TILE.getMatcher(input)) != null){
            System.out.println(cheatCodeController.thorTile(new Position(
                    Integer.parseInt(matcher.group("x")),
                    Integer.parseInt(matcher.group("y"))
                    )
            ));
        }
        else{
            System.out.println("invalid command.");
        }
    }
}
