package Client.Assets;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;

public class WeatherAsset {

    private final ArrayList<Texture> rainParticles;
    private final ArrayList<Texture> snowParticles;

    private final Texture nightOverlay;

    public WeatherAsset() {
        this.rainParticles = new ArrayList<>(Arrays.asList(
            new Texture("weather/rainy/1.png"),
            new Texture("weather/rainy/2.png")
        ));
        this.snowParticles = new ArrayList<>(Arrays.asList(
            new Texture("weather/snow/1.png"),
            new Texture("weather/snow/2.png"),
            new Texture("weather/snow/3.png")
        ));

        this.nightOverlay = new Texture("ui/black.png"); // 1x1 black pixel texture

    }

    public ArrayList<Texture> getRainParticles() {
        return rainParticles;
    }

    public ArrayList<Texture> getSnowParticles() {
        return snowParticles;
    }

    public Texture getNightOverlay() {
        return nightOverlay;
    }
}
