package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class TradeRequestResponseMessage extends Message {

    private final String sender;
    private final String reciever;
    private final boolean isAccepted;

    public TradeRequestResponseMessage(String sender, String reciever, boolean isAccepted) {
        super(MessageType.TRADE_REQUEST_RESPONSE);
        this.sender = sender;
        this.reciever = reciever;
        this.isAccepted = isAccepted;
    }

    public String getSender() {
        return sender;
    }

    public String getReciever() {
        return reciever;
    }

    public boolean isAccepted() {
        return isAccepted;
    }
}
