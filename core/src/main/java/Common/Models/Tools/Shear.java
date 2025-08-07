package Common.Models.Tools;

import Client.Assets.ToolAsset;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Shear extends Tool {
    public Shear(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }


    @Override
    public String getName() {
        return "Shear";
    }

    @Override
    public Sprite show() {
        return ToolAsset.show(this);
    }
}
