package Server.Controllers;

import Common.Models.App;
import Common.Models.Menu;
import Common.Models.Result;

public class MainMenuControllers {

    public Result enterMenu(String menuName){
        if(menuName.equalsIgnoreCase("login menu") ||
           menuName.equalsIgnoreCase("signup menu")){
            App.getInstance().setCurrentMenu(Menu.LoginMenu);
            return new Result(true, "you are now in LOGIN MENU.");
        }
        else if(menuName.equalsIgnoreCase("game menu")){
            App.getInstance().setCurrentMenu(Menu.GameMenu);
            return new Result(true, "you are now in GAME MENU.");
        }
        else if(menuName.equalsIgnoreCase("profile menu")){
            App.getInstance().setCurrentMenu(Menu.ProfileMenu);
            return new Result(true, "you are now in PROFILE MENU.");
        }
        else
            return new Result(false, "enter the correct MENU NAME\nlogin(register) menu\nmain menu\nprofile menu\ngame menu");
    }

    public Result logout(){
        if (App.getInstance().getCurrentUser() == null) {
            return new Result(false, "you should login first!");
        }
        App.getInstance().setCurrentUser(null);
        App.getInstance().setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "you logged out successfully! you are now in LOGIN/REGISTER Menu!");
    }
}
