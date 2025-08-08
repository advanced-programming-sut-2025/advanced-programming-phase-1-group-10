package Common.Network.Send.MessageTypes.LobbyMessages;

import Common.Network.Send.Message;

import java.util.Map;

public class StartGameMessage extends Message {
    private String username;
    private  long worldSeed;
    private Map<String,String> players;
    private int index;

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

    public void setPlayers(Map<String,String> players) {
        this.players = players;
    }

    public Map<String, String> getPlayers() {
        return players;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
