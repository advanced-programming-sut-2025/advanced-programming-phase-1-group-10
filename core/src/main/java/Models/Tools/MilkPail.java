package Models.Tools;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class MilkPail extends Tool {

    public MilkPail(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    private final String name = "Milk Pail";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Sprite show() {
        return toolAsset.show(this);
    }
}
