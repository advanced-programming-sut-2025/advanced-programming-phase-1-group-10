package src.Models.Weather;

import src.Models.App;
import src.Models.Result;
import src.Models.Tile;

public class WeatherManagement {

    private static WeatherManagement instance;

    private WeatherManagement() {

    }

    public static WeatherManagement getInstance() {
        if(instance == null) {
            instance = new WeatherManagement();
        }
        return instance;
    }

    public void doLightning(Tile tile) {

    }

    public Result showWeather() {
        return new Result(true, "Game weather: " + App.getInstance().getCurrentGame().getWeather().getName());
    }

    public Result showWeatherForecast() {
        return new Result(true, "Weather forecast: " + App.getInstance().getCurrentGame().getNextDayWeather().getName());
    }


}
