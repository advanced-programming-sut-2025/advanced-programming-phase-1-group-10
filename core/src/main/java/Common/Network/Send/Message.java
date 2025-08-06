package Common.Network.Send;

public abstract class Message {
    protected MessageType type;

    public Message(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public enum MessageType {
        JOIN_REQUEST,
        MOVE_PLAYER,
        TOOL_USED;
        ;
    }
}


