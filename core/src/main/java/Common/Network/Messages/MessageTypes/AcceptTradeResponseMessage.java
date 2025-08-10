package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class AcceptTradeResponseMessage extends Message {

    private final String sender;
    private final String reciever;
    private final boolean isAccepted;

    public AcceptTradeResponseMessage(String sender, String reciever, boolean isAccepted) {
        super(Message.MessageType.ACCEPT_TRADING_RESPONSE);
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
