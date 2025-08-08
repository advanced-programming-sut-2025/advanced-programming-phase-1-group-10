package Common.Network.Messages;

import java.util.Map;

public class Message {
    private MessageType type;
    private Map<String, Object> data;

    public Message(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public enum MessageType {
        START_GAME,
        JOIN_REQUEST,
        MOVE_PLAYER,
        CREATE_LOBBY,
        LIST_LOBBIES_REQUEST,
        LIST_LOBBIES_RESPONSE,
        JOIN_LOBBY_REQUEST,
        JOIN_LOBBY_RESPONSE,
        LOBBY_UPDATE,
        PLAYER_READY,
        LEAVE_LOBBY,
        PLAYER_FARM_TYPE_UPDATE,
        ERROR,
        HOE_USED,
        PICKAXE_USED,
        WATERING_CAN_USED;
    }
}
