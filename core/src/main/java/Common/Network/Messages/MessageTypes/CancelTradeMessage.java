package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class CancelTradeMessage extends Message {

    private final String sender;
    private final String receiver;

    public CancelTradeMessage(String sender, String receiver) {
        super(MessageType.CANCEL_TRADING);
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
