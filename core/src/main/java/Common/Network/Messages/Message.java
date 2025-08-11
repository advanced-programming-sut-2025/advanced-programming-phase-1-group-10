package Common.Network.Messages;


public class Message {
    private final MessageType type;

    public Message(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
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
        WATERING_CAN_USED,
        ADD_XP,
        SEND_GIFT,
        SEND_TEXT_FRIEND,
        RATE_GIFT,
        GIVE_BOUQUET,
        PUBLIC_CHAT,
        ASK_MARARIAGE,
        RESPONSE_MARARIAGE,
        TRADE_REQUEST,
        TRADE_REQUEST_RESPONSE,
        CHANGE_ITEMS_SLOT,
        CANCEL_TRADING,
        ACCEPT_TRADING,
        ACCEPT_TRADING_RESPONSE,
        EMOTION,
        FERTILIZER_USED,
        ;
    }
}
