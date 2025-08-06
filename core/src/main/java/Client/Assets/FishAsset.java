package Client.Assets;

import Common.Models.Animal.FishType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ObjectMap;

public class FishAsset {
    private final ObjectMap<FishType, Sprite> fishSprites;

    public FishAsset() {
        fishSprites = new ObjectMap<>();
        loadFishAssets();
    }

    private void loadFishAssets() {

        for (FishType fishType : FishType.values()) {
            String fishName = fishType.getName().replace(" ", "_");
            String texturePath = "Animals/Fish/" + fishName + ".png";

            try {
                Texture fishTexture = new Texture(Gdx.files.internal(texturePath));
                Sprite fishSprite = new Sprite(fishTexture);
                fishSprites.put(fishType, fishSprite);
            } catch (Exception e) {
                Gdx.app.error("FishAsset", "Error loading fish asset for " + fishName, e);

                // default fish
                Texture defaultTexture = new Texture(Gdx.files.internal("Animals/Fish/Tuna.png"));
                fishSprites.put(fishType, new Sprite(defaultTexture));
            }
        }
    }

    public Sprite getFishSprite(FishType fishType) {
        return new Sprite(fishSprites.get(fishType));
    }

    public void dispose() {

        for (Sprite sprite : fishSprites.values()) {
            sprite.getTexture().dispose();
        }
    }

    public Sprite show(FishType fishType){
        return fishSprites.get(fishType);
    }
}
