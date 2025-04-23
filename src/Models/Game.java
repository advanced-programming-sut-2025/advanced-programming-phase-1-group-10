package src.Models;

import src.Models.DateTime.DateTime;
import src.Models.Weather.Weather;
import src.Models.PlayerStuff.Player;

import java.util.ArrayList;

public class Game {

    private DateTime gameTime;
    private ArrayList<Player> players;
    private Map gameMap;

    private Weather weather;
    private Weather nextDayWeather;


    public Weather getWeather() {
        return weather;
    }

    public Weather getNextDayWeather() {
        return nextDayWeather;
    }
}
