package Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class AnimalAsset {

    private static final float FRAME_DURATION = 0.15f;
    private final Map<String, AnimalData> animalAssetsMap;

    public AnimalAsset() {
        animalAssetsMap = new HashMap<>();

        String[] animalNames = {"Chicken", "Duck", "Rabbit", "Dinosaur", "Cow", "Goat", "Sheep", "Pig"};

        for (String animalName : animalNames) {
            loadAnimalAssets(animalName);
        }
    }

    private void loadAnimalAssets(String animalName) {
        String spriteSheetPath = "Animals/Sprites/" + animalName + ".png";
        Texture spriteSheet = new Texture(Gdx.files.internal(spriteSheetPath));
        spriteSheet.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        String singleTexturePath = "Animals/Textures/" + animalName + ".png";
        Texture singleTexture = new Texture(Gdx.files.internal(singleTexturePath));

        // we can change this variables for each animal
        int rows = 5;
        int cols = 4;


         if (animalName.equals("Rabbit") || animalName.equals("Chicken") || animalName.equals("Dinosaur")) {
             rows = 7;
         } else if(animalName.equals("Duck")){
             rows = 14;
         }

        int frameWidth = spriteSheet.getWidth() / cols;
        int frameHeight = spriteSheet.getHeight() / rows;

        TextureRegion[][] tmp = createRegionsFromSheet(spriteSheet, frameWidth, frameHeight);

        AnimalData animalData = new AnimalData();
        animalData.spriteSheet = spriteSheet;
        animalData.singleTexture = singleTexture;

        animalData.idleFrame = tmp[0][0];


        switch (animalName) {
            case "Cow", "Goat", "Sheep", "Pig", "Rabbit", "Dinosaur" -> {
                animalData.walkDownAnimation = createExactAnimation(tmp[0], 0, 4);
                animalData.walkLeftAnimation = createFlippedAnimation(tmp[1], 0, 4);
                animalData.walkRightAnimation = createExactAnimation(tmp[1], 0, 4);
                animalData.walkUpAnimation = createExactAnimation(tmp[2], 0, 4);
                if (animalName.equals("Rabbit") || animalName.equals("Dinosaur")) {
                    animalData.petAnimation = createExactAnimation(tmp[6], 0, 4);
                } else
                    animalData.petAnimation = createExactAnimation(tmp[4], 0, 4);
            }
            case "Chicken" -> {
                animalData.walkLeftAnimation = createExactAnimation(tmp[0], 0, 4);
                animalData.walkRightAnimation = createExactAnimation(tmp[1], 0, 4);
                animalData.walkUpAnimation = createExactAnimation(tmp[2], 0, 4);
                animalData.walkDownAnimation = createExactAnimation(tmp[6], 0, 4);
                animalData.petAnimation = createExactAnimation(tmp[4],0,4);
            }
            case "Duck" -> {
                animalData.walkLeftAnimation = createExactAnimation(tmp[0], 0, 4);
                animalData.walkRightAnimation = createFlippedAnimation(tmp[0], 0, 4);
                animalData.walkUpAnimation = createExactAnimation(tmp[2], 0, 4);
                animalData.walkDownAnimation = createFlippedAnimation(tmp[2], 0, 4);
                animalData.petAnimation = createExactAnimation(tmp[13],0,4);
            }
        }

        animalAssetsMap.put(animalName, animalData);
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

    private Animation<TextureRegion> createExactAnimation(TextureRegion[] framesRow, int startIndex, int frameCount) {
        Array<TextureRegion> frames = new Array<>(TextureRegion.class);
        for (int i = startIndex; i < startIndex + frameCount && i < framesRow.length; i++) {
            frames.add(new TextureRegion(framesRow[i]));
        }
        return new Animation<>(FRAME_DURATION, frames, Animation.PlayMode.LOOP);
    }

    private Animation<TextureRegion> createFlippedAnimation(TextureRegion[] framesRow, int startIndex, int frameCount) {
        Array<TextureRegion> frames = new Array<>(TextureRegion.class);
        for (int i = startIndex; i < startIndex + frameCount && i < framesRow.length; i++) {
            TextureRegion flipped = new TextureRegion(framesRow[i]);
            flipped.flip(true, false);
            frames.add(flipped);
        }
        return new Animation<>(FRAME_DURATION, frames, Animation.PlayMode.LOOP);
    }

    private static class AnimalData {
        Texture spriteSheet;
        Texture singleTexture;
        TextureRegion idleFrame;
        Animation<TextureRegion> walkDownAnimation;
        Animation<TextureRegion> walkLeftAnimation;
        Animation<TextureRegion> walkRightAnimation;
        Animation<TextureRegion> walkUpAnimation;
        Animation<TextureRegion> petAnimation;
    }

    public Animation<TextureRegion> getWalkDownAnimation(String animalName) {
        return animalAssetsMap.get(animalName).walkDownAnimation;
    }

    public Animation<TextureRegion> getWalkLeftAnimation(String animalName) {
        return animalAssetsMap.get(animalName).walkLeftAnimation;
    }

    public Animation<TextureRegion> getWalkRightAnimation(String animalName) {
        return animalAssetsMap.get(animalName).walkRightAnimation;
    }

    public Animation<TextureRegion> getWalkUpAnimation(String animalName) {
        return animalAssetsMap.get(animalName).walkUpAnimation;
    }

    public TextureRegion getIdleFrame(String animalName) {
        return animalAssetsMap.get(animalName).idleFrame;
    }

    public Texture getSingleTexture(String animalName) {
        return animalAssetsMap.get(animalName).singleTexture;
    }

    public Animation<TextureRegion> getPetAnimation(String animalName){
        return animalAssetsMap.get(animalName).petAnimation;
    }

    public void dispose() {
        for (AnimalData data : animalAssetsMap.values()) {
            data.spriteSheet.dispose();
            data.singleTexture.dispose();
        }
    }
}
