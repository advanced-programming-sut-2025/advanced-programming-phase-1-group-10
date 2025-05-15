package Models.NPC;

import Models.App;
import Models.DateTime.Season;
import Models.Item;
import Models.Place.NpcHosue;
import Models.Position;
import Models.Weather.Weather;

import java.util.ArrayList;

public class Sebastian extends NPC {
    public Sebastian(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    @Override
    public String getSymbol() {
        return "Se";
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
    public String talk() {
        if(App.getInstance().getCurrentGame().getWeather() == Weather.SNOW){
            return "Snow blankets the world in a quiet, cold stillness, turning everything into a soft, white wonderland.";
        } else if(App.getInstance().getCurrentGame().getGameTime().getSeason() == Season.SUMMER){
            return "How do you bear this weather?";
        } else if(App.getInstance().getCurrentGame().getNextDayWeather() == Weather.SUNNY){
            return "Tommorow is sunny. Ops!";
        } else if(hasRelationWithNPC(this.getName())){
            return "Hi, " + App.getInstance().getCurrentGame().getCurrentPlayer().getName() + "! what are you looking for my friend?";
        }
        return "Do you want to hurt me?";
    }
}

