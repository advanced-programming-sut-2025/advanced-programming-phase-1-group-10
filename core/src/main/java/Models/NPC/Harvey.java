package Models.NPC;

import Models.Animal.Fish;
import Models.Animal.FishType;
import Models.App;
import Models.Bar.Bar;
import Models.Bar.BarType;
import Models.DateTime.Season;
import Models.Item;
import Models.Mineral.Mineral;
import Models.Mineral.MineralTypes;
import Models.Place.NpcHosue;
import Models.Planets.Fruit;
import Models.Planets.FruitType;
import Models.Position;
import Models.Quest;
import Models.Weather.Weather;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Arrays;

public class Harvey extends NPC {
    public Harvey(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    private final ArrayList<Quest> quests = new ArrayList<>(Arrays.asList(
            new Quest(1,false,"Give 1 Gold Bar and win 5000 gold",new Bar(BarType.GOLD_BAR,1),5000,null),
            new Quest(2,false,"Give 40 Bananas and win 30 Salmons ",new Fruit(FruitType.BANANA,40),0,new Fish(FishType.SALMON,30)),
            new Quest(3,false,"Give 150 Stones and win 50 Quartz",new Mineral(MineralTypes.STONE,150),0,new Mineral(MineralTypes.QUARTZ,50))
    ));

    @Override
    public Sprite show() {
        return new Sprite(new Texture("npc/harvey/icon.png"));
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

    @Override
    public ArrayList<Quest> getQuests() {
        return quests;
    }
}
