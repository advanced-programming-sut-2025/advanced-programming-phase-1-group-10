package Models.NPC;

import Models.App;
import Models.Bar.Bar;
import Models.Bar.BarType;
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

public class Abigel extends NPC {

    private final ArrayList<Quest> quests = new ArrayList<>(Arrays.asList(
            new Quest(1,false,"Give 50 Irons and get two Diamonds",new Mineral(MineralTypes.IRON,50),0,new Mineral(MineralTypes.DIAMOND,2)),
            new Quest(2,false,"Give 1 Radioactive bar and get 5000 gold",new Bar(BarType.RADIOACTIVE_BAR,1),5000,null),
            new Quest(3,false,"Give 50 wheats and win 5 Golden Bar",new Crop(CropTypeNormal.WHEAT,50),0,new Bar(BarType.GOLD_BAR,5))
    ));

    public Abigel(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    @Override
    public String getSymbol() {
        return "Ab";
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

    @Override
    public ArrayList<Quest> getQuests() {
        return quests;
    }
}
