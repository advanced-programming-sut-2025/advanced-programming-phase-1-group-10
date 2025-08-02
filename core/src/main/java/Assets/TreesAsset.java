package Assets;

import Models.DateTime.Season;
import Models.Planets.FruitType;
import Models.Planets.TreeCropType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class TreesAsset {

    private final Map<String, TreeData> treeAssetsMap;
    private final Map<String, Sprite> fruitSprites;

    public TreesAsset() {
        treeAssetsMap = new HashMap<>();
        fruitSprites = new HashMap<>();

        for (TreeCropType treeCropType : TreeCropType.values()) {
            loadTreeAssets(treeCropType);
        }
    }

    private void loadTreeAssets(TreeCropType treeCropType) {
        String treeName = treeCropType.getName().replace(" ", "_");
        String treeFolderPath = "Trees/" + treeName + "/";

        TreeData data = new TreeData();

        // Load Sapling
        FileHandle saplingFile = Gdx.files.internal(treeFolderPath + treeName + "_Sapling.png");
        if (saplingFile.exists()) {
            data.saplingTexture = new Texture(saplingFile);
        }

        // Load stages 1-4
        for (int i = 1; i <= 4; i++) {
            FileHandle stageFile = Gdx.files.internal(treeFolderPath + treeName + "_Stage_" + i + ".png");
            if (stageFile.exists()) {
                data.stageTextures.put(i, new Texture(stageFile));
            }
        }

        // Load Stage 5 with conditional logic
        FileHandle fruitFile = Gdx.files.internal(treeFolderPath + treeName + "_Stage_5_Fruit.png");
        if (fruitFile.exists()) {
            data.stage5FruitTexture = new Texture(fruitFile);
        } else {
            FileHandle seasonalFile = Gdx.files.internal(treeFolderPath + treeName + "_Stage_5.png");
            if (seasonalFile.exists()) {
                Texture seasonalSheet = new Texture(seasonalFile);
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
        treeAssetsMap.put(treeCropType.getName(), data);

        // Load Fruit Asset
        FruitType fruitType = treeCropType.getFruitType();
        if (fruitType != null && !fruitSprites.containsKey(fruitType.getName())) {
            String fruitName = fruitType.getName().replace(" ", "_");
            String fruitFilePath = "Trees/" + treeName + "/" + fruitName +".png";
            try {
                Texture fruitTexture = new Texture(Gdx.files.internal(fruitFilePath));
                Sprite fruitSprite = new Sprite(fruitTexture);
                fruitSprites.put(fruitType.getName(), fruitSprite);
            } catch (Exception e) {
                Gdx.app.error("TreesAsset", "Failed to load fruit texture for: " + fruitName + " at " + fruitFilePath, e);
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
        Texture saplingTexture;
        Map<Integer, Texture> stageTextures = new HashMap<>();
        Texture stage5FruitTexture;
        Texture stage5SeasonalSheet;
        Map<Season, TextureRegion> stage5SeasonalTextures = new HashMap<>();
    }

    public Texture getSaplingTexture(String treeName) {
        return treeAssetsMap.get(treeName).saplingTexture;
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

    public void dispose() {
        for (TreeData data : treeAssetsMap.values()) {
            if (data.saplingTexture != null) data.saplingTexture.dispose();
            for (Texture texture : data.stageTextures.values()) {
                texture.dispose();
            }
            if (data.stage5FruitTexture != null) data.stage5FruitTexture.dispose();
            if (data.stage5SeasonalSheet != null) data.stage5SeasonalSheet.dispose();
        }
        for (Sprite sprite : fruitSprites.values()) {
            if (sprite.getTexture() != null) {
                sprite.getTexture().dispose();
            }
        }
    }
}
