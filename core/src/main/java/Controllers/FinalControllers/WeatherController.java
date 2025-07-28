package Controllers.FinalControllers;

import Assets.WeatherAsset;
import Models.App;
import Models.Map;
import Models.Weather.Weather;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class WeatherController {

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
    private final int PARTICLE_COUNT = 100;

    public void update(SpriteBatch batch) {
        Weather weather = App.getInstance().getCurrentGame().getWeather();
        String weatherType = weather.getName().toLowerCase(); // "snow", "rain", etc.

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

        // ✅ Draw weather particles every frame
        float delta = Gdx.graphics.getDeltaTime();
        for (Particle p : particles) {
            p.update(delta);
            batch.draw(p.texture, p.x, p.y);
        }

        // ✅ Draw night overlay after particles every frame
        if (weatherType.equals("snow") || weatherType.equals("rain")) {
            batch.setColor(0f, 0f, 0f, 0.2f);  // dark translucent overlay
            batch.draw(weatherAsset.getNightOverlay(), 0, 0, Map.mapWidth * Map.tileSize, Map.mapHeight * Map.tileSize);
            batch.setColor(1f, 1f, 1f, 1f); // reset to default after drawing overlay
        }
    }

}

