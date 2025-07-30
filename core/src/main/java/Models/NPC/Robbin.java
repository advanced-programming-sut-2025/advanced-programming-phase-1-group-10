package Models.NPC;

import Models.App;
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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Arrays;

public class Robbin extends NPC {
    public Robbin(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    private final ArrayList<Quest> quests = new ArrayList<>(Arrays.asList(
            new Quest(1,false,"Give 20 Quartz and win 2000 gold",new Mineral(MineralTypes.QUARTZ,20),2000,null),
            new Quest(2,false,"Give 5 bomb and win 5000 gold ",new Crafting(CraftingType.Bomb,4),5000,null),
            new Quest(3,false,"Give 5 Ancient fruit and win 40 Iridium",new Crop(CropTypeNormal.ANCIENT_FRUIT,5),0,new Mineral(MineralTypes.IRIDIUM,40))
    ));

    @Override
    public Sprite show() {
        return new Sprite(new Texture("npc/robbin/icon.png"));
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

    @Override
    public ArrayList<Quest> getQuests() {
        return quests;
    }
}
