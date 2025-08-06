package Server.Controllers.FinalControllers;

import Client.Assets.ThunderAsset;
import Client.Assets.WeatherAsset;
import Common.Models.App;
import Common.Models.Map;
import Common.Models.Weather.Weather;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class WeatherController {

    private final ThunderAsset thunderAsset = new ThunderAsset();
    private final ArrayList<Float> thunderXs = new ArrayList<>();
    private final ArrayList<Float> thunderYs = new ArrayList<>();
    private final ArrayList<Float> thunderStateTimes = new ArrayList<>();
    private final ArrayList<Boolean> thunderFinished = new ArrayList<>();
    private float timeUntilNextThunder = 0f;

    private static class Particle {
        float x, y, speedY;
        Texture texture;

        public Particle(float x, float y, float speedY, Texture texture) {
            this.x = x;
            this.y = y;
            this.speedY = speedY;
            this.texture = texture;
        }

        public void update(float deltaTime) {
            y -= speedY * deltaTime;
            if (y < -texture.getHeight()) {
                y = Gdx.graphics.getHeight() + MathUtils.random(0, 100);
                x = MathUtils.random(0, Gdx.graphics.getWidth());
            }
        }
    }

    private final WeatherAsset weatherAsset = new WeatherAsset();
    private final ArrayList<Particle> particles = new ArrayList<>();

    private String lastWeatherType = "";
    private static final int PARTICLE_COUNT = 100;

    public void update(SpriteBatch batch) {
        Weather weather = App.getInstance().getCurrentGame().getWeather();
        String weatherType = weather.getName().toLowerCase(); // "snow", "rain", "storm", etc.

        // If weather changed, regenerate particles
        if (!weatherType.equals(lastWeatherType)) {
            particles.clear();
            ArrayList<Texture> textures = null;

            if (weatherType.equals("snow")) {
                textures = weatherAsset.getSnowParticles();
            } else if (weatherType.equals("rain")) {
                textures = weatherAsset.getRainParticles();
            }

            if (textures != null) {
                for (int i = 0; i < PARTICLE_COUNT; i++) {
                    Texture texture = textures.get(MathUtils.random(textures.size() - 1));
                    float x = MathUtils.random(0, Gdx.graphics.getWidth());
                    float y = MathUtils.random(0, Gdx.graphics.getHeight());
                    float speedY = weatherType.equals("snow") ? MathUtils.random(30f, 70f) : MathUtils.random(200f, 400f);
                    particles.add(new Particle(x, y, speedY, texture));
                }
            }

            lastWeatherType = weatherType;
        }

        float delta = Gdx.graphics.getDeltaTime();

        // Draw weather particles
        for (Particle p : particles) {
            p.update(delta);
            batch.draw(p.texture, p.x, p.y);
        }

        // Automatic thunder spawn only during actual storm
        if (weatherType.equals("storm")) {
            timeUntilNextThunder -= delta;
            if (timeUntilNextThunder <= 0f) {
                timeUntilNextThunder = MathUtils.random(0.5f, 2f);
                float x = MathUtils.random(0, Gdx.graphics.getWidth());
                float y = MathUtils.random(Gdx.graphics.getHeight() * 0.5f, Gdx.graphics.getHeight());
                thunderXs.add(x);
                thunderYs.add(y);
                thunderStateTimes.add(0f);
                thunderFinished.add(false);
                // Optional: trigger bright flash or sound here
            }
        }

        // Always update & draw any active thunder instances (cheat or auto)
        if (!thunderXs.isEmpty()) {
            Animation<TextureRegion> thunderAnim = thunderAsset.getAnimation();
            for (int i = 0; i < thunderXs.size(); i++) {
                if (thunderFinished.get(i)) continue;
                float st = thunderStateTimes.get(i) + delta;
                thunderStateTimes.set(i, st);
                if (thunderAnim.isAnimationFinished(st)) {
                    thunderFinished.set(i, true);
                    continue;
                }
                TextureRegion frame = thunderAnim.getKeyFrame(st);
                float drawX = thunderXs.get(i) - frame.getRegionWidth() / 2f;
                float drawY = thunderYs.get(i) - frame.getRegionHeight() / 2f;
                batch.draw(frame, drawX, drawY);
            }

            // Cleanup finished thunders
            for (int i = thunderXs.size() - 1; i >= 0; i--) {
                if (thunderFinished.get(i)) {
                    thunderXs.remove(i);
                    thunderYs.remove(i);
                    thunderStateTimes.remove(i);
                    thunderFinished.remove(i);
                }
            }
        }

        // Night overlay (applies to snow/rain/storm)
        if (weatherType.equals("snow") || weatherType.equals("rain") || weatherType.equals("storm")) {
            batch.setColor(0f, 0f, 0f, 0.2f);
            batch.draw(weatherAsset.getNightOverlay(), 0, 0, Map.mapWidth * Map.tileSize, Map.mapHeight * Map.tileSize);
            batch.setColor(1f, 1f, 1f, 1f);
        }
    }


    /** External trigger (e.g., from cheat) to spawn thunder at a given screen/world position */
    public void spawnThunderAt(float screenX, float screenY) {
        thunderXs.add(screenX);
        thunderYs.add(screenY);
        thunderStateTimes.add(0f);
        thunderFinished.add(false);
    }
}

