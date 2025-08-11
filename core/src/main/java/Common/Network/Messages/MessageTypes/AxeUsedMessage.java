package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class AxeUsedMessage extends Message {
    private int x, y;

    public AxeUsedMessage(int x, int y){
        super(MessageType.AXE_USED);
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
