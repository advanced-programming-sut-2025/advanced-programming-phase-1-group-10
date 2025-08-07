package Common.Network.Send.MessageTypes;

import Common.Models.Lobby;
import Common.Models.PlayerStuff.Player;
import Common.Network.Send.Message;

import java.util.ArrayList;

public class JoinLobbyResponseMessage extends Message {
    private boolean success;
    private String errorMessage;
    private String lobbyId;
    private String lobbyName;
    private ArrayList<Player> players;
    private boolean isAdmin;

    public JoinLobbyResponseMessage(boolean success, String errorMessage, String lobbyId,
                                   String lobbyName, ArrayList<Player> players, boolean isAdmin) {
        super(MessageType.JOIN_LOBBY_RESPONSE);
        this.success = success;
        this.errorMessage = errorMessage;
        this.lobbyId = lobbyId;
        this.lobbyName = lobbyName;
        this.players = players;
        this.isAdmin = isAdmin;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
