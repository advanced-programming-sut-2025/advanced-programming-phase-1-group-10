package Client.Assets;

import Common.Models.PlayerStuff.Gender;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class PlayerAsset {




    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 4;
    private static final float FRAME_DURATION = 0.1f;

    private final TextureRegion idleFrameMale;

    private final Animation<TextureRegion> walkDownAnimationMale;
    private final Animation<TextureRegion> walkLeftAnimationMale;
    private final Animation<TextureRegion> walkRightAnimationMale;
    private final Animation<TextureRegion> walkUpAnimationMale;


    private final TextureRegion idleFrameFemale;

    private final Animation<TextureRegion> walkDownAnimationFemale;
    private final Animation<TextureRegion> walkLeftAnimationFemale;
    private final Animation<TextureRegion> walkRightAnimationFemale;
    private final Animation<TextureRegion> walkUpAnimationFemale;

    private Texture spriteSheet;

    public PlayerAsset() {
        spriteSheet = new Texture(Gdx.files.internal("player/movementMale.png"));

        TextureRegion[][] tmpMale = TextureRegion.split(spriteSheet,
            spriteSheet.getWidth() / FRAME_COLS,
            spriteSheet.getHeight() / FRAME_ROWS);

        idleFrameMale = tmpMale[0][0]; // First frame (idle facing down)

        walkDownAnimationMale = createAnimation(tmpMale[0]); // First row
        walkRightAnimationMale = createAnimation(tmpMale[1]); // Second row
        walkUpAnimationMale = createAnimation(tmpMale[2]); // Third row
        walkLeftAnimationMale = createAnimation(tmpMale[3]); // Fourth row

        spriteSheet = new Texture(Gdx.files.internal("player/movementFemale.png"));

        TextureRegion[][] tmpFemale = TextureRegion.split(spriteSheet,
            spriteSheet.getWidth() / FRAME_COLS,
            spriteSheet.getHeight() / FRAME_ROWS);

        idleFrameFemale = tmpFemale[0][0]; // First frame (idle facing down)

        walkDownAnimationFemale = createAnimation(tmpFemale[0]); // First row
        walkRightAnimationFemale = createAnimation(tmpFemale[1]); // Second row
        walkUpAnimationFemale = createAnimation(tmpFemale[2]); // Third row
        walkLeftAnimationFemale = createAnimation(tmpFemale[3]); // Fourth row
    }

    private Animation<TextureRegion> createAnimation(TextureRegion[] framesRow) {
        Array<TextureRegion> frames = new Array<>(TextureRegion.class);
        for (TextureRegion region : framesRow) {
            frames.add(region);
        }
        return new Animation<>(FRAME_DURATION, frames, Animation.PlayMode.LOOP);
    }

    public Animation<TextureRegion> getWalkDownAnimation(Gender gender) {
        return gender == Gender.Male ? walkDownAnimationMale : walkDownAnimationFemale;
    }

    public Animation<TextureRegion> getWalkLeftAnimation(Gender gender) {
        return gender == Gender.Male ? walkLeftAnimationMale : walkLeftAnimationFemale;
    }

    public Animation<TextureRegion> getWalkRightAnimation(Gender gender) {
        return gender == Gender.Male ? walkRightAnimationMale : walkRightAnimationFemale;
    }

    public Animation<TextureRegion> getWalkUpAnimation(Gender gender) {
        return gender == Gender.Male ? walkUpAnimationMale : walkUpAnimationFemale;
    }

    public TextureRegion getIdleFrame(Gender gender) {
        return gender == Gender.Male ? idleFrameMale : idleFrameFemale;
    }

    public void dispose() {
        spriteSheet.dispose();
    }
}
