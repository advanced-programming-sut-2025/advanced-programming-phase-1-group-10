package Controllers.FinalControllers;

import Controllers.MessageSystem;
import Models.Animal.Animal;
import Models.Animal.AnimalProduct;
import Models.App;
import Models.Map;
import Models.PlayerStuff.Player;
import Models.Position;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class AnimalController {

    private final Stage stage;
    private final Table menuTable;
    private final Table infoTable;
    private Animal selectedAnimal;
    private boolean showMenu = false;
    private boolean showInfoBox = false;
    private float infoBoxTimer = 0f;
    private static final float INFO_BOX_DURATION = 3.0f;
    private TextButton feedButton, petButton, removeButton, sellButton, harvestButton;

    private final BitmapFont smallFont;
    private final BitmapFont largeFont;

    private final Color buttonColor = new Color(1f, 0.9f, 0.7f, 0.9f);
    private final Color buttonHoverColor = new Color(1f, 0.8f, 0.5f, 1f);

    private float menuX;
    private float menuY;

    private InputProcessor previousInputProcessor;

    private final InputAdapter outsideClickListener;

    public AnimalController() {
        stage = new Stage(new ScreenViewport());
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter paramSmall = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramSmall.size = 40;
        paramSmall.color = Color.BROWN;
        smallFont = generator.generateFont(paramSmall);
        FreeTypeFontGenerator.FreeTypeFontParameter paramLarge = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramLarge.size = 60;
        paramLarge.color = Color.BROWN;
        largeFont = generator.generateFont(paramLarge);
        generator.dispose();

        menuTable = new Table();
        menuTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("friendship/button.png")))));
        menuTable.pad(10);
        createButtons();
        stage.addActor(menuTable);
        menuTable.setVisible(false);

        infoTable = new Table();
        infoTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("friendship/button.png")))));
        infoTable.pad(10);
        stage.addActor(infoTable);
        infoTable.setVisible(false);

        outsideClickListener = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 stageCoords = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                Actor hitActor = stage.hit(stageCoords.x, stageCoords.y, true);
                if (hitActor == null || (!menuTable.isAscendantOf(hitActor) && !infoTable.isAscendantOf(hitActor))) {
                    hideUI();
                    return true;
                }
                return false;
            }
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    hideUI();
                    return true;
                }
                return false;
            }
        };
    }

    private void createButtons() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = smallFont;
        buttonStyle.fontColor = Color.BROWN;
        feedButton = createButton("Feed", buttonStyle);
        petButton = createButton("Pet", buttonStyle);
        removeButton = createButton("Remove", buttonStyle);
        sellButton = createButton("Sell", buttonStyle);
        harvestButton = createButton("Harvest", buttonStyle);
        menuTable.add(new Label("Animal Menu", new Label.LabelStyle(largeFont, Color.BROWN))).colspan(1).pad(5).row();
        menuTable.add(feedButton).pad(3).fillX().width(300).row();
        menuTable.add(petButton).pad(3).fillX().width(300).row();
        menuTable.add(removeButton).pad(3).fillX().width(300).row();
        menuTable.add(sellButton).pad(3).fillX().width(300).row();
        menuTable.add(harvestButton).pad(3).fillX().width(300);
        menuTable.pack();
    }

    private TextButton createButton(String text, TextButton.TextButtonStyle style) {
        TextButton button = new TextButton(text, style);
        button.pad(8);
        button.align(Align.center);
        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.setColor(buttonHoverColor);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.setColor(buttonColor);
            }
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleButtonClick(text);
            }
        });
        return button;
    }

    private void handleButtonClick(String buttonText) {
        if (selectedAnimal == null) return;
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        switch (buttonText) {
            case "Feed":
                System.out.println("Feeding " + selectedAnimal.getName());
                App.getInstance().getGameControllerFinal().getAnimalBuildingController().startFeeding(selectedAnimal);
                break;
            case "Pet":
                if (!selectedAnimal.isFree()) {
                    float releaseX, releaseY;
                    float offset = Map.tileSize;
                    if (App.getInstance().getGameControllerFinal().getAnimalBuildingController().isShowingCoopInterior()) {
                        Position coopPos = App.getInstance().getGameControllerFinal().getAnimalBuildingController().getSelectedCoop().getPosition();
                        releaseX = coopPos.getX() + offset;
                        releaseY = coopPos.getY();
                    } else {
                        Position barnPos = App.getInstance().getGameControllerFinal().getAnimalBuildingController().getSelectedBarn().getPosition();
                        releaseX = barnPos.getX() + offset;
                        releaseY = barnPos.getY();
                    }
                    App.getInstance().getGameControllerFinal().getAnimalMovementController().startPetting(selectedAnimal, releaseX, releaseY);
                    App.getInstance().getGameControllerFinal().getAnimalBuildingController().closeInteriorView();
                    System.out.println("Started petting " + selectedAnimal.getName() + " outside its building");
                }
                break;
            case "Remove":
                if (!selectedAnimal.isFree()) {
                    float releaseX, releaseY;
                    float offset = 2 * Map.tileSize;
                    if (App.getInstance().getGameControllerFinal().getAnimalBuildingController().isShowingCoopInterior()) {
                        Position coopPos = App.getInstance().getGameControllerFinal().getAnimalBuildingController().getSelectedCoop().getPosition();
                        releaseX = coopPos.getX() + offset;
                        releaseY = coopPos.getY() + offset;
                    } else {
                        Position barnPos = App.getInstance().getGameControllerFinal().getAnimalBuildingController().getSelectedBarn().getPosition();
                        releaseX = barnPos.getX() + offset;
                        releaseY = barnPos.getY() + offset;
                    }
                    App.getInstance().getGameControllerFinal().getAnimalMovementController().releaseAnimal(selectedAnimal, releaseX, releaseY);
                    System.out.println("Removed " + selectedAnimal.getName() + " from building");
                    App.getInstance().getGameControllerFinal().getAnimalBuildingController().closeInteriorView();
                }
                break;
            case "Sell":
                System.out.println("Selling " + selectedAnimal.getName());
                break;
            case "Harvest":
                ArrayList<AnimalProduct> productsToHarvest = new ArrayList<>(selectedAnimal.getProducedProducts());
                if (productsToHarvest.isEmpty()) {
                    MessageSystem.showInfo("No products to harvest!", 3.0f);
                    break;
                }
                int harvestedCount = 0;
                ArrayList<AnimalProduct> successfullyHarvested = new ArrayList<>();
                for (AnimalProduct product : productsToHarvest) {
                    boolean added = player.getInventory().getBackPack().addItem(product);
                    if (added) {
                        successfullyHarvested.add(product);
                        harvestedCount++;
                    } else {
                        MessageSystem.showError("Not enough space in backpack to harvest all products!", 5.0f);
                        break;
                    }
                }
                for(AnimalProduct product : successfullyHarvested) {
                    selectedAnimal.removeProduct(product);
                }
                if (harvestedCount > 0) {
                    MessageSystem.showInfo(harvestedCount + " products harvested from " + selectedAnimal.getName() + "!", 5.0f);
                }
                System.out.println("Harvesting products from " + selectedAnimal.getName());
                break;
        }
        hideUI();
    }

    public void render(float delta) {
        if (showMenu || showInfoBox) {
            stage.act(delta);
            stage.draw();
        }
        if (showInfoBox) {
            infoBoxTimer += delta;
            if (infoBoxTimer >= INFO_BOX_DURATION) {
                hideInfoBox();
            }
        }
    }

    public boolean handleRightClick(float screenX, float screenY, Animal animal, float worldX, float worldY) {
        hideUI();
        if (animal == null) return false;
        selectedAnimal = animal;

        showMenu = true;
        menuX = screenX;
        menuY = Gdx.graphics.getHeight() - screenY;
        if (menuX + menuTable.getWidth() > Gdx.graphics.getWidth()) {
            menuX = Gdx.graphics.getWidth() - menuTable.getWidth();
        }
        if (menuY + menuTable.getHeight() > Gdx.graphics.getHeight()) {
            menuY = Gdx.graphics.getHeight() - menuTable.getHeight();
        }
        menuTable.setPosition(menuX, menuY);
        menuTable.setVisible(true);

        previousInputProcessor = Gdx.input.getInputProcessor();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(outsideClickListener);
        Gdx.input.setInputProcessor(multiplexer);
        System.out.println("Showing menu for " + animal.getName() + " at position: " + worldX + ", " + worldY);
        return true;
    }

    public boolean handleLeftClick(float screenX, float screenY, Animal animal, float worldX, float worldY) {
        hideUI();
        if (animal == null) return false;
        selectedAnimal = animal;

        showAnimalInfoBox(animal, worldX, worldY);

        previousInputProcessor = Gdx.input.getInputProcessor();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(outsideClickListener);
        Gdx.input.setInputProcessor(multiplexer);
        return true;
    }

    private void showAnimalInfoBox(Animal animal, float worldX, float worldY) {
        infoTable.clearChildren();
        Label.LabelStyle labelStyle = new Label.LabelStyle(smallFont, Color.BROWN);
        infoTable.add(new Label(animal.getName(), labelStyle)).row();
        infoTable.add(new Label("Friendship: " + animal.getFriendShip(), labelStyle)).row();
        infoTable.pack();

        float infoBoxWidth = infoTable.getWidth();
        float infoBoxHeight = infoTable.getHeight();

        Vector3 screenCoords = new Vector3(worldX, worldY, 0);
        stage.getCamera().project(screenCoords);

        float x = screenCoords.x - infoBoxWidth / 2;
        float y = screenCoords.y + Animal.ANIMAL_HEIGHT + 10;

        x = Math.max(0, x);
        x = Math.min(Gdx.graphics.getWidth() - infoBoxWidth, x);
        y = Math.min(Gdx.graphics.getHeight() - infoBoxHeight, y);

        infoTable.setPosition(x, y);
        infoTable.setVisible(true);
        showInfoBox = true;
        infoBoxTimer = 0f;
    }

    public void hideInfoBox() {
        showInfoBox = false;
        infoTable.setVisible(false);
    }

    public void hideUI() {
        showMenu = false;
        menuTable.setVisible(false);
        hideInfoBox();
        selectedAnimal = null;
        if (previousInputProcessor != null) {
            Gdx.input.setInputProcessor(previousInputProcessor);
        } else {
            Gdx.input.setInputProcessor(App.getInstance().getGameControllerFinal().getGameLauncherView());
        }
    }

    public boolean isMenuVisible() {
        return showMenu;
    }

    public boolean isInfoBoxVisible() {
        return showInfoBox;
    }

    public void dispose() {
        smallFont.dispose();
        largeFont.dispose();
        stage.dispose();
    }
}
