package Common.Network.Send.MessageTypes;

import Common.Network.Send.Message;

import java.util.ArrayList;

public class StartGameMessage extends Message {
    private String username;
    private  long worldSeed;
    private ArrayList<String> playerNames;

    public StartGameMessage() {
        super(MessageType.START_GAME);
    }

    public StartGameMessage(String username) {
        super(MessageType.START_GAME);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public long getWorldSeed() {
        return worldSeed;
    }

    public void setWorldSeed(long worldSeed) {
        this.worldSeed = worldSeed;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }
}
