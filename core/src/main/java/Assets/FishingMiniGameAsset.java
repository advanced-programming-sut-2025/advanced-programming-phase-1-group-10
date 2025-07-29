package Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Disposable;

public class FishingMiniGameAsset implements Disposable {
    private Texture backgroundTexture;
    private Texture fishingBarTexture;
    private Texture fishTexture;
    private Texture bobberTexture;
    private Texture greenBarTexture;

    public FishingMiniGameAsset() {

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();
        backgroundTexture = new Texture(pixmap);
        pixmap.dispose();

        try {
            fishingBarTexture = new Texture(Gdx.files.internal("Animals/Fish/MiniGame/water_lane.png"));
        } catch (Exception e) {

            Pixmap pixmap1 = new Pixmap(60, 300, Pixmap.Format.RGBA8888);
            pixmap.setColor(0.8f, 0.8f, 0.8f, 1);
            pixmap.fillRectangle(0, 0, 60, 300);
            pixmap.setColor(0.6f, 0.8f, 1, 1);
            pixmap.fillRectangle(15, 10, 30, 280);
            fishingBarTexture = new Texture(pixmap);
            pixmap.dispose();
        }

        try {
            fishTexture = new Texture(Gdx.files.internal("Animals/Fish/MiniGame/fish_icon.png"));
        } catch (Exception e) {
            Pixmap pixmap1 = new Pixmap(40, 25, Pixmap.Format.RGBA8888);
            pixmap1.setColor(0.2f, 0.6f, 1, 1);
            pixmap1.fillCircle(20, 12, 10);
            pixmap1.setColor(0.1f, 0.3f, 0.8f, 1);
            pixmap1.fillTriangle(30, 12, 40, 5, 40, 20);
            fishTexture = new Texture(pixmap1);
            pixmap1.dispose();
        }

        try {
            bobberTexture = new Texture(Gdx.files.internal("Minigames/Fishing/bobber.png"));
        } catch (Exception e) {
            Pixmap pixmap1 = new Pixmap(40, 15, Pixmap.Format.RGBA8888);
            pixmap1.setColor(0.8f, 0.3f, 0.2f, 1);
            pixmap1.fillRectangle(0, 0, 40, 15);
            bobberTexture = new Texture(pixmap1);
            pixmap1.dispose();
        }

        try {
            greenBarTexture = new Texture(Gdx.files.internal("Animals/Fish/MiniGame/safezone.png"));
        } catch (Exception e) {
            Pixmap pixmap1 = new Pixmap(40, 40, Pixmap.Format.RGBA8888);
            pixmap1.setColor(0.2f, 0.8f, 0.2f, 1);
            pixmap1.fillRectangle(0, 0, 40, 40);
            greenBarTexture = new Texture(pixmap1);
            pixmap1.dispose();
        }
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public Texture getFishingBarTexture() {
        return fishingBarTexture;
    }

    public Texture getFishTexture() {
        return fishTexture;
    }

    public Texture getBobberTexture() {
        return bobberTexture;
    }

    public Texture getGreenBarTexture() {
        return greenBarTexture;
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        fishingBarTexture.dispose();
        fishTexture.dispose();
        bobberTexture.dispose();
        greenBarTexture.dispose();
    }
}
