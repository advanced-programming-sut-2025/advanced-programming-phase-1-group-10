package Common.Models.NPC;

import Common.Models.Animal.Fish;
import Common.Models.Animal.FishType;
import Common.Models.Bar.Bar;
import Common.Models.Bar.BarType;
import Common.Models.Cooking.Cooking;
import Common.Models.Cooking.CookingType;
import Common.Models.Crafting.Crafting;
import Common.Models.Crafting.CraftingType;
import Common.Models.Item;
import Common.Models.Place.NpcHosue;
import Common.Models.Planets.Fruit;
import Common.Models.Planets.FruitType;
import Common.Models.Position;
import Common.Models.Quest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Lia extends NPC {

    String[] dialogues = {
        "Good morning, sunshine! I just pulled a batch of cinnamon rolls out of the oven. Want one?",
        "You have that look — like someone who needs a warm muffin and a kind word. Lucky for you, I’ve got both!",
        "My kitchen smells like sugar and spices today. Just the way I like it!",
        "If you ever need to lift your spirits, baking does wonders. I could teach you a thing or two if you like.",
        "I’ve been working on a new recipe with honey and lavender. Think you’d want to taste test for me?",
        "The bakery gets quiet in the afternoons. That’s when I like to sit and dream up new treats.",
        "I saw you running around all morning — you’re working hard! Come in and take a break with some tea.",
        "They say food is love, and well… I’m full of it!",
        "If you're ever feeling down, just come by. We'll mix up some dough and talk things out.",
        "You always bring such good energy with you. I can feel it when you walk in.",
        "You know, baking and friendship aren’t so different — they both need warmth and patience.",
        "The best part of baking is seeing people smile. And you’ve got a great smile, by the way.",
        "I made too many cookies again. Oh no! Guess I’ll have to give them away… hint hint!",
        "I’ve been saving some of the blueberry scones for a special guest. And lucky you — you're here!",
        "You have a way of making even a busy day feel calm. Thanks for stopping in.",
        "Don’t worry if you burn a few loaves — it happens to the best of us. Trust me!",
        "Baking for someone is a special kind of care. Want to learn how to knead dough properly?",
        "The smell of bread reminds me of home. I hope it makes you feel at home too.",
        "I left a little package for you behind the counter. Just something sweet to say thanks.",
        "People might forget what you say, but they'll remember how you made them feel — and cookies help!"
    };

    public Lia(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    private final ArrayList<Quest> quests = new ArrayList<>(Arrays.asList(
            new Quest(1,false,"Give 20 Mushrooms and win 750 gold",new Fruit(FruitType.COMMON_MUSHROOM,20),750,null),
            new Quest(2,false,"Give 10 Salmon and win 100 bread",new Fish(FishType.SALMON,10),0,new Cooking(CookingType.BREAD,100)),
            new Quest(3,false,"Give 2 Iridium Bar and win 4 Bee house ",new Bar(BarType.IRIDIUM_BAR,2),0,new Crafting(CraftingType.BeeHouse,4))
    ));

    @Override
    public Sprite show() {
        return new Sprite(new Texture("npc/lia/icon.png"));
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
