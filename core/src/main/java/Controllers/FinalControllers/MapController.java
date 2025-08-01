package Controllers.FinalControllers;

import Assets.GrassAssets;
import Assets.OtherAssets;
import Models.*;
import Models.Place.Lake;
import Models.Place.Quarry;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapController {

    public Map map;

    public MapController() {
        this.map = App.getInstance().getCurrentGame().getGameMap();
    }

    public void update(SpriteBatch batch) {
        int playerX = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition().getX();
        int playerY = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition().getY();

        for (int y = 0; y < Map.mapHeight; y++) {
            for (int x = 0; x < Map.mapWidth; x++) {
                Tile tile = map.getMap()[y][x];
                tile.setRenderInside(false);

                if (tile.getPlace() != null &&
                    tile.getPlace().contains(new Position(playerX, playerY))) {
                    tile.setRenderInside(true);
                }

                updateTileType(tile);
                renderTile(batch, tile, x * Map.tileSize, y * Map.tileSize);

                try {
                    batch.draw(tile.getItem().show(), x * Map.tileSize, y * Map.tileSize);
                } catch (NullPointerException ignored) {}
            }
        }
    }

    public void updateTileType(Tile tile) {
        if (tile.getPlace() instanceof Lake) {
            tile.setTileType(TileType.Lake);
        } else if (tile.getPlace() instanceof Quarry) {
            tile.setTileType(TileType.Quarry);
        } else if (tile.getTileType() == TileType.Wall) {
            tile.setTileType(TileType.Wall);
        } else if (!tile.getisPlow() && !tile.isWatered()) {
            tile.setTileType(TileType.Grass);
        } else if (tile.getisPlow() && !tile.isWatered()) {
            tile.setTileType(TileType.Plowed);
        } else if (tile.getisPlow() && tile.isWatered()) {
            tile.setTileType(TileType.Watered);
        }
    }

    public void renderTile(SpriteBatch batch, Tile tile, float x, float y) {
        TextureRegion baseGrass = GrassAssets.getGrassSprite(
            App.getInstance().getCurrentGame().getGameTime().getSeason()
        );

        TextureRegion regionToDraw = tile.isRenderInside()
            ? tile.getAssetRegionInside()
            : tile.getAssetRegionOutside();

        if (regionToDraw != null) {
            batch.draw(baseGrass, x, y);
            batch.draw(regionToDraw, x, y);
        } else if (tile.getTileType() == TileType.Grass) {
            batch.draw(baseGrass, x, y);
        } else if (tile.getTileType() == TileType.Wall) {
            batch.draw(baseGrass, x, y);
            batch.draw(OtherAssets.bush, x, y);
        } else {
            batch.draw(tile.getTileType().getSprite(), x, y);
        }
    }
}
