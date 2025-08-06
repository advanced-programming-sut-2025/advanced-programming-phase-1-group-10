package Server.Controllers.FinalControllers;

import Common.Models.App;
import Common.Models.DateTime.DateTime;
import Common.Models.DateTime.Season;
import Common.Models.PlayerStuff.Player;
import Common.Models.Weather.Weather;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.Color;

public class BarController {

    private final TextureRegion backgroundRegion;
    private final BitmapFont font;

    private final int uiWidth;
    private final int uiHeight;

    private final Texture springIcon = new Texture("Clock/season/spring.png");
    private final Texture summerIcon = new Texture("Clock/season/summer.png");
    private final Texture fallIcon = new Texture("Clock/season/fall.png");
    private final Texture winterIcon = new Texture("Clock/season/winter.png");

    private final Texture sunnyIcon = new Texture("Clock/weather/sunny.png");
    private final Texture rainyIcon = new Texture("Clock/weather/rainy.png");
    private final Texture stormIcon = new Texture("Clock/weather/storm.png");
    private final Texture snowIcon = new Texture("Clock/weather/snow.png");

    public BarController() {
        Texture texture = new Texture("Clock/Clock.png");
        this.backgroundRegion = new TextureRegion(texture);

        // === Generate high-quality font from .ttf ===
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
            Gdx.files.internal("font/mainFont.ttf") // Path to your font
        );
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 34;
        params.color = Color.BROWN;
        params.minFilter = Texture.TextureFilter.Nearest;
        params.magFilter = Texture.TextureFilter.Nearest;
        this.font = generator.generateFont(params);
        generator.dispose();

        this.uiWidth = backgroundRegion.getRegionWidth();
        this.uiHeight = backgroundRegion.getRegionHeight();
    }

    public void update(SpriteBatch batch, int screenWidth, int screenHeight) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();

        int x = screenWidth - uiWidth - 10;
        int y = screenHeight - uiHeight - 10;

        batch.draw(backgroundRegion, x, y);

        DateTime dateTime = App.getInstance().getCurrentGame().getGameTime();
        String timeStr = dateTime.getHour() + ":00";
        String date = dateTime.getYear() + "-" + dateTime.getMonth() + "-" + dateTime.getDay();
        String moneyStr = String.valueOf(player.getGold()).replaceAll(".", "$0 ").trim();

        font.draw(batch, timeStr, x + 160, y + 215);
        font.draw(batch, date, x + 130, y + 120);
        font.draw(batch, moneyStr, x + 69, y + 42);

        batch.draw(getSeasonTexture(dateTime.getSeason()), x + 120, y + 137, 60, 40);
        batch.draw(getWeatherTexture(App.getInstance().getCurrentGame().getWeather()), x + 190, y + 137, 60, 40);
    }

    public void dispose() {
        font.dispose();
        backgroundRegion.getTexture().dispose();
    }

    private Texture getSeasonTexture(Season season) {
        return switch (season) {
            case SPRING -> springIcon;
            case SUMMER -> summerIcon;
            case FALL -> fallIcon;
            case WINTER -> winterIcon;
        };
    }

    private Texture getWeatherTexture(Weather weather) {
        return switch (weather) {
            case SUNNY -> sunnyIcon;
            case SNOW -> snowIcon;
            case RAIN -> rainyIcon;
            case STORM -> stormIcon;
        };
    }
}
