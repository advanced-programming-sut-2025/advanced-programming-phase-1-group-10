package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class HoeUsedMessage extends Message {
    private int x, y;
    private boolean plowed;
    private int newEnergy;

    public HoeUsedMessage() {
        super(MessageType.HOE_USED);
    }

    public HoeUsedMessage(int x, int y, boolean plowed) {
        super(MessageType.HOE_USED);
        this.x = x;
        this.y = y;
        this.plowed = plowed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isPlowed() {
        return plowed;
    }

    public void setPlowed(boolean plowed) {
        this.plowed = plowed;
    }

    public int getNewEnergy() {
        return newEnergy;
    }

    public void setNewEnergy(int newEnergy) {
        this.newEnergy = newEnergy;
    }
}
