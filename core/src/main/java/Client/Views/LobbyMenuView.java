package Client.Views;

import Client.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LobbyMenuView implements Screen {

    private final Stage stage;
    private final Skin skin;
    private Texture backgroundTexture;
    private final Table mainTable;

    private final Color TITLE_COLOR = new Color(0.4f, 0.2f, 0.1f, 1f);

    public LobbyMenuView(Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());

        try {
            this.backgroundTexture = new Texture(Gdx.files.internal("backgrounds/LobbyMenu.png"));
        } catch (Exception e) {
            System.out.println("Background image not found: " + e.getMessage());
        }

        this.mainTable = new Table();
        createUI();
    }

    private void createUI() {
        mainTable.setFillParent(true);
        mainTable.pad(50);

        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.getFont("Impact"), TITLE_COLOR);
        Label titleLabel = new Label("Lobby Menu", titleStyle);
        titleLabel.setFontScale(2.0f);
        mainTable.add(titleLabel).colspan(2).padBottom(30).row();

        TextButton createLobbyButton = new TextButton("Create Lobby", skin);
        createLobbyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showCreateLobbyDialog();
            }
        });
        mainTable.add(createLobbyButton).width(300).height(80).padBottom(20).row();


        TextButton listLobbiesButton = new TextButton("List Lobbies", skin);
        listLobbiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showLobbyListDialog();
            }
        });
        mainTable.add(listLobbiesButton).width(300).height(80).padBottom(20).row();


        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().switchScreen(new MainMenuView());
            }
        });
        mainTable.add(backButton).width(300).height(60).padTop(20).row();

        stage.addActor(mainTable);
    }

    private void showCreateLobbyDialog() {
        Dialog createLobbyDialog = new Dialog("Create Lobby", skin) {
            private final TextField lobbyNameField;
            private final CheckBox privateCheckBox;
            private final CheckBox visibleCheckBox;
            private final TextField passwordField;
            private final Table passwordTable;

            {

                getContentTable().pad(20);

                lobbyNameField = new TextField("", skin);
                lobbyNameField.setMessageText("Enter lobby name...");
                getContentTable().add(new Label("Lobby Name:", skin)).align(Align.left).padRight(10);
                getContentTable().add(lobbyNameField).width(400).pad(10).row();
                Table checkboxTable = new Table();

                privateCheckBox = new CheckBox(" Private", skin);
                checkboxTable.add(privateCheckBox).align(Align.left).padRight(30);

                visibleCheckBox = new CheckBox(" Visible", skin);
                visibleCheckBox.setChecked(true);
                checkboxTable.add(visibleCheckBox).align(Align.left);

                getContentTable().add(checkboxTable).colspan(2).align(Align.left).padTop(10).padBottom(10).row();

                passwordTable = new Table();
                passwordField = new TextField("", skin);
                passwordField.setMessageText("Enter password...");
                passwordField.setPasswordMode(true);
                passwordField.setPasswordCharacter('*');
                passwordTable.add(new Label("Password:", skin)).align(Align.left).padRight(10);
                passwordTable.add(passwordField).width(400).pad(10);
                passwordTable.setVisible(false);

                getContentTable().add(passwordTable).colspan(2).padTop(10).padBottom(10).row();

                privateCheckBox.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        passwordTable.setVisible(privateCheckBox.isChecked());
                        pack();
                    }
                });

                button("Create", "create");
                button("Cancel", "cancel");
            }

            @Override
            protected void result(Object object) {
                if (object.equals("create")) {
                    String lobbyName = lobbyNameField.getText().trim();
                    boolean isPrivate = privateCheckBox.isChecked();
                    boolean isVisible = visibleCheckBox.isChecked();
                    String password = isPrivate ? passwordField.getText() : "";

                    if (!lobbyName.isEmpty()) {
                        if (isPrivate && password.isEmpty()) {
                            showErrorDialog("Password is required for private lobbies.");
                        } else {
                            // TODO: add creat lobby logic
                            System.out.println("Lobby '" + lobbyName + "' created.");
                            System.out.println("Private: " + isPrivate);
                            System.out.println("Visible: " + isVisible);
                            if (isPrivate) {
                                System.out.println("Password: " + password);
                            }
                        }
                    } else {
                        showErrorDialog("Lobby name cannot be empty.");
                    }
                }
            }
        };

        createLobbyDialog.show(stage);
        createLobbyDialog.setSize(700, 500);
        createLobbyDialog.setPosition(
                (Gdx.graphics.getWidth() - createLobbyDialog.getWidth()) / 2,
                (Gdx.graphics.getHeight() - createLobbyDialog.getHeight()) / 2
        );
    }

    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog("Error", skin);
        errorDialog.text(message);
        errorDialog.button("OK");
        errorDialog.show(stage);
    }

    private void showLobbyListDialog() {
        Dialog lobbyListDialog = new Dialog("Lobby List", skin) {
            {
                // TODO: add list off lobbies
                getContentTable().add(new Label("Loading lobbies...", skin)).row();

                // TODO: join lobby logic

                button("Refresh", "refresh");
                button("Close", "close");
            }

            @Override
            protected void result(Object object) {
                if (object.equals("refresh")) {
                    // TODO: add refresh lobbies logic
                }
            }
        };

        lobbyListDialog.show(stage);
        lobbyListDialog.setSize(800,500);
        lobbyListDialog.setPosition(
            (Gdx.graphics.getWidth() - lobbyListDialog.getWidth()) / 2,
            (Gdx.graphics.getHeight() - lobbyListDialog.getHeight()) / 2
        );
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (backgroundTexture != null) {
            stage.getBatch().begin();
            stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.getBatch().end();
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }
}
