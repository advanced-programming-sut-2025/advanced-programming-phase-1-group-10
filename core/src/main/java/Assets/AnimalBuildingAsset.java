package Assets;

import Models.Map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimalBuildingAsset {
    private final Sprite coop;
    private final Sprite bigCoop;
    private final Sprite deluxeCoop;

    private final Sprite barn;
    private final Sprite bigBarn;
    private final Sprite deluxeBarn;

    private Sprite coopInside;
    private Sprite barnInside;

    private final TextureRegion[][] coopRegions;
    private final TextureRegion[][] bigCoopRegions;
    private final TextureRegion[][] deluxeCoopRegions;

    private final TextureRegion[][] barnRegions;
    private final TextureRegion[][] bigBarnRegions;
    private final TextureRegion[][] deluxeBarnRegions;

    public AnimalBuildingAsset() {
        this.coop = new Sprite(new Texture("Animals/Coop/Coop.png"));
        this.bigCoop = new Sprite(new Texture("Animals/Coop/BigCoop.png"));
        this.deluxeCoop = new Sprite(new Texture("Animals/Coop/DeluxeCoop.png"));

        this.barn = new Sprite(new Texture("Animals/Barn/Barn.png"));
        this.bigBarn = new Sprite(new Texture("Animals/Barn/BigBarn.png"));
        this.deluxeBarn = new Sprite(new Texture("Animals/Barn/DeluxeBarn.png"));

        this.barnInside = new Sprite(new Texture("Animals/Barn/barninside.png"));
        this.coopInside = new Sprite(new Texture("Animals/Coop/coopinside.png"));

        this.coopRegions = TextureRegion.split(new Texture("Animals/Coop/Coop.png"), Map.tileSize, Map.tileSize);
        this.bigCoopRegions = TextureRegion.split(new Texture("Animals/Coop/BigCoop.png"), Map.tileSize, Map.tileSize);
        this.deluxeCoopRegions = TextureRegion.split(new Texture("Animals/Coop/DeluxeCoop.png"), Map.tileSize, Map.tileSize);

        this.barnRegions = TextureRegion.split(new Texture("Animals/Barn/Barn.png"), Map.tileSize, Map.tileSize);
        this.bigBarnRegions = TextureRegion.split(new Texture("Animals/Barn/BigBarn.png"), Map.tileSize, Map.tileSize);
        this.deluxeBarnRegions = TextureRegion.split(new Texture("Animals/Barn/DeluxeBarn.png"), Map.tileSize, Map.tileSize);
    }

    public Sprite getCoop() {
        return coop;
    }

    public Sprite getBigCoop() {
        return bigCoop;
    }

    public Sprite getDeluxeCoop() {
        return deluxeCoop;
    }

    public Sprite getBarn() {
        return barn;
    }

    public Sprite getBigBarn() {
        return bigBarn;
    }

    public Sprite getDeluxeBarn() {
        return deluxeBarn;
    }

    public Sprite getCoopInside() {
        return coopInside;
    }

    public Sprite getBarnInside() {
        return barnInside;
    }

    public TextureRegion[][] getCoopRegions() {
        return coopRegions;
    }

    public TextureRegion[][] getBigCoopRegions() {
        return bigCoopRegions;
    }

    public TextureRegion[][] getDeluxeCoopRegions() {
        return deluxeCoopRegions;
    }

    public TextureRegion[][] getBarnRegions() {
        return barnRegions;
    }

    public TextureRegion[][] getBigBarnRegions() {
        return bigBarnRegions;
    }

    public TextureRegion[][] getDeluxeBarnRegions() {
        return deluxeBarnRegions;
    }

    public Sprite getCoopinside() {
        return coopInside;
    }

    public Sprite getBarninside() {
        return barnInside;
    }
}
