package Client;

import Client.Views.MainMenuView;
import Client.Controllers.MessageSystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Main extends Game {
    private SpriteBatch batch;
    private Skin skin;

    @Override
    public void create() {
        batch = new SpriteBatch();


        try {
            skin = new Skin(Gdx.files.internal("assets/mainskin/skin.json"));
        } catch (Exception e) {
            System.out.println("Error loading skin: " + e.getMessage());
            e.printStackTrace();
        }
        MessageSystem.initialize();
        switchScreen(new MainMenuView());
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (skin != null) {
            skin.dispose();
        }


        if (getScreen() != null) {
            getScreen().dispose();
        }
    }

    public void switchScreen(Screen newScreen) {

        if (getScreen() != null && getScreen() != newScreen) {
            getScreen().dispose();
        }
        setScreen(newScreen);
    }

    private static Main instance;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Skin getSkin() {
        return skin;
    }
}
