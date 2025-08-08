package Common.Network.Messages.MessageTypes.LobbyMessages;

import Common.Network.Messages.Message;

public class PlayerFarmTypeUpdateMessage extends Message {
    private String username;
    private String farmType;

    public PlayerFarmTypeUpdateMessage(String username, String farmType) {
        super(MessageType.PLAYER_FARM_TYPE_UPDATE);
        this.username = username;
        this.farmType = farmType;
    }

    public String getUsername() {
        return username;
    }

    public String getFarmType() {
        return farmType;
    }
}
