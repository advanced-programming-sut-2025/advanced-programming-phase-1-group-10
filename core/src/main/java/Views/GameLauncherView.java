package Views;

import Assets.TextureCache;
import Controllers.FinalControllers.AnimalListController;
import Controllers.FinalControllers.GameControllerFinal;
import Controllers.MessageSystem;
import Models.App;
import Models.Map;
import Models.Planets.Fruit;
import Models.Planets.Tree;
import Models.PlayerStuff.Player;
import Models.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.Scanner;

public class GameLauncherView implements AppMenu, Screen, InputProcessor {

    private final Skin skin;

    private final Stage stage;
    private final OrthographicCamera camera;
    private final OrthographicCamera hudCamera;
    private final StretchViewport viewport;
    private final GameControllerFinal controller;

    // temporary variables
    private boolean isDraggingAnimal = false;
    private String draggedAnimalName = null;
    private float draggedAnimalX;
    private float draggedAnimalY;

    private final SpriteBatch batch;

    private float elapsedTime;

    public GameLauncherView(Skin skin) {
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        this.stage = new Stage(viewport);
        this.controller = new GameControllerFinal();
        App.getInstance().setGameControllerFinal(controller);
        this.controller.setView(this);
        this.batch = (SpriteBatch) stage.getBatch();
        this.hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.update();
        viewport.apply();
        Gdx.input.setInputProcessor(this);
        this.skin = skin;
    }

    @Override
    public void checkCommand(Scanner scanner) {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (controller.getAnimalBuildingController().isShowingInterior()) {
            batch.setProjectionMatrix(hudCamera.combined);
            batch.begin();
            controller.getAnimalBuildingController().update(batch, delta);
            batch.end();
            elapsedTime += delta;
            return;
        }

        // Get camera dimensions in world units
        float halfWidth = camera.viewportWidth * camera.zoom / 2f;
        float halfHeight = camera.viewportHeight * camera.zoom / 2f;

        // Get map boundaries in pixels
        float mapPixelWidth = Map.mapWidth * Map.tileSize;
        float mapPixelHeight = Map.mapHeight * Map.tileSize;

        // Get player coordinates
        float targetX = App.getInstance().getCurrentGame().getCurrentPlayer().getX();
        float targetY = App.getInstance().getCurrentGame().getCurrentPlayer().getY();

        // Clamp camera position so it doesn't show areas outside the map
        float cameraX = Math.max(halfWidth, Math.min(targetX, mapPixelWidth - halfWidth));
        float cameraY = Math.max(halfHeight, Math.min(targetY, mapPixelHeight - halfHeight));
        camera.position.set(cameraX, cameraY, 0);

        camera.zoom = 0.6f;
        camera.update();

        // ----- Render game world -----
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        controller.update(batch, delta);
        batch.end();

        // ----- Render HUD/UI (BarController) -----
        // Switch to screen-space projection (identity matrix)
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        controller.updateSecondCamera(batch, delta, viewport);

        if (isDraggingAnimal && draggedAnimalName != null) {
            Texture animalTexture = controller.getAnimalListController().getAnimalAsset().getSingleTexture(draggedAnimalName);
            if (animalTexture != null) {
                batch.draw(animalTexture, draggedAnimalX, draggedAnimalY, 64, 64);
            }
        }
        batch.end();

        stage.act(delta);
        stage.draw();

        controller.getFriendshipController().checkFKeyPress();
        if (controller.getFriendshipController().isMenuOpen()) {
            controller.getFriendshipController().render(delta);
        }


        controller.getCheatBoxController().render();
        elapsedTime += delta;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        hudCamera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        controller.getAnimalBuildingController().dispose();
        controller.getCheatBoxController().dispose();
        MessageSystem.dispose();
        if (controller.getFishController() != null) {
            controller.getFishController().dispose();
        }
        controller.getFishingMiniGameController().dispose();
        TextureCache.disposeAll();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (controller.isFishingMiniGameActive()) {
            if (keycode == Input.Keys.UP || keycode == Input.Keys.DOWN || keycode == Input.Keys.Q) {
                if (keycode == Input.Keys.Q) {
                    controller.setFishingMiniGameActive(false);
                }
                return true;
            }
        }

        if (keycode == Input.Keys.SLASH) {
            int barnType = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
                barnType = 0; // normal barn
            } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
                barnType = 1; // big barn
            } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
                barnType = 2; // deluxe barn
            }

            controller.getAnimalBuildingController().startPlacingBarn(barnType);
            return true;
        }

        if (keycode == Input.Keys.BACKSLASH) {
            int coopType = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
                coopType = 0; // normal coop
            } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
                coopType = 1; // big coop
            } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
                coopType = 2; // deluxe coop
            }

            controller.getAnimalBuildingController().startPlacingCoop(coopType);
            return true;
        }

        if ((keycode == Input.Keys.NUM_1 || keycode == Input.Keys.NUM_2
            || keycode == Input.Keys.NUM_3) &&
            (controller.getAnimalBuildingController().isPlacingBarn()
                || controller.getAnimalBuildingController().isPlacingCoop())) {
            int buildingType = keycode - Input.Keys.NUM_1;
            if (controller.getAnimalBuildingController().isPlacingBarn()) {
                controller.getAnimalBuildingController().changeBarnType(buildingType);
            } else if (controller.getAnimalBuildingController().isPlacingCoop()) {
                controller.getAnimalBuildingController().changeCoopType(buildingType);
            }

            return true;
        }

        if (keycode == Input.Keys.N) {
            controller.getAnimalListController().toggleVisibility();
        }
        controller.getInventoryBarController().selectSlotByKey(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (controller.getAnimalBuildingController().isShowingInterior()) {
            boolean handled = controller.getAnimalBuildingController().handleInteriorClick(screenX, screenY, button);
            if (handled) {
                return true;
            }
        }

        if (controller.getAnimalListController().isShowing() && button == Input.Buttons.LEFT) {
            AnimalListController.AnimalDisplayData animalData = controller.getAnimalListController().getAnimalAt(screenX, screenY);
            if (animalData != null) {
                isDraggingAnimal = true;
                draggedAnimalName = animalData.name;
                draggedAnimalX = screenX;
                draggedAnimalY = screenY;
                return true;
            }
        }

        Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
        float clickX = worldCoords.x;
        float clickY = worldCoords.y;

        int tileX = (int)(clickX / Map.tileSize);
        int tileY = (int)(clickY / Map.tileSize);

        Tile clickedTile = App.getInstance().getCurrentGame().getGameMap().getMap()[tileY][tileX];
        if (clickedTile.getItem() instanceof Tree) {
            Tree tree = (Tree) clickedTile.getItem();
            if (tree.hasFruits()) {
                Fruit harvestedFruit = tree.harvestFruit();
                if (harvestedFruit != null) {
                    boolean added = App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().addItem(harvestedFruit);
                    if(added) {
                        MessageSystem.showInfo("Fruit " + harvestedFruit.getName() + " harvested!", 4.0f);
                    }
                    return true;
                }
            }
        }

        boolean buildingClicked = controller.getAnimalBuildingController().handleClick(clickX, clickY);
        if (buildingClicked) {
            return true;
        }

        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        float playerX = player.getX();
        float playerY = player.getY();

        float dx = clickX - playerX;
        float dy = clickY - playerY;

        String direction;
        if (Math.abs(dx) > Math.abs(dy)) {
            direction = (dx > 0) ? "RIGHT" : "LEFT";
        } else {
            direction = (dy > 0) ? "UP" : "DOWN";
        }

        controller.getInteractController().useItemInDirection(direction);

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDraggingAnimal) {
            draggedAnimalX = screenX - 32;
            draggedAnimalY = Gdx.graphics.getHeight() - screenY - 32;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (isDraggingAnimal && button == Input.Buttons.LEFT) {
            Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
            float dropX = worldCoords.x;
            float dropY = worldCoords.y;

            boolean animalPlaced = controller.getAnimalBuildingController().placeAnimalOnBuilding(draggedAnimalName, dropX, dropY);

            isDraggingAnimal = false;
            draggedAnimalName = null;
            return animalPlaced;
        }
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (amountY > 0) {
            controller.getInventoryBarController().scrollSlot(+1);
        } else if (amountY < 0) {
            controller.getInventoryBarController().scrollSlot(-1);
        }
        return true;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public Stage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return skin;
    }
}
