package Common.Network.Messages.MessageTypes.LobbyMessages;

import Common.Network.Messages.Message;

public class ResponseMarriage extends Message {

    private String sender;
    private String receiver;
    private boolean isAccepted;

    public ResponseMarriage(String sender, String receiver, boolean isAccepted) {
        super(MessageType.RESPONSE_MARARIAGE);
        this.sender = sender;
        this.receiver = receiver;
        this.isAccepted = isAccepted;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public boolean isAccepted() {
        return isAccepted;
    }
}
