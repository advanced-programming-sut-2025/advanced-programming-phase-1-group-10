package Common.Models.Tools;

import Client.Assets.ToolAsset;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MilkPail extends Tool {

    public MilkPail(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    @Override
    public String getName() {
        return "Milk Pail";
    }

    @Override
    public Sprite show() {
        return ToolAsset.show(this);
    }
}
