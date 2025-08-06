package Common.Models;

import Common.Models.PlayerStuff.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lobby {
    private final String lobbyId;
    private final String name;
    private ArrayList<Player> players ;
    private Map<Player, Boolean> playersReadyStatus;
    private boolean isPrivate;
    private String password;
    private boolean isVisible;

    public Lobby(String lobbyId, String name){
        this.lobbyId = lobbyId;
        this.name = name;
        this.players = new ArrayList<>();
        this.playersReadyStatus = new HashMap<>();
        this.isPrivate = false;
        this.isVisible = true;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Map<Player, Boolean> getPlayersReadyStatus() {
        return playersReadyStatus;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getPassword() {
        return password;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
