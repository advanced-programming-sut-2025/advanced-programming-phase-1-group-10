package Common.Network.Messages.MessageTypes.LobbyMessages;

import Common.Network.Messages.Message;

public class AskMarriageMessage extends Message {

    private final String sender;
    private final String receiver;

    public AskMarriageMessage(String sender, String receiver) {
        super(MessageType.ASK_MARARIAGE);
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
