package Client.Assets;

import Common.Models.Planets.Crop.ForagingCropType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;
import java.util.Map;

public class ForagingCropAsset {
    private final Map<String, Sprite> cropSprites;

    public ForagingCropAsset() {
        cropSprites = new HashMap<>();

        for (ForagingCropType cropType : ForagingCropType.values()) {
            loadCropAsset(cropType);
        }
    }

    private void loadCropAsset(ForagingCropType cropType) {
        String cropName = cropType.getName().replaceAll(" ", "_");
        String filePath = "ForagingCrops/" + cropName + ".png";

        try {
            if (TextureCache.exists(filePath)) {
                Texture texture = TextureCache.get(filePath);
                Sprite sprite = new Sprite(texture);
                cropSprites.put(cropType.getName(), sprite);
                System.out.println("Loaded crop asset: " + filePath);
            } else {
                System.err.println("Crop asset not found: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error loading crop asset: " + filePath);
            e.printStackTrace();
        }
    }

    public Sprite getCropSprite(String cropName) {
        return cropSprites.get(cropName);
    }

    public Sprite getCropSprite(ForagingCropType cropType) {
        return getCropSprite(cropType.getName());
    }

    public void dispose() {
        cropSprites.clear();
    }
}
