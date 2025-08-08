package Common.Utilis;

import Common.Network.Send.Message;
import Common.Network.Send.MessageTypes.*;
import Common.Network.Send.MessageTypes.LobbyMessages.*;
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
                default:
                    throw new IllegalArgumentException("Unknown message type: " + type);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON: " + e.getMessage(), e);
        }
    }
}
