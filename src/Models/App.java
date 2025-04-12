package src.Models;

public class App {

    private Menu currentMenu = Menu.LoginMenu;

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public enum Gender {
        Male,
        Female,
        ;
    }
}
