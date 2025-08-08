package Common.Network.Send.MessageTypes.LobbyMessages;

import Common.Network.Send.Message;

public class ListLobbiesRequestMessage extends Message {
    private String username;

    public ListLobbiesRequestMessage(String username) {
        super(MessageType.LIST_LOBBIES_REQUEST);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
