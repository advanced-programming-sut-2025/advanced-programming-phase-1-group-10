package Views;

import Controllers.FinalControllers.GameControllerFinal;
import Models.App;
import Models.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.Scanner;

public class GameLauncherView implements AppMenu, Screen, InputProcessor {

    private final Stage stage;
    private final OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private final StretchViewport viewport;
    private final GameControllerFinal controller;


    private final SpriteBatch batch;

    private float elapsedTime;

    public GameLauncherView() {
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        this.stage = new Stage(viewport);
        this.controller = new GameControllerFinal();
        this.controller.setView(this);
        this.batch = (SpriteBatch) stage.getBatch();
        this.hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.update();
        viewport.apply();
        Gdx.input.setInputProcessor(this);
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

        // Get camera dimensions in world units
        float halfWidth = camera.viewportWidth * camera.zoom / 2f;
        float halfHeight = camera.viewportHeight * camera.zoom / 2f;

        // Get map boundaries in pixels
        float mapPixelWidth = Map.mapWidth * Map.tileSize;
        float mapPixelHeight = Map.mapHeight * Map.tileSize;

        // Get player coordinates
        float targetX = App.getInstance().getCurrentGame().getCurrentPlayer().getX();
        float targetY = App.getInstance().getCurrentGame().getCurrentPlayer().getY();

        // Clamp camera position so it doesn’t show areas outside the map
        float cameraX = Math.max(halfWidth, Math.min(targetX, mapPixelWidth - halfWidth));
        float cameraY = Math.max(halfHeight, Math.min(targetY, mapPixelHeight - halfHeight));
        camera.position.set(cameraX, cameraY, 0);

        camera.zoom = 1f;
        camera.update();

        // ----- Render game world -----
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        controller.update((SpriteBatch) stage.getBatch());
        batch.end();

        // ----- Render HUD/UI (BarController) -----
        // Switch to screen-space projection (identity matrix)
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        controller.getBarController().update(batch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

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

    }

    @Override
    public boolean keyDown(int i) {
        return false;
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
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
