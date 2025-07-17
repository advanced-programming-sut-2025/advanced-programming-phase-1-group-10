package Views;

import Controllers.RegisterManuController;
import Models.*;

import com.Fianl.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
    private Table rootTable;
    private TextField usernameField, passwordField;
    private CheckBox stayLoggedInCheckbox;
    private Label messageLabel;
    private Texture backgroundTexture;
    private String forgotPasswordUsername;
    private SpriteBatch backgroundBatch;


    private final Color TITLE_COLOR = new Color(0.2f, 0.1f, 0.05f, 1f);
    private final Color TEXT_COLOR = new Color(0.1f, 0.05f, 0f, 1f);
    private final Color BUTTON_COLOR_NORMAL = new Color(0.3f, 0.6f, 0.2f, 1f);
    private final Color BUTTON_COLOR_HOVER = new Color(0.4f, 0.7f, 0.3f, 1f);
    private final Color ERROR_COLOR = new Color(0.8f, 0.2f, 0.2f, 1f);
    private final Color SUCCESS_COLOR = new Color(0.2f, 0.6f, 0.3f, 1f);
    private final Color PANEL_BACKGROUND_COLOR = new Color(0.95f, 0.95f, 0.85f, 0.8f);


    public LoginMenuView() {
        controller = new RegisterManuController();
        stage = new Stage(new ScreenViewport());
        backgroundBatch = new SpriteBatch();

        Main main = (Main)Gdx.app.getApplicationListener();
        skin = main.getSkin();


        try {
            backgroundTexture = new Texture(Gdx.files.internal("assets/backgrounds/LoginMenu.png"));


        } catch (Exception e) {
            System.out.println("Background image not found or loaded incorrectly: " + e.getMessage());

            backgroundTexture = new Texture(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGB888);
            backgroundTexture.draw(new com.badlogic.gdx.graphics.Pixmap(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGB888), 0, 0);
        }

        createUI();

        Gdx.input.setInputProcessor(stage);
    }

    private void createUI() {
        rootTable = new Table();
        rootTable.setFillParent(true);



        Table loginPanel = new Table(skin);

        loginPanel.pad(40);
        loginPanel.defaults().spaceBottom(15).align(Align.center);


        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.getFont("default-font"), TITLE_COLOR);
        Label titleLabel = new Label("Welcome to Stardew Valley!", titleStyle);
        titleLabel.setFontScale(1.8f);
        loginPanel.add(titleLabel).colspan(2).padBottom(80).row();


        Label usernameLabel = new Label("Username:", skin);
        usernameLabel.setColor(TEXT_COLOR);
        loginPanel.add(usernameLabel).align(Align.left);
        usernameField = new TextField("", skin);
        usernameField.setMessageText("Enter your username");
        loginPanel.add(usernameField).width(300).height(40).row();


        Label passwordLabel = new Label("Password:", skin);
        passwordLabel.setColor(TEXT_COLOR);
        loginPanel.add(passwordLabel).align(Align.left);
        passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        passwordField.setMessageText("Enter your password");
        loginPanel.add(passwordField).width(300).height(40).row();


        stayLoggedInCheckbox = new CheckBox(" Keep me logged in", skin);
        stayLoggedInCheckbox.getLabel().setColor(TEXT_COLOR);
        loginPanel.add(stayLoggedInCheckbox).colspan(2).align(Align.left).padBottom(25).row();


        Table buttonRow = new Table();
        TextButton loginButton = new TextButton("Login", skin);
        styleButton(loginButton, BUTTON_COLOR_NORMAL, BUTTON_COLOR_HOVER);
        buttonRow.add(loginButton).width(140).height(50).padRight(20);

        TextButton registerButton = new TextButton("Register", skin);
        styleButton(registerButton, BUTTON_COLOR_NORMAL, BUTTON_COLOR_HOVER);
        buttonRow.add(registerButton).width(140).height(50);
        loginPanel.add(buttonRow).colspan(2).padBottom(20).row();


        TextButton forgotPasswordButton = new TextButton("Forgot Password?", skin);
        forgotPasswordButton.getLabel().setColor(TEXT_COLOR.cpy().mul(1.5f));
        forgotPasswordButton.setStyle(skin.get("default", TextButton.TextButtonStyle.class));
        loginPanel.add(forgotPasswordButton).colspan(2).padBottom(40).row();


        TextButton backtoMainMenuButton = new TextButton("Back to Main Menu", skin);
        styleButton(backtoMainMenuButton, new Color(0.7f, 0.4f, 0.2f, 1f), new Color(0.8f, 0.5f, 0.3f, 1f));
        loginPanel.add(backtoMainMenuButton).colspan(2).width(250).height(50).row();



        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);
        loginPanel.add(messageLabel).colspan(2).width(350).padTop(25).row();



        rootTable.add(loginPanel).center().expand().row();
        stage.addActor(rootTable);



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

        backtoMainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.getInstance().setCurrentMenu(Menu.MainMenu);
                Main.getInstance().switchScreen(new MainMenuView());
            }
        });
    }


    private void styleButton(TextButton button, Color normalColor, Color hoverColor) {
        button.setColor(normalColor);
        button.getLabel().setColor(Color.WHITE);
        button.getLabel().setFontScale(1.1f);

        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.setColor(hoverColor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.setColor(normalColor);
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


            App.getInstance().setCurrentUser(user);
            stage.addAction(com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence(
                com.badlogic.gdx.scenes.scene2d.actions.Actions.delay(1.5f),
                com.badlogic.gdx.scenes.scene2d.actions.Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Main.getInstance().switchScreen(new MainMenuView());
                    }
                })
            ));

        } else {
            showErrorMessage(result.message());
        }
    }

    private void showForgotPasswordDialog() {
        Dialog forgotDialog = new Dialog("Forgot Password", skin);

        Table forgotTable = new Table();
        forgotTable.pad(20);
        forgotTable.defaults().spaceBottom(10);

        forgotTable.add(new Label("Username:", skin)).padRight(10);
        final TextField usernameInput = new TextField("", skin);
        forgotTable.add(usernameInput).width(250).padBottom(20).row();

        final Label errorLabel = new Label("", skin);
        errorLabel.setColor(ERROR_COLOR);
        errorLabel.setWrap(true);
        forgotTable.add(errorLabel).colspan(2).width(350).padBottom(20).row();

        TextButton submitButton = new TextButton("Submit", skin);
        styleButton(submitButton, BUTTON_COLOR_NORMAL, BUTTON_COLOR_HOVER);
        forgotTable.add(submitButton).colspan(2).width(150).height(40).row();

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
        forgotDialog.button("Cancel", false, skin.get("default", TextButton.TextButtonStyle.class)).key(com.badlogic.gdx.Input.Keys.ESCAPE, false);
        forgotDialog.show(stage);
    }

    private void showSecurityQuestionDialog(User user) {
        Dialog securityDialog = new Dialog("Security Question", skin);

        Table securityTable = new Table();
        securityTable.pad(20);
        securityTable.defaults().spaceBottom(10);

        int questionNumber = user.getPickQuestionNumber();
        String question = SecurityQuestions.getQuestionByNumber(questionNumber);

        securityTable.add(new Label("Security Question:", skin)).colspan(2).padBottom(10).row();

        Label questionLabel = new Label(question, skin);
        questionLabel.setWrap(true);
        securityTable.add(questionLabel).colspan(2).width(350).padBottom(20).row();

        securityTable.add(new Label("Answer:", skin)).padRight(10);
        final TextField answerInput = new TextField("", skin);
        securityTable.add(answerInput).width(250).row();

        final Label errorLabel = new Label("", skin);
        errorLabel.setColor(ERROR_COLOR);
        errorLabel.setWrap(true);
        securityTable.add(errorLabel).colspan(2).width(350).padBottom(20).row();

        TextButton submitButton = new TextButton("Submit", skin);
        styleButton(submitButton, BUTTON_COLOR_NORMAL, BUTTON_COLOR_HOVER);
        securityTable.add(submitButton).colspan(2).width(150).height(40).row();

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
        securityDialog.button("Cancel", false, skin.get("default", TextButton.TextButtonStyle.class)).key(com.badlogic.gdx.Input.Keys.ESCAPE, false);
        securityDialog.show(stage);
    }

    private void showNewPasswordDialog(User user) {
        Dialog newPasswordDialog = new Dialog("Change Password", skin);

        newPasswordDialog.setWidth(500);

        Table passwordTable = new Table();
        passwordTable.pad(20);
        passwordTable.defaults().spaceBottom(10);

        Label questionLabel = new Label("Would you like a random password?", skin);
        questionLabel.setWrap(true);
        passwordTable.add(questionLabel).colspan(2).width(400).padBottom(20).row();

        Table buttonTable = new Table();
        TextButton yesButton = new TextButton("Yes", skin);
        styleButton(yesButton, BUTTON_COLOR_NORMAL, BUTTON_COLOR_HOVER);
        TextButton noButton = new TextButton("No", skin);
        styleButton(noButton, new Color(0.7f, 0.4f, 0.2f, 1f), new Color(0.8f, 0.5f, 0.3f, 1f));

        buttonTable.add(yesButton).width(100).height(40).padRight(20);
        buttonTable.add(noButton).width(100).height(40);

        passwordTable.add(buttonTable).colspan(2).row();

        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String randomPass = RegisterManuController.generateStrongPassword(10);
                Result result = controller.changePassword(randomPass, true);
                user.setPassword(result.message());
                SaveData.saveUsersToFile(App.getInstance().getUsers());
                showSuccessMessage("Your password changed successfully! NEW PASSWORD: " + result.message());
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
        newPasswordDialog.button("Cancel", false, skin.get("default", TextButton.TextButtonStyle.class)).key(com.badlogic.gdx.Input.Keys.ESCAPE, false);
        newPasswordDialog.show(stage);
    }

    private void showCustomPasswordDialog(User user) {
        Dialog customPasswordDialog = new Dialog("Enter New Password", skin);

        Table passwordTable = new Table();
        passwordTable.pad(20);
        passwordTable.defaults().spaceBottom(10);

        passwordTable.add(new Label("New Password:", skin)).padRight(10);
        final TextField newPasswordField = new TextField("", skin);
        newPasswordField.setPasswordCharacter('*');
        newPasswordField.setPasswordMode(true);
        passwordTable.add(newPasswordField).width(250).row();

        final Label errorLabel = new Label("", skin);
        errorLabel.setColor(ERROR_COLOR);
        errorLabel.setWrap(true);
        passwordTable.add(errorLabel).colspan(2).width(350).padBottom(20).row();

        TextButton submitButton = new TextButton("Submit", skin);
        styleButton(submitButton, BUTTON_COLOR_NORMAL, BUTTON_COLOR_HOVER);
        passwordTable.add(submitButton).colspan(2).width(150).height(40).row();

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String newPassword = newPasswordField.getText();
                Result result = controller.changePassword(newPassword, false);

                if (result.state()) {
                    user.setPassword(result.message());
                    SaveData.saveUsersToFile(App.getInstance().getUsers());
                    showSuccessMessage("Your password changed successfully! NEW PASSWORD: " + result.message());
                    customPasswordDialog.hide();
                } else {
                    errorLabel.setText(result.message());


                    TextButton retryButton = new TextButton("Try Again", skin);
                    styleButton(retryButton, new Color(0.6f, 0.6f, 0.6f, 1f), new Color(0.7f, 0.7f, 0.7f, 1f));
                    passwordTable.add(retryButton).colspan(2).width(150).height(40).padTop(10).row();
                    passwordTable.pack();

                    retryButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            newPasswordField.setText("");
                            errorLabel.setText("");
                            passwordTable.removeActor(retryButton);
                            passwordTable.pack();
                        }
                    });
                }
            }
        });

        customPasswordDialog.getContentTable().add(passwordTable);
        customPasswordDialog.button("Cancel", false, skin.get("default", TextButton.TextButtonStyle.class)).key(com.badlogic.gdx.Input.Keys.ESCAPE, false);
        customPasswordDialog.show(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (backgroundTexture != null) {
            backgroundBatch.begin();
            backgroundBatch.setColor(1, 1, 1, 1);
            backgroundBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            backgroundBatch.end();
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
        if (backgroundBatch != null) {
            backgroundBatch.dispose();
        }
    }

    @Override
    public void checkCommand(Scanner scanner) {}
}
