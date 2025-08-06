package Client.Assets;

import Common.Models.Planets.Crop.CropTypeNormal;
import Common.Models.Planets.SeedType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;
import java.util.Map;

public class CropAsset {
    private final Map<String, Map<Integer, Sprite>> cropStageSprites;
    private final Map<String, Sprite> seedSprites;
    private final Map<String, Sprite> fruitSprites;
    private final int MAX_STAGES = 10;
    public static final Sprite mixedSeedSprite = new Sprite(new Texture("Crops/Mixed_Seeds.png"));


    public CropAsset() {
        cropStageSprites = new HashMap<>();
        seedSprites = new HashMap<>();
        fruitSprites = new HashMap<>();

        for (CropTypeNormal cropType : CropTypeNormal.values()) {
            loadCropAssets(cropType);
            loadFruitAsset(cropType);
        }
    }

    private void loadCropAssets(CropTypeNormal cropType) {
        String cropName = cropType.getName().replaceAll(" ", "_");
        Map<Integer, Sprite> stageSprites = new HashMap<>();

        loadSeedSprite(cropType.getSource());

        for (int stage = 1; stage <= MAX_STAGES; stage++) {
            String stagePath = "Crops/" + cropName + "_Stage_" + stage + ".png";
            if (TextureCache.exists(stagePath)) {
                Sprite stageSprite = new Sprite(TextureCache.get(stagePath));
                stageSprites.put(stage, stageSprite);
                System.out.println("Loaded crop stage: " + stagePath);
            } else {
                if (stage > cropType.getCropTypes().size()) {
                    System.out.println("No more stages found for: " + cropName + " after stage " + (stage - 1));
                    break;
                } else {
                    System.err.println("Required crop stage not found: " + stagePath);
                }
            }
        }

        if (!stageSprites.isEmpty()) {
            cropStageSprites.put(cropType.getName(), stageSprites);
        }
    }

    private void loadFruitAsset(CropTypeNormal cropType) {
        String cropName = cropType.getName().replaceAll(" ", "_");
        String fruitPath = "Crops/" + cropName + ".png";

        if (TextureCache.exists(fruitPath)) {
            Sprite fruitSprite = new Sprite(TextureCache.get(fruitPath));
            fruitSprites.put(cropType.getName(), fruitSprite);
            System.out.println("Loaded crop fruit: " + fruitPath);
        } else {
            System.err.println("Crop fruit sprite not found: " + fruitPath);
        }
    }

    private void loadSeedSprite(SeedType seedType) {
        if (seedType == null) return;

        String seedName = seedType.getName().replaceAll(" ", "_");
        String seedPath = "Crops/" + seedName + ".png";

        if (TextureCache.exists(seedPath)) {
            Sprite seedSprite = new Sprite(TextureCache.get(seedPath));
            seedSprites.put(seedType.getName(), seedSprite);
            System.out.println("Loaded seed sprite: " + seedPath);
        } else {
            System.err.println("Seed sprite not found: " + seedPath);
        }
    }

    public Sprite getCropStageSprite(String cropName, int stage) {
        Map<Integer, Sprite> stages = cropStageSprites.get(cropName);
        if (stages != null) {
            return stages.get(stage);
        }
        return null;
    }

    public Sprite getSeedSprite(String seedName) {
        return seedSprites.get(seedName);
    }

    public Sprite getFruitSprite(String cropName) {
        return fruitSprites.get(cropName);
    }

    public boolean hasStageSprite(String cropName, int stage) {
        Map<Integer, Sprite> stages = cropStageSprites.get(cropName);
        return stages != null && stages.containsKey(stage);
    }

    public int getMaxStage(String cropName) {
        Map<Integer, Sprite> stages = cropStageSprites.get(cropName);
        if (stages == null || stages.isEmpty()) {
            return 0;
        }

        int maxStage = 0;
        for (int stage : stages.keySet()) {
            if (stage > maxStage) {
                maxStage = stage;
            }
        }
        return maxStage;
    }

    public void dispose() {
        for (Map<Integer, Sprite> stages : cropStageSprites.values()) {
            stages.clear();
        }
        cropStageSprites.clear();
        seedSprites.clear();
        fruitSprites.clear();
    }
}
