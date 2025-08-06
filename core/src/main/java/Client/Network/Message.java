package Client.Network;

public abstract class Message {
    protected MessageType type;

    public Message(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public enum MessageType {
        MOVE_PLAYER,
        ;
    }
}


