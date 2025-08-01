package Controllers.FinalControllers;

import Assets.AnimalBuildingAsset;
import Assets.AnimalAsset;
import Assets.AnimalProductAsset;
import Controllers.MessageSystem;
import Models.*;
import Models.Animal.Animal;
import Models.Animal.AnimalProduct;
import Models.Animal.AnimalType;
import Models.PlayerStuff.Player;
import Models.Place.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static Models.Map.mapHeight;
import static Models.Map.mapWidth;

public class AnimalBuildingController {

    private final AnimalBuildingAsset animalBuildingAsset;
    private final AnimalAsset animalAsset;
    private final AnimalProductAsset animalProductAsset;

    private int selectedCoopType = 0;
    private boolean isPlacingCoop = false;
    private float tempCoopX = 0;
    private float tempCoopY = 0;
    private final List<Coop> placedCoops = new ArrayList<>();

    private Coop selectedCoop = null;
    private boolean showingCoopInterior = false;

    private int selectedBarnType = 0;
    private boolean isPlacingBarn = false;
    private float tempBarnX = 0;
    private float tempBarnY = 0;
    private final List<Barn> placedBarns = new ArrayList<>();

    private Barn selectedBarn = null;
    private boolean showingBarnInterior = false;

    private final float MOVEMENT_SPEED = 5.0f;
    private Tile[][] map = App.getInstance().getCurrentGame().getGameMap().getMap();

    private float interiorDisplayTime = 0;
    private final float INTERIOR_DISPLAY_DURATION = 45.0f;
    private boolean autoHideInterior = true;

    private ShapeRenderer shapeRenderer;

    private float interiorX;
    private float interiorY;
    private float interiorScale = 1.5f;

    private final AnimalController animalController;
    private final List<AnimalPosition> visibleAnimals = new ArrayList<>();
    private final List<ProductPosition> visibleProducts = new ArrayList<>();


    private Animal feedingAnimal = null;
    private float feedingTime = 0;
    private static final float FEEDING_DURATION = 2.0f;

    private Texture hayHopperFullTexture;
    private Texture hayHopperTexture;

    private static class AnimalPosition {
        Animal animal;
        float x, y, width, height;

        AnimalPosition(Animal animal, float x, float y, float width, float height) {
            this.animal = animal;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        boolean contains(float testX, float testY) {
            return testX >= x && testX <= x + width &&
                testY >= y && testY <= y + height;
        }
    }

    private static class ProductPosition {
        Animal animal;
        AnimalProduct product;
        float x, y, width, height;

        ProductPosition(Animal animal, AnimalProduct product, float x, float y, float width, float height) {
            this.animal = animal;
            this.product = product;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        boolean contains(float testX, float testY) {
            return testX >= x && testX <= x + width &&
                testY >= y && testY <= y + height;
        }
    }

    public AnimalBuildingController(AnimalAsset animalAsset) {
        this.animalBuildingAsset = new AnimalBuildingAsset();
        this.shapeRenderer = new ShapeRenderer();
        this.animalAsset = animalAsset;
        this.animalProductAsset = new AnimalProductAsset();
        this.animalController = new AnimalController();
        this.hayHopperFullTexture = new Texture(Gdx.files.internal("Animals/Hay_Hopper_Full.png"));
        this.hayHopperTexture = new Texture(Gdx.files.internal("Animals/Hay_Hopper.png"));
    }

    public void update(SpriteBatch batch, float delta) {
        if (feedingAnimal != null) {
            feedingTime += delta;
            feedingAnimal.setStateTime(feedingTime);

            if (feedingTime >= FEEDING_DURATION) {
                feedingAnimal.feed();
                MessageSystem.showInfo(feedingAnimal.getName() + " was fed!", 5.0f);
                feedingAnimal = null;
                feedingTime = 0;
            }
        }
        if (isShowingInterior()) {
            renderInterior(batch);
            if (animalController != null && (animalController.isMenuVisible() || animalController.isInfoBoxVisible())) {
                animalController.render(delta);
            }
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
            Sprite coopSprite;
            if (coop.isDeluxe()) {
                coopSprite = animalBuildingAsset.getDeluxeCoop();
            } else if (coop.isBig()) {
                coopSprite = animalBuildingAsset.getBigCoop();
            } else {
                coopSprite = animalBuildingAsset.getCoop();
            }
            batch.draw(coopSprite, pos.getX(), pos.getY());
        }

        for (Barn barn : placedBarns) {
            Position pos = barn.getPosition();
            Sprite barnSprite;
            if (barn.isDeluxe()) {
                barnSprite = animalBuildingAsset.getDeluxeBarn();
            } else if (barn.isBig()) {
                barnSprite = animalBuildingAsset.getBigBarn();
            } else {
                barnSprite = animalBuildingAsset.getBarn();
            }
            batch.draw(barnSprite, pos.getX(), pos.getY());
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
            Sprite interiorSprite = animalBuildingAsset.getCoopInside();
            float spriteWidth = interiorSprite.getWidth() * interiorScale;
            float spriteHeight = interiorSprite.getHeight() * interiorScale;
            interiorX = (screenWidth - spriteWidth) / 2;
            interiorY = (screenHeight - spriteHeight) / 2;
            batch.draw(interiorSprite, interiorX, interiorY, spriteWidth, spriteHeight);
            renderAnimalsInside(batch, selectedCoop.getAnimals(), interiorX, interiorY, spriteWidth, spriteHeight);
        } else if (showingBarnInterior && selectedBarn != null) {
            Sprite interiorSprite = animalBuildingAsset.getBarnInside();
            float spriteWidth = interiorSprite.getWidth() * interiorScale;
            float spriteHeight = interiorSprite.getHeight() * interiorScale;
            interiorX = (screenWidth - spriteWidth) / 2;
            interiorY = (screenHeight - spriteHeight) / 2;
            batch.draw(interiorSprite, interiorX, interiorY, spriteWidth, spriteHeight);
            renderAnimalsInside(batch, selectedBarn.getAnimals(), interiorX, interiorY, spriteWidth, spriteHeight);
        }
    }

    private void renderAnimalsInside(SpriteBatch batch, List<Animal> animals, float interiorX, float interiorY, float interiorWidth, float interiorHeight) {
        if (animalAsset == null) {
            System.err.println("AnimalAsset is not set in AnimalBuildingController.");
            return;
        }

        visibleAnimals.clear();
        visibleProducts.clear();

        float animalSize = 96f;
        float padding = 30f;
        float startYOffset = interiorHeight * 0.3f;
        float drawableHeight = interiorHeight - startYOffset - padding;
        int animalsPerRow = (int) ((interiorWidth - 2 * padding) / (animalSize + padding));

        if (animalsPerRow < 1) {
            animalsPerRow = 1;
        }

        for (int i = 0; i < animals.size(); i++) {
            Animal animal = animals.get(i);

            int row = i / animalsPerRow;
            int col = i % animalsPerRow;
            float x = interiorX + padding + col * (animalSize + padding) + 500;
            float y = interiorY + startYOffset + drawableHeight - padding - animalSize - row * (animalSize + padding) - 230;

            if (y < interiorY + padding) {
                continue;
            }

            TextureRegion currentFrame;

            if (animal == feedingAnimal) {
                currentFrame = animalAsset.getFeedAnimation(animal.getAnimalType().getType()).getKeyFrame(feedingTime, false);


                float hopperSize = 64f;
                float hopperX = x + (animalSize - hopperSize) / 2f;
                float hopperY = y - hopperSize ;

                if (feedingTime < FEEDING_DURATION / 2) {
                    batch.draw(hayHopperFullTexture, hopperX, hopperY, hopperSize, hopperSize);
                } else if (feedingTime < FEEDING_DURATION) {
                    batch.draw(hayHopperTexture, hopperX, hopperY, hopperSize, hopperSize);
                }

            } else {
                currentFrame = animalAsset.getIdleFrame(animal.getAnimalType().getType());
            }

            if (currentFrame != null) {
                batch.draw(currentFrame, x, y, animalSize, animalSize);
                visibleAnimals.add(new AnimalPosition(animal, x, y, animalSize, animalSize));

                List<AnimalProduct> products = animal.getProducedProducts();
                for (int j = 0; j < products.size(); j++) {
                    AnimalProduct product = products.get(j);
                    float productSize = 48f;
                    float productX = x + (animalSize - productSize) / 2f;
                    float productY = y - productSize - 10 - (j * (productSize + 5));
                    Sprite productSprite = animalProductAsset.getSprite(product.getName());

                    batch.draw(productSprite, productX, productY, productSize, productSize);
                    visibleProducts.add(new ProductPosition(animal, product, productX, productY, productSize, productSize));
                }
            }
        }
    }

    public void startFeeding(Animal animal) {
        this.feedingAnimal = animal;
        this.feedingTime = 0;
        this.feedingAnimal.setStateTime(0);
    }

    public boolean handleInteriorClick(float screenX, float screenY, int button) {
        if (!isShowingInterior() || (visibleAnimals.isEmpty() && visibleProducts.isEmpty())) {
            return false;
        }

        float worldY = Gdx.graphics.getHeight() - screenY;

        if (button == 1) {
            for (AnimalPosition animalPos : visibleAnimals) {
                if (animalPos.contains(screenX, worldY)) {

                    return animalController.handleRightClick(screenX, screenY, animalPos.animal, animalPos.x, animalPos.y);
                }
            }
        } else if (button == 0) {
            for (AnimalPosition animalPos : visibleAnimals) {
                if (animalPos.contains(screenX, worldY)) {

                    return animalController.handleLeftClick(screenX, screenY, animalPos.animal, animalPos.x, animalPos.y);
                }
            }
            for (ProductPosition productPos : visibleProducts) {
                if (productPos.contains(screenX, worldY)) {
                    Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();
                    boolean added = currentPlayer.getInventory().getBackPack().addItem(productPos.product);
                    if (added) {
                        productPos.animal.removeProduct(productPos.product);
                        MessageSystem.showInfo(productPos.product.getName() + " was harvested!", 3.0f);
                    } else {
                        MessageSystem.showError("Not enough space in backpack!", 3.0f);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void renderPlacingBuilding(SpriteBatch batch) {
        if (isPlacingCoop) {
            Sprite coopSprite;
            switch (selectedCoopType) {
                case 1:
                    coopSprite = animalBuildingAsset.getBigCoop();
                    break;
                case 2:
                    coopSprite = animalBuildingAsset.getDeluxeCoop();
                    break;
                default:
                    coopSprite = animalBuildingAsset.getCoop();
                    break;
            }
            coopSprite.setAlpha(0.7f);
            coopSprite.setPosition(tempCoopX, tempCoopY);
            coopSprite.draw(batch);
            coopSprite.setAlpha(1.0f);
        }

        if (isPlacingBarn) {
            Sprite barnSprite;
            switch (selectedBarnType) {
                case 1:
                    barnSprite = animalBuildingAsset.getBigBarn();
                    break;
                case 2:
                    barnSprite = animalBuildingAsset.getDeluxeBarn();
                    break;
                default:
                    barnSprite = animalBuildingAsset.getBarn();
                    break;
            }
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
        if (feedingAnimal != null) {
            return;
        }
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

    private void placeCoop() {
        if (isValidPlacement(tempCoopX, tempCoopY, true)) {
            Position position = new Position((int)tempCoopX, (int)tempCoopY);
            Sprite coopSprite;
            switch (selectedCoopType) {
                case 1:
                    coopSprite = animalBuildingAsset.getBigCoop();
                    break;
                case 2:
                    coopSprite = animalBuildingAsset.getDeluxeCoop();
                    break;
                default:
                    coopSprite = animalBuildingAsset.getCoop();
                    break;
            }
            int coopWidth = coopSprite.getRegionWidth();
            int coopHeight = coopSprite.getRegionHeight();
            Coop newCoop = new Coop(position, coopHeight, coopWidth);
            switch (selectedCoopType) {
                case 1:
                    newCoop.setBig();
                    break;
                case 2:
                    newCoop.setDeluxe();
                    break;
            }
            placedCoops.add(newCoop);
            MessageSystem.showInfo("Coop added to map!",4.0f);
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
            System.out.println(newCoop.getPlaceName() + " placed at tile: " + startTileX + ", " + startTileY);
            isPlacingCoop = false;
        } else {
            System.out.println("Cannot place coop here. Invalid position.");
        }
    }

    private void placeBarn() {
        if (isValidPlacement(tempBarnX, tempBarnY, false)) {
            Position position = new Position((int)tempBarnX, (int)tempBarnY);
            Sprite barnSprite;
            switch (selectedBarnType) {
                case 1:
                    barnSprite = animalBuildingAsset.getBigBarn();
                    break;
                case 2:
                    barnSprite = animalBuildingAsset.getDeluxeBarn();
                    break;
                default:
                    barnSprite = animalBuildingAsset.getBarn();
                    break;
            }
            int barnWidth = barnSprite.getRegionWidth();
            int barnHeight = barnSprite.getRegionHeight();
            Barn newBarn = new Barn(position, barnHeight, barnWidth);
            switch (selectedBarnType) {
                case 1:
                    newBarn.setBig();
                    break;
                case 2:
                    newBarn.setDeluxe();
                    break;
            }
            placedBarns.add(newBarn);
            MessageSystem.showInfo("Barn added to map!", 4.0f);
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
            System.out.println(newBarn.getPlaceName() + " placed at tile: " + startTileX + ", " + startTileY);
            isPlacingBarn = false;
        } else {
            System.out.println("Cannot place barn here. Invalid position.");
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
            Sprite coopSprite;
            if (coop.isDeluxe()) {
                coopSprite = animalBuildingAsset.getDeluxeCoop();
            } else if (coop.isBig()) {
                coopSprite = animalBuildingAsset.getBigCoop();
            } else {
                coopSprite = animalBuildingAsset.getCoop();
            }
            Rectangle bounds = new Rectangle(pos.getX(), pos.getY(),
                coopSprite.getRegionWidth(),
                coopSprite.getRegionHeight());
            if (bounds.contains(worldX, worldY)) {
                System.out.println("Clicked on " + coop.getPlaceName() + " at: " + pos.getX() + ", " + pos.getY());
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
            Sprite barnSprite;
            if (barn.isDeluxe()) {
                barnSprite = animalBuildingAsset.getDeluxeBarn();
            } else if (barn.isBig()) {
                barnSprite = animalBuildingAsset.getBigBarn();
            } else {
                barnSprite = animalBuildingAsset.getBarn();
            }
            Rectangle bounds = new Rectangle(pos.getX(), pos.getY(),
                barnSprite.getRegionWidth(),
                barnSprite.getRegionHeight());
            if (bounds.contains(worldX, worldY)) {
                System.out.println("Clicked on " + barn.getPlaceName() + " at: " + pos.getX() + ", " + pos.getY());
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

    private boolean isValidPlacement(float x, float y, boolean isCoop) {
        Sprite buildingSprite;
        if (isCoop) {
            switch (selectedCoopType) {
                case 1:
                    buildingSprite = animalBuildingAsset.getBigCoop();
                    break;
                case 2:
                    buildingSprite = animalBuildingAsset.getDeluxeCoop();
                    break;
                default:
                    buildingSprite = animalBuildingAsset.getCoop();
                    break;
            }
        } else {
            switch (selectedBarnType) {
                case 1:
                    buildingSprite = animalBuildingAsset.getBigBarn();
                    break;
                case 2:
                    buildingSprite = animalBuildingAsset.getDeluxeBarn();
                    break;
                default:
                    buildingSprite = animalBuildingAsset.getBarn();
                    break;
            }
        }
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
        if (animalController != null) {
            animalController.dispose();
        }
        animalProductAsset.dispose();
        hayHopperFullTexture.dispose();
        hayHopperTexture.dispose();
    }

    public boolean placeAnimalOnBuilding(String animalName, float worldX, float worldY) {
        for (Coop coop : placedCoops) {
            Position pos = coop.getPosition();
            Rectangle bounds = new Rectangle(pos.getX(), pos.getY(),
                animalBuildingAsset.getCoop().getRegionWidth(),
                animalBuildingAsset.getCoop().getRegionHeight());
            if (bounds.contains(worldX, worldY)) {
                Animal animal = null;
                for(AnimalType animalType : AnimalType.values()){
                    if(animalType.getType().equalsIgnoreCase(animalName)){
                        animal = new Animal(animalType,animalName);
                    }
                }
                if(!coop.isFull()) {
                    coop.addAnimal(animal);
                    coop.setAnimalCount(coop.getAnimalCount() + 1);
                    App.getInstance().getCurrentGame().getAnimals().put(animalName,animal);
                    App.getInstance().getCurrentGame().getCurrentPlayer().getPlayerAnimals().add(animal);
                    MessageSystem.showInfo("A " + animalName + " added to COOP!", 5.0f);
                    System.out.println(animalName + " placed in Coop at: " + pos.getX() + ", " + pos.getY());
                }
                else {
                    MessageSystem.showError("The selected coop is FULL!", 5.0f);
                }
                return true;
            }
        }

        for (Barn barn : placedBarns) {
            Position pos = barn.getPosition();
            Rectangle bounds = new Rectangle(pos.getX(), pos.getY(),
                animalBuildingAsset.getBarn().getRegionWidth(),
                animalBuildingAsset.getBarn().getRegionHeight());
            if (bounds.contains(worldX, worldY)) {
                Animal animal = null;
                for(AnimalType animalType : AnimalType.values()){
                    if(animalType.getType().equalsIgnoreCase(animalName)){
                        animal = new Animal(animalType,animalName);
                    }
                }
                if(!barn.isFull()) {
                    barn.addAnimal(animal);
                    barn.setAnimalCount(barn.getAnimalCount() + 1);
                    App.getInstance().getCurrentGame().getAnimals().put(animalName,animal);
                    App.getInstance().getCurrentGame().getCurrentPlayer().getPlayerAnimals().add(animal);
                    MessageSystem.showInfo("A " + animalName + "added to BARN!", 5.0f);
                    System.out.println(animalName + " placed in Barn at: " + pos.getX() + ", " + pos.getY());
                }
                else {
                    MessageSystem.showError("The selected barn is FULL!", 5.0f);
                }
                return true;
            }
        }
        return false;
    }

    public void removeAnimalFromBuilding(Animal animal) {
        if (selectedCoop != null) {
            selectedCoop.getAnimals().remove(animal);
            selectedCoop.setAnimalCount(selectedCoop.getAnimalCount() - 1);
            if (isShowingCoopInterior()) {
                closeInteriorView();
            }
        } else if (selectedBarn != null) {
            selectedBarn.getAnimals().remove(animal);
            selectedBarn.setAnimalCount(selectedBarn.getAnimalCount() - 1);
            if (isShowingBarnInterior()) {
                closeInteriorView();
            }
        }
    }

    public void startPlacingBarn(int barnType) {
        if (!isPlacingCoop && !isPlacingBarn) {
            isPlacingBarn = true;
            selectedBarnType = barnType;
            tempBarnX = App.getInstance().getCurrentGame().getCurrentPlayer().getX();
            tempBarnY = App.getInstance().getCurrentGame().getCurrentPlayer().getY();
            System.out.println("Starting to place " + getBarnTypeName(barnType));
        }
    }

    public void startPlacingCoop(int coopType) {
        if (!isPlacingCoop && !isPlacingBarn) {
            isPlacingCoop = true;
            selectedCoopType = coopType;
            tempCoopX = App.getInstance().getCurrentGame().getCurrentPlayer().getX();
            tempCoopY = App.getInstance().getCurrentGame().getCurrentPlayer().getY();
            System.out.println("Starting to place " + getCoopTypeName(coopType));
        }
    }

    public void changeBarnType(int barnType) {
        if (isPlacingBarn) {
            selectedBarnType = barnType;
            System.out.println("Changed to " + getBarnTypeName(barnType));
        }
    }

    public void changeCoopType(int coopType) {
        if (isPlacingCoop) {
            selectedCoopType = coopType;
            System.out.println("Changed to " + getCoopTypeName(coopType));
        }
    }

    private String getBarnTypeName(int type) {
        switch (type) {
            case 1: return "Big Barn";
            case 2: return "Deluxe Barn";
            default: return "Barn";
        }
    }

    private String getCoopTypeName(int type) {
        switch (type) {
            case 1: return "Big Coop";
            case 2: return "Deluxe Coop";
            default: return "Coop";
        }
    }
}
