package Views;

import Controllers.GameMenuControllers;
import Models.*;
import Models.Commands.GameMenuCommands;
import Models.PlayerStuff.Player;

import com.Fianl.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

import static Models.App.getInstance;

public class GameMenuView implements Screen, AppMenu {

    private GameMenuControllers controller;
    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private Label messageLabel;
    private Texture backgroundTexture;
    private TextButton newGameButton;


    private final Color TITLE_COLOR = new Color(0.4f, 0.2f, 0.1f, 1f);
    private final Color BUTTON_COLOR = new Color(0.55f, 0.78f, 0.25f, 1f);
    private final Color BUTTON_HOVER_COLOR = new Color(0.65f, 0.88f, 0.35f, 1f);
    private final Color ERROR_COLOR = new Color(0.8f, 0.2f, 0.2f, 1f);
    private final Color SUCCESS_COLOR = new Color(0.2f, 0.6f, 0.3f, 1f);

    public GameMenuView() {
        controller = new GameMenuControllers();
        stage = new Stage(new ScreenViewport());


        Main main = (Main)Gdx.app.getApplicationListener();
        skin = main.getSkin();


        try {
            backgroundTexture = new Texture(Gdx.files.internal("backgrounds/game_menu_bg.png"));
        } catch (Exception e) {
            System.out.println("Background image not found: " + e.getMessage());
        }

        createUI();


        Gdx.input.setInputProcessor(stage);
    }

    private void createUI() {

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(50);


        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.getFont("default-font"), TITLE_COLOR);
        Label titleLabel = new Label("GAME MENU", titleStyle);
        titleLabel.setFontScale(2.0f);
        mainTable.add(titleLabel).colspan(2).padBottom(50).row();


        newGameButton = createAnimatedButton("New Game", BUTTON_COLOR);
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showNewGameDialog();
            }
        });
        mainTable.add(newGameButton).colspan(2).width(300).height(60).padBottom(20).row();


        TextButton backButton = createAnimatedButton("Back to Main Menu", BUTTON_COLOR);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().switchScreen(new MainMenuView());
            }
        });
        mainTable.add(backButton).colspan(2).width(300).height(60).padBottom(20).row();


        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);
        mainTable.add(messageLabel).colspan(2).width(400).padTop(20).row();

        stage.addActor(mainTable);
    }

    private TextButton createAnimatedButton(String text, final Color color) {
        TextButton button = new TextButton(text, skin);
        button.setColor(color);

        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.addAction(Actions.sequence(
                    Actions.parallel(
                        Actions.color(BUTTON_HOVER_COLOR, 0.2f),
                        Actions.scaleTo(1.05f, 1.05f, 0.2f, Interpolation.swingOut)
                    )
                ));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.setColor(color);
                button.addAction(Actions.sequence(
                    Actions.parallel(
                        Actions.color(color, 0.2f),
                        Actions.scaleTo(1f, 1f, 0.2f, Interpolation.swingIn)
                    )
                ));
            }
        });

        return button;
    }

    private void showNewGameDialog() {
        Dialog newGameDialog = new Dialog("New Game", skin);

        Table contentTable = new Table();
        contentTable.pad(20);


        Table formTable = new Table();
        formTable.pad(10);


        ArrayList<TextField> usernameFields = new ArrayList<>();
        ArrayList<SelectBox<String>> farmTypeSelectBoxes = new ArrayList<>();


        TextButton addPlayerButton = new TextButton("+ Add Player", skin);
        formTable.add(addPlayerButton).colspan(2).width(150).padBottom(20).row();


        addPlayerFields(formTable, usernameFields, farmTypeSelectBoxes, 1);


        addPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (usernameFields.size() < 4) {
                    int playerNumber = usernameFields.size() + 1;
                    addPlayerFields(formTable, usernameFields, farmTypeSelectBoxes, playerNumber);


                    contentTable.pack();
                } else {
                    showErrorMessage("Maximum 4 players allowed.");
                }
            }
        });


        Table buttonTable = new Table();
        TextButton createButton = new TextButton("Create", skin);
        //TextButton cancelButton = new TextButton("Cancel", skin);

        buttonTable.add(createButton).width(120).padRight(20);
        //buttonTable.add(cancelButton).width(120);


        formTable.add(buttonTable).colspan(2).padTop(20).row();


        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);
        formTable.add(messageLabel).colspan(2).width(400).padTop(20).row();


        createButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ArrayList<String> usernames = new ArrayList<>();
                ArrayList<String> farmTypes = new ArrayList<>();

                for (TextField textField : usernameFields) {
                    usernames.add(textField.getText().trim());
                }

                for (SelectBox<String> selectBox : farmTypeSelectBoxes) {
                    farmTypes.add(selectBox.getSelected());
                }


                Result result = controller.createGame(
                    usernames.size() > 0 ? usernames.get(0) : "",
                    usernames.size() > 1 ? usernames.get(1) : "",
                    usernames.size() > 2 ? usernames.get(2) : "",
                    usernames.size() > 3 ? usernames.get(3) : ""
                );

                if (result.state()) {

                    App.getInstance().setCurrentMenu(Menu.GameLauncher);
                    showSuccessMessage("game created!");
                    // going to the game
                    newGameDialog.hide();
                } else {
                    showErrorMessage(result.message());
                }
            }
        });

//        cancelButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                newGameDialog.hide();
//            }
//        });


        ScrollPane scrollPane = new ScrollPane(formTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);


        contentTable.add(scrollPane).width(500).height(400);

        newGameDialog.getContentTable().add(contentTable);
        newGameDialog.button("Cancel", false).key(com.badlogic.gdx.Input.Keys.ESCAPE, false);
        newGameDialog.show(stage);


        newGameDialog.setPosition(
            (Gdx.graphics.getWidth() - newGameDialog.getWidth()) / 2,
            (Gdx.graphics.getHeight() - newGameDialog.getHeight()) / 2
        );
    }

    private void addPlayerFields(Table contentTable, ArrayList<TextField> usernameFields, ArrayList<SelectBox<String>> farmTypeSelectBoxes, int playerNumber) {

        Label usernameLabel = new Label("Player " + playerNumber + " Username:", skin);
        usernameLabel.setFontScale(1.1f);
        contentTable.add(usernameLabel).padRight(10).align(Align.right).padBottom(5);
        TextField usernameField = new TextField("", skin);
        usernameField.getStyle().font.getData().setScale(1.1f);
        contentTable.add(usernameField).width(250).padBottom(5).row();
        usernameFields.add(usernameField);

        Label farmTypeLabel = new Label("Player " + playerNumber + " Farm Type:", skin);
        farmTypeLabel.setFontScale(1.1f);
        contentTable.add(farmTypeLabel).padRight(10).align(Align.right).padBottom(15);
        SelectBox<String> farmTypeSelectBox = new SelectBox<>(skin);
        farmTypeSelectBox.setItems("Type 1", "Type 2");
        farmTypeSelectBox.getStyle().font.getData().setScale(1.1f);
        contentTable.add(farmTypeSelectBox).width(180).padBottom(15).row();
        farmTypeSelectBoxes.add(farmTypeSelectBox);
    }

    private void navigateToMenu(String menuName) {
        if (menuName.equalsIgnoreCase("login menu") || menuName.equalsIgnoreCase("signup menu")) {
            Main.getInstance().switchScreen(new LoginMenuView());
        } else if (menuName.equalsIgnoreCase("game menu")) {
        } else if (menuName.equalsIgnoreCase("profile menu")) {
            Main.getInstance().switchScreen(new ProfileMenuView());
        }
    }

    private void showSuccessMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setColor(SUCCESS_COLOR);
    }

    private void showErrorMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setColor(ERROR_COLOR);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (backgroundTexture != null) {
            stage.getBatch().begin();
            stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.getBatch().end();
        }


        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }

    @Override
    public void checkCommand(Scanner scanner) {
    }
}
