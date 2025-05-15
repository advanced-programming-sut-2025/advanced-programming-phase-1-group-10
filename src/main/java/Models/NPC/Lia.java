package Models.NPC;

import Models.App;
import Models.DateTime.Season;
import Models.Item;
import Models.Place.NpcHosue;
import Models.Position;
import Models.Weather.Weather;

import java.util.ArrayList;

public class Lia extends NPC {
    public Lia(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    @Override
    public String getSymbol() {
        return "Li";
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
            return "Storm, that's teriffying!";
        } else if(App.getInstance().getCurrentGame().getGameTime().getSeason() == Season.SPRING){
            return "Although it's beautiful, but I dont like Spring.";
        } else if(App.getInstance().getCurrentGame().getNextDayWeather() == Weather.STORM){
            return "Tommorw would be a scary day.";
        } else if(hasRelationWithNPC(this.getName())){
            return "Hello "+ App.getInstance().getCurrentGame().getCurrentPlayer().getName() + ", what are you doing?";
        }
        return "Do you want to hurt me?";
    }

}
