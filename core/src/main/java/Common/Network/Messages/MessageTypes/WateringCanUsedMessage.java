package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class WateringCanUsedMessage extends Message {

    private final int x, y;
    private final boolean watered;

    public WateringCanUsedMessage(int x, int y, boolean watered) {
        super(MessageType.WATERING_CAN_USED);
        this.x = x;
        this.y = y;
        this.watered = watered;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWatered() {
        return watered;
    }
}
