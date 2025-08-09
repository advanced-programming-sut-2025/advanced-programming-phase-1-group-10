package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class GiveBouquetMessage extends Message {

    private final String senderName;
    private final String receiverName;

    public GiveBouquetMessage(String senderName, String receiverName) {
        super(MessageType.GIVE_BOUQUET);
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }
}
