package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class AddXpMessage extends Message {

    private final String senderPlayer;
    private final String goalPlayer;
    private final int xp;

    public AddXpMessage(String senderPlayer, String goalPlayer, int xp) {
        super(MessageType.ADD_XP);
        this.senderPlayer = senderPlayer;
        this.goalPlayer = goalPlayer;
        this.xp = xp;
    }

    public String getSenderPlayer() {
        return senderPlayer;
    }

    public String getGoalPlayer() {
        return goalPlayer;
    }

    public int getXp() {
        return xp;
    }
}
