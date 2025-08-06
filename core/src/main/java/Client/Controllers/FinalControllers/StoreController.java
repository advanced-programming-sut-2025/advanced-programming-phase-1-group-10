package Client.Controllers.FinalControllers;



import Client.Assets.StoreAsset;
import Common.Models.App;
import Common.Models.Map;
import Common.Models.Place.Store.Store;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StoreController {

    private final StoreAsset storeAsset = new StoreAsset();

    public void update(SpriteBatch batch) {
        for (Store store : App.getInstance().getCurrentGame().getStores()) {
            TextureRegion[][] region = switch (store.getPlaceName()) {
                case "blackSmith" -> storeAsset.getBlackSmithOutside();
                case "carpenter" -> storeAsset.getCarpenterOutside();
                case "fishShop" -> storeAsset.getFishStoreOutside();
                case "jojaMart" -> storeAsset.getJojoMartOutside();
                case "marineRanch" -> storeAsset.getMarineRanchOutside();
                case "pierreGeneral" -> storeAsset.getPierreGeneralOutside();
                case "stardropSaloon" -> storeAsset.getStardropSaloonOutside();
                default -> throw new RuntimeException("Unknown store place: " + store.getPlaceName());
            };

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
