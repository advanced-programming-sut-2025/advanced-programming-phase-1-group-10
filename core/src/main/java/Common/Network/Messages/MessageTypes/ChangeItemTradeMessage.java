package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class ChangeItemTradeMessage extends Message {
    private final String sender;
    private final String receiver;
    private final String item;
    private final int number;

    public ChangeItemTradeMessage(String sender, String receiver, String item, int number) {
        super(MessageType.CHANGE_ITEMS_SLOT);
        this.sender = sender;
        this.receiver = receiver;
        this.item = item;
        this.number = number;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getItem() {
        return item;
    }
    public int getNumber() {
        return number;
    }
}
