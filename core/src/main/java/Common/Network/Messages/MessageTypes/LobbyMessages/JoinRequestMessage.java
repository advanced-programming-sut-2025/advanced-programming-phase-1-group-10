package Common.Network.Messages.MessageTypes.LobbyMessages;

import Common.Network.Messages.Message;

public class JoinRequestMessage extends Message {
    private String username;
    private String password; // Optional

    public JoinRequestMessage() {
        super(MessageType.JOIN_REQUEST);
    }

    public JoinRequestMessage(String username, String password) {
        super(MessageType.JOIN_REQUEST);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
