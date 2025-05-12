package Models.NPC;

import Models.App;
import Models.DateTime.Season;
import Models.Item;
import Models.Place.NpcHosue;
import Models.Position;
import Models.Weather.Weather;

import java.util.ArrayList;

public class Harvey extends NPC {
    public Harvey(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    @Override
    public String getSymbol() {
        return "H";
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
        if(App.getInstance().getCurrentGame().getWeather() == Weather.SUNNY){
            return "It's a beautiful day with clear skies and warm sunshine all around.";
        } else if(App.getInstance().getCurrentGame().getGameTime().getSeason() == Season.SUMMER){
            return "Summer is the season of long sunny days, blooming nature, and the scent of adventure in the air.";
        } else if(App.getInstance().getCurrentGame().getGameTime().getSeason() == Season.WINTER){
            return "Where is the sun?? I can't see it through the clouds";
        } else if(hasRelationWithNPC(this.getName())){
            return "Tell my" + App.getInstance().getCurrentGame().getCurrentPlayer().getName() + ", how do you feel?";
        }
        return "A new person in the village? Unbilevable!";
    }
}
