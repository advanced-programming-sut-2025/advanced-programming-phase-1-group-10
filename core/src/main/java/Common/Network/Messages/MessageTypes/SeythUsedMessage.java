package Common.Network.Messages.MessageTypes;

import Common.Models.Item;
import Common.Network.Messages.Message;

public class SeythUsedMessage extends Message {
    private int x, y;

    public SeythUsedMessage(int x, int y){
        super(MessageType.SEYTH_USED);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
