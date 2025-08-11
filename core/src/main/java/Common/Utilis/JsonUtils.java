package Common.Utilis;

import Common.Network.Messages.Message;
import Common.Network.Messages.MessageTypes.*;
import Common.Network.Messages.MessageTypes.LobbyMessages.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtils {
    private static final GsonBuilder gsonBuilder = new GsonBuilder();
    private static final Gson gson;

    static {
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    public synchronized static String toJson(Message message) {
        return gson.toJson(message);
    }

    public synchronized static Message fromJson(String json) {
        return gson.fromJson(json, Message.class);
    }

    public synchronized static Message fromJsonWithType(String json) {
        try {
            // pars json as jsonobject
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

            String typeString = jsonObject.get("type").getAsString();
            Message.MessageType type = Message.MessageType.valueOf(typeString);

            switch (type) {
                case START_GAME:
                    return gson.fromJson(json, StartGameMessage.class);
                case JOIN_REQUEST:
                    return gson.fromJson(json, JoinRequestMessage.class);
                case CREATE_LOBBY:
                    return gson.fromJson(json, CreateLobbyMessage.class);
                case LIST_LOBBIES_REQUEST:
                    return gson.fromJson(json, ListLobbiesRequestMessage.class);
                case LIST_LOBBIES_RESPONSE:
                    return gson.fromJson(json, ListLobbiesResponseMessage.class);
                case JOIN_LOBBY_REQUEST:
                    return gson.fromJson(json, JoinLobbyRequestMessage.class);
                case JOIN_LOBBY_RESPONSE:
                    return gson.fromJson(json, JoinLobbyResponseMessage.class);
                case LOBBY_UPDATE:
                    return gson.fromJson(json, LobbyUpdateMessage.class);
                case PLAYER_READY:
                    return gson.fromJson(json, PlayerReadyMessage.class);
                case LEAVE_LOBBY:
                    return gson.fromJson(json, LeavelobbyMessage.class);
                case ERROR:
                    return gson.fromJson(json, ErrorMessage.class);
                case PLAYER_FARM_TYPE_UPDATE:
                    return gson.fromJson(json, PlayerFarmTypeUpdateMessage.class);
                case MOVE_PLAYER:
                    return gson.fromJson(json, MovePlayerMessage.class);
                case HOE_USED:
                    return gson.fromJson(json,  HoeUsedMessage.class);
                case PICKAXE_USED:
                    return gson.fromJson(json, PickaxeUsedMessage.class);
                case WATERING_CAN_USED:
                    return gson.fromJson(json, WateringCanUsedMessage.class);
                case ADD_XP:
                    return gson.fromJson(json, AddXpMessage.class);
                case SEND_TEXT_FRIEND:
                    return gson.fromJson(json, MessageSendMessage.class);
                case SEND_GIFT:
                    return gson.fromJson(json, SendGiftMessage.class);
                case RATE_GIFT:
                    return gson.fromJson(json, RateGiftMessage.class);
                case PUBLIC_CHAT:
                    return gson.fromJson(json, PublicChatMessage.class);
                case GIVE_BOUQUET:
                    return gson.fromJson(json, GiveBouquetMessage.class);
                case ASK_MARARIAGE:
                    return gson.fromJson(json, AskMarriageMessage.class);
                case RESPONSE_MARARIAGE:
                    return gson.fromJson(json, ResponseMarriage.class);
                case TRADE_REQUEST:
                    return gson.fromJson(json, TradeRequestMessage.class);
                case TRADE_REQUEST_RESPONSE:
                    return gson.fromJson(json, TradeRequestResponseMessage.class);
                case CHANGE_ITEMS_SLOT:
                    return gson.fromJson(json, ChangeItemTradeMessage.class);
                case CANCEL_TRADING:
                    return gson.fromJson(json, CancelTradeMessage.class);
                case ACCEPT_TRADING:
                    return gson.fromJson(json, AceeptTradeMessage.class);
                case ACCEPT_TRADING_RESPONSE:
                    return gson.fromJson(json, AcceptTradeResponseMessage.class);
                case EMOTION:
                    return gson.fromJson(json, EmotionMessage.class);
                case FERTILIZER_USED:
                    return gson.fromJson(json, FertilizerUsedMessage.class);
                case SEYTH_USED:
                    return gson.fromJson(json, SeythUsedMessage.class);
                case AXE_USED:
                    return gson.fromJson(json, AxeUsedMessage.class);
                case PLANT_SEED:
                    return gson.fromJson(json, PlantSeedMessage.class);
                default:
                    throw new IllegalArgumentException("Unknown message type: " + type);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON: " + e.getMessage(), e);
        }
    }
}
