package Client.Views;

import Client.Main;
import Client.Network.ClientNetworkManager;
import Common.Models.PlayerStuff.Player;
import Common.Network.Send.MessageTypes.JoinLobbyResponseMessage;
import Common.Network.Send.MessageTypes.LobbyUpdateMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LobbyView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private Texture backgroundTexture;
    private final Table mainTable;
    private final Table playersTable;
    private final ClientNetworkManager networkManager;

    private final String lobbyId;
    private final String lobbyName;
    private final boolean isAdmin;

    private final Color TITLE_COLOR = new Color(0.4f, 0.2f, 0.1f, 1f);
    private final Color ADMIN_COLOR = new Color(0.8f, 0.6f, 0.1f, 1f);
    private final Color PLAYER_COLOR = new Color(0.2f, 0.6f, 0.8f, 1f);

    private Map<String, CheckBox> readyStatusCheckboxes;
    private Label statusLabel;
    private TextButton startGameButton;

    public LobbyView(Skin skin, JoinLobbyResponseMessage lobbyInfo) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.networkManager = ClientNetworkManager.getInstance();

        this.lobbyId = lobbyInfo.getLobbyId();
        this.lobbyName = lobbyInfo.getLobbyName();
        this.isAdmin = lobbyInfo.isAdmin();

        readyStatusCheckboxes = new HashMap<>();

        try {
            this.backgroundTexture = new Texture(Gdx.files.internal("backgrounds/LobbyBackground.png"));
        } catch (Exception e) {
            System.out.println("Background image not found: " + e.getMessage());
        }

        this.mainTable = new Table();
        this.playersTable = new Table(skin);

        createUI(lobbyInfo.getPlayers());
        setupNetworkCallbacks();
    }

    private void setupNetworkCallbacks() {

        networkManager.setOnLobbyJoined(response -> {
            if (response.isSuccess()) {
                Gdx.app.postRunnable(() -> {

                    showSuccessMessage("Lobby created successfully!", () -> {

                        Main.getInstance().switchScreen(new LobbyView(skin, response));
                    });
                });
            } else {
                Gdx.app.postRunnable(() -> {
                    showErrorDialog(response.getErrorMessage());
                });
            }
        });


        networkManager.setOnError(errorMessage -> {
            Gdx.app.postRunnable(() -> {
                showErrorDialog(errorMessage);
            });
        });
    }

    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog("Error", skin);
        errorDialog.text(message);
        errorDialog.button("OK");
        errorDialog.show(stage);
    }

    private void showSuccessMessage(String message, Runnable onClose) {
        Dialog successDialog = new Dialog("Success", skin) {
            @Override
            protected void result(Object object) {
                if (onClose != null) {
                    onClose.run();
                }
            }
        };
        successDialog.text(message);
        successDialog.button("OK");
        successDialog.show(stage);
    }


    private void createUI(ArrayList<Player> players) {
        mainTable.setFillParent(true);
        mainTable.pad(50);


        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.getFont("Impact"), TITLE_COLOR);
        Label titleLabel = new Label("Lobby: " + lobbyName, titleStyle);
        titleLabel.setFontScale(2.0f);
        mainTable.add(titleLabel).colspan(2).padBottom(30).row();


        statusLabel = new Label(isAdmin ? "You are the lobby admin" : "Waiting for the game to start...", skin);
        statusLabel.setColor(isAdmin ? ADMIN_COLOR : PLAYER_COLOR);
        mainTable.add(statusLabel).colspan(2).padBottom(20).row();


        Label playersLabel = new Label("Players", skin);
        playersLabel.setFontScale(1.5f);
        mainTable.add(playersLabel).colspan(2).padBottom(10).padTop(20).row();


        playersTable.defaults().pad(10);
        playersTable.add(new Label("Player", skin)).width(250);
        playersTable.add(new Label("Status", skin)).width(150).row();

        updatePlayersTable(players, new HashMap<>());

        ScrollPane scrollPane = new ScrollPane(playersTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        mainTable.add(scrollPane).colspan(2).width(400).height(200).padBottom(30).row();


        CheckBox readyCheckbox = new CheckBox(" I'm ready", skin);
        readyCheckbox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                networkManager.setPlayerReady(readyCheckbox.isChecked());
            }
        });
        mainTable.add(readyCheckbox).colspan(2).padBottom(30).row();


        Table buttonsTable = new Table();

        if (isAdmin) {
            startGameButton = new TextButton("Start Game", skin);
            startGameButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    networkManager.startGame();
                }
            });
            startGameButton.setDisabled(true);
            buttonsTable.add(startGameButton).width(200).height(60).padRight(20);
        }

        TextButton leaveButton = new TextButton("Leave Lobby", skin);
        leaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                networkManager.leaveLobby();
                Main.getInstance().switchScreen(new LobbyMenuView(skin));
            }
        });
        buttonsTable.add(leaveButton).width(200).height(60);

        mainTable.add(buttonsTable).colspan(2).padTop(20).row();

        stage.addActor(mainTable);
    }

    private void updatePlayersTable(ArrayList<Player> players, Map<Player, Boolean> readyStatus) {

        playersTable.clear();


        playersTable.defaults().pad(10);
        playersTable.add(new Label("Player", skin)).width(250);
        playersTable.add(new Label("Status", skin)).width(150).row();

        readyStatusCheckboxes.clear();

        boolean allReady = true;

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            String playerName = player.getName();


            String displayName = playerName;
            if (i == 0) {
                displayName += " (Admin)";
            }

            Label nameLabel = new Label(displayName, skin);
            if (i == 0) {
                nameLabel.setColor(ADMIN_COLOR);
            }

            playersTable.add(nameLabel).width(250);


            boolean isReady = readyStatus.containsKey(player) ? readyStatus.get(player) : false;
            if (!isReady) {
                allReady = false;
            }

            Label statusLabel = new Label(isReady ? "Ready" : "Not Ready", skin);
            statusLabel.setColor(isReady ? new Color(0.2f, 0.8f, 0.2f, 1f) : new Color(0.8f, 0.2f, 0.2f, 1f));
            playersTable.add(statusLabel).width(150).row();


            if (playerName.equals(networkManager.getUsername())) {
                CheckBox checkbox = (CheckBox) mainTable.findActor("readyCheckbox");
                if (checkbox != null) {
                    checkbox.setChecked(isReady);
                    readyStatusCheckboxes.put(playerName, checkbox);
                }
            }
        }


        if (isAdmin && startGameButton != null) {
            startGameButton.setDisabled(!allReady || players.size() < 2);

            if (players.size() < 2) {
                statusLabel.setText("Need at least 2 players to start");
            } else if (!allReady) {
                statusLabel.setText("Waiting for all players to be ready");
            } else {
                statusLabel.setText("All players ready! You can start the game");
            }
        }
    }


    private void showMessage(String message) {
        Dialog dialog = new Dialog("Message", skin);
        dialog.text(message);
        dialog.button("OK");
        dialog.show(stage);
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

        ClientNetworkManager networkManager = ClientNetworkManager.getInstance();
        networkManager.setOnLobbiesListUpdated(null);
        networkManager.setOnLobbyJoined(null);
        networkManager.setOnLobbyUpdated(null);
        networkManager.setOnError(null);
        networkManager.setOnGameStarted(null);


        if (!networkManager.isInGame()) {
            networkManager.disconnect();
        }
    }


    @Override
    public void dispose() {
        stage.dispose();
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }
}
