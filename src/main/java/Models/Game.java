package Models;


import Models.DateTime.DateTime;
import Models.DateTime.Season;
import Models.PlayerStuff.Player;
import Models.Weather.Weather;

import java.util.ArrayList;

public class Game {

    private String gameOwner;

    private DateTime gameTime;
    private final ArrayList<Player> players = new ArrayList<>();
    private Map gameMap;

    private Player currentPlayer;

    private Weather weather;
    private Weather nextDayWeather;

    public Game(String gameOwner) {
        this.gameOwner = gameOwner;
        this.gameTime = new DateTime(Season.SPRING,2000,1,1,9);
        this.weather = Weather.SUNNY;
        this.nextDayWeather = Weather.SUNNY;
        this.gameMap = new Map();
    }

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

    public DateTime getGameTime() {
        return gameTime;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Map getGameMap() {
        return gameMap;
    }
}
