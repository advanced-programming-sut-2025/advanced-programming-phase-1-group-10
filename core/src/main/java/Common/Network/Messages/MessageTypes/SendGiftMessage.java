package Common.Network.Messages.MessageTypes;

import Common.Models.FriendShip.Gift;
import Common.Models.Item;
import Common.Network.Messages.Message;

public class SendGiftMessage extends Message {

    private final String senderName;
    private final String recieverName;
    private final String itemName;
    private final int ItemNumber;
    private final int giftSeed;

    public SendGiftMessage(String senderName, String recieverName, String itemName, int ItemNumber, int giftSeed) {
        super(MessageType.SEND_GIFT);
        this.senderName = senderName;
        this.recieverName = recieverName;
        this.itemName = itemName;
        this.ItemNumber = ItemNumber;
        this.giftSeed = giftSeed;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getRecieverName() {
        return recieverName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemNumber() {
        return ItemNumber;
    }

    public int getGiftSeed() {
        return giftSeed;
    }
}
