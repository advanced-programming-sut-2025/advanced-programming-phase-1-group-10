package Assets;

import Models.Map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GreenHouseAsset {
    private final TextureRegion[][] broken;
    private final TextureRegion[][] greenhouseInside;
    private final TextureRegion[][] greenhouuseOutside;


    public GreenHouseAsset() {
        broken = TextureRegion.split(new Texture("place/greenhouse/greenhouseBroken.png"), Map.tileSize, Map.tileSize);
        greenhouseInside = TextureRegion.split(new Texture("place/greenhouse/greenhouseInside.png"), Map.tileSize, Map.tileSize);
        greenhouuseOutside = TextureRegion.split(new Texture("place/greenhouse/greenhouseOutside.png"), Map.tileSize, Map.tileSize);
    }

    public TextureRegion[][] getBroken() {
        return broken;
    }

    public TextureRegion[][] getGreenhouseInside() {
        return greenhouseInside;
    }

    public TextureRegion[][] getGreenhouuseOutside() {
        return greenhouuseOutside;
    }
}
