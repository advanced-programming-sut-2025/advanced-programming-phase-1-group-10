package Controllers.FinalControllers;

import Models.App;
import Models.Map;
import Models.Place.Lake;
import Models.Place.Quarry;
import Models.Tile;
import Models.TileType;
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
                updateTileType(tile);
                tile.render(batch, x * Map.tileSize, y * Map.tileSize);
            }
        }
    }

    public void updateTileType(Tile tile){
        if(tile.getPlace() instanceof Lake){
            tile.setTileType(TileType.Lake);
        } else if(tile.getPlace() instanceof Quarry) {
            tile.setTileType(TileType.Quarry);
        }
        else if(tile.getTileType() == TileType.Wall){
            tile.setTileType(TileType.Wall);
        } else if(!tile.getisPlow() && !tile.isWatered()){
            tile.setTileType(TileType.Grass);
        } else if(tile.getisPlow() && !tile.isWatered()){
            tile.setTileType(TileType.Plowed);
        } else if(tile.getisPlow() && tile.isWatered()){
            tile.setTileType(TileType.Watered);
        }
    }
}
