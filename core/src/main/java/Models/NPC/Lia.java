package Models.NPC;

import Models.Animal.Fish;
import Models.Animal.FishType;
import Models.App;
import Models.Bar.Bar;
import Models.Bar.BarType;
import Models.Cooking.Cooking;
import Models.Cooking.CookingType;
import Models.Crafting.Crafting;
import Models.Crafting.CraftingType;
import Models.DateTime.Season;
import Models.Item;
import Models.Place.NpcHosue;
import Models.Planets.Fruit;
import Models.Planets.FruitType;
import Models.Position;
import Models.Quest;
import Models.Weather.Weather;

import java.util.ArrayList;
import java.util.Arrays;

public class Lia extends NPC {
    public Lia(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    private final ArrayList<Quest> quests = new ArrayList<>(Arrays.asList(
            new Quest(1,false,"Give 20 Mushrooms and win 750 gold",new Fruit(FruitType.COMMON_MUSHROOM,20),750,null),
            new Quest(2,false,"Give 10 Salmon and win 100 bread",new Fish(FishType.SALMON,10),0,new Cooking(CookingType.BREAD,100)),
            new Quest(3,false,"Give 2 Iridium Bar and win 4 Bee house ",new Bar(BarType.IRIDIUM_BAR,2),0,new Crafting(CraftingType.BeeHouse,4))
    ));

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

    @Override
    public ArrayList<Quest> getQuests() {
        return quests;
    }
}
