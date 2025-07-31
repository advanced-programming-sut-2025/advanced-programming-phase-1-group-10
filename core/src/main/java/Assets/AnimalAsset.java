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


        String singleTexturePath = "Animals/Textures/" + animalName + ".png";
        Texture singleTexture = new Texture(Gdx.files.internal(singleTexturePath));


        int frameCols = 4;
        int frameRows = 6;
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
            spriteSheet.getWidth() / frameCols,
            spriteSheet.getHeight() / frameRows);


        AnimalData animalData = new AnimalData();
        animalData.spriteSheet = spriteSheet;
        animalData.singleTexture = singleTexture;

        animalData.idleFrame = tmp[0][0];
        animalData.walkDownAnimation = createAnimation(tmp[0]);
        animalData.walkRightAnimation = createAnimation(tmp[1]);
        animalData.walkUpAnimation = createAnimation(tmp[2]);
        animalData.walkLeftAnimation = createAnimation(tmp[3]);
        animalData.peckingAnimation = createAnimation(tmp[4]);
        animalData.specialActionAnimation = createAnimation(tmp[5]);

        animalAssetsMap.put(animalName, animalData);
    }

    private Animation<TextureRegion> createAnimation(TextureRegion[] framesRow) {
        Array<TextureRegion> frames = new Array<>(TextureRegion.class);
        for (TextureRegion region : framesRow) {
            frames.add(region);
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
        Animation<TextureRegion> peckingAnimation;
        Animation<TextureRegion> specialActionAnimation;
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

    public Animation<TextureRegion> getPeckingAnimation(String animalName) {
        return animalAssetsMap.get(animalName).peckingAnimation;
    }

    public Animation<TextureRegion> getSpecialActionAnimation(String animalName) {
        return animalAssetsMap.get(animalName).specialActionAnimation;
    }

    public TextureRegion getIdleFrame(String animalName) {
        return animalAssetsMap.get(animalName).idleFrame;
    }


    public Texture getSingleTexture(String animalName) {
        return animalAssetsMap.get(animalName).singleTexture;
    }

    public void dispose() {
        for (AnimalData data : animalAssetsMap.values()) {
            data.spriteSheet.dispose();
            data.singleTexture.dispose();
        }
    }
}
