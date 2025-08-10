package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class AceeptTradeMessage extends Message {

    private final String sender;
    private final String receiver;

    public AceeptTradeMessage(String sender, String receiver) {
        super(MessageType.ACCEPT_TRADING);
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
