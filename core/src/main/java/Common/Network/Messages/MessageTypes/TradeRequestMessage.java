package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class TradeRequestMessage extends Message {

    private final String sender;
    private final String receiver;

    public TradeRequestMessage(String sender, String receiver) {
        super(MessageType.TRADE_REQUEST);
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
