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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FishingMiniGameController {
    private FishingMiniGameAsset assets;
    private BitmapFont titleFont;
    private BitmapFont normalFont;
    private BitmapFont resultFont;
    private GameControllerFinal gameController;
    private ShapeRenderer shapeRenderer;


    private static final float SCALE_FACTOR = 1.5f;


    private static final float BASE_BAR_WIDTH = 105;
    private static final float BASE_BAR_HEIGHT = 600;
    private static final float BASE_FISH_WIDTH = 25;
    private static final float BASE_FISH_HEIGHT = 25;
    private static final float BASE_GREEN_BAR_WIDTH = 20;
    private static final float BASE_GREEN_BAR_HEIGHT = 120;
    private static final float MAX_PROGRESS = 100f;

    private float BAR_WIDTH;
    private float BAR_HEIGHT;
    private float FISH_WIDTH;
    private float FISH_HEIGHT;
    private float GREEN_BAR_WIDTH;
    private float GREEN_BAR_HEIGHT;


    private float BAR_X;
    private float BAR_Y;
    private float INFO_BOX_X;
    private float INFO_BOX_Y;
    private float TITLE_BOX_X;
    private float TITLE_BOX_Y;


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
        shapeRenderer = new ShapeRenderer();


        BAR_WIDTH = BASE_BAR_WIDTH * SCALE_FACTOR;
        BAR_HEIGHT = BASE_BAR_HEIGHT * SCALE_FACTOR;
        FISH_WIDTH = BASE_FISH_WIDTH * SCALE_FACTOR;
        FISH_HEIGHT = BASE_FISH_HEIGHT * SCALE_FACTOR;
        GREEN_BAR_WIDTH = BASE_GREEN_BAR_WIDTH * SCALE_FACTOR;
        GREEN_BAR_HEIGHT = BASE_GREEN_BAR_HEIGHT * SCALE_FACTOR;


        initializeFonts();


        updatePosition();
    }

    private void initializeFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));


        FreeTypeFontGenerator.FreeTypeFontParameter titleParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParam.size = (int)(40 * SCALE_FACTOR);
        titleParam.color = Color.WHITE;
        titleParam.borderWidth = 2;
        titleParam.borderColor = Color.BLACK;
        titleFont = generator.generateFont(titleParam);


        FreeTypeFontGenerator.FreeTypeFontParameter normalParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        normalParam.size = (int)(24 * SCALE_FACTOR);
        normalParam.color = Color.WHITE;
        normalParam.borderWidth = 1;
        normalParam.borderColor = Color.BLACK;
        normalFont = generator.generateFont(normalParam);


        FreeTypeFontGenerator.FreeTypeFontParameter resultParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        resultParam.size = (int)(32 * SCALE_FACTOR);
        resultParam.color = Color.YELLOW;
        resultParam.borderWidth = 2;
        resultParam.borderColor = Color.BLACK;
        resultFont = generator.generateFont(resultParam);

        generator.dispose();
    }

    private void updatePosition() {

        float PADDING_X = 50 * SCALE_FACTOR;
        float PADDING_Y = 50 * SCALE_FACTOR;


        BAR_X = PADDING_X;
        BAR_Y = Gdx.graphics.getHeight() - BAR_HEIGHT - PADDING_Y;


        float INFO_BOX_WIDTH = 400 * SCALE_FACTOR;
        float INFO_BOX_HEIGHT = 150 * SCALE_FACTOR;
        INFO_BOX_X = BAR_X + BAR_WIDTH + (50 * SCALE_FACTOR);
        INFO_BOX_Y = BAR_Y + (BAR_HEIGHT / 2) - (INFO_BOX_HEIGHT / 2);


        float TITLE_BOX_WIDTH = 600 * SCALE_FACTOR;
        float TITLE_BOX_HEIGHT = 60 * SCALE_FACTOR;
        TITLE_BOX_X = BAR_X;
        TITLE_BOX_Y = BAR_Y + BAR_HEIGHT + (20 * SCALE_FACTOR);
    }

    public void startGame() {
        resetGame();
        gameController.setFishingMiniGameActive(true);


        updatePosition();
    }

    private void resetGame() {
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

        // change the difficultyFactor for easy
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        difficultyFactor = 0.5f;

        Season currentSeason = App.getInstance().getCurrentGame().getGameTime().getSeason();
        List<FishType> seasonalFishes = java.util.Arrays.stream(FishType.values())
            .filter(fish -> fish.getSeason() == currentSeason)
            .collect(Collectors.toList());

        Random random = new Random();
        caughtFishType = seasonalFishes.get(random.nextInt(seasonalFishes.size()));
    }

    public void update(SpriteBatch batch, float delta) {
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

        batch.end();

        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(0.3f, 0.3f, 0.3f, 0.9f));
        float boxWidth = 550 * SCALE_FACTOR;
        float boxHeight = 200 * SCALE_FACTOR;
        float boxX = Gdx.graphics.getWidth() / 2f - boxWidth / 2f;
        float boxY = Gdx.graphics.getHeight() / 2f - boxHeight / 2f;
        shapeRenderer.rect(boxX - (5 * SCALE_FACTOR), boxY - (5 * SCALE_FACTOR), boxWidth + (10 * SCALE_FACTOR), boxHeight + (10 * SCALE_FACTOR));

        shapeRenderer.setColor(new Color(0.1f, 0.1f, 0.2f, 0.85f));
        shapeRenderer.rect(boxX, boxY, boxWidth, boxHeight);

        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

        batch.begin();

        if (!resultMessage.isEmpty()) {

            GlyphLayout layout = new GlyphLayout(resultFont, resultMessage);
            float textX = Gdx.graphics.getWidth() / 2f - layout.width / 2f;
            float textY = Gdx.graphics.getHeight() / 2f + layout.height / 2f;

            resultFont.draw(batch, resultMessage, textX, textY);
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


        fishY = MathUtils.clamp(fishY, BAR_Y + FISH_HEIGHT / 2, BAR_Y + BAR_HEIGHT - FISH_HEIGHT / 2);


        float bobberSpeed = 200 * SCALE_FACTOR;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            bobberY += bobberSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            bobberY -= bobberSpeed * delta;
        }

        bobberY = MathUtils.clamp(bobberY, BAR_Y + GREEN_BAR_HEIGHT / 2, BAR_Y + BAR_HEIGHT - GREEN_BAR_HEIGHT / 2);


        boolean fishInGreenZone = Math.abs(fishY - bobberY) < GREEN_BAR_HEIGHT / 2;


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
    }

    private void renderMiniGame(SpriteBatch batch) {

        batch.setColor(1, 1, 1, 1);
        batch.draw(assets.getBackgroundTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(Color.WHITE);
        batch.end();

        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(0.1f, 0.1f, 0.2f, 0.7f));
        float infoBoxWidth = 400 * SCALE_FACTOR;
        float infoBoxHeight = 150 * SCALE_FACTOR;
        shapeRenderer.rect(INFO_BOX_X, INFO_BOX_Y, infoBoxWidth, infoBoxHeight);

        shapeRenderer.setColor(new Color(0.2f, 0.2f, 0.4f, 0.8f));
        float titleBoxWidth = 500 * SCALE_FACTOR;
        float titleBoxHeight = 60 * SCALE_FACTOR;
        shapeRenderer.rect(TITLE_BOX_X + 140, TITLE_BOX_Y - 70, titleBoxWidth, titleBoxHeight);

        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

        batch.begin();

        batch.draw(assets.getFishingBarTexture(), BAR_X, BAR_Y, BAR_WIDTH, BAR_HEIGHT);

        batch.draw(assets.getGreenBarTexture(), BAR_X + (BAR_WIDTH - GREEN_BAR_WIDTH) / 2 + 8, bobberY - GREEN_BAR_HEIGHT / 2, GREEN_BAR_WIDTH, GREEN_BAR_HEIGHT);

        if(caughtFishType.equals(FishType.LEGEND) ||
        caughtFishType.equals(FishType.GLACIERFISH) ||
        caughtFishType.equals(FishType.ANGLER) ||
        caughtFishType.equals(FishType.CRIMSONFISH)){
            batch.draw(assets.getLegendryFishTexture(), BAR_X + (BAR_WIDTH - FISH_WIDTH) / 2 + 5, fishY - FISH_HEIGHT / 2, FISH_WIDTH, FISH_HEIGHT);
        }
        else
            batch.draw(assets.getFishTexture(), BAR_X + (BAR_WIDTH - FISH_WIDTH) / 2 + 5, fishY - FISH_HEIGHT / 2, FISH_WIDTH, FISH_HEIGHT);

        float progressBarWidth = 10 * SCALE_FACTOR;
        float progressBarX = BAR_X + BAR_WIDTH + (15 * SCALE_FACTOR) - 59;
        float progressBarHeight = (catchProgress / MAX_PROGRESS) * BAR_HEIGHT;
        Color progressColor = isPerfectCatch ? Color.GREEN : Color.RED;
        batch.setColor(progressColor);
        batch.draw(assets.getBobberTexture(), progressBarX, BAR_Y, progressBarWidth, progressBarHeight);
        batch.setColor(Color.WHITE);


        GlyphLayout titleLayout = new GlyphLayout(titleFont, "Fishing Challenge");
        titleFont.draw(batch, "Fishing Challenge", TITLE_BOX_X + (titleBoxWidth - titleLayout.width) / 2 + 150, TITLE_BOX_Y - 70 + (titleBoxHeight + titleLayout.height) / 2);


        normalFont.draw(batch, "Press UP/DOWN to move bobber", INFO_BOX_X + (20 * SCALE_FACTOR), INFO_BOX_Y + (120 * SCALE_FACTOR));
        normalFont.draw(batch, "Keep fish in the green zone", INFO_BOX_X + (20 * SCALE_FACTOR), INFO_BOX_Y + (80 * SCALE_FACTOR));
        normalFont.draw(batch, "Press Q to quit", INFO_BOX_X + (20 * SCALE_FACTOR), INFO_BOX_Y + (40 * SCALE_FACTOR));

        if (isPerfectCatch && !isGameOver && catchProgress > 0) {
            titleFont.setColor(Color.GOLD);
            GlyphLayout perfectLayout = new GlyphLayout(titleFont, "PERFECT!");
            titleFont.draw(batch, "PERFECT!", INFO_BOX_X + (infoBoxWidth - perfectLayout.width) / 2, INFO_BOX_Y + infoBoxHeight + (10 * SCALE_FACTOR));
            titleFont.setColor(Color.WHITE);
        }
    }

    private void catchFish() {
        isGameOver = true;
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();


        player.setFishingAbility(player.getFishingAbility() + 5);

        Fish caughtFish = new Fish(caughtFishType, 1);

        boolean added = player.getInventory().getBackPack().addItem(caughtFish);

        Quality fishQuality = caughtFish.getQuality();
        if (isPerfectCatch) {
            if (fishQuality.equals(Quality.STEEL)) {
                fishQuality = Quality.GOLD;
            } else if (fishQuality.equals(Quality.GOLD)) {
                fishQuality = Quality.IRIDIUM;
            } else {
                fishQuality = Quality.STEEL;
            }

            // 2.4 برابر شدن توانایی ماهیگیری
            player.setFishingAbility((int) (player.getFishingAbility() * 2.4));
        }
        else
            player.setFishingAbility(player.getFarmingAbility() + 10);

        if (added) {
            if (isPerfectCatch) {
                resultMessage = "Perfect! You caught a " + fishQuality.toString() + " " + caughtFishType.getName();
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
        if (titleFont != null) {
            titleFont.dispose();
        }
        if (normalFont != null) {
            normalFont.dispose();
        }
        if (resultFont != null) {
            resultFont.dispose();
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }

    public void handleKeyDown(int keycode) {
        if (keycode == Input.Keys.Q) {
            exitMiniGame();
        }
    }


    public void resize(int width, int height) {
        updatePosition();
    }
}
