package Assets;

import Models.Map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimalBuildingAsset {

    private final Sprite coop;
    private final Sprite barn;

    private Sprite coopinside;
    private Sprite barninside;

    private final TextureRegion[][] coopRegions;
    private final TextureRegion[][] barnRegions;

    public AnimalBuildingAsset() {

        this.coop = new Sprite(new Texture("Animals/Coop/Coop.png"));
        this.barn = new Sprite(new Texture("Animals/Barn/Barn.png"));

        this.barninside = new Sprite(new Texture("Animals/Barn/barninside.png"));
        this.coopinside = new Sprite(new Texture("Animals/Coop/coopinside.png"));

        this.coopRegions = TextureRegion.split(new Texture("Animals/Coop/Coop.png"), Map.tileSize, Map.tileSize);
        this.barnRegions = TextureRegion.split(new Texture("Animals/Barn/Barn.png"), Map.tileSize, Map.tileSize);
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

    public TextureRegion[][] getCoopRegions() {
        return coopRegions;
    }

    public TextureRegion[][] getBarnRegions() {
        return barnRegions;
    }
}
