package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class FertilizerUsedMessage extends Message {
    private int x, y;
    private boolean fertilized;

    public FertilizerUsedMessage(int x, int y, boolean fertilized){
        super(MessageType.FERTILIZER_USED);
        this.x = x;
        this.y = y;
        this.fertilized = fertilized;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isFertilized() {
        return fertilized;
    }
}
