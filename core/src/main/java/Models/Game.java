package Models;


import Models.Animal.Animal;
import Models.DateTime.DateTime;
import Models.DateTime.Season;
import Models.NPC.NPC;
import Models.Place.Store.Store;
import Models.PlayerStuff.Player;
import Models.Weather.Weather;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private final String gameOwner;

    private final DateTime gameTime;
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<NPC> npcs = new ArrayList<>();
    private final ArrayList<Store> stores = new ArrayList<>();
    private HashMap<String , Animal> animals = new HashMap<>();

    private final Map gameMap;

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

    public ArrayList<Store> getStores() {
        return stores;
    }

    public String getGameOwner() {
        return gameOwner;
    }

    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public void setNextDayWeather(Weather nextDayWeather) {
        this.nextDayWeather = nextDayWeather;
    }

    public Player getPlayerByName(String name){
        for(Player player : players){
            if(player.getName().equals(name)){
                return player;
            }
        }
        return null;
    }

    public HashMap<String, Animal> getAnimals() {
        return animals;
    }
}
