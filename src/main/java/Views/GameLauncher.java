package Views;

import Controllers.GameController;
import Models.App;
import Models.Commands.GameCommands;
import Models.Commands.GameMenuCommands;
import Models.Position;
import Models.Result;
import Models.Tile;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameLauncher implements AppMenu{

    GameController controller = new GameController();
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
            System.out.println(result.message());
        } else
            System.out.println("invalid command.");
    }
}
