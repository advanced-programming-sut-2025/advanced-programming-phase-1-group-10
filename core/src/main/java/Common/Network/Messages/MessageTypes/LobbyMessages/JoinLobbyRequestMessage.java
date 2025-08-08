package Common.Network.Messages.MessageTypes.LobbyMessages;

import Common.Network.Messages.Message;

public class JoinLobbyRequestMessage extends Message {
    private String lobbyId;
    private String username;
    private String password;

    public JoinLobbyRequestMessage(String lobbyId, String username, String password) {
        super(MessageType.JOIN_LOBBY_REQUEST);
        this.lobbyId = lobbyId;
        this.username = username;
        this.password = password;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
