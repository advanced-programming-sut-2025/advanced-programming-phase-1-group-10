package Assets;

import Models.Animal.AnimalProductType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;
import java.util.Map;

public class AnimalProductAsset {

    private final Map<String, Sprite> productSprites;

    public AnimalProductAsset() {
        productSprites = new HashMap<>();
        loadAssets();
    }

    private void loadAssets() {
        for (AnimalProductType productType : AnimalProductType.values()) {
            String productName = productType.getName();
            String fileName = productName.replace(" ", "_") + ".png";
            String filePath = "Animals/AnimalProducts/" + fileName;

            try {
                Texture texture = new Texture(Gdx.files.internal(filePath));
                Sprite sprite = new Sprite(texture);
                productSprites.put(productName, sprite);
            } catch (Exception e) {
                Gdx.app.error("AnimalProductAsset", "Failed to load texture for: " + productName + " at " + filePath, e);
            }
        }
    }

    public Sprite getSprite(String productName) {
        return productSprites.get(productName);
    }

    public Sprite show(AnimalProductType animalProductType){
        return productSprites.get(animalProductType.getName());
    }

    public void dispose() {
        for (Sprite sprite : productSprites.values()) {
            if (sprite.getTexture() != null) {
                sprite.getTexture().dispose();
            }
        }
    }
}
