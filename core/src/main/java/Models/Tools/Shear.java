package Models.Tools;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Shear extends Tool {
    public Shear(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    private final String name = "Shear";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Sprite show() {
        return toolAsset.show(this);
    }
}
