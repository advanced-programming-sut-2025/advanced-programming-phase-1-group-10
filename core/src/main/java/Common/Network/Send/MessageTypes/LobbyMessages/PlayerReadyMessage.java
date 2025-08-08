package Common.Network.Send.MessageTypes.LobbyMessages;

import Common.Network.Send.Message;

public class PlayerReadyMessage extends Message {
    private String username;
    private boolean isReady;

    public PlayerReadyMessage(String username, boolean isReady) {
        super(MessageType.PLAYER_READY);
        this.username = username;
        this.isReady = isReady;
    }

    public String getUsername() {
        return username;
    }

    public boolean isReady() {
        return isReady;
    }
}
