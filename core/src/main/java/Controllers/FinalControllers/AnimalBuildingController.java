package Controllers.FinalControllers;

import Assets.AnimalBuildingAsset;
import Models.*;
import Models.Place.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import static Models.Map.mapHeight;
import static Models.Map.mapWidth;

public class AnimalBuildingController {

    private final AnimalBuildingAsset animalBuildingAsset;
    private boolean isPlacingCoop = false;
    private float tempCoopX = 0;
    private float tempCoopY = 0;
    private final List<Coop> placedCoops = new ArrayList<>();
    private final float MOVEMENT_SPEED = 5.0f;
    private Tile[][] map = App.getInstance().getCurrentGame().getGameMap().getMap();

    public AnimalBuildingController() {
        this.animalBuildingAsset = new AnimalBuildingAsset();
    }

    public void update(SpriteBatch batch) {
        for (Coop coop : placedCoops) {
            Position pos = coop.getPosition();
            batch.draw(animalBuildingAsset.getCoop(), pos.getX(), pos.getY());
        }

        handleInput();

        if (isPlacingCoop) {
            Sprite coopSprite = animalBuildingAsset.getCoop();
            coopSprite.setAlpha(0.7f);
            coopSprite.setPosition(tempCoopX, tempCoopY);
            coopSprite.draw(batch);
            coopSprite.setAlpha(1.0f);
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.C) && !isPlacingCoop) {
            isPlacingCoop = true;
            tempCoopX = App.getInstance().getCurrentGame().getCurrentPlayer().getX();
            tempCoopY = App.getInstance().getCurrentGame().getCurrentPlayer().getY();
        }

        if (isPlacingCoop) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                tempCoopY += MOVEMENT_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                tempCoopY -= MOVEMENT_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                tempCoopX -= MOVEMENT_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                tempCoopX += MOVEMENT_SPEED;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                placeCoop();
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                isPlacingCoop = false;
            }
        }
    }

    private void placeCoop() {
        if (isValidPlacement(tempCoopX, tempCoopY)) {
            Position position = new Position((int)tempCoopX, (int)tempCoopY);
            int coopWidth = animalBuildingAsset.getCoop().getRegionWidth();
            int coopHeight = animalBuildingAsset.getCoop().getRegionHeight();

            Coop newCoop = new Coop(position, coopHeight, coopWidth);
            placedCoops.add(newCoop);

            // add to map
            Map gameMap = App.getInstance().getCurrentGame().getGameMap();

            System.out.println("Coop placed at: " + tempCoopX + ", " + tempCoopY);

            isPlacingCoop = false;
        } else {
            System.out.println("Cannot place coop here. Invalid position.");
        }
    }

    private boolean isValidPlacement(float x, float y) {
        float mapPixelWidth = mapWidth * Map.tileSize;
        float mapPixelHeight = mapHeight * Map.tileSize;

        if (x < 0 || y < 0 || x + animalBuildingAsset.getCoop().getRegionWidth() > mapPixelWidth ||
            y + animalBuildingAsset.getCoop().getRegionHeight() > mapPixelHeight) {
            System.out.println("Invalid placement: Out of map boundaries");
            return false;
        }

        int tileSize = Map.tileSize;
        int startTileX = (int) Math.floor(x / tileSize);
        int startTileY = (int) Math.floor(y / tileSize);

        int coopWidthInTiles = (int) Math.ceil(animalBuildingAsset.getCoop().getRegionWidth() / tileSize);
        int coopHeightInTiles = (int) Math.ceil(animalBuildingAsset.getCoop().getRegionHeight() / tileSize);

        if (startTileX + coopWidthInTiles > mapWidth || startTileY + coopHeightInTiles > mapHeight) {
            System.out.println("Invalid placement: Building extends beyond map boundaries");
            return false;
        }

        for (int tileX = startTileX; tileX < startTileX + coopWidthInTiles; tileX++) {
            for (int tileY = startTileY; tileY < startTileY + coopHeightInTiles; tileY++) {
                if (tileX < 0 || tileX >= mapWidth || tileY < 0 || tileY >= mapHeight) {
                    System.out.println("Invalid placement: Tile coordinates out of bounds");
                    return false;
                }

                Tile tile = map[tileX][tileY];
                if (tile == null) {
                    System.out.println("Invalid placement: Tile is null");
                    return false;
                }

                if (tile.getTileType() != TileType.Grass) {
                    System.out.println("Invalid placement: Tile is not grass");
                    return false;
                }

                if (tile.getPlace() != null) {
                    System.out.println("Invalid placement: Tile already has a place");
                    return false;
                }
            }
        }

        for (Coop placedCoop : placedCoops) {
            Position placedPos = placedCoop.getPosition();
            int placedWidth = animalBuildingAsset.getCoop().getRegionWidth();
            int placedHeight = animalBuildingAsset.getCoop().getRegionHeight();

            if (x < placedPos.getX() + placedWidth &&
                x + animalBuildingAsset.getCoop().getRegionWidth() > placedPos.getX() &&
                y < placedPos.getY() + placedHeight &&
                y + animalBuildingAsset.getCoop().getRegionHeight() > placedPos.getY()) {
                System.out.println("Invalid placement: Overlaps with another coop");
                return false;
            }
        }

        return true;
    }

    // this method should be complete.
    private boolean isValidTileTypeForBuilding(TileType tileType) {
        return tileType == TileType.Grass;
    }

    public boolean isPlacingCoop() {
        return isPlacingCoop;
    }

    public List<Coop> getPlacedCoops() {
        return placedCoops;
    }
}
