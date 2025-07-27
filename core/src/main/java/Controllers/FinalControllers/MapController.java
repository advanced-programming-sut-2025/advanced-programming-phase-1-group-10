package Controllers.FinalControllers;

import Models.*;
import Models.Place.Lake;
import Models.Place.Quarry;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapController {

    public Map map;

    public MapController() {
        this.map = App.getInstance().getCurrentGame().getGameMap();
    }

    public void update(SpriteBatch batch) {
        // Get player position
        int playerX = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition().getX();
        int playerY = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition().getY();

        for (int y = 0; y < Map.mapHeight; y++) {
            for (int x = 0; x < Map.mapWidth; x++) {
                Tile tile = map.getMap()[y][x];

                // Reset renderInside by default
                tile.setRenderInside(false);

                // If this tile is part of a Place
                if (tile.getPlace() != null) {

                if (tile.getPlace().contains(new Position(playerX, playerY))) {
                    tile.setRenderInside(true);
                }

                }

                updateTileType(tile);
                tile.render(batch, x * Map.tileSize, y * Map.tileSize);

                try {
                    batch.draw(tile.getItem().show(), x * Map.tileSize, y * Map.tileSize);
                } catch (NullPointerException ignored) {}
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
