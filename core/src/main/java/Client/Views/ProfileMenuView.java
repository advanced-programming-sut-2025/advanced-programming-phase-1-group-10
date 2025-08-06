package Client.Views;

import Client.Controllers.ProfileMenuControllers;
import Client.Controllers.RegisterManuController;
import Common.Models.App;
import Common.Models.Result;
import Common.Models.SaveData;
import Common.Models.User;

import Client.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Scanner;

public class ProfileMenuView implements Screen, AppMenu {
    private ProfileMenuControllers controller;
    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private Table contentTable;
    private ScrollPane scrollPane;
    private Label messageLabel;
    private Texture backgroundTexture;
    private Texture avatarTexture;
    private Texture coinTexture;
    private Texture gameIconTexture;
    private User currentUser;


    private enum ViewMode {
        PROFILE_INFO,
        CHANGE_USERNAME,
        CHANGE_NICKNAME,
        CHANGE_EMAIL,
        CHANGE_PASSWORD
    }

    private ViewMode currentMode = ViewMode.PROFILE_INFO;


    private final Color TITLE_COLOR = new Color(0.4f, 0.2f, 0.1f, 1f);
    private final Color HEADER_COLOR = new Color(0.55f, 0.78f, 0.25f, 1f);
    private final Color BUTTON_COLOR = new Color(0.55f, 0.78f, 0.25f, 1f);
    private final Color BUTTON_HOVER_COLOR = new Color(0.65f, 0.88f, 0.35f, 1f);
    private final Color BACK_BUTTON_COLOR = new Color(0.8f, 0.3f, 0.3f, 1f);
    private final Color ERROR_COLOR = new Color(0.8f, 0.2f, 0.2f, 1f);
    private final Color SUCCESS_COLOR = new Color(0.2f, 0.6f, 0.3f, 1f);
    private final Color LABEL_COLOR = new Color(0.3f, 0.3f, 0.3f, 1f);
    private final Color VALUE_COLOR = new Color(0.1f, 0.4f, 0.6f, 1f);

    public ProfileMenuView() {
        controller = new ProfileMenuControllers();
        stage = new Stage(new ScreenViewport());


        Main main = (Main) Gdx.app.getApplicationListener();
        skin = main.getSkin();


        currentUser = App.getInstance().getCurrentUser();


        loadTextures();

        createUI();


        Gdx.input.setInputProcessor(stage);
    }

    private void loadTextures() {
        try {
            backgroundTexture = new Texture(Gdx.files.internal("backgrounds/ProfileMenu.png"));
        } catch (Exception e) {
            System.out.println("Background image not found: " + e.getMessage());
        }

        try {

            if (currentUser.getAvatarPath() != null && !currentUser.getAvatarPath().isEmpty()) {
                avatarTexture = new Texture(Gdx.files.internal(currentUser.getAvatarPath()));
            } else {

                String gender = currentUser.getGender().toString().toLowerCase();
                avatarTexture = new Texture(Gdx.files.internal("avatars/" + gender + "_avatar.png"));
            }
        } catch (Exception e) {

            try {
                avatarTexture = new Texture(Gdx.files.internal("avatars/default.png"));
            } catch (Exception ex) {
                System.out.println("Avatar image not found: " + ex.getMessage());
            }
        }

        try {
            coinTexture = new Texture(Gdx.files.internal("icons/coin.png"));
        } catch (Exception e) {
            System.out.println("Coin icon not found: " + e.getMessage());
        }

        try {
            gameIconTexture = new Texture(Gdx.files.internal("icons/game_icon.png"));
        } catch (Exception e) {
            System.out.println("Game icon not found: " + e.getMessage());
        }
    }

    private void createUI() {

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(20);


        contentTable = new Table();
        contentTable.pad(20);


        showProfileInfo();


        scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);


        mainTable.add(scrollPane).expand().fill();

        stage.addActor(mainTable);
    }

    private void showProfileInfo() {
        contentTable.clear();
        currentMode = ViewMode.PROFILE_INFO;


        Label titleLabel = new Label("PROFILE", skin);
        titleLabel.setColor(TITLE_COLOR);
        titleLabel.setFontScale(1.8f);
        titleLabel.setAlignment(Align.center);
        titleLabel.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.fadeIn(0.5f, Interpolation.fade)
        ));
        contentTable.add(titleLabel).colspan(2).padBottom(30).row();


        if (avatarTexture != null) {
            Image avatarImage = new Image(avatarTexture);
            avatarImage.setOrigin(avatarImage.getWidth() / 2, avatarImage.getHeight() / 2);
            avatarImage.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.parallel(
                    Actions.fadeIn(0.8f),
                    Actions.scaleTo(1.1f, 1.1f, 0.5f, Interpolation.swing),
                    Actions.scaleTo(1f, 1f, 0.5f, Interpolation.swing)
                )
            ));

            contentTable.add(avatarImage).size(160, 160).colspan(2).padBottom(30).row();
        }

        TextButton changeAvatarButton = createAnimatedButton("Change Avatar");
        changeAvatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showAvatarSelectionDialog();
            }
        });
        contentTable.add(changeAvatarButton).colspan(2).width(300).padBottom(20).row();


        Table infoTable = new Table();
        //infoTable.setBackground(skin.newDrawable("white", new Color(1, 1, 1, 0.8f)));
        infoTable.pad(15);


        addInfoRow(infoTable, "Username:", currentUser.getUsername());


        addInfoRow(infoTable, "Nickname:", currentUser.getNickname());


        addInfoRow(infoTable, "Email:", currentUser.getEmail());


        addInfoRow(infoTable, "Gender:", currentUser.getGender().toString());


        Table coinRow = new Table();
        Label coinLabel = new Label("Gold:", skin);
        coinLabel.setColor(LABEL_COLOR);
        coinRow.add(coinLabel).padRight(10);

        Table coinValueContainer = new Table();
        if (coinTexture != null) {
            Image coinIcon = new Image(coinTexture);
            coinValueContainer.add(coinIcon).size(20, 20).padRight(5);
        }

        Label coinValue = new Label(String.valueOf(currentUser.gold), skin);
        coinValue.setColor(VALUE_COLOR);
        coinValueContainer.add(coinValue);

        coinRow.add(coinValueContainer).expandX().left();
        infoTable.add(coinRow).expandX().fillX().padBottom(10).row();


        Table gameRow = new Table();
        Label gameLabel = new Label("Games Played:", skin);
        gameLabel.setColor(LABEL_COLOR);
        gameRow.add(gameLabel).padRight(10);

        Table gameValueContainer = new Table();
        if (gameIconTexture != null) {
            Image gameIcon = new Image(gameIconTexture);
            gameValueContainer.add(gameIcon).size(20, 20).padRight(5);
        }

        Label gameValue = new Label(String.valueOf(currentUser.games), skin);
        gameValue.setColor(VALUE_COLOR);
        gameValueContainer.add(gameValue);

        gameRow.add(gameValueContainer).expandX().left();
        infoTable.add(gameRow).expandX().fillX().padBottom(10).row();


        infoTable.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.delay(0.3f),
            Actions.fadeIn(0.5f)
        ));

        contentTable.add(infoTable).colspan(2).width(400).padBottom(30).row();


        Table buttonsTable = new Table();


        TextButton changeUsernameButton = createAnimatedButton("Change Username");
        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showChangeUsernameForm();
            }
        });
        buttonsTable.add(changeUsernameButton).width(300).padBottom(10).row();


        TextButton changeNicknameButton = createAnimatedButton("Change Nickname");
        changeNicknameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showChangeNicknameForm();
            }
        });
        buttonsTable.add(changeNicknameButton).width(300).padBottom(10).row();


        TextButton changeEmailButton = createAnimatedButton("Change Email");
        changeEmailButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showChangeEmailForm();
            }
        });
        buttonsTable.add(changeEmailButton).width(300).padBottom(10).row();


        TextButton changePasswordButton = createAnimatedButton("Change Password");
        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showChangePasswordForm();
            }
        });
        buttonsTable.add(changePasswordButton).width(300).padBottom(10).row();


        TextButton backButton = createAnimatedButton("Back to Main Menu");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = controller.enterMenu("main menu");
                if (result.state()) {
                    Main.getInstance().switchScreen(new MainMenuView());
                } else {
                    showErrorMessage(result.message());
                }
            }
        });
        buttonsTable.add(backButton).width(300).padTop(20);


        buttonsTable.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.delay(0.6f),
            Actions.fadeIn(0.5f)
        ));

        contentTable.add(buttonsTable).colspan(2).padBottom(20).row();


        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);
        contentTable.add(messageLabel).colspan(2).width(400).padTop(20);
    }

    private void showAvatarSelectionDialog() {
        Dialog avatarDialog = new Dialog("Choose Avatar", skin);

        Table avatarTable = new Table();
        avatarTable.pad(20);

        Label titleLabel = new Label("Select your avatar:", skin);
        titleLabel.setColor(HEADER_COLOR);
        avatarTable.add(titleLabel).colspan(3).padBottom(20).row();


        final String[] avatarPaths = {
            "avatars/avatar1.png",
            "avatars/avatar2.png",
            "avatars/avatar3.png"
        };

        Table avatarsRow = new Table();

        for (int i = 0; i < avatarPaths.length; i++) {
            final int index = i;
            try {
                Texture avatarTex = new Texture(Gdx.files.internal(avatarPaths[i]));
                Image avatarImage = new Image(avatarTex);


                avatarImage.addListener(new ClickListener() {
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        avatarImage.addAction(Actions.scaleTo(1.1f, 1.1f, 0.2f, Interpolation.swing));
                    }

                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        avatarImage.addAction(Actions.scaleTo(1f, 1f, 0.2f, Interpolation.swing));
                    }

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        currentUser.setAvatarPath(avatarPaths[index]);


                        SaveData.saveUsersToFile(App.getInstance().getUsers());


                        if (avatarTexture != null) {
                            avatarTexture.dispose();
                        }
                        avatarTexture = new Texture(Gdx.files.internal(avatarPaths[index]));


                        showSuccessMessage("Avatar changed successfully!");


                        avatarDialog.hide();


                        stage.addAction(Actions.sequence(
                            Actions.delay(1f),
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    showProfileInfo();
                                }
                            })
                        ));
                    }
                });

                avatarsRow.add(avatarImage).size(100, 100).pad(10);

            } catch (Exception e) {
                System.out.println("Avatar " + (i+1) + " not found: " + e.getMessage());
                Label errorLabel = new Label("Avatar " + (i+1) + "\nnot found", skin);
                errorLabel.setAlignment(Align.center);
                avatarsRow.add(errorLabel).size(100, 100).pad(10);
            }
        }

        avatarTable.add(avatarsRow).padBottom(20).row();


        TextButton backButton = new TextButton("Cancel", skin);
//        backButton.setColor(BACK_BUTTON_COLOR);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                avatarDialog.hide();
            }
        });

        avatarTable.add(backButton).width(120).padTop(10);

        avatarDialog.getContentTable().add(avatarTable);
        avatarDialog.show(stage);
    }


    private void addInfoRow(Table table, String label, String value) {
        Table row = new Table();
        Label labelWidget = new Label(label, skin);
        labelWidget.setColor(LABEL_COLOR);
        row.add(labelWidget).padRight(10);

        Label valueWidget = new Label(value, skin);
        valueWidget.setColor(VALUE_COLOR);
        row.add(valueWidget).expandX().left();

        table.add(row).expandX().fillX().padBottom(10).row();
    }

    private TextButton createAnimatedButton(String text) {
        TextButton button = new TextButton(text, skin);

        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.addAction(Actions.sequence(
                    Actions.parallel(
//                        Actions.color(BUTTON_HOVER_COLOR, 0.2f),
                        Actions.scaleTo(1.05f, 1.05f, 0.2f, Interpolation.swingOut)
                    )
                ));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.addAction(Actions.sequence(
                    Actions.parallel(
//                        Actions.color(color, 0.2f),
                        Actions.scaleTo(1f, 1f, 0.2f, Interpolation.swingIn)
                    )
                ));
            }
        });

        return button;
    }

    private void showChangeUsernameForm() {
        contentTable.clear();
        currentMode = ViewMode.CHANGE_USERNAME;

        Label titleLabel = new Label("Change Username", skin);
        titleLabel.setColor(HEADER_COLOR);
        titleLabel.setFontScale(1.5f);
        contentTable.add(titleLabel).colspan(2).padBottom(30).row();


        contentTable.add(new Label("Current Username:", skin)).padRight(10);
        contentTable.add(new Label(currentUser.getUsername(), skin)).padBottom(20).row();


        contentTable.add(new Label("New Username:", skin)).padRight(10);
        final TextField usernameField = new TextField("", skin);
        contentTable.add(usernameField).width(250).padBottom(30).row();


        Table buttonTable = new Table();
        TextButton submitButton = new TextButton("Submit", skin);
//        submitButton.setColor(BUTTON_COLOR);

        TextButton backButton = new TextButton("Back", skin);
//        backButton.setColor(BACK_BUTTON_COLOR);

        buttonTable.add(submitButton).width(150).padRight(20);
        buttonTable.add(backButton).width(150);

        contentTable.add(buttonTable).colspan(2).padBottom(20).row();


        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);
        contentTable.add(messageLabel).colspan(2).width(400).padTop(20).row();


        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleChangeUsername(usernameField.getText());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showProfileInfo();
            }
        });
    }

    private void showChangeNicknameForm() {
        contentTable.clear();
        currentMode = ViewMode.CHANGE_NICKNAME;


        Label titleLabel = new Label("Change Nickname", skin);
        titleLabel.setColor(HEADER_COLOR);
        titleLabel.setFontScale(1.5f);
        contentTable.add(titleLabel).colspan(2).padBottom(30).row();


        contentTable.add(new Label("Current Nickname:", skin)).padRight(10);
        contentTable.add(new Label(currentUser.getNickname(), skin)).padBottom(20).row();


        contentTable.add(new Label("New Nickname:", skin)).padRight(10);
        final TextField nicknameField = new TextField("", skin);
        contentTable.add(nicknameField).width(250).padBottom(30).row();


        Table buttonTable = new Table();
        TextButton submitButton = new TextButton("Submit", skin);
        submitButton.setColor(BUTTON_COLOR);

        TextButton backButton = new TextButton("Back", skin);
//        backButton.setColor(BACK_BUTTON_COLOR);

        buttonTable.add(submitButton).width(150).padRight(20);
        buttonTable.add(backButton).width(150);

        contentTable.add(buttonTable).colspan(2).padBottom(20).row();


        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);
        contentTable.add(messageLabel).colspan(2).width(400).padTop(20).row();


        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleChangeNickname(nicknameField.getText());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showProfileInfo();
            }
        });
    }

    private void showChangeEmailForm() {
        contentTable.clear();
        currentMode = ViewMode.CHANGE_EMAIL;


        Label titleLabel = new Label("Change Email", skin);
        titleLabel.setColor(HEADER_COLOR);
        titleLabel.setFontScale(1.5f);
        contentTable.add(titleLabel).colspan(2).padBottom(30).row();


        contentTable.add(new Label("Current Email:", skin)).padRight(10);
        contentTable.add(new Label(currentUser.getEmail(), skin)).padBottom(20).row();


        contentTable.add(new Label("New Email:", skin)).padRight(10);
        final TextField emailField = new TextField("", skin);
        contentTable.add(emailField).width(250).padBottom(30).row();


        Table buttonTable = new Table();
        TextButton submitButton = new TextButton("Submit", skin);
        submitButton.setColor(BUTTON_COLOR);

        TextButton backButton = new TextButton("Back", skin);
//        backButton.setColor(BACK_BUTTON_COLOR);

        buttonTable.add(submitButton).width(150).padRight(20);
        buttonTable.add(backButton).width(150);

        contentTable.add(buttonTable).colspan(2).padBottom(20).row();


        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);
        contentTable.add(messageLabel).colspan(2).width(400).padTop(20).row();


        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleChangeEmail(emailField.getText());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showProfileInfo();
            }
        });
    }

    private void showChangePasswordForm() {
        contentTable.clear();
        currentMode = ViewMode.CHANGE_PASSWORD;


        Label titleLabel = new Label("Change Password", skin);
        titleLabel.setColor(HEADER_COLOR);
        titleLabel.setFontScale(1.5f);
        contentTable.add(titleLabel).colspan(2).padBottom(30).row();


        contentTable.add(new Label("Current Password:", skin)).padRight(10);
        final TextField oldPasswordField = new TextField("", skin);
        oldPasswordField.setPasswordCharacter('*');
        oldPasswordField.setPasswordMode(true);
        contentTable.add(oldPasswordField).width(250).padBottom(20).row();


        contentTable.add(new Label("New Password:", skin)).padRight(10);
        final TextField newPasswordField = new TextField("", skin);
        newPasswordField.setPasswordCharacter('*');
        newPasswordField.setPasswordMode(true);
        contentTable.add(newPasswordField).width(250).padBottom(30).row();

        TextButton randomPasswordButton = new TextButton("Generate Random Password", skin);
        randomPasswordButton.setColor(new Color(0.3f, 0.6f, 0.9f, 1f));
        randomPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                String randomPass = RegisterManuController.generateStrongPassword(12);
                newPasswordField.setText(randomPass);
                showSuccessMessage("Random password generated: " + randomPass);
            }
        });

        contentTable.add(randomPasswordButton).colspan(2).width(400).padBottom(20).row();


        Table buttonTable = new Table();
        TextButton submitButton = new TextButton("Submit", skin);
        submitButton.setColor(BUTTON_COLOR);

        TextButton backButton = new TextButton("Back", skin);
//        backButton.setColor(BACK_BUTTON_COLOR);

        buttonTable.add(submitButton).width(150).padRight(20);
        buttonTable.add(backButton).width(150);

        contentTable.add(buttonTable).colspan(2).padBottom(20).row();


        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);
        contentTable.add(messageLabel).colspan(2).width(400).padTop(20).row();


        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleChangePassword(newPasswordField.getText(), oldPasswordField.getText());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showProfileInfo();
            }
        });
    }

    private void handleChangeUsername(String newUsername) {
        Result result = controller.changeUsername(newUsername);

        if (!result.state() && result.message().contains("username already taken")) {
            showUsernameSuggestions(newUsername);
        } else if (result.state()) {
            showSuccessMessage(result.message());
            SaveData.saveUsersToFile(App.getInstance().getUsers());


            stage.addAction(Actions.sequence(
                Actions.delay(2f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        showProfileInfo();
                    }
                })
            ));
        } else {
            showErrorMessage(result.message());
        }
    }

    private void showUsernameSuggestions(String username) {
        ArrayList<String> suggestions = controller.suggestAlternativeUsernames(username);

        Dialog suggestionDialog = new Dialog("Username Suggestions", skin);

        Table suggestionTable = new Table();
        suggestionTable.pad(20);

        Label infoLabel = new Label("Username already taken! Please choose one of these suggestions:", skin);
        infoLabel.setWrap(true);
        suggestionTable.add(infoLabel).width(400).padBottom(20).row();


        for (final String suggestion : suggestions) {
            TextButton suggestionButton = new TextButton(suggestion, skin);
            suggestionButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Result result = controller.changeUsername(suggestion);
                    if (result.state()) {
                        showSuccessMessage(result.message());
                        suggestionDialog.hide();


                        stage.addAction(Actions.sequence(
                            Actions.delay(2f),
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    showProfileInfo();
                                }
                            })
                        ));
                    } else {
                        showErrorMessage(result.message());
                        suggestionDialog.hide();
                    }
                }
            });
            suggestionTable.add(suggestionButton).width(300).padBottom(10).row();
        }


        TextButton newUsernameButton = new TextButton("Try another username", skin);
        newUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                suggestionDialog.hide();
            }
        });
        suggestionTable.add(newUsernameButton).width(350).padTop(10).row();

        suggestionDialog.getContentTable().add(suggestionTable);
        suggestionDialog.button("Cancel", false);
        suggestionDialog.show(stage);
    }

    private void handleChangeNickname(String newNickname) {
        Result result = controller.changeNickName(newNickname);

        if (result.state()) {
            showSuccessMessage(result.message());
            SaveData.saveUsersToFile(App.getInstance().getUsers());


            stage.addAction(Actions.sequence(
                Actions.delay(2f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        showProfileInfo();
                    }
                })
            ));
        } else {
            showErrorMessage(result.message());
        }
    }

    private void handleChangeEmail(String newEmail) {
        Result result = controller.changeEmail(newEmail);

        if (result.state()) {
            showSuccessMessage(result.message());
            SaveData.saveUsersToFile(App.getInstance().getUsers());


            stage.addAction(Actions.sequence(
                Actions.delay(2f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        showProfileInfo();
                    }
                })
            ));
        } else {
            showErrorMessage(result.message());
        }
    }

    private void handleChangePassword(String newPassword, String oldPassword) {
        Result result = controller.changePassword(newPassword, oldPassword);

        if (result.state()) {
            showSuccessMessage(result.message());
            SaveData.saveUsersToFile(App.getInstance().getUsers());


            stage.addAction(Actions.sequence(
                Actions.delay(2f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        showProfileInfo();
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


        messageLabel.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.fadeIn(0.5f),
            Actions.delay(3f),
            Actions.fadeOut(0.5f)
        ));
    }

    private void showErrorMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setColor(ERROR_COLOR);


        messageLabel.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.fadeIn(0.5f),
            Actions.delay(3f),
            Actions.fadeOut(0.5f)
        ));
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
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();

        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }

        if (avatarTexture != null) {
            avatarTexture.dispose();
        }

        if (coinTexture != null) {
            coinTexture.dispose();
        }

        if (gameIconTexture != null) {
            gameIconTexture.dispose();
        }
    }

    @Override
    public void checkCommand(Scanner scanner) {}
}
