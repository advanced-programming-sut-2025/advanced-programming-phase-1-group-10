package Common.Models.NPC;

import Common.Models.Cooking.Cooking;
import Common.Models.Cooking.CookingType;
import Common.Models.Crafting.Crafting;
import Common.Models.Crafting.CraftingType;
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

public class Sebastian extends NPC {

    String[] dialogues = {
        "Hey hey! Wanna race to the big tree? I bet I can beat you this time!",
        "I found a shiny rock earlier. I named it ‘Steve’. Wanna see it?",
        "You smell like flowers. Have you been rolling in the meadow again?",
        "Sometimes I pretend the clouds are giant sheep and I’m their shepherd!",
        "Wanna hear a secret? I buried treasure near the old stump. Don’t tell anyone!",
        "The grown-ups are always so serious. Why don’t they play more?",
        "I drew a picture of you and me. You look like a hero!",
        "Let’s build a fort out of leaves and sticks! It'll be the coolest base ever!",
        "I think butterflies have tiny maps in their wings. How else do they know where to go?",
        "The forest makes music if you listen really really hard!",
        "You're like the big sibling I never had. It’s nice.",
        "I saw a rabbit today! It twitched its nose and ran away. I think it was saying hi.",
        "Wanna play hide and seek later? I know the best hiding spot ever!",
        "If I grow up, I wanna be just like you!",
        "The stars are like little night candles. They keep monsters away.",
        "Sometimes I wish I could fly. But jumping really high is almost the same!",
        "You’re always so nice to me. I like that.",
        "I’m not scared of the dark… much. But it’s better with you around.",
        "My friend moved away, but I think you’re even better!",
        "Can we go on an adventure soon? I’ll pack snacks!"
    };

    public Sebastian(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    private final ArrayList<Quest> quests = new ArrayList<>(Arrays.asList(
            new Quest(1,false,"Give 10 Irons and get 1 Diamonds",new Mineral(MineralTypes.IRON,10),0,new Mineral(MineralTypes.DIAMOND,1)),
            new Quest(2,false,"Give a Beehouse and get 5000 gold",new Crafting(CraftingType.BeeHouse,1),5000,null),
            new Quest(3,false,"Give 100 wheats and win 100 bread",new Crop(CropTypeNormal.WHEAT,100),0,new Cooking(CookingType.BREAD,100))
    ));

    @Override
    public Sprite show() {
        return new Sprite(new Texture("npc/sebastian/icon.png"));
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

        return dialogues[new Random().nextInt(dialogues.length)];
    }


    @Override
    public ArrayList<Quest> getQuests() {
        return quests;
    }
}

