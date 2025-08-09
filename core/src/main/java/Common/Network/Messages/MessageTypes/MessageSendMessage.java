package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

import static Common.Network.Messages.Message.MessageType.SEND_TEXT_FRIEND;


public class MessageSendMessage extends Message {

    private final String senderPlayerName;
    private final String receiverPlayerName;
    private final String text;

    public MessageSendMessage(String senderPlayerName, String receiverPlayerName, String text) {
        super(SEND_TEXT_FRIEND);
        this.senderPlayerName = senderPlayerName;
        this.receiverPlayerName = receiverPlayerName;
        this.text = text;
    }

    public String getSenderPlayerName() {
        return senderPlayerName;
    }

    public String getReceiverPlayerName() {
        return receiverPlayerName;
    }

    public String getText() {
        return text;
    }
}
