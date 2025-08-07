package Common.Models;

import Client.Views.*;

import java.util.Scanner;
import java.util.function.Supplier;

public enum Menu {
    LoginMenu(LoginMenu::new),
    GameMenu(GameMenu::new),
    RegisterMenu(RegisterMenuView::new),
    LoginMenuView(LoginMenuView::new),
    ProfileMenu(ProfileMenu::new),
    MainMenu(MainMenuView::new),
    ExitMenu(ExitMenu::new);

    private final Supplier<AppMenu> menuSupplier;
    private AppMenu menuInstance;

    Menu(Supplier<AppMenu> supplier) {
        this.menuSupplier = supplier;
    }

    public AppMenu getMenu() {
        if (menuInstance == null) {
            menuInstance = menuSupplier.get();
        }
        return menuInstance;
    }

    public void check(Scanner scanner) {
        getMenu().checkCommand(scanner);
    }
}
