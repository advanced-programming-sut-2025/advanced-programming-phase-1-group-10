package Models;




import Views.*;

import java.util.Scanner;

public enum Menu {

    LoginMenu(new LoginMenu()),
    GameMenu(new GameMenu()),
    ProfileMenu(new ProfileMenu()),
    MainMenu(new MainMenu()),
    TradeMenu(new TradeMenu()),
    ExitMenu(new ExitMenu());
    ;
    private final AppMenu menu;
    Menu(AppMenu menu) {
        this.menu = menu;
    }
    public void check(Scanner scanner) {
        this.menu.checkCommand(scanner);
    }
}
