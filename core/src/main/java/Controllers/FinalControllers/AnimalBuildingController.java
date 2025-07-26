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

    // coop
    private boolean isPlacingCoop = false;
    private float tempCoopX = 0;
    private float tempCoopY = 0;
    private final List<Coop> placedCoops = new ArrayList<>();

    // barn
    private boolean isPlacingBarn = false;
    private float tempBarnX = 0;
    private float tempBarnY = 0;
    private final List<Barn> placedBarns = new ArrayList<>();

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

        for (Barn barn : placedBarns) {
            Position pos = barn.getPosition();
            batch.draw(animalBuildingAsset.getBarn(), pos.getX(), pos.getY());
        }

        handleInput();

        if (isPlacingCoop) {
            Sprite coopSprite = animalBuildingAsset.getCoop();
            coopSprite.setAlpha(0.7f);
            coopSprite.setPosition(tempCoopX, tempCoopY);
            coopSprite.draw(batch);
            coopSprite.setAlpha(1.0f);
        }

        if (isPlacingBarn) {
            Sprite barnSprite = animalBuildingAsset.getBarn();
            barnSprite.setAlpha(0.7f);
            barnSprite.setPosition(tempBarnX, tempBarnY);
            barnSprite.draw(batch);
            barnSprite.setAlpha(1.0f);
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.C) && !isPlacingCoop && !isPlacingBarn) {
            isPlacingCoop = true;
            tempCoopX = App.getInstance().getCurrentGame().getCurrentPlayer().getX();
            tempCoopY = App.getInstance().getCurrentGame().getCurrentPlayer().getY();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.B) && !isPlacingBarn && !isPlacingCoop) {
            isPlacingBarn = true;
            tempBarnX = App.getInstance().getCurrentGame().getCurrentPlayer().getX();
            tempBarnY = App.getInstance().getCurrentGame().getCurrentPlayer().getY();
        }

        if (isPlacingCoop) {
            handleBuildingMovement(tempCoopX, tempCoopY, true);

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                placeCoop();
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                isPlacingCoop = false;
            }
        }

        if (isPlacingBarn) {
            handleBuildingMovement(tempBarnX, tempBarnY, false);

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                placeBarn();
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                isPlacingBarn = false;
            }
        }
    }

    private void handleBuildingMovement(float x, float y, boolean isCoop) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (isCoop) tempCoopY += MOVEMENT_SPEED;
            else tempBarnY += MOVEMENT_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (isCoop) tempCoopY -= MOVEMENT_SPEED;
            else tempBarnY -= MOVEMENT_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (isCoop) tempCoopX -= MOVEMENT_SPEED;
            else tempBarnX -= MOVEMENT_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (isCoop) tempCoopX += MOVEMENT_SPEED;
            else tempBarnX += MOVEMENT_SPEED;
        }
    }

    private void placeCoop() {
        if (isValidPlacement(tempCoopX, tempCoopY, true)) {
            Position position = new Position((int)tempCoopX, (int)tempCoopY);
            int coopWidth = animalBuildingAsset.getCoop().getRegionWidth();
            int coopHeight = animalBuildingAsset.getCoop().getRegionHeight();

            Coop newCoop = new Coop(position, coopHeight, coopWidth);
            placedCoops.add(newCoop);

            System.out.println("Coop placed at: " + tempCoopX + ", " + tempCoopY);
            isPlacingCoop = false;
        } else {
            System.out.println("Cannot place coop here. Invalid position.");
        }
    }

    private void placeBarn() {
        if (isValidPlacement(tempBarnX, tempBarnY, false)) {
            Position position = new Position((int)tempBarnX, (int)tempBarnY);
            int barnWidth = animalBuildingAsset.getBarn().getRegionWidth();
            int barnHeight = animalBuildingAsset.getBarn().getRegionHeight();

            Barn newBarn = new Barn(position, barnHeight, barnWidth);
            placedBarns.add(newBarn);

            System.out.println("Barn placed at: " + tempBarnX + ", " + tempBarnY);
            isPlacingBarn = false;
        } else {
            System.out.println("Cannot place barn here. Invalid position.");
        }
    }

    private boolean isValidPlacement(float x, float y, boolean isCoop) {
        Sprite buildingSprite = isCoop ? animalBuildingAsset.getCoop() : animalBuildingAsset.getBarn();

        float mapPixelWidth = mapWidth * Map.tileSize;
        float mapPixelHeight = mapHeight * Map.tileSize;

        if (x < 0 || y < 0 || x + buildingSprite.getRegionWidth() > mapPixelWidth ||
            y + buildingSprite.getRegionHeight() > mapPixelHeight) {
            System.out.println("Invalid placement: Out of map boundaries");
            return false;
        }

        int tileSize = Map.tileSize;
        int startTileX = (int) Math.floor(x / tileSize);
        int startTileY = (int) Math.floor(y / tileSize);

        int buildingWidthInTiles = (int) Math.ceil((double) buildingSprite.getRegionWidth() / tileSize);
        int buildingHeightInTiles = (int) Math.ceil((double) buildingSprite.getRegionHeight() / tileSize);

        if (startTileX + buildingWidthInTiles > mapWidth || startTileY + buildingHeightInTiles > mapHeight) {
            System.out.println("Invalid placement: Building extends beyond map boundaries");
            return false;
        }

        for (int tileX = startTileX; tileX < startTileX + buildingWidthInTiles; tileX++) {
            for (int tileY = startTileY; tileY < startTileY + buildingHeightInTiles; tileY++) {
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

        if (isCoop) {
            for (Coop placedCoop : placedCoops) {
                Position placedPos = placedCoop.getPosition();
                int placedWidth = animalBuildingAsset.getCoop().getRegionWidth();
                int placedHeight = animalBuildingAsset.getCoop().getRegionHeight();

                if (x < placedPos.getX() + placedWidth &&
                    x + buildingSprite.getRegionWidth() > placedPos.getX() &&
                    y < placedPos.getY() + placedHeight &&
                    y + buildingSprite.getRegionHeight() > placedPos.getY()) {
                    System.out.println("Invalid placement: Overlaps with another coop");
                    return false;
                }
            }

            for (Barn placedBarn : placedBarns) {
                Position placedPos = placedBarn.getPosition();
                int placedWidth = animalBuildingAsset.getBarn().getRegionWidth();
                int placedHeight = animalBuildingAsset.getBarn().getRegionHeight();

                if (x < placedPos.getX() + placedWidth &&
                    x + buildingSprite.getRegionWidth() > placedPos.getX() &&
                    y < placedPos.getY() + placedHeight &&
                    y + buildingSprite.getRegionHeight() > placedPos.getY()) {
                    System.out.println("Invalid placement: Overlaps with a barn");
                    return false;
                }
            }
        } else {
            for (Coop placedCoop : placedCoops) {
                Position placedPos = placedCoop.getPosition();
                int placedWidth = animalBuildingAsset.getCoop().getRegionWidth();
                int placedHeight = animalBuildingAsset.getCoop().getRegionHeight();

                if (x < placedPos.getX() + placedWidth &&
                    x + buildingSprite.getRegionWidth() > placedPos.getX() &&
                    y < placedPos.getY() + placedHeight &&
                    y + buildingSprite.getRegionHeight() > placedPos.getY()) {
                    System.out.println("Invalid placement: Overlaps with a coop");
                    return false;
                }
            }

            for (Barn placedBarn : placedBarns) {
                Position placedPos = placedBarn.getPosition();
                int placedWidth = animalBuildingAsset.getBarn().getRegionWidth();
                int placedHeight = animalBuildingAsset.getBarn().getRegionHeight();

                if (x < placedPos.getX() + placedWidth &&
                    x + buildingSprite.getRegionWidth() > placedPos.getX() &&
                    y < placedPos.getY() + placedHeight &&
                    y + buildingSprite.getRegionHeight() > placedPos.getY()) {
                    System.out.println("Invalid placement: Overlaps with another barn");
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isPlacingCoop() {
        return isPlacingCoop;
    }

    public boolean isPlacingBarn() {
        return isPlacingBarn;
    }

    public List<Coop> getPlacedCoops() {
        return placedCoops;
    }

    public List<Barn> getPlacedBarns() {
        return placedBarns;
    }
}
