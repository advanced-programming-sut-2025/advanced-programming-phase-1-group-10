package Client.Controllers.FinalControllers;

import Client.Controllers.CheatCodeControllers;
import Client.Controllers.MessageSystem;
import Common.Models.App;
import Common.Models.Commands.CheatCodeCommands;
import Common.Models.Map;
import Common.Models.Position;
import Common.Models.Result;
import Client.Main;
import Common.Network.Messages.Message;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.regex.Matcher;

public class CheatBoxController {

    private InputProcessor previousInputProcessor;
    CheatCodeControllers cheatCodeController = new CheatCodeControllers();
    private final GameControllerFinal gameController;

    private boolean isVisible = false;
    private final Stage stage;
    private final TextField cheatTextField;
    private final int CHEATBOX_WIDTH = 600;
    private final int CHEATBOX_HEIGHT = 80;
    private final int PADDING = 20;


    private String lastEnteredText = "";

    public CheatBoxController(GameControllerFinal gameController) {

        this.gameController = gameController;

        stage = new Stage(new ScreenViewport());

        Skin skin = Main.getInstance().getSkin();

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle(skin.get(TextField.TextFieldStyle.class));

        textFieldStyle.fontColor = new Color(0, 1, 0, 1);

        cheatTextField = new TextField("", textFieldStyle);
        cheatTextField.setSize(CHEATBOX_WIDTH, CHEATBOX_HEIGHT);

        cheatTextField.setPosition(PADDING, PADDING);

        cheatTextField.setAlignment(Align.left);
        cheatTextField.setBlinkTime(0.5f);
        cheatTextField.setMessageText("Enter cheat code...");


        stage.addActor(cheatTextField);
    }

    public void update(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.GRAVE)) {
            toggleVisibility();
        }

        if (isVisible) {

            stage.act(delta);


            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                String enteredText = cheatTextField.getText();
                if (!enteredText.isEmpty()) {
                    processCheatCode(enteredText);
                    lastEnteredText = enteredText;
                    cheatTextField.setText("");
                }
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                hideCheatBox();
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                cheatTextField.setText(lastEnteredText);
                cheatTextField.setCursorPosition(lastEnteredText.length());
            }
        }
    }

    public void render() {
        if (isVisible) {
            stage.draw();
        }
    }

    public void toggleVisibility() {
        if (isVisible) {
            hideCheatBox();
        } else {
            showCheatBox();
        }
    }

    private void showCheatBox() {
        isVisible = true;
        previousInputProcessor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(stage);
        cheatTextField.setText("");
        cheatTextField.setDisabled(false);
        cheatTextField.setVisible(true);
        stage.setKeyboardFocus(cheatTextField);
    }

    private void hideCheatBox() {
        isVisible = false;
        cheatTextField.setDisabled(true);
        cheatTextField.setVisible(false);
        Gdx.input.setInputProcessor(previousInputProcessor);
    }

    private void processCheatCode(String input) {

        if(App.getInstance().getCurrentGame().isOnline()){
            MessageSystem.showError("Cheating in Online games ?!",2f);
            return;
        }


        System.out.println("Processing cheat code: " + input);
        Matcher matcher;
        if ((matcher = CheatCodeCommands.CHEAT_HOUR.getMatcher(input)) != null) {
            Result result = cheatCodeController.advanceHour(Integer.parseInt(matcher.group("x")));
            if (result.state())
                MessageSystem.showInfo(result.message(),5.0f);

        } else if ((matcher = CheatCodeCommands.CHEAT_DAY.getMatcher(input)) != null) {
            Result result = cheatCodeController.advanceDay(Integer.parseInt(matcher.group("x")));
            if (result.state())
                MessageSystem.showInfo(result.message(),5.0f);

        } else if ((matcher = CheatCodeCommands.CHANGE_NEXT_DAY_WEATHER.getMatcher(input)) != null) {
            Result result = cheatCodeController.changeWeather(
                matcher.group("Type").trim()
            );
            if (result.state())
                MessageSystem.showInfo(result.message(),5.0f);
            else
                MessageSystem.showError(result.message(),4.0f);

        } else if ((matcher = CheatCodeCommands.THOR_TILE.getMatcher(input)) != null) {
            int tileX = Integer.parseInt(matcher.group("x"));
            int tileY = Integer.parseInt(matcher.group("y"));
            Position pos = new Position(tileX, tileY);
            MessageSystem.showMessage(cheatCodeController.thorTile(pos).message(),2f,Color.GREEN);



            // Convert tile position to world coordinates (center of tile)
            float worldX = tileX * Map.tileSize + Map.tileSize * 0.5f;
            float worldY = tileY * Map.tileSize + Map.tileSize * 0.5f;
            WeatherController weatherController = gameController.getWeatherController();

            weatherController.spawnThunderAt(worldX, worldY);

        } else if((matcher = CheatCodeCommands.ADD_MONEY.getMatcher(input)) != null){
            Result result = cheatCodeController.addMoney(
                matcher.group("count")
            );
            if (result.state())
                MessageSystem.showInfo(result.message(),5.0f);
            else
                MessageSystem.showError(result.message(),4.0f);

        } else if((matcher = CheatCodeCommands.SET_ENERGY.getMatcher(input)) != null){
            Result result = cheatCodeController.setEnergy(matcher.group("value"));
            if (result.state())
                MessageSystem.showInfo(result.message(),5.0f);
            else
                MessageSystem.showError(result.message(),4.0f);

        } else if ((matcher = CheatCodeCommands.SET_ENERGY_UNLIMITED.getMatcher(input)) != null) {
            Result result = cheatCodeController.setUnlimitedEnergy();
            if (result.state())
                MessageSystem.showInfo(result.message(),5.0f);

        } else if((matcher = CheatCodeCommands.SET_ANIMAL_FRIENDSHIP.getMatcher(input)) != null){
            String name = matcher.group("animalName");
            String amount = matcher.group("amount");
            Result result = cheatCodeController.setAnimalFriendShip(name,amount);

            if (result.state())
                MessageSystem.showInfo(result.message(),5.0f);
            else
                MessageSystem.showError(result.message(),4.0f);
        } else if((matcher = CheatCodeCommands.ADD_SEED.getMatcher(input)) != null){
            cheatCodeController.addSeedToInventory(
                matcher.group("seedname")
            );
        } else if((matcher = CheatCodeCommands.ADD_FERTILIZER.getMatcher(input)) != null){
            cheatCodeController.addFertilizerToInventory();
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void dispose() {
        stage.dispose();
    }
}
