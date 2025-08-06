package Common.Models.NPC;

import Common.Models.Animal.Fish;
import Common.Models.Animal.FishType;
import Common.Models.Bar.Bar;
import Common.Models.Bar.BarType;
import Common.Models.Item;
import Common.Models.Mineral.Mineral;
import Common.Models.Mineral.MineralTypes;
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

public class Harvey extends NPC {

    String[] dialogues = {
        "You remind me of someone I knew long ago. Curious eyes, strong heart.",
        "This village may seem quiet, but there’s magic in its silence. Listen close, you’ll hear it.",
        "Sometimes, doing nothing is exactly what the soul needs.",
        "There are lessons in every breeze, every sunrise. You just need to be still enough to hear them.",
        "Don’t rush life. Even the tree knows it must take its time to grow tall.",
        "You seek answers, but don’t forget to ask the right questions first.",
        "Kindness is a language understood by all. You speak it well.",
        "You carry something special within you — I can see it in the way you move through the world.",
        "Pain is not an enemy, child. It's a teacher. One we rarely thank, but always remember.",
        "When I was your age, I wanted to change the world. Now I plant flowers and watch them bloom.",
        "You have fire in your spirit. Be careful it doesn’t burn you from within.",
        "It’s okay to be unsure. The stars don’t always show the path, but they’re there to guide.",
        "The world has many stories. Tell yours with grace.",
        "You’re not alone, even when it feels that way. We all walk together, even from afar.",
        "Sometimes the best thing you can do is sit, breathe, and wait for clarity.",
        "Every scar you carry tells a tale. Wear them with honor.",
        "I saw you help that stranger yesterday. The world needs more of that.",
        "Your hands are young, but your spirit is old. That’s rare.",
        "There’s strength in silence. Don’t fear it.",
        "Come visit me again. I enjoy our little talks — they remind me that hope still walks these lands."
    };

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
        return dialogues[new Random().nextInt(dialogues.length)];

    }

    @Override
    public ArrayList<Quest> getQuests() {
        return quests;
    }
}
