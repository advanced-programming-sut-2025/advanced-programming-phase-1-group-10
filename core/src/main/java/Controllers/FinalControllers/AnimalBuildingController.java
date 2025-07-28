package Controllers.FinalControllers;

import Assets.AnimalBuildingAsset;
import Models.*;
import Models.Place.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

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

    private Coop selectedCoop = null;
    private boolean showingCoopInterior = false;

    private boolean isPlacingBarn = false;
    private float tempBarnX = 0;
    private float tempBarnY = 0;
    private final List<Barn> placedBarns = new ArrayList<>();

    private Barn selectedBarn = null;
    private boolean showingBarnInterior = false;

    private final float MOVEMENT_SPEED = 5.0f;
    private Tile[][] map = App.getInstance().getCurrentGame().getGameMap().getMap();

    private float interiorDisplayTime = 0;
    private final float INTERIOR_DISPLAY_DURATION = 5.0f;
    private boolean autoHideInterior = true;

    private ShapeRenderer shapeRenderer;

    private float interiorX;
    private float interiorY;
    private float interiorScale = 1.5f;

    public AnimalBuildingController() {
        this.animalBuildingAsset = new AnimalBuildingAsset();
        this.shapeRenderer = new ShapeRenderer();
    }

    public void update(SpriteBatch batch, float delta) {
        if (isShowingInterior()) {
            renderInterior(batch);
        } else {
            renderBuildings(batch);
            renderPlacingBuilding(batch);
        }

        handleInput(delta);
        updateInteriorDisplayTime(delta);
    }

    private void renderBuildings(SpriteBatch batch) {
        for (Coop coop : placedCoops) {
            Position pos = coop.getPosition();
            batch.draw(animalBuildingAsset.getCoop(), pos.getX(), pos.getY());
        }

        for (Barn barn : placedBarns) {
            Position pos = barn.getPosition();
            batch.draw(animalBuildingAsset.getBarn(), pos.getX(), pos.getY());
        }
    }

    private void renderInterior(SpriteBatch batch) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(0, 0, screenWidth, screenHeight);
        shapeRenderer.end();

        batch.begin();

        if (showingCoopInterior && selectedCoop != null) {
            Sprite interiorSprite = animalBuildingAsset.getCoopinside();
            float spriteWidth = interiorSprite.getWidth() * interiorScale;
            float spriteHeight = interiorSprite.getHeight() * interiorScale;

            interiorX = (screenWidth - spriteWidth) / 2;
            interiorY = (screenHeight - spriteHeight) / 2;

            batch.draw(interiorSprite, interiorX, interiorY, spriteWidth, spriteHeight);

        } else if (showingBarnInterior && selectedBarn != null) {
            Sprite interiorSprite = animalBuildingAsset.getBarninside();
            float spriteWidth = interiorSprite.getWidth() * interiorScale;
            float spriteHeight = interiorSprite.getHeight() * interiorScale;

            interiorX = (screenWidth - spriteWidth) / 2;
            interiorY = (screenHeight - spriteHeight) / 2;

            batch.draw(interiorSprite, interiorX, interiorY, spriteWidth, spriteHeight);

        }

    }

    private void renderPlacingBuilding(SpriteBatch batch) {
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

    private void updateInteriorDisplayTime(float delta) {
        if (autoHideInterior && (showingCoopInterior || showingBarnInterior)) {
            interiorDisplayTime += delta;

            if (interiorDisplayTime >= INTERIOR_DISPLAY_DURATION) {
                closeInteriorView();
            }
        }
    }

    private void handleInput(float delta) {
        if (isShowingInterior()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                closeInteriorView();
                return;
            }

            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            }

            return;
        }

        handleBuildingPlacement();
    }

    private void handleBuildingPlacement() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSLASH) && !isPlacingCoop && !isPlacingBarn) {
            isPlacingCoop = true;
            tempCoopX = App.getInstance().getCurrentGame().getCurrentPlayer().getX();
            tempCoopY = App.getInstance().getCurrentGame().getCurrentPlayer().getY();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SLASH) && !isPlacingBarn && !isPlacingCoop) {
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

    public boolean handleClick(float worldX, float worldY) {
        if (isShowingInterior()) {
            return true;
        }

        if (isPlacingCoop || isPlacingBarn) {
            return false;
        }


        for (Coop coop : placedCoops) {
            Position pos = coop.getPosition();
            Rectangle bounds = new Rectangle(pos.getX(), pos.getY(),
                animalBuildingAsset.getCoop().getRegionWidth(),
                animalBuildingAsset.getCoop().getRegionHeight());

            if (bounds.contains(worldX, worldY)) {
                System.out.println("Clicked on Coop at: " + pos.getX() + ", " + pos.getY());
                selectedCoop = coop;
                showingCoopInterior = true;
                showingBarnInterior = false;
                selectedBarn = null;
                interiorDisplayTime = 0;
                return true;
            }
        }


        for (Barn barn : placedBarns) {
            Position pos = barn.getPosition();
            Rectangle bounds = new Rectangle(pos.getX(), pos.getY(),
                animalBuildingAsset.getBarn().getRegionWidth(),
                animalBuildingAsset.getBarn().getRegionHeight());

            if (bounds.contains(worldX, worldY)) {
                System.out.println("Clicked on Barn at: " + pos.getX() + ", " + pos.getY());
                selectedBarn = barn;
                showingBarnInterior = true;
                showingCoopInterior = false;
                selectedCoop = null;
                interiorDisplayTime = 0;
                return true;
            }
        }

        return false;
    }


    public void closeInteriorView() {
        showingCoopInterior = false;
        showingBarnInterior = false;
        selectedCoop = null;
        selectedBarn = null;
        interiorDisplayTime = 0;
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


            int tileSize = Map.tileSize;
            int startTileX = (int) Math.floor(tempCoopX / tileSize);
            int startTileY = (int) Math.floor(tempCoopY / tileSize);
            int coopWidthInTiles = (int) Math.ceil((double) coopWidth / tileSize);
            int coopHeightInTiles = (int) Math.ceil((double) coopHeight / tileSize);


            for (int row = 0; row < coopHeightInTiles; row++) {
                for (int col = 0; col < coopWidthInTiles; col++) {
                    int tileX = startTileX + col;
                    int tileY = startTileY + row;
                    if (tileX < mapWidth && tileY < mapHeight) {
                        map[tileX][tileY].setPlace(newCoop);
                    }
                }
            }

            System.out.println("Coop placed at tile: " + startTileX + ", " + startTileY);
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

            int tileSize = Map.tileSize;
            int startTileX = (int) Math.floor(tempBarnX / tileSize);
            int startTileY = (int) Math.floor(tempBarnY / tileSize);
            int barnWidthInTiles = (int) Math.ceil((double) barnWidth / tileSize);
            int barnHeightInTiles = (int) Math.ceil((double) barnHeight / tileSize);

            for (int row = 0; row < barnHeightInTiles; row++) {
                for (int col = 0; col < barnWidthInTiles; col++) {
                    int tileX = startTileX + col;
                    int tileY = startTileY + row;
                    if (tileX < mapWidth && tileY < mapHeight) {
                        map[tileX][tileY].setPlace(newBarn);
                    }
                }
            }

            System.out.println("Barn placed at tile: " + startTileX + ", " + startTileY);
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

        if (startTileX + buildingWidthInTiles >= mapWidth || startTileY + buildingHeightInTiles >= mapHeight) {
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

    public boolean isShowingCoopInterior() {
        return showingCoopInterior;
    }

    public boolean isShowingBarnInterior() {
        return showingBarnInterior;
    }

    public boolean isShowingInterior() {
        return showingCoopInterior || showingBarnInterior;
    }

    public Coop getSelectedCoop() {
        return selectedCoop;
    }

    public Barn getSelectedBarn() {
        return selectedBarn;
    }

    public void setAutoHideInterior(boolean autoHide) {
        this.autoHideInterior = autoHide;
    }

    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}
