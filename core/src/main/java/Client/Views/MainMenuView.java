package Client.Views;

import Common.Models.App;
import Common.Models.Result;
import Common.Models.SaveData;
import Common.Models.User;
import Client.Controllers.GameMenuControllers;
import Client.Controllers.MainMenuControllers;

import Client.Main;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainMenuView implements Screen, AppMenu {
    private MainMenuControllers controller;
    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private Label messageLabel;
    private Texture backgroundTexture;
    private Texture avatarTexture;
    private User currentUser;

    long sharedSeed;



    private final Color TITLE_COLOR = new Color(0.4f, 0.2f, 0.1f, 1f);
    private final Color BUTTON_COLOR = new Color(0.55f, 0.78f, 0.25f, 1f);
    private final Color BUTTON_HOVER_COLOR = new Color(0.65f, 0.88f, 0.35f, 1f);
    private final Color LOGOUT_COLOR = new Color(0.8f, 0.3f, 0.3f, 1f);
    private final Color ERROR_COLOR = new Color(0.8f, 0.2f, 0.2f, 1f);
    private final Color SUCCESS_COLOR = new Color(0.2f, 0.6f, 0.3f, 1f);
    private final Color NICKNAME_COLOR = new Color(0.3f, 0.6f, 0.9f, 1f);

    public MainMenuView() {
        controller = new MainMenuControllers();
        stage = new Stage(new ScreenViewport());

        sharedSeed = System.currentTimeMillis();

        Main main = (Main)Gdx.app.getApplicationListener();
        skin = main.getSkin();


        currentUser = App.getInstance().getCurrentUser();


        try {
            backgroundTexture = new Texture(Gdx.files.internal("assets/backgrounds/MainMenu.png"));
        } catch (Exception e) {
            System.out.println("Background image not found: " + e.getMessage());
        }

        if (currentUser != null) {
            loadUserAvatar();
        }

        createUI();


        Gdx.input.setInputProcessor(stage);
    }

    private void loadUserAvatar() {
        try {

            if (currentUser.getAvatarPath() != null && !currentUser.getAvatarPath().isEmpty()) {
                avatarTexture = new Texture(Gdx.files.internal(currentUser.getAvatarPath()));
            } else {

                avatarTexture = new Texture(Gdx.files.internal("assets/avatars/avatar1.png"));


                currentUser.setAvatarPath("assets/avatars/avatar1.png");
                SaveData.saveUsersToFile(App.getInstance().getUsers());
            }
        } catch (Exception e) {
            System.out.println("Avatar image not found: " + e.getMessage());
            try {

                avatarTexture = new Texture(Gdx.files.internal("assets/avatars/avatar1.png"));


                currentUser.setAvatarPath("assets/avatars/avatar1.png");
                SaveData.saveUsersToFile(App.getInstance().getUsers());
            } catch (Exception ex) {
                System.out.println("Default avatar not found either: " + ex.getMessage());
            }
        }
    }

    private void createUI() {

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(50);


        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.getFont("Impact"), TITLE_COLOR);
        Label titleLabel = new Label("STARDEW VALLEY", titleStyle);
        titleLabel.setFontScale(2.0f);
        mainTable.add(titleLabel).colspan(2).padBottom(30).row();


        if (currentUser != null) {
            Table userInfoTable = new Table();


            if (avatarTexture != null) {
                Image avatarImage = new Image(avatarTexture);
                avatarImage.setOrigin(avatarImage.getWidth() / 2, avatarImage.getHeight() / 2);


                avatarImage.addAction(Actions.sequence(
                    Actions.alpha(0),
                    Actions.fadeIn(0.8f),
                    Actions.forever(Actions.sequence(
                        Actions.scaleTo(1.05f, 1.05f, 1f),
                        Actions.scaleTo(1f, 1f, 1f)
                    ))
                ));

                userInfoTable.add(avatarImage).size(160, 160).padRight(30);
            }


            Table userTextInfo = new Table();


            Label welcomeLabel = new Label("Welcome,", skin);
            welcomeLabel.setFontScale(1.1f);
            userTextInfo.add(welcomeLabel).left().row();


            Label nicknameLabel = new Label(currentUser.getNickname(), skin);
            nicknameLabel.setFontScale(1.3f);
            nicknameLabel.setColor(NICKNAME_COLOR);
            userTextInfo.add(nicknameLabel).left().padTop(5);

            userInfoTable.add(userTextInfo).left();

            mainTable.add(userInfoTable).colspan(2).padBottom(40).row();
        }

        createMenuButton("Game Menu", "game menu");
        createMenuButton("Profile Menu", "profile menu");
        createMenuButton("Go To Lobby","lobby menu");

        TextButton quickGame = new TextButton("quick game", skin);
        quickGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameMenuControllers controllers  = new GameMenuControllers();
                Result result = controllers.quickGame(sharedSeed);
                if(result.state()) {
                    long sharedSeed = System.currentTimeMillis();
                    controllers.setUpFarms(new ArrayList<>(Arrays.asList("1","2","2","1")), sharedSeed);
                    Main.getInstance().switchScreen(new GameLauncherView(skin));
                }
            }
        });
        mainTable.add(quickGame).colspan(2).width(300).height(80).padBottom(20).row();


        if (currentUser == null) {
            createMenuButton("Login / Register", "login menu");
        } else {

            TextButton logoutButton = new TextButton("Logout", skin);
            styleButton(logoutButton, LOGOUT_COLOR);

            logoutButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    logoutButton.setColor(LOGOUT_COLOR.cpy().mul(1.2f));
                    logoutButton.addAction(Actions.scaleTo(1.05f, 1.05f, 0.1f));
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    logoutButton.setColor(LOGOUT_COLOR);
                    logoutButton.addAction(Actions.scaleTo(1f, 1f, 0.1f));
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    handleLogout();
                }
            });

            mainTable.add(logoutButton).colspan(2).width(300).height(60).padBottom(20).row();
        }


        mainTable.add().height(30).row();


        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);
        mainTable.add(messageLabel).colspan(2).width(400).padTop(20).row();


        Label groupNumber = new Label("Group 10", skin);
        groupNumber.setColor(Color.DARK_GRAY);
        mainTable.add(groupNumber).colspan(2).padTop(50).row();

        stage.addActor(mainTable);
    }

    private void createMenuButton(String text, final String menuName) {
        TextButton button = new TextButton(text, skin);
        //styleButton(button, color);

        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //button.setColor(BUTTON_HOVER_COLOR);
                button.addAction(Actions.scaleTo(1.05f, 1.05f, 0.1f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //button.setColor(color);
                button.addAction(Actions.scaleTo(1f, 1f, 0.1f));
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleMenuNavigation(menuName);
            }
        });

        mainTable.add(button).colspan(2).width(300).height(80).padBottom(20).row();
    }

    private void styleButton(TextButton button, Color color) {
        button.setColor(color);
        Label buttonLabel = button.getLabel();
        buttonLabel.setFontScale(1.2f);
        buttonLabel.setColor(Color.WHITE);
    }

    private void handleMenuNavigation(String menuName) {

        if (currentUser == null && (menuName.equals("profile menu") || menuName.equals("game menu"))) {
            showErrorMessage("You need to login first!");
            return;
        }

        Result result = controller.enterMenu(menuName);

        if (result.state()) {

            navigateToMenu(menuName);
        } else {
            showErrorMessage(result.message());
        }
    }

    private void navigateToMenu(String menuName) {
        if (menuName.equalsIgnoreCase("login menu") || menuName.equalsIgnoreCase("signup menu")) {
            Main.getInstance().switchScreen(new LoginMenuView());
        } else if (menuName.equalsIgnoreCase("game menu")) {
            Main.getInstance().switchScreen(new GameMenuView());
        } else if (menuName.equalsIgnoreCase("profile menu")) {
            Main.getInstance().switchScreen(new ProfileMenuView());
        } else if(menuName.equalsIgnoreCase("lobby menu")){
            Main.getInstance().switchScreen(new LobbyMenuView(skin));
        }
    }

    private void handleLogout() {
        Result result = controller.logout();

        if (result.state()) {
            showSuccessMessage(result.message());


            stage.addAction(Actions.sequence(
                Actions.delay(1.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Main.getInstance().switchScreen(new LoginMenuView());
                    }
                })
            ));
        } else {
            showErrorMessage(result.message());
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

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (backgroundTexture != null) {
            stage.getBatch().begin();
            stage.getBatch().setColor(1, 1, 1, 1);
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
        if (avatarTexture != null) {
            avatarTexture.dispose();
        }
    }

    @Override
    public void checkCommand(Scanner scanner) {
    }
}
