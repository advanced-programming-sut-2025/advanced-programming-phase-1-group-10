package Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FaintAsset {

    private static final int FRAME_COUNT = 6;
    private final TextureRegion[] frames;
    private final Animation<TextureRegion> animation;

    /**
     * @param frameDuration seconds per frame when playing the faint animation
     */
    public FaintAsset(float frameDuration) {
        frames = new TextureRegion[FRAME_COUNT];
        for (int i = 0; i < FRAME_COUNT; i++) {
            String path = "faint/" + (i + 1) + ".png";
            Texture t = new Texture(Gdx.files.internal(path));
            frames[i] = new TextureRegion(t);
        }
        animation = new Animation<>(frameDuration, frames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    /** Default constructor with 0.1s per frame */
    public FaintAsset() {
        this(0.1f);
    }

    /** Get raw frame by index (0-based) */
    public TextureRegion getFrame(int index) {
        if (index < 0 || index >= FRAME_COUNT) throw new IllegalArgumentException("Frame index out of range");
        return frames[index];
    }

    /** Get the animation object to drive playback */
    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
