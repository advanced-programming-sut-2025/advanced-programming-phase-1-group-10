package Client.Views;

import Client.Main;
import Client.Network.ClientNetworkManager;
import Common.Network.Send.MessageTypes.JoinLobbyResponseMessage;
import Common.Network.Send.MessageTypes.ListLobbiesResponseMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.List;

public class LobbyMenuView implements Screen {

    private final Stage stage;
    private final Skin skin;
    private Texture backgroundTexture;
    private final Table mainTable;
    private final ClientNetworkManager networkManager;


    private Dialog loadingDialog;

    private final Color TITLE_COLOR = new Color(0.4f, 0.2f, 0.1f, 1f);

    public LobbyMenuView(Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.networkManager = ClientNetworkManager.getInstance();
        this.loadingDialog = null;

        try {
            this.backgroundTexture = new Texture(Gdx.files.internal("backgrounds/LobbyMenu.png"));
        } catch (Exception e) {
            System.out.println("Background image not found: " + e.getMessage());
        }

        this.mainTable = new Table();
        createUI();
        setupNetworkCallbacks();
    }

    private void setupNetworkCallbacks() {

        networkManager.setOnLobbyJoined(response -> {
            Gdx.app.postRunnable(() -> {

                hideLoadingDialog();

                if (response.isSuccess()) {
                    Main.getInstance().switchScreen(new LobbyView(skin, response));
                } else {
                    showErrorDialog(response.getErrorMessage());
                }
            });
        });


        networkManager.setOnError(errorMessage -> {
            Gdx.app.postRunnable(() -> {

                hideLoadingDialog();

                showErrorDialog(errorMessage);
            });
        });
    }


    private void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.hide();
            loadingDialog = null;
        }
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

        TextButton searchLobbyButton = new TextButton("Search Lobby by ID", skin);
        searchLobbyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showSearchLobbyDialog();
            }
        });
        mainTable.add(searchLobbyButton).width(300).height(80).padBottom(20).row();

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().switchScreen(new MainMenuView());
            }
        });
        mainTable.add(backButton).width(300).height(80).padTop(20).row();

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

                            showLoadingDialog("Creating Lobby", "Creating lobby, please wait...");


                            stage.addAction(Actions.sequence(
                                Actions.delay(15f),
                                Actions.run(() -> {

                                    if (loadingDialog != null) {
                                        hideLoadingDialog();
                                        showErrorDialog("Timeout: No response from server. Please try again.");
                                    }
                                })
                            ));

                            networkManager.createLobby(lobbyName, isPrivate, isVisible, password);

                            hide();
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


    private void showLoadingDialog(String title, String message) {

        hideLoadingDialog();


        loadingDialog = new Dialog(title, skin);
        loadingDialog.text(message);
        loadingDialog.show(stage);
    }

    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog("Error", skin);
        errorDialog.text(message);
        errorDialog.button("OK");
        errorDialog.show(stage);
    }

    private void showLobbyListDialog() {

        showLoadingDialog("Loading Lobbies", "Loading lobbies, please wait...");


        networkManager.requestLobbiesList();

        Dialog lobbyListDialog = new Dialog("Lobby List", skin) {
            private Table lobbiesTable;

            {
                lobbiesTable = new Table(skin);
                lobbiesTable.defaults().pad(10);


                lobbiesTable.add(new Label("Name", skin)).width(250);
                lobbiesTable.add(new Label("Players", skin)).width(100);
                lobbiesTable.add(new Label("Status", skin)).width(100);
                lobbiesTable.add(new Label("Actions", skin)).width(150).row();

                ScrollPane scrollPane = new ScrollPane(lobbiesTable, skin);
                scrollPane.setFadeScrollBars(false);
                scrollPane.setScrollingDisabled(true, false);

                getContentTable().add(scrollPane).width(900).height(550).pad(20);


                networkManager.setOnLobbiesListUpdated(lobbies -> {

                    Gdx.app.postRunnable(() -> {

                        hideLoadingDialog();


                        updateLobbyList(lobbies);
                        show(stage);
                    });
                });

                button("Refresh", "refresh");
                button("Close", "close");
            }

            private void updateLobbyList(List<ListLobbiesResponseMessage.LobbyInfo> lobbies) {

                lobbiesTable.clear();


                lobbiesTable.defaults().pad(10);
                lobbiesTable.add(new Label("Name", skin)).width(250);
                lobbiesTable.add(new Label("Players", skin)).width(100);
                lobbiesTable.add(new Label("Status", skin)).width(100);
                lobbiesTable.add(new Label("Actions", skin)).width(150).row();

                if (lobbies == null || lobbies.isEmpty()) {
                    lobbiesTable.add("No lobbies available").colspan(4).row();
                    return;
                }

                for (ListLobbiesResponseMessage.LobbyInfo lobby : lobbies) {
                    lobbiesTable.add(lobby.getName()).width(250);
                    lobbiesTable.add(lobby.getPlayerCount() + "/4").width(100);

                    String status = lobby.isPrivate() ? "Private" : "Public";
                    lobbiesTable.add(status).width(100);

                    Table actionsTable = new Table();
                    TextButton joinButton = new TextButton("Join", skin);

                    joinButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if (lobby.isPrivate()) {
                                showPasswordDialog(lobby.getLobbyId());
                            } else {

                                showLoadingDialog("Joining Lobby", "Joining lobby, please wait...");
                                networkManager.joinLobby(lobby.getLobbyId(), null);
                                hide();
                            }
                        }
                    });

                    actionsTable.add(joinButton).width(100);
                    lobbiesTable.add(actionsTable).width(150).row();
                }
            }

            private void showPasswordDialog(String lobbyId) {
                Dialog passwordDialog = new Dialog("Enter Password", skin) {
                    private TextField passwordField;

                    {
                        passwordField = new TextField("", skin);
                        passwordField.setPasswordMode(true);
                        passwordField.setPasswordCharacter('*');

                        getContentTable().add(new Label("Password:", skin)).padRight(10);
                        getContentTable().add(passwordField).width(200).pad(10).row();

                        button("Join", "join");
                        button("Cancel", "cancel");
                    }

                    @Override
                    protected void result(Object object) {
                        if (object.equals("join")) {
                            String password = passwordField.getText();

                            showLoadingDialog("Joining Lobby", "Joining lobby, please wait...");
                            networkManager.joinLobby(lobbyId, password);
                            hide();
                        }
                    }
                };

                passwordDialog.show(stage);
            }

            @Override
            protected void result(Object object) {
                if (object.equals("refresh")) {

                    showLoadingDialog("Loading Lobbies", "Loading lobbies, please wait...");
                    networkManager.requestLobbiesList();
                }
            }
        };



        lobbyListDialog.setSize(1200, 800);
        lobbyListDialog.setPosition(
            (Gdx.graphics.getWidth() - lobbyListDialog.getWidth()) / 2,
            (Gdx.graphics.getHeight() - lobbyListDialog.getHeight()) / 2
        );
    }

    private void showSearchLobbyDialog() {
        Dialog searchLobbyDialog = new Dialog("Search Lobby by ID", skin) {
            private final TextField lobbyIdField;
            private final TextField passwordField;
            private final Table passwordTable;
            private CheckBox privateCheckBox;

            {
                getContentTable().pad(20);

                lobbyIdField = new TextField("", skin);
                lobbyIdField.setMessageText("Enter Lobby ID...");
                getContentTable().add(new Label("Lobby ID:", skin)).align(Align.left).padRight(10);
                getContentTable().add(lobbyIdField).width(400).pad(10).row();

                privateCheckBox = new CheckBox(" Private Lobby?", skin);
                getContentTable().add(privateCheckBox).colspan(2).align(Align.left).padTop(10).padBottom(10).row();

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

                button("Search & Join", "search_join");
                button("Cancel", "cancel");
            }

            @Override
            protected void result(Object object) {
                if (object.equals("search_join")) {
                    String lobbyId = lobbyIdField.getText().trim();
                    boolean isPrivate = privateCheckBox.isChecked();
                    String password = isPrivate ? passwordField.getText() : "";

                    if (!lobbyId.isEmpty()) {
                        if (isPrivate && password.isEmpty()) {
                            showErrorDialog("Password is required for private lobbies.");
                        } else {
                            showLoadingDialog("Joining Lobby", "Searching and joining lobby, please wait...");
                            networkManager.joinLobby(lobbyId, password);
                            hide();
                        }
                    } else {
                        showErrorDialog("Lobby ID cannot be empty.");
                    }
                }
            }
        };

        searchLobbyDialog.show(stage);
        searchLobbyDialog.setSize(800, 500);
        searchLobbyDialog.setPosition(
            (Gdx.graphics.getWidth() - searchLobbyDialog.getWidth()) / 2,
            (Gdx.graphics.getHeight() - searchLobbyDialog.getHeight()) / 2
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
    public void hide() {

        networkManager.setOnLobbiesListUpdated(null);
        networkManager.setOnLobbyJoined(null);
        networkManager.setOnError(null);


        hideLoadingDialog();
    }

    @Override
    public void dispose() {
        stage.dispose();
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }
}
