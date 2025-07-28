package Models.Weather;

import Models.App;
import Models.Result;
import Models.Tile;

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

    public static Result showWeather() {
        return new Result(true, "Game weather: " + App.getInstance().getCurrentGame().getWeather().getName());
    }

    public static Result showWeatherForecast() {
        return new Result(true, "Weather forecast: " + App.getInstance().getCurrentGame().getNextDayWeather().getName());
    }


}
