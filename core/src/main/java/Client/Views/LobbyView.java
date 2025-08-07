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

        networkManager.setOnLobbyUpdated((LobbyUpdateMessage updateMessage) -> {
            Gdx.app.postRunnable(() -> {
                updatePlayersTable(updateMessage.getPlayers(), updateMessage.getReadyStatus());
            });
        });

    }

    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog("Error", skin);
        errorDialog.text(message);
        errorDialog.button("OK");
        errorDialog.show(stage);


        errorDialog.setSize(600, 300);
        centerDialog(errorDialog);
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


        successDialog.setSize(600, 300);
        centerDialog(successDialog);
    }


    private void centerDialog(Dialog dialog) {
        dialog.setPosition(
            (Gdx.graphics.getWidth() - dialog.getWidth()) / 2,
            (Gdx.graphics.getHeight() - dialog.getHeight()) / 2
        );
    }

    private void createUI(ArrayList<String> players) {
        mainTable.setFillParent(true);

        mainTable.pad(80);


        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.getFont("Impact"), TITLE_COLOR);
        Label titleLabel = new Label("Lobby: " + lobbyName, titleStyle);

        titleLabel.setFontScale(2.5f);
        mainTable.add(titleLabel).colspan(2).padBottom(40).row();


        statusLabel = new Label(isAdmin ? "You are the lobby admin" : "Waiting for the game to start...", skin);
        statusLabel.setColor(isAdmin ? ADMIN_COLOR : PLAYER_COLOR);

        statusLabel.setFontScale(1.3f);
        mainTable.add(statusLabel).colspan(2).padBottom(30).row();


        Label playersLabel = new Label("Players", skin);
        playersLabel.setFontScale(1.8f);
        mainTable.add(playersLabel).colspan(2).padBottom(20).padTop(30).row();


        playersTable.defaults().pad(15);


        Label playerHeaderLabel = new Label("Player", skin);
        playerHeaderLabel.setFontScale(1.3f);

        Label statusHeaderLabel = new Label("Status", skin);
        statusHeaderLabel.setFontScale(1.3f);


        playersTable.add(playerHeaderLabel).width(350);
        playersTable.add(statusHeaderLabel).width(200).row();

        updatePlayersTable(players, new HashMap<>());

        ScrollPane scrollPane = new ScrollPane(playersTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);


        mainTable.add(scrollPane).colspan(2).width(800).height(400).padBottom(40).row();


        CheckBox readyCheckbox = new CheckBox(" I'm ready", skin);
        readyCheckbox.setName("readyCheckbox");

        readyCheckbox.getLabel().setFontScale(1.3f);
        readyCheckbox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                networkManager.setPlayerReady(readyCheckbox.isChecked());
            }
        });
        mainTable.add(readyCheckbox).colspan(2).padBottom(40).row();

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

            startGameButton.getLabel().setFontScale(1.3f);
            buttonsTable.add(startGameButton).width(250).height(80).padRight(30);
        }

        TextButton leaveButton = new TextButton("Leave Lobby", skin);
        leaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                networkManager.leaveLobby();
                Main.getInstance().switchScreen(new LobbyMenuView(skin));
            }
        });

        leaveButton.getLabel().setFontScale(1.3f);
        buttonsTable.add(leaveButton).width(250).height(80);

        mainTable.add(buttonsTable).colspan(2).padTop(30).row();

        stage.addActor(mainTable);
    }

    private void updatePlayersTable(ArrayList<String> players, Map<String, Boolean> readyStatus) {

        playersTable.clear();


        playersTable.defaults().pad(15);

        Label playerHeaderLabel = new Label("Player", skin);
        playerHeaderLabel.setFontScale(1.3f);

        Label statusHeaderLabel = new Label("Status", skin);
        statusHeaderLabel.setFontScale(1.3f);

        playersTable.add(playerHeaderLabel).width(350);
        playersTable.add(statusHeaderLabel).width(200).row();

        readyStatusCheckboxes.clear();

        boolean allReady = true;


        for (int i = 0; i < players.size(); i++) {
            String playerName = players.get(i);


            String displayName = playerName;
            if (i == 0) {
                displayName += " (Admin)";
            }

            Label nameLabel = new Label(displayName, skin);

            nameLabel.setFontScale(1.1f);
            if (i == 0) {
                nameLabel.setColor(ADMIN_COLOR);
            }

            playersTable.add(nameLabel).width(350);


            boolean isReady = readyStatus.getOrDefault(playerName, false);

            if (!isReady) {
                allReady = false;
            }

            Label statusLabel = new Label(isReady ? "Ready" : "Not Ready", skin);

            statusLabel.setFontScale(1.1f);
            statusLabel.setColor(isReady ? new Color(0.2f, 0.8f, 0.2f, 1f) : new Color(0.8f, 0.2f, 0.2f, 1f));
            playersTable.add(statusLabel).width(200).row();


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


        dialog.setSize(600, 300);
        centerDialog(dialog);
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
