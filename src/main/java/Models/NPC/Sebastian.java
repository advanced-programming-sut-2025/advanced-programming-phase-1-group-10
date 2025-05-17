package Models.NPC;

import Models.App;
import Models.Bar.Bar;
import Models.Bar.BarType;
import Models.Cooking.Cooking;
import Models.Cooking.CookingType;
import Models.Crafting.Crafting;
import Models.Crafting.CraftingType;
import Models.DateTime.Season;
import Models.Item;
import Models.Mineral.Mineral;
import Models.Mineral.MineralTypes;
import Models.Place.NpcHosue;
import Models.Planets.Crop.Crop;
import Models.Planets.Crop.CropTypeNormal;
import Models.Position;
import Models.Quest;
import Models.Weather.Weather;

import java.util.ArrayList;
import java.util.Arrays;

public class Sebastian extends NPC {
    public Sebastian(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    private final ArrayList<Quest> quests = new ArrayList<>(Arrays.asList(
            new Quest(1,false,"Give 10 Irons and get 1 Diamonds",new Mineral(MineralTypes.IRON,10),0,new Mineral(MineralTypes.DIAMOND,1)),
            new Quest(2,false,"Give a Beehouse and get 5000 gold",new Crafting(CraftingType.BeeHouse,1),5000,null),
            new Quest(3,false,"Give 100 wheats and win 100 bread",new Crop(CropTypeNormal.WHEAT,100),0,new Cooking(CookingType.BREAD,100))
    ));

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

