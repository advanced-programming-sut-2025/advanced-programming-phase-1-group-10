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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.Scanner;

public class GameLauncherView implements AppMenu, Screen, InputProcessor {

    private final Stage stage;
    private OrthographicCamera camera;
    private final StretchViewport viewport;
    private final GameControllerFinal controller;

    public GameLauncherView() {
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        this.stage = new Stage(viewport);
        this.controller = new GameControllerFinal();
        this.controller.setView(this);

        viewport.apply();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void checkCommand(Scanner scanner) {

    }

    @Override
    public void show() {
        float centerX = Map.mapWidth * Map.tileSize / 2f;
        float centerY = Map.mapHeight * Map.tileSize / 2f;

        camera.position.set(centerX, centerY, 0);
        camera.update();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().setProjectionMatrix(camera.combined);

        camera.position.set(App.getInstance().getCurrentGame().getCurrentPlayer().getX(), App.getInstance().getCurrentGame().getCurrentPlayer().getY(), 0);
        camera.update();

        camera.zoom = 1f;

        stage.getBatch().begin();
        controller.update((SpriteBatch) stage.getBatch());
        stage.getBatch().end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
