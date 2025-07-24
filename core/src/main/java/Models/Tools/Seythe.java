package Models.Tools;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Seythe extends Tool {
    public Seythe(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    @Override
    public Sprite show() {
        return toolAsset.show(this);
    }

    private final String name = "Seythe";

    @Override
    public String getName() {
        return name;
    }
}
