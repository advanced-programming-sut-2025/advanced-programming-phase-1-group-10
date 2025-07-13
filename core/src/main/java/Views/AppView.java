package Views;



import Models.App;
import Models.Menu;

import java.util.Scanner;

public class AppView {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getInstance().getCurrentMenu().check(scanner);
        } while (App.getInstance().getCurrentMenu() != Menu.ExitMenu);
    }
}
