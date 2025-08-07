package Common.Network.Send.MessageTypes;

import Common.Models.PlayerStuff.Player;
import Common.Network.Send.Message;

import java.util.ArrayList;
import java.util.Map;

public class LobbyUpdateMessage extends Message {
    private String lobbyId;
    private ArrayList<Player> players;
    private Map<Player, Boolean> readyStatus;

    public LobbyUpdateMessage(String lobbyId, ArrayList<Player> players, Map<Player, Boolean> readyStatus) {
        super(MessageType.LOBBY_UPDATE);
        this.lobbyId = lobbyId;
        this.players = players;
        this.readyStatus = readyStatus;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Map<Player, Boolean> getReadyStatus() {
        return readyStatus;
    }
}
