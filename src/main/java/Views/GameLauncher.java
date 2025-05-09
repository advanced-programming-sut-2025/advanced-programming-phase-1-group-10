package Views;

import Controllers.GameController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameLauncher implements AppMenu{

    GameController controller = new GameController();
    @Override
    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
    }
}
