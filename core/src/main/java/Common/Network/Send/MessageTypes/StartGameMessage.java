package Common.Network.Send.MessageTypes;

import Common.Network.Send.Message;

public class StartGameMessage extends Message {
    private String username;

    public StartGameMessage() {
        super(MessageType.START_GAME);
    }

    public StartGameMessage(String username) {
        super(MessageType.START_GAME);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
