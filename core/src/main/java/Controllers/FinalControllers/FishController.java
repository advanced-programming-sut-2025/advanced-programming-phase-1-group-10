package Controllers.FinalControllers;

import Assets.FishAsset;
import Models.Animal.FishType;
import Models.App;
import Models.DateTime.Season;
import Models.Farm;
import Models.Game;
import Models.Place.Lake;
import Models.Place.Place;
import Models.Position;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FishController implements Disposable {
    private final FishAsset fishAsset;
    private final List<LakeFishGroup> lakeFishGroups;
    private float updateTimer = 0;
    private final float UPDATE_INTERVAL = 0.5f;
    private Season lastSeason;

    private static class LakeFishGroup {
        Lake lake;
        Array<FishData> fishes;

        LakeFishGroup(Lake lake) {
            this.lake = lake;
            this.fishes = new Array<>();
        }
    }

    private static class FishData {
        FishType type;
        Sprite sprite;
        Vector2 position;
        Vector2 direction;
        float speed;
        float rotation;

        FishData(FishType type, Sprite sprite, Vector2 position) {
            this.type = type;
            this.sprite = sprite;
            this.position = position;
            this.direction = new Vector2(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f)).nor();
            this.speed = MathUtils.random(20f, 50f);
            this.rotation = 0;
        }
    }

    public FishController(List<Lake> lakes) {
        this.fishAsset = new FishAsset();
        this.lakeFishGroups = new ArrayList<>();
        this.lastSeason = App.getInstance().getCurrentGame().getGameTime().getSeason();


        for (Lake lake : lakes) {
            LakeFishGroup lakeFishGroup = new LakeFishGroup(lake);
            populateLakeWithSeasonalFish(lake, lakeFishGroup.fishes, lastSeason);
            lakeFishGroups.add(lakeFishGroup);
        }
    }

    public static List<Lake> findAllLakes() {
        List<Lake> lakes = new ArrayList<>();
        Game game = App.getInstance().getCurrentGame();


        for (int i = 0; i < 4; i++) {
            Farm farm = game.getPlayers().get(i).getFarm();
            if (farm != null) {
                for (Place place : farm.getPlaces()) {
                    if (place instanceof Lake) {
                        lakes.add((Lake) place);
                    }
                }
            }
        }

        return lakes;
    }

    private void populateLakeWithSeasonalFish(Lake lake, Array<FishData> fishes, Season currentSeason) {
        Position lakePos = lake.getPosition();
        int lakeWidth = lake.getWidth();
        int lakeHeight = lake.getHeight();

        List<FishType> seasonalFishes = Stream.of(FishType.values())
            .filter(fishType -> fishType.getSeason() == currentSeason)
            .collect(Collectors.toList());

        for (FishType fishType : seasonalFishes) {

            int count = MathUtils.random(2, 3);
            for (int i = 0; i < count; i++) {

                float x = lakePos.getY() * 32f + MathUtils.random(lakeWidth * 32f);
                float y = lakePos.getX() * 32f + MathUtils.random(lakeHeight * 32f);

                Sprite fishSprite = fishAsset.getFishSprite(fishType);
                float scale = 0.85f;

                fishSprite.setSize(32 * scale, 32 * scale);
                fishSprite.setOriginCenter();

                fishes.add(new FishData(fishType, fishSprite, new Vector2(x, y)));
            }
        }
    }

    public void update(SpriteBatch batch, float delta) {

        Season currentSeason = App.getInstance().getCurrentGame().getGameTime().getSeason();
        if (currentSeason != lastSeason) {

            updateFishForNewSeason(currentSeason);
            lastSeason = currentSeason;
        }

        updateTimer += delta;


        if (updateTimer >= UPDATE_INTERVAL) {
            updateTimer = 0;
            for (LakeFishGroup group : lakeFishGroups) {
                updateFishPositions(group.lake, group.fishes);
            }
        }

        for (LakeFishGroup group : lakeFishGroups) {
            for (FishData fish : group.fishes) {
                fish.sprite.setPosition(fish.position.x, fish.position.y);
                fish.sprite.setRotation(fish.rotation);
                fish.sprite.draw(batch);
            }
        }
    }

    private void updateFishForNewSeason(Season newSeason) {

        for (LakeFishGroup group : lakeFishGroups) {
            group.fishes.clear();
            populateLakeWithSeasonalFish(group.lake, group.fishes, newSeason);
        }
    }

    private void updateFishPositions(Lake lake, Array<FishData> fishes) {
        Position lakePos = lake.getPosition();
        int lakeWidth = lake.getWidth();
        int lakeHeight = lake.getHeight();

        float leftBound = lakePos.getY() * 32f;
        float rightBound = lakePos.getY() * 32f + lakeWidth * 32f;
        float bottomBound = lakePos.getX() * 32f;
        float topBound = lakePos.getX() * 32f + lakeHeight * 32f;

        for (FishData fish : fishes) {

            if (MathUtils.randomBoolean(0.2f)) {
                fish.direction.set(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f)).nor();
            }

            float newX = fish.position.x + fish.direction.x * fish.speed;
            float newY = fish.position.y + fish.direction.y * fish.speed;

            if (newX < leftBound || newX > rightBound - fish.sprite.getWidth()) {
                fish.direction.x *= -1;
                newX = MathUtils.clamp(newX, leftBound, rightBound - fish.sprite.getWidth());
            }

            if (newY < bottomBound || newY > topBound - fish.sprite.getHeight()) {
                fish.direction.y *= -1;
                newY = MathUtils.clamp(newY, bottomBound, topBound - fish.sprite.getHeight());
            }

            fish.position.set(newX, newY);
            fish.rotation = fish.direction.angleDeg();

            if (fish.direction.x < 0) {
                fish.sprite.setFlip(true, false);
            } else {
                fish.sprite.setFlip(false, false);
            }
        }
    }

    @Override
    public void dispose() {
        if (fishAsset != null) {
            fishAsset.dispose();
        }
    }
}
