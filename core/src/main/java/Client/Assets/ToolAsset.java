package Client.Assets;

import Common.Models.Tools.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ToolAsset {

    // AXE
    private final Sprite starterAxe = new Sprite(new Texture("tools/axe/starter.png"));
    private final Sprite copperAxe = new Sprite(new Texture("tools/axe/copper.png"));
    private final Sprite steelAxe = new Sprite(new Texture("tools/axe/steel.png"));
    private final Sprite goldAxe = new Sprite(new Texture("tools/axe/gold.png"));
    private final Sprite iridiumAxe = new Sprite(new Texture("tools/axe/iridium.png"));

    // HOE
    private final Sprite starterHoe = new Sprite(new Texture("tools/hoe/starter.png"));
    private final Sprite copperHoe = new Sprite(new Texture("tools/hoe/copper.png"));
    private final Sprite steelHoe = new Sprite(new Texture("tools/hoe/steel.png"));
    private final Sprite goldHoe = new Sprite(new Texture("tools/hoe/gold.png"));
    private final Sprite iridiumHoe = new Sprite(new Texture("tools/hoe/iridium.png"));

    // PICKAXE
    private final Sprite starterPickaxe = new Sprite(new Texture("tools/pickaxe/starter.png"));
    private final Sprite copperPickaxe = new Sprite(new Texture("tools/pickaxe/copper.png"));
    private final Sprite steelPickaxe = new Sprite(new Texture("tools/pickaxe/steel.png"));
    private final Sprite goldPickaxe = new Sprite(new Texture("tools/pickaxe/gold.png"));
    private final Sprite iridiumPickaxe = new Sprite(new Texture("tools/pickaxe/iridium.png"));

    // WATERING CAN
    private final Sprite starterCan = new Sprite(new Texture("tools/wateringcan/starter.png"));
    private final Sprite copperCan = new Sprite(new Texture("tools/wateringcan/copper.png"));
    private final Sprite steelCan = new Sprite(new Texture("tools/wateringcan/steel.png"));
    private final Sprite goldCan = new Sprite(new Texture("tools/wateringcan/gold.png"));
    private final Sprite iridiumCan = new Sprite(new Texture("tools/wateringcan/iridium.png"));

    // SCYTHE
    private final Sprite starterScythe = new Sprite(new Texture("tools/seythe/starter.png"));
    private final Sprite copperScythe = new Sprite(new Texture("tools/seythe/copper.png"));
    private final Sprite steelScythe = new Sprite(new Texture("tools/seythe/steel.png"));
    private final Sprite goldScythe = new Sprite(new Texture("tools/seythe/gold.png"));
    private final Sprite iridiumScythe = new Sprite(new Texture("tools/seythe/iridium.png"));

    // FISHINH POLE
    private final Sprite trainingpole = new Sprite(new Texture("tools/fishingpole/training.png"));
    private final Sprite bamboopole = new Sprite(new Texture("tools/fishingpole/bamboo.png"));
    private final Sprite fiberglasspole = new Sprite(new Texture("tools/fishingpole/fiberglass.png"));
    private final Sprite iridiumpole = new Sprite(new Texture("tools/fishingpole/iridium.png"));

    //SHEARS
    private final Sprite shears = new Sprite(new Texture("tools/shears.png"));

    //MILKPAIL
    private final Sprite milkPail = new Sprite(new Texture("tools/MilkPail.png"));

    public Sprite show(Tool tool) {
        Quality q = tool.getQuality();

        if (tool instanceof Axe) {
            return switch (q) {
                case STARTER -> starterAxe;
                case COPPER -> copperAxe;
                case STEEL -> steelAxe;
                case GOLD -> goldAxe;
                case IRIDIUM -> iridiumAxe;
            };
        } else if (tool instanceof Hoe) {
            return switch (q) {
                case STARTER -> starterHoe;
                case COPPER -> copperHoe;
                case STEEL -> steelHoe;
                case GOLD -> goldHoe;
                case IRIDIUM -> iridiumHoe;
            };
        } else if (tool instanceof Pickaxe) {
            return switch (q) {
                case STARTER -> starterPickaxe;
                case COPPER -> copperPickaxe;
                case STEEL -> steelPickaxe;
                case GOLD -> goldPickaxe;
                case IRIDIUM -> iridiumPickaxe;
            };
        } else if (tool instanceof WateringCan) {
            return switch (q) {
                case STARTER -> starterCan;
                case COPPER -> copperCan;
                case STEEL -> steelCan;
                case GOLD -> goldCan;
                case IRIDIUM -> iridiumCan;
            };
        } else if (tool instanceof Seythe) {
            return switch (q) {
                case STARTER -> starterScythe;
                case COPPER -> copperScythe;
                case STEEL -> steelScythe;
                case GOLD -> goldScythe;
                case IRIDIUM -> iridiumScythe;
            };
        } else if (tool instanceof FishingPole){
            return switch (q) {
                case STARTER -> trainingpole;
                case COPPER -> bamboopole;
                case STEEL -> fiberglasspole;
                case GOLD, IRIDIUM -> iridiumpole;
            };
        } else if(tool instanceof Shear){
            return shears;
        } else if(tool instanceof MilkPail){
            return milkPail;
        }

        return null; // Unknown tool type
    }
}

