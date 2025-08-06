package Common.Models.NPC;

import Common.Models.Crafting.Crafting;
import Common.Models.Crafting.CraftingType;
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

public class Robbin extends NPC {

    String[] dialogues = {
        "Hey, traveler. Sit down a moment. I was just working on a new tune. Want to hear it?",
        "You ever feel like a melody is stuck in your chest, just waiting to come out?",
        "Music’s like sunlight — it touches everyone, whether they notice or not.",
        "I write better songs when you’re around. Must be your energy.",
        "Wanna jam sometime? Doesn’t matter if you can’t play — music finds a way.",
        "I’ve been humming this tune all morning. Think I’ll name it after you.",
        "The best lyrics come when you're not trying too hard — just like friendships.",
        "I saw you in the fields yesterday — it looked like a music video in motion.",
        "Not all songs need words. Sometimes a glance is enough.",
        "When I can’t sleep, I strum my guitar until my dreams come back.",
        "I found an old record player. Wanna come listen with me sometime?",
        "You're like a walking harmony. Weird, huh?",
        "People don’t listen enough. I mean really listen — to each other, to themselves.",
        "Ever tried writing your thoughts down like lyrics? It helps.",
        "I heard laughter down the street. Made me smile. Hope it was you.",
        "You’ve got rhythm — even when you walk.",
        "We should throw a little music night. Just us and the moon.",
        "You're always welcome at my campfire. It’s where the best songs happen.",
        "If your heart had a sound, I bet it’d be beautiful.",
        "Stay cool out there. The world needs more good vibes like yours."
    };

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
        return dialogues[new Random().nextInt(dialogues.length)];
    }


    @Override
    public ArrayList<Quest> getQuests() {
        return quests;
    }
}
