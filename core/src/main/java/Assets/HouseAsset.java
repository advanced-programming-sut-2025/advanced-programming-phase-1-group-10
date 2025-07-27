package Assets;

import Models.Map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HouseAsset {
    private final TextureRegion[][] houseRegions;
    private final TextureRegion[][] houseInsideRegions;

    public HouseAsset() {
        houseRegions = TextureRegion.split(new Texture("place/house/houseOutside.png"), Map.tileSize, Map.tileSize);
        houseInsideRegions = TextureRegion.split(new Texture("place/house/houseInside.png"), Map.tileSize, Map.tileSize);
    }

    public TextureRegion[][] getHouseRegions() {
        return houseRegions;
    }

    public TextureRegion[][] getHouseInsideRegions() {
        return houseInsideRegions;
    }
}
