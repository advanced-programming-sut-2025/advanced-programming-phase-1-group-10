package Common.Models.NPC;

import Common.Models.Bar.Bar;
import Common.Models.Bar.BarType;
import Common.Models.Item;
import Common.Models.Mineral.Mineral;
import Common.Models.Mineral.MineralTypes;
import Common.Models.Place.NpcHosue;
import Common.Models.Planets.Crop.Crop;
import Common.Models.Planets.Crop.CropTypeNormal;
import Common.Models.Position;
import Common.Models.Quest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Abigel extends NPC {

    String[] dialogues = {
        "Hey there! I just finished feeding the cows. If you ever need fresh milk, come see me in the morning.",
        "The soil’s been kind this year. I think the carrots are going to be sweeter than usual.",
        "Good to see you! I was just about to bake a pie with some apples I picked — want to join?",
        "Farming’s tough work, but it keeps the heart strong and the spirit grounded.",
        "You remind me of myself when I started out — full of energy and ready to take on the world.",
        "I saw you working out there earlier. You've got a good rhythm. Keep at it, and this land will reward you.",
        "Some days, all I want is a quiet evening, a warm fire, and a hot bowl of stew. You ever feel like that?",
        "I found an old horseshoe near the barn. They say it brings luck. Want it?",
        "The chickens have been restless lately. Could be a storm coming. Watch yourself out there.",
        "You know, people come and go, but the land... the land stays. That’s why I trust it more than most.",
        "It’s always a pleasure seeing a friendly face like yours around here.",
        "I heard the forest is full of wildflowers today. Might be worth taking a walk, clear your mind.",
        "Woke up with the sunrise and thought of you. You seem like someone who appreciates a quiet dawn.",
        "I've got extra seeds if you want to try growing something new. Just let me know.",
        "Some folks talk to plants. Me? I listen to them. They’ve got stories if you pay attention.",
        "You look tired, friend. Make sure you’re resting enough. The farm can wait a little.",
        "The scarecrow out back? I made it when I was your age. Still does its job!",
        "Next time you're free, come help me harvest. There's something peaceful about it.",
        "We might not say much, but folks around here do care. Don’t forget that.",
        "If you ever need advice, or just a cup of tea, my door’s open."
    };

    private final ArrayList<Quest> quests = new ArrayList<>(Arrays.asList(
            new Quest(1,false,"Give 50 Irons and get 2 Diamonds",new Mineral(MineralTypes.IRON,50),0,new Mineral(MineralTypes.DIAMOND,2)),
            new Quest(2,false,"Give 1 Radioactive bar and get 5000 gold",new Bar(BarType.RADIOACTIVE_BAR,1),5000,null),
            new Quest(3,false,"Give 50 wheats and win 5 Golden Bar",new Crop(CropTypeNormal.WHEAT,50),0,new Bar(BarType.GOLD_BAR,5))
    ));

    public Abigel(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    @Override
    public Sprite show() {
        return new Sprite(new Texture("npc/abigel/icon.png"));
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
        return dialogues[new Random().nextInt(dialogues.length)];
    }

    @Override
    public ArrayList<Quest> getQuests() {
        return quests;
    }
}
