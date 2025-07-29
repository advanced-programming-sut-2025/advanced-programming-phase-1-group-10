package Controllers.FinalControllers;

import Assets.FishingMiniGameAsset;
import Models.Animal.Fish;
import Models.Animal.FishType;
import Models.App;
import Models.DateTime.Season;
import Models.PlayerStuff.Player;
import Models.Tools.Quality;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FishingMiniGameController {
    private FishingMiniGameAsset assets;
    private BitmapFont font;
    private GameControllerFinal gameController;


    private static final float BAR_X = 350;
    private static final float BAR_Y = 150;
    private static final float BAR_WIDTH = 60;
    private static final float BAR_HEIGHT = 300;
    private static final float FISH_WIDTH = 40;
    private static final float FISH_HEIGHT = 25;
    private static final float BOBBER_WIDTH = 40;
    private static final float BOBBER_HEIGHT = 15;
    private static final float GREEN_BAR_WIDTH = 40;
    private static final float GREEN_BAR_HEIGHT = 40;
    private static final float MAX_PROGRESS = 100f;


    private float fishY;
    private float fishTargetY;
    private float bobberY;
    private float catchProgress;
    private boolean isPerfectCatch;
    private boolean isGameOver;
    private FishType caughtFishType;
    private float gameTime;
    private float difficultyFactor;
    private float fishMovementTimer;
    private String resultMessage = "";
    private float resultDisplayTime;



    public FishingMiniGameController(GameControllerFinal gameController) {
        this.gameController = gameController;
        assets = new FishingMiniGameAsset();
        font = new BitmapFont();
        font.getData().setScale(2);
    }

    public void startGame() {
        resetGame();
        System.out.println("start game called...");

        gameController.setFishingMiniGameActive(true);
    }

    private void resetGame() {
        System.out.println("reset game called...");
        fishY = BAR_Y + BAR_HEIGHT / 2;
        fishTargetY = fishY;
        bobberY = fishY;
        catchProgress = 0;
        isPerfectCatch = true;
        isGameOver = false;
        gameTime = 0;
        fishMovementTimer = 0;
        resultMessage = "";
        resultDisplayTime = 0;


        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();

        difficultyFactor = 0.1f;


        Season currentSeason = App.getInstance().getCurrentGame().getGameTime().getSeason();
        List<FishType> seasonalFishes = java.util.Arrays.stream(FishType.values())
            .filter(fish -> fish.getSeason() == currentSeason)
            .collect(Collectors.toList());

        Random random = new Random();
        caughtFishType = seasonalFishes.get(random.nextInt(seasonalFishes.size()));
    }

    public void update(SpriteBatch batch, float delta) {
        System.out.println("update called ...");

        if (isGameOver) {

            renderResult(batch);


            resultDisplayTime += delta;
            if (resultDisplayTime > 3.0f) {
                exitMiniGame();
            }
        } else {

            updateGame(delta);


            renderMiniGame(batch);
        }
    }

    private void renderResult(SpriteBatch batch) {

        batch.setColor(0, 0, 0, 0.8f);
        batch.draw(assets.getBackgroundTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(Color.WHITE);


        if (!resultMessage.isEmpty()) {
            font.setColor(Color.YELLOW);
            font.draw(batch, resultMessage, 250, 300);
        }
    }


    private void updateGame(float delta) {
        gameTime += delta;
        fishMovementTimer += delta;


        if (fishMovementTimer > 0.5f * difficultyFactor) {
            fishMovementTimer = 0;

            float range = BAR_HEIGHT * 0.7f;
            fishTargetY = BAR_Y + (BAR_HEIGHT - range) / 2 + MathUtils.random(range);
        }


        fishY = MathUtils.lerp(fishY, fishTargetY, delta * 3f * difficultyFactor);


        fishY = MathUtils.clamp(fishY, BAR_Y + FISH_HEIGHT/2, BAR_Y + BAR_HEIGHT - FISH_HEIGHT/2);


        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            bobberY += 200 * delta;
            if (bobberY > BAR_Y + BAR_HEIGHT - GREEN_BAR_HEIGHT/2) {
                bobberY = BAR_Y + BAR_HEIGHT - GREEN_BAR_HEIGHT/2;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            bobberY -= 200 * delta;
            if (bobberY < BAR_Y + GREEN_BAR_HEIGHT/2) {
                bobberY = BAR_Y + GREEN_BAR_HEIGHT/2;
            }
        }


        boolean fishInGreenZone = Math.abs(fishY - bobberY) < GREEN_BAR_HEIGHT/2;


        if (fishInGreenZone) {
            catchProgress += delta * 30;
            if (catchProgress > MAX_PROGRESS) {
                catchProgress = MAX_PROGRESS;

                catchFish();
            }
        } else {
            catchProgress -= delta * 40;
            isPerfectCatch = false;
            if (catchProgress < 0) {
                catchProgress = 0;

                if (gameTime > 3.0f) {
                    loseFish();
                }
            }
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            exitMiniGame();
        }


        if (isGameOver) {
            resultDisplayTime += delta;
            if (resultDisplayTime > 3.0f) {
                exitMiniGame();
            }
        }
    }

    private void renderMiniGame(SpriteBatch batch) {

        batch.setColor(0, 0, 0, 0.8f);
        batch.draw(assets.getBackgroundTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(Color.WHITE);


        batch.draw(assets.getFishingBarTexture(), BAR_X, BAR_Y, BAR_WIDTH, BAR_HEIGHT);


        batch.draw(assets.getGreenBarTexture(), BAR_X + 10, bobberY - GREEN_BAR_HEIGHT/2, GREEN_BAR_WIDTH, GREEN_BAR_HEIGHT);


        batch.draw(assets.getFishTexture(), BAR_X + 10, fishY - FISH_HEIGHT/2, FISH_WIDTH, FISH_HEIGHT);


        float progressBarHeight = (catchProgress / MAX_PROGRESS) * BAR_HEIGHT;
        Color progressColor = isPerfectCatch ? Color.GREEN : Color.RED;
        batch.setColor(progressColor);
        batch.draw(assets.getBobberTexture(), BAR_X + BAR_WIDTH + 20, BAR_Y, 20, progressBarHeight);
        batch.setColor(Color.WHITE);


        font.setColor(Color.WHITE);
        font.draw(batch, "Press UP/DOWN to move bobber", 450, 550);
        font.draw(batch, "Keep fish in the green zone", 450, 520);
        font.draw(batch, "Press Q to quit", 450, 490);

        if (isPerfectCatch && !isGameOver && catchProgress > 0) {
            font.setColor(Color.GOLD);
            font.draw(batch, "PERFECT!", 450, 450);
        }

        if (!resultMessage.isEmpty()) {
            font.setColor(Color.YELLOW);
            font.draw(batch, resultMessage, 250, 300);
        }
    }

    private void catchFish() {
        isGameOver = true;
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();


        player.setFishingAbility(player.getFishingAbility() + 5);


        Quality fishQuality = Quality.STARTER;
        if (isPerfectCatch) {
            if (caughtFishType.getPrice() >= 1000) {
                fishQuality = Quality.IRIDIUM;
            } else if (caughtFishType.getPrice() >= 500) {
                fishQuality = Quality.GOLD;
            } else {
                fishQuality = Quality.GOLD;
            }


            player.setFishingAbility(player.getFishingAbility() + 7);
        }


        Fish caughtFish = new Fish(caughtFishType, 1);
        boolean added = player.getInventory().getBackPack().addItem(caughtFish);

        if (added) {
            if (isPerfectCatch) {
                resultMessage = "Perfect! You caught a " + fishQuality + " " + caughtFishType.getName();
            } else {
                resultMessage = "You caught a " + caughtFishType.getName();
            }
        } else {
            resultMessage = "Inventory is full! Fish released.";
        }
    }

    private void loseFish() {
        isGameOver = true;
        resultMessage = "The fish got away!";
    }

    private void exitMiniGame() {

        gameController.setFishingMiniGameActive(false);
    }

    public void dispose() {
        if (assets != null) {
            assets.dispose();
        }
        if (font != null) {
            font.dispose();
        }
    }

    public void handleKeyDown(int keycode) {

        if (keycode == Input.Keys.Q) {
            exitMiniGame();
        }
    }
}
