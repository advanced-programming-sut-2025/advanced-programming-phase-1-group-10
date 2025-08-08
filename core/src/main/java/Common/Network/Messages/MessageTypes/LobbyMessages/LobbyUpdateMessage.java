package Common.Network.Messages.MessageTypes.LobbyMessages;

import Common.Network.Messages.Message;

import java.util.ArrayList;
import java.util.Map;

public class LobbyUpdateMessage extends Message {
    private String lobbyId;
    private ArrayList<String> players;
    private Map<String, Boolean> readyStatus;
    private Map<String, String> farmTypes;

    public LobbyUpdateMessage(String lobbyId, ArrayList<String> players, Map<String, Boolean> readyStatus) {
        super(MessageType.LOBBY_UPDATE);
        this.lobbyId = lobbyId;
        this.players = players;
        this.readyStatus = readyStatus;

    }

    public String getLobbyId() {
        return lobbyId;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public Map<String, Boolean> getReadyStatus() {
        return readyStatus;
    }

    public Map<String, String> getFarmTypes() {
        return farmTypes;
    }


    public void setFarmTypes(Map<String, String> farmTypes) {
        this.farmTypes = farmTypes;
    }
}
