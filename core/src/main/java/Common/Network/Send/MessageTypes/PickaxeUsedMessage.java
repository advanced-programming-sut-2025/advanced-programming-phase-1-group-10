package Common.Network.Send.MessageTypes;

import Common.Network.Send.Message;

public class PickaxeUsedMessage extends Message {
    private int x, y;
    private boolean plowed;
    private int newEnergy;
    private int newXP;

    public PickaxeUsedMessage() {
        super(MessageType.TOOL_USED);
    }

    public PickaxeUsedMessage(int x, int y, boolean plowed, int newEnergy, int newXP) {
        super(MessageType.TOOL_USED);
        this.x = x;
        this.y = y;
        this.plowed = plowed;
        this.newEnergy = newEnergy;
        this.newXP = newXP;
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

    public int getNewXP() {
        return newXP;
    }

    public void setNewXP(int newXP) {
        this.newXP = newXP;
    }
}
