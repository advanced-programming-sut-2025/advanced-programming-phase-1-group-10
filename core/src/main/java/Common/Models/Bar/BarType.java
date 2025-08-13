package Common.Models.Bar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public enum BarType {

    COPPER_BAR ("Copper Bar",new Sprite(new Texture("bar/Copper_Bar.png"))),
    IRON_BAR ("Iron Bar",new Sprite(new Texture("bar/Iron_Bar.png"))),
    GOLD_BAR  ("Gold Bar",new Sprite(new Texture("bar/Gold_Bar.png"))),
    IRIDIUM_BAR("Iridium Bar",new Sprite(new Texture("bar/Iridium_Bar.png"))),
    RADIOACTIVE_BAR("Radioactive Bar",new Sprite(new Texture("bar/Radioactive_Bar.png")))
        ;

    private final String name;
    private final Sprite sprite;

    BarType(String name, Sprite sprite) {
        this.name = name;
        this.sprite = sprite;
    }

    public String getName() {
        return name;
    }

    public Sprite getSprite() {
        return sprite;
    }

}
