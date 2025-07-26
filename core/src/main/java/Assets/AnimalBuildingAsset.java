package Assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimalBuildingAsset {
    private final Sprite coop;
    private final Sprite barn;

    public AnimalBuildingAsset() {
        this.coop = new Sprite(new Texture("Animals/Coop/Coop.png"));
        this.barn = new Sprite(new Texture("Animals/Barn/Barn.png"));
    }

    public Sprite getCoop() {
        return coop;
    }

    public Sprite getBarn() {
        return barn;
    }
}
