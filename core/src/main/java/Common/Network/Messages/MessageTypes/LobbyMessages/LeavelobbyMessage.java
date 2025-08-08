package Common.Network.Messages.MessageTypes.LobbyMessages;

import Common.Network.Messages.Message;

public class LeavelobbyMessage extends Message {
    private String username;
    private String lobbyId;

    public LeavelobbyMessage(String username, String lobbyId) {
        super(MessageType.LEAVE_LOBBY);
        this.username = username;
        this.lobbyId = lobbyId;
    }

    public String getUsername() {
        return username;
    }

    public String getLobbyId() {
        return lobbyId;
    }
}
