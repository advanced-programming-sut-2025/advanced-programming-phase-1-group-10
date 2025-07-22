package Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class PlayerAsset {




    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 4;
    private static final float FRAME_DURATION = 0.1f;

    private TextureRegion idleFrame;

    private Animation<TextureRegion> walkDownAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> walkRightAnimation;
    private Animation<TextureRegion> walkUpAnimation;

    private Texture spriteSheet;

    public PlayerAsset() {
        spriteSheet = new Texture(Gdx.files.internal("player/movement.png"));

        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
            spriteSheet.getWidth() / FRAME_COLS,
            spriteSheet.getHeight() / FRAME_ROWS);

        idleFrame = tmp[0][0]; // First frame (idle facing down)

        walkDownAnimation = createAnimation(tmp[0]); // First row
        walkRightAnimation = createAnimation(tmp[1]); // Second row
        walkUpAnimation = createAnimation(tmp[2]); // Third row
        walkLeftAnimation = createAnimation(tmp[3]); // Fourth row
    }

    private Animation<TextureRegion> createAnimation(TextureRegion[] framesRow) {
        Array<TextureRegion> frames = new Array<>(TextureRegion.class);
        for (TextureRegion region : framesRow) {
            frames.add(region);
        }
        return new Animation<>(FRAME_DURATION, frames, Animation.PlayMode.LOOP);
    }

    public Animation<TextureRegion> getWalkDownAnimation() {
        return walkDownAnimation;
    }

    public Animation<TextureRegion> getWalkLeftAnimation() {
        return walkLeftAnimation;
    }

    public Animation<TextureRegion> getWalkRightAnimation() {
        return walkRightAnimation;
    }

    public Animation<TextureRegion> getWalkUpAnimation() {
        return walkUpAnimation;
    }

    public TextureRegion getIdleFrame() {
        return idleFrame;
    }

    public void dispose() {
        spriteSheet.dispose();
    }
}
