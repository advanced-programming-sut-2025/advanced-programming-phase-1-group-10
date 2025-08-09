package Client.Views;

import Client.Assets.TextureCache;
import Common.Models.App;
import Common.Models.Game;
import Common.Models.Map;
import Client.Controllers.FinalControllers.AnimalListController;
import Client.Controllers.FinalControllers.GameControllerFinal;
import Client.Controllers.MessageSystem;
import Common.Models.PlayerStuff.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import Client.Main;

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

    // setting variables
    private final TextButton settingsButton;
    private final Table settingsTable;
    private final TextButton subButton1;
    private final TextButton subButton2;
    private boolean areSubButtonsVisible = false;

    // chat variables
    private final TextButton chatButton;
    private final Table chatWindowTable;
    private boolean isChatWindowVisible = false;
    private final TextButton privateTab;
    private final TextButton publicTab;
    private final ScrollPane chatScrollPane;
    private final TextArea chatTextArea;
    private Table privatePlayersTable;



    public GameLauncherView(Skin skin) {
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        this.hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport(hudCamera), batch);
        this.controller = new GameControllerFinal();
        App.getInstance().setGameControllerFinal(controller);
        this.controller.setView(this);
        hudCamera.update();
        viewport.apply();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        this.skin = skin;
        settingsTable = new Table(Main.getInstance().getSkin());
        stage.addActor(settingsTable);

        this.settingsButton = new TextButton("Settings", skin);
        this.subButton1 = new TextButton("Exit Game",skin);
        this.subButton2 = new TextButton("Remove Player",skin);

        settingsTable.add(settingsButton).pad(5).row();
        settingsTable.add(subButton1).pad(5).row();
        settingsTable.add(subButton2).pad(5);
        subButton1.setVisible(false);
        subButton2.setVisible(false);

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                areSubButtonsVisible = !areSubButtonsVisible;
                subButton1.setVisible(areSubButtonsVisible);
                subButton2.setVisible(areSubButtonsVisible);
            }
        });

        subButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Exit Game", Main.getInstance().getSkin()) {
                    {
                        text("Are you sure you want to exit?");
                        button("Yes", true);
                        button("No", false);
                    }
                    @Override
                    protected void result(Object object) {
                        if ((Boolean) object) {
                            Gdx.app.exit();
                        }
                    }
                }.show(stage);
            }
        });

        subButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Remove Player", Main.getInstance().getSkin()) {
                    private final TextField nameField;

                    {
                        nameField = new TextField("", Main.getInstance().getSkin());
                        nameField.setMessageText("Enter player name...");
                        getContentTable().add(nameField).pad(10).width(300).row();
                        button("Remove", true);
                        button("Cancel", false);
                    }

                    @Override
                    protected void result(Object object) {
                        if ((Boolean) object) {
                            String playerName = nameField.getText().trim();
                            if (playerName.isEmpty()) {
                                MessageSystem.showError("Please enter a player name.", 3.0f);
                                return;
                            }
                            Game currentGame = App.getInstance().getCurrentGame();
                            Player playerToRemove = currentGame.getPlayerByName(playerName);

                            if (playerToRemove == null) {
                                MessageSystem.showError("Player '" + playerName + "' not found.", 3.0f);
                                return;
                            }
                            if (playerToRemove.getName().equals(currentGame.getGameOwner())) {
                                MessageSystem.showError("Cannot remove the game owner!", 3.0f);
                                return;
                            }
                            if (playerToRemove.getName().equals(currentGame.getCurrentPlayer().getName())) {
                                MessageSystem.showError("Cannot remove yourself!", 3.0f);
                                return;
                            }
                            currentGame.getPlayers().remove(playerToRemove);
                            MessageSystem.showInfo("Player '" + playerName + "' has been removed.", 3.0f);
                        }
                    }
                }.show(stage);
            }
        });

        chatButton = new TextButton("Chat", skin);
        stage.addActor(chatButton);

        chatWindowTable = new Table(skin);
        chatWindowTable.setVisible(false);
        stage.addActor(chatWindowTable);

        privateTab = new TextButton("Private", skin);
        publicTab = new TextButton("Public", skin);

        Table tabRow = new Table();
        tabRow.add(privateTab).pad(5);
        tabRow.add(publicTab).pad(5);

        chatTextArea = new TextArea("", skin);
        chatTextArea.setDisabled(true);
        chatScrollPane = new ScrollPane(chatTextArea, skin);
        chatScrollPane.setFadeScrollBars(false);

        chatWindowTable.add(tabRow).expandX().fillX().row();
        chatWindowTable.add(chatScrollPane).expand().fill().pad(5).row();

        chatButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isChatWindowVisible = !isChatWindowVisible;
                chatWindowTable.setVisible(isChatWindowVisible);
            }
        });

        privateTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game currentGame = App.getInstance().getCurrentGame();
                if (!currentGame.isOnline()) {
                    chatTextArea.setText("Private chat is only available in online mode.");
                    return;
                }

                privatePlayersTable = new Table(getSkin());
                privatePlayersTable.top().left();

                for (Player player : currentGame.getPlayers()) {
                    if (player.getName().equals(currentGame.getCurrentPlayer().getName())) {
                        continue;
                    }
                    TextButton playerButton = new TextButton(player.getName(), getSkin());
                    playerButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            openPrivateChatWindow(player.getName());
                        }
                    });
                    privatePlayersTable.add(playerButton).pad(5).row();
                }
                chatScrollPane.setWidget(privatePlayersTable);
            }
        });

        publicTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO add public messages
                chatTextArea.setText("[Public messages here]");
            }
        });
    }


    private void openPrivateChatWindow(String playerName) {
        Dialog privateChatDialog = new Dialog("Chat with " + playerName, getSkin()) {
            private final TextArea messagesArea;
            private final TextField inputField;

            {
                messagesArea = new TextArea("", getSkin());
                messagesArea.setDisabled(true);
                messagesArea.setPrefRows(10);

                ScrollPane msgScroll = new ScrollPane(messagesArea, getSkin());
                msgScroll.setFadeScrollBars(false);

                inputField = new TextField("", getSkin());
                inputField.setMessageText("Type your message...");

                TextButton sendButton = new TextButton("Send", getSkin());
                sendButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        String msg = inputField.getText().trim();
                        if (!msg.isEmpty()) {
                            // TODO add network
                            messagesArea.appendText("Me: " + msg + "\n");
                            inputField.setText("");
                        }
                    }
                });

                TextButton closeButton = new TextButton("Close", getSkin());
                closeButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        remove();
                    }
                });

                getContentTable().add(msgScroll).colspan(2).expand().fill().pad(10).row();
                getContentTable().add(inputField).expandX().fillX().pad(10);
                getContentTable().add(sendButton).pad(10).row();
                getContentTable().add(closeButton).colspan(2).pad(10);
            }
        };
        privateChatDialog.setSize(400, 300);
        privateChatDialog.show(stage);
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

            stage.act(delta);
            stage.draw();
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

        // ----- Render Stage and its actors -----
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
        stage.getViewport().update(width, height, true);
        float tableWidth = settingsTable.getPrefWidth();
        float tableHeight = settingsTable.getPrefHeight();
        settingsTable.setPosition(width - tableWidth + 30, height - tableHeight - 100);

        chatButton.setPosition(width - chatButton.getWidth() - 20, 20);
        float chatWidth = 800;
        float chatHeight = 600;
        chatWindowTable.setSize(chatWidth, chatHeight);
        chatWindowTable.setPosition(width - chatWidth - 30, 80);
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
        stage.dispose();
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
        if (stage.touchDown(screenX, screenY, pointer, button)) {
            return true;
        }
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
                draggedAnimalY = Gdx.graphics.getHeight() - screenY;
                return true;
            }
        }

        Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
        float clickX = worldCoords.x;
        float clickY = worldCoords.y;

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
