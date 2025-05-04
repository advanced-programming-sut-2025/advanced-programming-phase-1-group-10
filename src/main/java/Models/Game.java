package Models;


import Models.DateTime.DateTime;
import Models.PlayerStuff.Player;
import Models.Weather.Weather;

import java.util.ArrayList;

public class Game {

    private DateTime gameTime;
    private ArrayList<Player> players;
    private Map gameMap;

    private Player currentPlayer;

    private Weather weather;
    private Weather nextDayWeather;


    public Weather getWeather() {
        return weather;
    }

    public Weather getNextDayWeather() {
        return nextDayWeather;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
