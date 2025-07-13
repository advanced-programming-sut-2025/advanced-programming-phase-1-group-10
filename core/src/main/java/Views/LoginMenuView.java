package Views;

import Controllers.RegisterManuController;
import Models.*;

import com.Fianl.Main;
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

import java.util.Scanner;

public class LoginMenuView implements Screen, AppMenu {
    private RegisterManuController controller;
    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private TextField usernameField, passwordField;
    private CheckBox stayLoggedInCheckbox;
    private Label messageLabel;
    private ScrollPane scrollPane;
    private Texture backgroundTexture;
    private String forgotPasswordUsername;


    private final Color BACKGROUND_COLOR = new Color(0.93f, 0.89f, 0.8f, 1);
    private final Color TITLE_COLOR = new Color(0.4f, 0.2f, 0.1f, 1);
    private final Color BUTTON_COLOR = new Color(0.55f, 0.78f, 0.25f, 1);
    private final Color ERROR_COLOR = new Color(0.8f, 0.2f, 0.2f, 1);
    private final Color SUCCESS_COLOR = new Color(0.2f, 0.6f, 0.3f, 1);

    public LoginMenuView() {
        controller = new RegisterManuController();
        stage = new Stage(new ScreenViewport());


        Main main = (Main)Gdx.app.getApplicationListener();
        skin = main.getSkin();


        try {
            backgroundTexture = new Texture(Gdx.files.internal("assets/backgrounds/farm1.png"));
        } catch (Exception e) {
            System.out.println("Background image not found: " + e.getMessage());

        }

        createUI();


        Gdx.input.setInputProcessor(stage);
    }

    private void createUI() {

        mainTable = new Table();
        mainTable.setFillParent(false);
        mainTable.pad(50);
        mainTable.setBackground(skin.newDrawable("white", BACKGROUND_COLOR));


        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.getFont("default-font"), TITLE_COLOR);
        Label titleLabel = new Label("Stardew Valley Login", titleStyle);




        mainTable.add(titleLabel).colspan(2).pad(20).row();


        mainTable.add().height(30).row();


        mainTable.add(new Label("Username:", skin)).align(Align.left).padRight(10);
        usernameField = new TextField("", skin);
        mainTable.add(usernameField).width(250).padBottom(20).row();


        mainTable.add(new Label("Password:", skin)).align(Align.left).padRight(10);
        passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        mainTable.add(passwordField).width(250).padBottom(20).row();


        stayLoggedInCheckbox = new CheckBox(" Stay logged in", skin);
        mainTable.add(stayLoggedInCheckbox).colspan(2).padBottom(30).row();


        Table buttonTable = new Table();
        TextButton loginButton = new TextButton("Login", skin);
        TextButton registerButton = new TextButton("Register", skin);

        buttonTable.add(loginButton).width(150).padRight(20);
        buttonTable.add(registerButton).width(150);

        mainTable.add(buttonTable).colspan(2).padBottom(20).row();


        TextButton forgotPasswordButton = new TextButton("Forgot Password", skin);
        mainTable.add(forgotPasswordButton).colspan(2).width(200).padBottom(30).row();


        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);
        mainTable.add(messageLabel).colspan(2).width(400).padTop(20).row();


        scrollPane = new ScrollPane(mainTable, skin);
        scrollPane.setFillParent(true);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setupFadeScrollBars(0f, 0f);
        scrollPane.setOverscroll(false, false);


        stage.addActor(scrollPane);


        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleLogin();
            }
        });

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.getInstance().setCurrentMenu(Menu.RegisterMenu);
                Main.getInstance().switchScreen(new RegisterMenuView());
            }
        });

        forgotPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showForgotPasswordDialog();
            }
        });
    }


    private void showSuccessMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setColor(SUCCESS_COLOR);
    }


    private void showErrorMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setColor(ERROR_COLOR);
        Gdx.input.setInputProcessor(stage);


        stage.setKeyboardFocus(null);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        User user = App.getInstance().getUserByUserName(username);

        if (user == null) {
            showErrorMessage("No user with this username exists!");
            return;
        }

        String password = passwordField.getText();
        String stayLoggedIn = stayLoggedInCheckbox.isChecked() ? "true" : "";

        Result result = controller.login(username, password, stayLoggedIn);

        if (result.state()) {
            showSuccessMessage(result.message());







        } else {
            showErrorMessage(result.message());
        }
    }

    private void showForgotPasswordDialog() {
        Dialog forgotDialog = new Dialog("Forgot Password", skin);

        Table forgotTable = new Table();
        forgotTable.pad(20);

        forgotTable.add(new Label("Username:", skin)).padRight(10);
        final TextField usernameInput = new TextField("", skin);
        forgotTable.add(usernameInput).width(250).padBottom(20).row();


        final Label errorLabel = new Label("", skin);
        errorLabel.setColor(ERROR_COLOR);
        errorLabel.setWrap(true);
        forgotTable.add(errorLabel).colspan(2).width(350).padBottom(20).row();

        TextButton submitButton = new TextButton("Submit", skin);
        forgotTable.add(submitButton).colspan(2).width(150).row();

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameInput.getText();


                User user = App.getInstance().getUserByUserName(username);
                if (user == null) {
                    errorLabel.setText("No user with this username exists!");
                    return;
                }


                forgotPasswordUsername = username;


                App.getInstance().setCurrentUser(user);

                Result result = controller.forgotPassword(username);

                if (result.state()) {
                    forgotDialog.hide();
                    showSecurityQuestionDialog(user);
                } else {
                    errorLabel.setText(result.message());
                }
            }
        });

        forgotDialog.getContentTable().add(forgotTable);
        forgotDialog.button("Cancel", false).key(com.badlogic.gdx.Input.Keys.ESCAPE, false);
        forgotDialog.show(stage);
    }

    private void showSecurityQuestionDialog(User user) {
        Dialog securityDialog = new Dialog("Security Question", skin);

        Table securityTable = new Table();
        securityTable.pad(20);


        int questionNumber = user.getPickQuestionNumber();
        String question = SecurityQuestions.getQuestionByNumber(questionNumber);

        securityTable.add(new Label("Security Question:", skin)).colspan(2).padBottom(10).row();

        Label questionLabel = new Label(question, skin);
        questionLabel.setWrap(true);
        securityTable.add(questionLabel).colspan(2).width(350).padBottom(20).row();

        securityTable.add(new Label("Answer:", skin)).padRight(10);
        final TextField answerInput = new TextField("", skin);
        securityTable.add(answerInput).width(250).padBottom(20).row();


        final Label errorLabel = new Label("", skin);
        errorLabel.setColor(ERROR_COLOR);
        errorLabel.setWrap(true);
        securityTable.add(errorLabel).colspan(2).width(350).padBottom(20).row();

        TextButton submitButton = new TextButton("Submit", skin);
        securityTable.add(submitButton).colspan(2).width(150).row();

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String answer = answerInput.getText();
                Result result = controller.answerSecurityQuestion(answer);

                if (result.state()) {
                    securityDialog.hide();
                    showNewPasswordDialog(user);
                } else {
                    errorLabel.setText(result.message());
                }
            }
        });

        securityDialog.getContentTable().add(securityTable);
        securityDialog.button("Cancel", false).key(com.badlogic.gdx.Input.Keys.ESCAPE, false);
        Gdx.input.setInputProcessor(stage);
        securityDialog.show(stage);
    }

    private void showNewPasswordDialog(User user) {
        Dialog newPasswordDialog = new Dialog("Change Password", skin);

        Table passwordTable = new Table();
        passwordTable.pad(20);

        Label questionLabel = new Label("Would you like a random password?", skin);
        questionLabel.setWrap(true);
        passwordTable.add(questionLabel).colspan(2).padBottom(20).row();

        Table buttonTable = new Table();
        TextButton yesButton = new TextButton("Yes", skin);
        TextButton noButton = new TextButton("No", skin);

        buttonTable.add(yesButton).width(100).padRight(20);
        buttonTable.add(noButton).width(100);

        passwordTable.add(buttonTable).colspan(2).row();

        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String randomPass = RegisterManuController.generateStrongPassword(10);
                Result result = controller.changePassword(randomPass, true);
                user.setPassword(result.message());
                showSuccessMessage("your password changed successfully! NEW PASSWORD: " + result.message());
                newPasswordDialog.hide();


            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                newPasswordDialog.hide();
                showCustomPasswordDialog(user);
            }
        });

        newPasswordDialog.getContentTable().add(passwordTable);
        newPasswordDialog.button("Cancel", false).key(com.badlogic.gdx.Input.Keys.ESCAPE, false);
        newPasswordDialog.show(stage);
    }

    private void showCustomPasswordDialog(User user) {
        Dialog customPasswordDialog = new Dialog("Enter New Password", skin);

        Table passwordTable = new Table();
        passwordTable.pad(20);

        passwordTable.add(new Label("New Password:", skin)).padRight(10);
        final TextField newPasswordField = new TextField("", skin);
        newPasswordField.setPasswordCharacter('*');
        newPasswordField.setPasswordMode(true);
        passwordTable.add(newPasswordField).width(250).padBottom(20).row();


        final Label errorLabel = new Label("", skin);
        errorLabel.setColor(ERROR_COLOR);
        errorLabel.setWrap(true);
        passwordTable.add(errorLabel).colspan(2).width(350).padBottom(20).row();

        TextButton submitButton = new TextButton("Submit", skin);
        passwordTable.add(submitButton).colspan(2).width(150).row();

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String newPassword = newPasswordField.getText();
                Result result = controller.changePassword(newPassword, false);

                if (result.state()) {
                    user.setPassword(result.message());
                    showSuccessMessage("your password changed successfully! NEW PASSWORD: " + result.message());
                    customPasswordDialog.hide();


                } else {
                    errorLabel.setText(result.message());

                    TextButton retryButton = new TextButton("Try Again", skin);
                    passwordTable.add(retryButton).colspan(2).width(150).padTop(10).row();

                    retryButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {

                            newPasswordField.setText("");
                            errorLabel.setText("");
                        }
                    });
                }
            }
        });

        customPasswordDialog.getContentTable().add(passwordTable);
        customPasswordDialog.button("Cancel", false).key(com.badlogic.gdx.Input.Keys.ESCAPE, false);
        customPasswordDialog.show(stage);
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

        stage.act(delta);
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
