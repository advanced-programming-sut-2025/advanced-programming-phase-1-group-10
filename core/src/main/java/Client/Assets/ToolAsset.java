package Client.Assets;

import Common.Models.Tools.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;
import java.util.Map;

public class ToolAsset {

    private static final Map<String, Sprite> spriteCache = new HashMap<>();

    private static Sprite load(String path) {
        if (Gdx.files == null) {
            System.out.println("WARNING: Trying to load texture before LibGDX initialized: " + path);
            return null;
        }
        return spriteCache.computeIfAbsent(path, p -> new Sprite(new Texture(Gdx.files.internal(p))));
    }

    public static Sprite show(Tool tool) {
        Quality q = tool.getQuality();

        if (tool instanceof Axe) {
            return switch (q) {
                case STARTER -> load("tools/axe/starter.png");
                case COPPER -> load("tools/axe/copper.png");
                case STEEL -> load("tools/axe/steel.png");
                case GOLD -> load("tools/axe/gold.png");
                case IRIDIUM -> load("tools/axe/iridium.png");
            };
        } else if (tool instanceof Hoe) {
            return switch (q) {
                case STARTER -> load("tools/hoe/starter.png");
                case COPPER -> load("tools/hoe/copper.png");
                case STEEL -> load("tools/hoe/steel.png");
                case GOLD -> load("tools/hoe/gold.png");
                case IRIDIUM -> load("tools/hoe/iridium.png");
            };
        } else if (tool instanceof Pickaxe) {
            return switch (q) {
                case STARTER -> load("tools/pickaxe/starter.png");
                case COPPER -> load("tools/pickaxe/copper.png");
                case STEEL -> load("tools/pickaxe/steel.png");
                case GOLD -> load("tools/pickaxe/gold.png");
                case IRIDIUM -> load("tools/pickaxe/iridium.png");
            };
        } else if (tool instanceof WateringCan) {
            return switch (q) {
                case STARTER -> load("tools/wateringcan/starter.png");
                case COPPER -> load("tools/wateringcan/copper.png");
                case STEEL -> load("tools/wateringcan/steel.png");
                case GOLD -> load("tools/wateringcan/gold.png");
                case IRIDIUM -> load("tools/wateringcan/iridium.png");
            };
        } else if (tool instanceof Seythe) {
            return switch (q) {
                case STARTER -> load("tools/seythe/starter.png");
                case COPPER -> load("tools/seythe/copper.png");
                case STEEL -> load("tools/seythe/steel.png");
                case GOLD -> load("tools/seythe/gold.png");
                case IRIDIUM -> load("tools/seythe/iridium.png");
            };
        } else if (tool instanceof FishingPole) {
            return switch (q) {
                case STARTER -> load("tools/fishingpole/training.png");
                case COPPER -> load("tools/fishingpole/bamboo.png");
                case STEEL -> load("tools/fishingpole/fiberglass.png");
                case GOLD, IRIDIUM -> load("tools/fishingpole/iridium.png");
            };
        } else if (tool instanceof Shear) {
            return load("tools/shears.png");
        } else if (tool instanceof MilkPail) {
            return load("tools/MilkPail.png");
        }

        return null;
    }
}
