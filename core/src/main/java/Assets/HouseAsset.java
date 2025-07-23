package Assets;

import Models.Map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HouseAsset {
    private final TextureRegion[][] houseRegions;

    public HouseAsset(Texture texture) {
        houseRegions = TextureRegion.split(texture, Map.tileSize, Map.tileSize);
    }

    public TextureRegion[][] getHouseRegions() {
        return houseRegions;
    }
}
