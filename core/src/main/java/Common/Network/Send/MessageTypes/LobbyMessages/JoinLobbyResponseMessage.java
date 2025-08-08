package Common.Network.Send.MessageTypes.LobbyMessages;

import Common.Network.Send.Message;

import java.util.ArrayList;

public class JoinLobbyResponseMessage extends Message {
    private boolean success;
    private String errorMessage;
    private String lobbyId;
    private String lobbyName;
    private ArrayList<String> players;
    private boolean isAdmin;

    public JoinLobbyResponseMessage(boolean success, String errorMessage, String lobbyId,
                                    String lobbyName, ArrayList<String> players, boolean isAdmin) {
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

    public ArrayList<String> getPlayers() {
        return players;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
