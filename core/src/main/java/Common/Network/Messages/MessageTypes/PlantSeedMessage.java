// Common/Network/Messages/MessageTypes/PlantSeedMessage.java
package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class PlantSeedMessage extends Message {
    private final String playerName;
    private final int x, y;
    private final boolean isTree;
    private final String seedTypeName;

    public PlantSeedMessage(String playerName, int x, int y, boolean isTree, String seedTypeName){
        super(MessageType.PLANT_SEED);
        this.playerName = playerName;
        this.x = x;
        this.y = y;
        this.isTree = isTree;
        this.seedTypeName = seedTypeName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isTree() {
        return isTree;
    }

    public String getSeedTypeName() {
        return seedTypeName;
    }
}
