package Client.Assets;

import Common.Models.Map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StoreAsset {

    private final TextureRegion[][] blackSmithOutside;
    private final TextureRegion[][] carpenterOutside;
    private final TextureRegion[][] fishStoreOutside;
    private final TextureRegion[][] jojoMartOutside;
    private final TextureRegion[][] marineRanchOutside;
    private final TextureRegion[][] pierreGeneralOutside;
    private final TextureRegion[][] stardropSaloonOutside;

    public StoreAsset() {
        this.blackSmithOutside = TextureRegion.split(new Texture("store/blacksmithOutside.png"), Map.tileSize, Map.tileSize);
        this.carpenterOutside =  TextureRegion.split(new Texture("store/carpenterOutside.png"), Map.tileSize, Map.tileSize);
        this.fishStoreOutside = TextureRegion.split(new Texture("store/fishShopOutside.png"), Map.tileSize, Map.tileSize);
        this.jojoMartOutside = TextureRegion.split(new Texture("store/jojaMartOutside.png"), Map.tileSize, Map.tileSize);
        this.marineRanchOutside = TextureRegion.split(new Texture("store/marineRanchOutside.png"), Map.tileSize, Map.tileSize);
        this.pierreGeneralOutside = TextureRegion.split(new Texture("store/pierreGeneralOutside.png"), Map.tileSize, Map.tileSize);
        this.stardropSaloonOutside = TextureRegion.split(new Texture("store/stardropSaloonOutside.png"),Map.tileSize, Map.tileSize);
    }

    public TextureRegion[][] getBlackSmithOutside() {
        return blackSmithOutside;
    }

    public TextureRegion[][] getCarpenterOutside() {
        return carpenterOutside;
    }

    public TextureRegion[][] getFishStoreOutside() {
        return fishStoreOutside;
    }

    public TextureRegion[][] getJojoMartOutside() {
        return jojoMartOutside;
    }

    public TextureRegion[][] getMarineRanchOutside() {
        return marineRanchOutside;
    }

    public TextureRegion[][] getPierreGeneralOutside() {
        return pierreGeneralOutside;
    }

    public TextureRegion[][] getStardropSaloonOutside() {
        return stardropSaloonOutside;
    }
}
