package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class PublicChatMessage extends Message {
    private final String senderPlayerName;
    private final String text;

    public PublicChatMessage(String senderPlayerName, String text) {
        super(MessageType.PUBLIC_CHAT);
        this.senderPlayerName = senderPlayerName;
        this.text = text;
    }

    public String getSenderPlayerName() {
        return senderPlayerName;
    }

    public String getText() {
        return text;
    }
}
