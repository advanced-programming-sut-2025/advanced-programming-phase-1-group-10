package Client.Assets;

import Common.Models.DateTime.Season;
import Common.Models.Planets.Crop.CropTypeNormal;
import Common.Models.Planets.FruitType;
import Common.Models.Planets.SeedType;
import Common.Models.Planets.TreeCropType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class TreesAsset {

    private final Map<String, TreeData> treeAssetsMap;
    private final Map<String, Sprite> fruitSprites;
    private final Map<String, Sprite> seedSpries;

    public TreesAsset() {
        treeAssetsMap = new HashMap<>();
        fruitSprites = new HashMap<>();
        seedSpries = new HashMap<>();

        for (TreeCropType treeCropType : TreeCropType.values()) {
            loadTreeAssets(treeCropType);
        }

        for (CropTypeNormal cropTypeNormal : CropTypeNormal.values()){
            loadCropSeedsAssets(cropTypeNormal.getSource());
        }
    }

    private void loadCropSeedsAssets(SeedType seedType){
        String seedName = seedType.getName().replaceAll(" ", "_");
        String path = "Crops/" + seedName + ".png";

        if (TextureCache.exists(path)) {
            Sprite newSeed = new Sprite(TextureCache.get(path));
            seedSpries.put(seedType.getName(), newSeed);
        }
    }

    private void loadTreeAssets(TreeCropType treeCropType) {
        String treeName = treeCropType.getName().replace(" ", "_");
        String treeFolderPath = "Trees/" + treeName + "/";

        TreeData data = new TreeData();

        String seedName = treeCropType.getSource().replaceAll(" ","_");
        // Load Sapling
        String saplingPath = treeFolderPath + seedName + ".png";
        if (TextureCache.exists(saplingPath)) {
            data.saplingSprite = new Sprite(TextureCache.get(saplingPath));
        }

        // Load stages 1-4
        for (int i = 1; i <= 4; i++) {
            String stagePath = treeFolderPath + treeName + "_Stage_" + i + ".png";
            if (TextureCache.exists(stagePath)) {
                data.stageTextures.put(i, TextureCache.get(stagePath));
            }
        }

        // Load Stage 5 with conditional logic
        String fruitStagePath = treeFolderPath + treeName + "_Stage_5_Fruit.png";
        if (TextureCache.exists(fruitStagePath)) {
            data.stage5FruitTexture = TextureCache.get(fruitStagePath);
            String seasonalPath = treeFolderPath + treeName + "_Stage_5.png";
            if (TextureCache.exists(seasonalPath)) {
                Texture seasonalSheet = TextureCache.get(seasonalPath);
                data.stage5SeasonalSheet = seasonalSheet;

                int frameWidth = seasonalSheet.getWidth() / 4;
                int frameHeight = seasonalSheet.getHeight();
                TextureRegion[] frames = new TextureRegion[4];

                frames[0] = new TextureRegion(seasonalSheet, 3 * frameWidth, 0, frameWidth, frameHeight);
                frames[1] = new TextureRegion(seasonalSheet, 2 * frameWidth, 0, frameWidth, frameHeight);
                frames[2] = new TextureRegion(seasonalSheet, 1 * frameWidth, 0, frameWidth, frameHeight);
                frames[3] = new TextureRegion(seasonalSheet, 0, 0, frameWidth, frameHeight);

                data.stage5SeasonalTextures.put(Season.WINTER, frames[0]);
                data.stage5SeasonalTextures.put(Season.FALL, frames[1]);
                data.stage5SeasonalTextures.put(Season.SPRING, frames[2]);
                data.stage5SeasonalTextures.put(Season.SUMMER, frames[3]);
            }
        } else {
            if(treeCropType.equals(TreeCropType.MUSHROOM_TREE)){
                String mushroomPath = "Trees/Mushroom/Mushroom_Stage_5.png";
                if (TextureCache.exists(mushroomPath)) {
                    data.stage5FruitTexture = TextureCache.get(mushroomPath);
                }
            }
            else {
                String seasonalPath = treeFolderPath + treeName + "_Stage_5.png";
                if (TextureCache.exists(seasonalPath)) {
                    Texture seasonalSheet = TextureCache.get(seasonalPath);
                    data.stage5SeasonalSheet = seasonalSheet;

                    int frameWidth = seasonalSheet.getWidth() / 4;
                    int frameHeight = seasonalSheet.getHeight();
                    TextureRegion[] frames = new TextureRegion[4];

                    frames[0] = new TextureRegion(seasonalSheet, 3 * frameWidth, 0, frameWidth, frameHeight);
                    frames[1] = new TextureRegion(seasonalSheet, 2 * frameWidth, 0, frameWidth, frameHeight);
                    frames[2] = new TextureRegion(seasonalSheet, 1 * frameWidth, 0, frameWidth, frameHeight);
                    frames[3] = new TextureRegion(seasonalSheet, 0, 0, frameWidth, frameHeight);

                    data.stage5SeasonalTextures.put(Season.WINTER, frames[0]);
                    data.stage5SeasonalTextures.put(Season.FALL, frames[1]);
                    data.stage5SeasonalTextures.put(Season.SPRING, frames[2]);
                    data.stage5SeasonalTextures.put(Season.SUMMER, frames[3]);
                }
            }
        }

        treeAssetsMap.put(treeCropType.getName(), data);

        // Load Fruit Asset
        FruitType fruitType = treeCropType.getFruitType();
        if (fruitType != null && !fruitSprites.containsKey(fruitType.getName())) {
            String fruitName = fruitType.getName().replace(" ", "_");
            String fruitFilePath = "Trees/" + treeName + "/" + fruitName + ".png";
            if (TextureCache.exists(fruitFilePath)) {
                Sprite fruitSprite = new Sprite(TextureCache.get(fruitFilePath));
                fruitSprites.put(fruitType.getName(), fruitSprite);
            } else {
                Gdx.app.error("TreesAsset", "Failed to load fruit texture for: " + fruitName + " at " + fruitFilePath);
            }
        }
    }

    private TextureRegion[][] createRegionsFromSheet(Texture sheet, int frameWidth, int frameHeight) {
        int rows = sheet.getHeight() / frameHeight;
        int cols = sheet.getWidth() / frameWidth;
        TextureRegion[][] regions = new TextureRegion[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                regions[row][col] = new TextureRegion(sheet, col * frameWidth, row * frameHeight, frameWidth, frameHeight);
            }
        }
        return regions;
    }

    private static class TreeData {
        Sprite saplingSprite;
        Map<Integer, Texture> stageTextures = new HashMap<>();
        Texture stage5FruitTexture;
        Texture stage5SeasonalSheet;
        Map<Season, TextureRegion> stage5SeasonalTextures = new HashMap<>();
    }

    public Sprite getSaplingSprite(String treeName) {
        return treeAssetsMap.get(treeName).saplingSprite;
    }

    public Texture getStageTexture(String treeName, int stage) {
        if (stage >= 1 && stage <= 4) {
            return treeAssetsMap.get(treeName).stageTextures.get(stage);
        }
        return null;
    }

    public Texture getStage5FruitTexture(String treeName) {
        return treeAssetsMap.get(treeName).stage5FruitTexture;
    }

    public TextureRegion getStage5SeasonalTexture(String treeName, Season season) {
        return treeAssetsMap.get(treeName).stage5SeasonalTextures.get(season);
    }

    public Sprite getFruitSprite(String fruitName) {
        return fruitSprites.get(fruitName);
    }

    public Sprite getCropSeedSprite(String cropSeedName){
        return seedSpries.get(cropSeedName);
    }

    public void dispose() {
        for (TreeData data : treeAssetsMap.values()) {
            if (data.saplingSprite != null && data.saplingSprite.getTexture() != null) {
                data.saplingSprite.getTexture().dispose();
            }

            for (Texture texture : data.stageTextures.values()) {
                if (texture != null) {
                    texture.dispose();
                }
            }

            if (data.stage5FruitTexture != null) {
                data.stage5FruitTexture.dispose();
            }

            if (data.stage5SeasonalSheet != null) {
                data.stage5SeasonalSheet.dispose();
            }
        }

        for (Sprite sprite : fruitSprites.values()) {
            if (sprite != null && sprite.getTexture() != null) {
                sprite.getTexture().dispose();
            }
        }

        for (Sprite sprite : seedSpries.values()) {
            if (sprite != null && sprite.getTexture() != null) {
                sprite.getTexture().dispose();
            }
        }

        treeAssetsMap.clear();
        fruitSprites.clear();
        seedSpries.clear();
    }
}
