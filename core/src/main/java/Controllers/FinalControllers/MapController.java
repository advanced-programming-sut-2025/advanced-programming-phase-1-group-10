package Controllers.FinalControllers;

import Models.App;
import Models.Map;
import Models.Tile;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapController {

    public Map map;

    public MapController() {
        this.map = App.getInstance().getCurrentGame().getGameMap();
    }

    public void update(SpriteBatch batch) {
        for (int y = 0; y < Map.mapHeight; y++) {
            for (int x = 0; x < Map.mapWidth; x++) {
                Tile tile = map.getMap()[y][x];
                tile.update();
                tile.render(batch, x * Map.tileSize, y * Map.tileSize);
            }
        }
    }
}
