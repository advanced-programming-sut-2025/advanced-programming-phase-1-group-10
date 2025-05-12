package Models.NPC;

import Models.App;
import Models.DateTime.Season;
import Models.Item;
import Models.Place.NpcHosue;
import Models.Position;
import Models.Weather.Weather;

import java.util.ArrayList;

public class Robbin extends NPC {
    public Robbin(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    @Override
    public String getSymbol() {
        return "R";
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public NpcHosue getHosue() {
        return super.getHosue();
    }

    @Override
    public ArrayList<Item> getFavoriteItems() {
        return super.getFavoriteItems();
    }

    public String talk() {
        if(App.getInstance().getCurrentGame().getWeather() == Weather.STORM){
            return "Storm, that's might be awful for farmers.";
        } else if(App.getInstance().getCurrentGame().getGameTime().getSeason() == Season.SPRING){
            return "What a beautiful season? I don't know why Lia doesn't like it";
        } else if(App.getInstance().getCurrentGame().getNextDayWeather() == Weather.RAIN){
            return "Look at those clouds! Tommorow will be raining";
        } else if(hasRelationWithNPC(this.getName())){
            return App.getInstance().getCurrentGame().getCurrentPlayer().getName() + "! nice to see you!";
        }
        return "You are new?";
    }

}
