package Common.Network.Messages.MessageTypes;

import Common.Models.Item;
import Common.Network.Messages.Message;

public class SeythUsedMessage extends Message {
    private int x, y;
    private Item tileItem;

    public SeythUsedMessage(int x, int y, Item item){
        super(MessageType.SEYTH_USED);
        this.x = x;
        this.y = y;
        this.tileItem = item;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Item getTileItem() {
        return tileItem;
    }
}
