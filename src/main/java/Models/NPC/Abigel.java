package Models.NPC;

import Models.App;
import Models.DateTime.Season;
import Models.Item;
import Models.Place.NpcHosue;
import Models.Position;
import Models.Weather.Weather;

import java.util.ArrayList;

public class Abigel extends NPC {
    public Abigel(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    @Override
    public String getSymbol() {
        return "A";
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

    @Override
    public String talk() {
        if(App.getInstance().getCurrentGame().getWeather() == Weather.RAIN){
            return "Rainy weather is aweasome! isn't it?";
        } else if(App.getInstance().getCurrentGame().getGameTime().getSeason() == Season.SUMMER){
            return "Summer is awful! I don't know how can someone likes it.";
        } else if(App.getInstance().getCurrentGame().getGameTime().getSeason() == Season.FALL){
            return "Falling falling!";
        } else if(hasRelationWithNPC(this.getName())){
            return "My frined! how's it going?";
        }
        return "I don't like strangers, What do you want?";
    }
}
