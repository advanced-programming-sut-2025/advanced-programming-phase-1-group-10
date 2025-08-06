

package Client.Views;

import Server.Controllers.RegisterManuController;
import Common.Models.App;
import Common.Models.Menu;
import Common.Models.Result;

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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Scanner;

public class RegisterMenuView implements Screen, AppMenu {
    private RegisterManuController controller;
    private Stage stage;
    private Skin skin;
    private ScrollPane scrollPane;
    private SpriteBatch batch;
    private Texture backgroundTexture;

    private enum ViewMode {
        REGISTER_FORM,
        USERNAME_SUGGESTIONS,
        SECURITY_QUESTION
    }

    private ViewMode currentMode = ViewMode.REGISTER_FORM;


    private Table mainTable;
    private Table registerTable;
    private Table suggestionsTable;
    private Table securityTable;


    private TextField usernameField, passwordField, confirmPasswordField, nicknameField, emailField;
    private CheckBox showPasswordCheckbox;
    private TextButton maleButton, femaleButton;
    private Label messageLabel;


    private int selectedQuestionNumber = 1;


    private final Color TITLE_COLOR = new Color(0.2f, 0.6f, 0.9f, 1f);
    private final Color LABEL_COLOR = new Color(0.2f, 0.6f, 0.9f, 1f);
    private final Color BUTTON_COLOR = new Color(0.2f, 0.6f, 0.9f, 1f);
    private final Color ERROR_COLOR = new Color(0.9f, 0.3f, 0.3f, 1f);
    private final Color SUCCESS_COLOR = new Color(0.3f, 0.7f, 0.3f, 1f);
    private final Color PANEL_BACKGROUND_COLOR_TRANSPARENT = new Color(0, 0, 0, 0f);

    public RegisterMenuView() {
        controller = new RegisterManuController();
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();

        try {
            backgroundTexture = new Texture(Gdx.files.internal("assets/backgrounds/RegisterMenu.png"));


        } catch (Exception e) {
            System.out.println("Background image not found or loaded incorrectly: " + e.getMessage());
            backgroundTexture = new Texture(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGB888);
            backgroundTexture.draw(new com.badlogic.gdx.graphics.Pixmap(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGB888), 0, 0);
        }
        Main main = (Main)Gdx.app.getApplicationListener();
        skin = main.getSkin();

        createUI();

        Gdx.input.setInputProcessor(stage);
    }

    private void createUI() {

        mainTable = new Table();
        mainTable.setFillParent(true);

        registerTable = new Table();
        suggestionsTable = new Table();
        securityTable = new Table();


        createRegisterForm();


//        scrollPane = new ScrollPane(mainTable, skin);
//        scrollPane.setFillParent(true);
//        scrollPane.setScrollingDisabled(true, false);

        stage.addActor(mainTable);
    }

    private void createRegisterForm() {
        registerTable.clear();
        registerTable.pad(50);


        Label titleLabel = new Label("REGISTER ACCOUNT", skin);
        titleLabel.setColor(TITLE_COLOR);
        registerTable.add(titleLabel).colspan(2).padBottom(30).row();


        registerTable.add(new Label("Username:", skin)).align(Align.left).padRight(10);
        if (usernameField == null) {
            usernameField = new TextField("", skin);
        }
        registerTable.add(usernameField).width(350).padBottom(15).row();


        registerTable.add(new Label("Nickname:", skin)).align(Align.left).padRight(10);
        if (nicknameField == null) {
            nicknameField = new TextField("", skin);
        }
        registerTable.add(nicknameField).width(350).padBottom(15).row();


        registerTable.add(new Label("Email:", skin)).align(Align.left).padRight(10);
        if (emailField == null) {
            emailField = new TextField("", skin);
        }
        registerTable.add(emailField).width(350).padBottom(15).row();


        registerTable.add(new Label("Gender:", skin)).align(Align.left).padRight(10);


        if (maleButton == null) {
            maleButton = new TextButton("Male", skin);
            femaleButton = new TextButton("Female", skin);
            maleButton.setChecked(true);


            maleButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (maleButton.isChecked()) {
                        femaleButton.setChecked(false);
                    } else if (!femaleButton.isChecked()) {
                        maleButton.setChecked(true);
                    }
                }
            });

            femaleButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (femaleButton.isChecked()) {
                        maleButton.setChecked(false);
                    } else if (!maleButton.isChecked()) {
                        femaleButton.setChecked(true);
                    }
                }
            });
        }

        Table genderTable = new Table();
        genderTable.add(maleButton).padRight(10);
        genderTable.add(femaleButton);

        registerTable.add(genderTable).padBottom(15).row();


        registerTable.add(new Label("Password:", skin)).align(Align.left).padRight(10);
        if (passwordField == null) {
            passwordField = new TextField("", skin);
            passwordField.setPasswordCharacter('*');
            passwordField.setPasswordMode(true);
        }
        registerTable.add(passwordField).width(350).padBottom(15).row();


        registerTable.add(new Label("Confirm Password:", skin)).align(Align.left).padRight(10);
        if (confirmPasswordField == null) {
            confirmPasswordField = new TextField("", skin);
            confirmPasswordField.setPasswordCharacter('*');
            confirmPasswordField.setPasswordMode(true);
        }
        registerTable.add(confirmPasswordField).width(350).padBottom(15).row();


        if (showPasswordCheckbox == null) {
            showPasswordCheckbox = new CheckBox("Show Password", skin);
            showPasswordCheckbox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    passwordField.setPasswordMode(!showPasswordCheckbox.isChecked());
                    confirmPasswordField.setPasswordMode(!showPasswordCheckbox.isChecked());
                }
            });
        }
        registerTable.add(showPasswordCheckbox).colspan(2).padBottom(20).row();


        TextButton randomPasswordButton = new TextButton("Generate Random Password", skin);
        randomPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String randomPass = RegisterManuController.generateStrongPassword(12);
                passwordField.setText(randomPass);
                confirmPasswordField.setText(randomPass);
                showSuccessMessage("Generated random password: " + randomPass);
            }
        });
        registerTable.add(randomPasswordButton).colspan(2).padBottom(30).row();


        TextButton registerButton = new TextButton("Register", skin);
        TextButton loginButton = new TextButton("Login", skin);

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleRegister();
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.getInstance().setCurrentMenu(Menu.LoginMenu);
                Main.getInstance().switchScreen(new LoginMenuView());
            }
        });

        Table buttonTable = new Table();
        buttonTable.add(registerButton).width(170).padRight(20);
        buttonTable.add(loginButton).width(170);

        registerTable.add(buttonTable).colspan(2).padBottom(20).row();


        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);
        registerTable.add(messageLabel).colspan(2).width(300).row();


        switchToView(ViewMode.REGISTER_FORM);
    }

    private void createSecurityQuestionForm() {
        securityTable.clear();
        securityTable.pad(50);


        Label titleLabel = new Label("SECURITY QUESTION", skin);
        titleLabel.setColor(TITLE_COLOR);
        securityTable.add(titleLabel).colspan(2).padBottom(30).row();

        securityTable.add(new Label("Please select a security question:", skin)).colspan(2).padBottom(20).row();


        final Label questionLabel = new Label("1. What was your first pet's name?", skin);
        questionLabel.setWrap(true);
        securityTable.add(questionLabel).colspan(2).width(370).padBottom(20).row();


        TextButton prevButton = new TextButton("<", skin);
        TextButton nextButton = new TextButton(">", skin);

        prevButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedQuestionNumber = (selectedQuestionNumber == 1) ? 5 : selectedQuestionNumber - 1;
                updateQuestionLabel(questionLabel);
            }
        });

        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedQuestionNumber = (selectedQuestionNumber == 5) ? 1 : selectedQuestionNumber + 1;
                updateQuestionLabel(questionLabel);
            }
        });

        Table navigationTable = new Table();
        navigationTable.add(prevButton).width(80).padRight(20);
        navigationTable.add(nextButton).width(80);

        securityTable.add(navigationTable).colspan(2).padBottom(20).row();


        securityTable.add(new Label("Answer:", skin)).align(Align.left).padRight(10);
        final TextField answerField = new TextField("", skin);
        securityTable.add(answerField).width(350).padBottom(15).row();


        securityTable.add(new Label("Confirm Answer:", skin)).align(Align.left).padRight(10);
        final TextField confirmAnswerField = new TextField("", skin);
        securityTable.add(confirmAnswerField).width(350).padBottom(30).row();


        TextButton confirmButton = new TextButton("Confirm", skin);
        TextButton backButton = new TextButton("Back", skin);

        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String answer = answerField.getText();
                String confirmAnswer = confirmAnswerField.getText();

                if (answer.isEmpty()) {
                    showErrorMessage("Please enter an answer");
                    return;
                }

                if (!answer.equals(confirmAnswer)) {
                    showErrorMessage("Answer and confirmation don't match!");
                    return;
                }

                try {
                    Result result = controller.pickQuestion(selectedQuestionNumber, answer);
                    switchToView(ViewMode.REGISTER_FORM);
                    showSuccessMessage(result.message());
                } catch (Exception e) {
                    showErrorMessage("Error: " + e.getMessage());
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchToView(ViewMode.REGISTER_FORM);
            }
        });

        Table buttonTable = new Table();
        buttonTable.add(confirmButton).width(160).padRight(20);
        buttonTable.add(backButton).width(160);

        securityTable.add(buttonTable).colspan(2).padBottom(20).row();


        Label securityMessageLabel = new Label("", skin);
        securityMessageLabel.setWrap(true);
        securityMessageLabel.setAlignment(Align.center);
        securityTable.add(securityMessageLabel).colspan(2).width(300).row();
    }

    private void updateQuestionLabel(Label questionLabel) {
        String question;
        switch (selectedQuestionNumber) {
            case 1:
                question = "1. What was your first pet's name?";
                break;
            case 2:
                question = "2. What is your mother's maiden name?";
                break;
            case 3:
                question = "3. What was the name of your elementary school?";
                break;
            case 4:
                question = "4. In what city were you born?";
                break;
            case 5:
                question = "5. What was your childhood nickname?";
                break;
            default:
                question = "1. What was your first pet's name?";
                selectedQuestionNumber = 1;
        }
        questionLabel.setText(question);
    }

    private void createUsernameSuggestionsForm(String username) {
        suggestionsTable.clear();
        suggestionsTable.pad(50);


        Label titleLabel = new Label("USERNAME SUGGESTIONS", skin);
        titleLabel.setColor(TITLE_COLOR);
        suggestionsTable.add(titleLabel).colspan(2).padBottom(30).row();

        suggestionsTable.add(new Label("Username '" + username + "' is already taken.\nPlease choose one of these suggestions:", skin)).colspan(2).padBottom(30).row();


        final String[] suggestions = {
            username + "94",
            username + "53",
            username + "78",
            "player" + username
        };

        for (final String suggestion : suggestions) {
            TextButton suggestionButton = new TextButton(suggestion, skin);
            suggestionButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    usernameField.setText(suggestion);

                    switchToView(ViewMode.REGISTER_FORM);
                    handleRegisterWithSelectedUsername(suggestion);
                }
            });
            suggestionsTable.add(suggestionButton).colspan(2).width(350).padBottom(10).row();
        }


        TextButton newUsernameButton = new TextButton("Try another username", skin);
        newUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                usernameField.setText("");
                switchToView(ViewMode.REGISTER_FORM);
            }
        });
        suggestionsTable.add(newUsernameButton).colspan(2).width(350).padTop(10).padBottom(20).row();


        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchToView(ViewMode.REGISTER_FORM);
            }
        });
        suggestionsTable.add(backButton).colspan(2).width(160).padTop(20);
    }


    private void switchToView(ViewMode mode) {
        currentMode = mode;
        mainTable.clear();

        switch (mode) {
            case REGISTER_FORM:
                mainTable.add(registerTable).expand().fill();
                break;
            case USERNAME_SUGGESTIONS:
                mainTable.add(suggestionsTable).expand().fill();
                break;
            case SECURITY_QUESTION:
                if (securityTable.getChildren().size == 0) {
                    createSecurityQuestionForm();
                }
                mainTable.add(securityTable).expand().fill();
                break;
        }
        Gdx.input.setInputProcessor(stage);
        stage.setKeyboardFocus(null);
        stage.setScrollFocus(null);


        mainTable.validate();
        stage.getViewport().apply(true);
    }


    private void showSuccessMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setColor(SUCCESS_COLOR);
    }


    private void showErrorMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setColor(ERROR_COLOR);
    }


    private void handleRegisterWithSelectedUsername(String username) {
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();
        String gender = maleButton.isChecked() ? "male" : "female";

        Result result = controller.register(username, password, confirmPassword, nickname, email, gender);

        if (result.state()) {

            showSuccessMessage(result.message());
            switchToView(ViewMode.SECURITY_QUESTION);
        } else {

            showErrorMessage(result.message());
        }
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();
        String gender = maleButton.isChecked() ? "male" : "female";

        Result result = controller.register(username, password, confirmPassword, nickname, email, gender);

        if (!result.state()) {

            if (result.message() != null && result.message().contains("username already taken")) {
                createUsernameSuggestionsForm(username);
                switchToView(ViewMode.USERNAME_SUGGESTIONS);
            } else {

                showErrorMessage(result.message());
            }
        } else {

            showSuccessMessage(result.message());
            switchToView(ViewMode.SECURITY_QUESTION);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (backgroundTexture != null) {
            batch.begin();
            batch.setColor(1, 1, 1, 1);
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
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
        if (stage != null) {
            stage.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }

    @Override
    public void checkCommand(Scanner scanner) {
    }
}
