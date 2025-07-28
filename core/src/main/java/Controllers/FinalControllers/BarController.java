package Controllers.FinalControllers;

import Models.App;
import Models.DateTime.DateTime;
import Models.DateTime.Season;
import Models.PlayerStuff.Player;
import Models.Weather.Weather;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private final Texture snowIcon =  new Texture("Clock/weather/snow.png");

    public BarController() {
        Texture texture = new Texture("Clock/Clock.png"); // Make sure path is correct
        this.backgroundRegion = new TextureRegion(texture);
        this.font = new BitmapFont(); // Replace with pixel-style .fnt for final polish
        this.font.setColor(Color.BLACK); // Matches Stardew's dark UI text

        this.uiWidth = backgroundRegion.getRegionWidth();   // 236
        this.uiHeight = backgroundRegion.getRegionHeight(); // 288
    }

    public void update(SpriteBatch batch, int screenWidth, int screenHeight) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();

        int x = screenWidth - uiWidth - 10;
        int y = screenHeight - uiHeight - 10;

        font.getData().setScale(2f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        batch.draw(backgroundRegion, x, y);

        DateTime dateTime = App.getInstance().getCurrentGame().getGameTime();
        String timeStr = dateTime.getHour() + ":00";
        String date = dateTime.getYear() + "-" + dateTime.getMonth() + "-" + dateTime.getDay();
        String moneyStr = String.valueOf(player.getGold()).replaceAll(".", "$0 ").trim();

        // Now, tune each line individually:
        // These offsets are from bottom-left of the image (x, y)

        font.draw(batch, timeStr, x + 160, y + 215);
        font.draw(batch, date, x + 130, y + 120);
        font.draw(batch, moneyStr, x + 69, y + 42);

        batch.draw(getSeasonTexture(App.getInstance().getCurrentGame().getGameTime().getSeason()), x + 120, y + 137,60,40);
        batch.draw(getWeatherTexture(App.getInstance().getCurrentGame().getWeather()),x + 190, y + 137,60,40);
    }



    private String formatTime(int hour24) {
        int hour12 = (hour24 == 0 || hour24 == 12) ? 12 : hour24 % 12;
        String ampm = (hour24 < 12) ? "AM" : "PM";
        return hour12 + ":00 " + ampm;
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
