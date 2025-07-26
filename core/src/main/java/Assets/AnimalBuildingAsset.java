package Assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimalBuildingAsset {
    private final Sprite coop;
    private final Sprite barn;
    private Sprite coopinside;
    private Sprite barninside;

    public AnimalBuildingAsset() {
        this.coop = new Sprite(new Texture("Animals/Coop/Coop.png"));
        this.barn = new Sprite(new Texture("Animals/Barn/Barn.png"));
        this.barninside = new Sprite(new Texture("Animals/Barn/barninside.png"));
        this.coopinside = new Sprite(new Texture("Animals/Coop/coopinside.png"));
    }

    public Sprite getCoopinside() {
        return coopinside;
    }

    public Sprite getBarninside() {
        return barninside;
    }

    public Sprite getCoop() {
        return coop;
    }

    public Sprite getBarn() {
        return barn;
    }
}
