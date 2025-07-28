package Controllers.FinalControllers;



import Assets.StoreAsset;
import Models.App;
import Models.Map;
import Models.Place.Store.Store;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StoreController {

    private final StoreAsset storeAsset = new StoreAsset();

    public void update(SpriteBatch batch) {
        for (Store store : App.getInstance().getCurrentGame().getStores()) {
            TextureRegion[][] region = null;

            switch (store.getPlaceName()) {
                case "blackSmith":
                    region = storeAsset.getBlackSmithOutside();
                    break;
                case "carpenter":
                    region = storeAsset.getCarpenterOutside();
                    break;
                case "fishShop":
                    region = storeAsset.getFishStoreOutside();
                    break;
                case "jojaMart":
                    region = storeAsset.getJojoMartOutside();
                    break;
                case "marineRanch":
                    region = storeAsset.getMarineRanchOutside();
                    break;
                case "pierreGeneral":
                    region = storeAsset.getPierreGeneralOutside();
                    break;
                case "stardropSaloon":
                    region = storeAsset.getStardropSaloonOutside();
                    break;
                default:
                    throw new RuntimeException("Unknown store place: " + store.getPlaceName());
            }

            int texHeight = region.length;
            int texWidth = region[0].length;

            for (int row = 0; row < store.getHeight(); row++) {
                for (int col = 0; col < store.getWidth(); col++) {
                    int drawX = (store.getPosition().getY() + col) * Map.tileSize;
                    int drawY = (store.getPosition().getX() + row) * Map.tileSize;

                    int regionRow = texHeight - 1 - row;

                    if (regionRow >= 0 && regionRow < texHeight && col < texWidth) {
                        batch.draw(region[regionRow][col], drawX, drawY);
                    }
                }
            }
        }
    }

}
