package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class EmotionMessage extends Message {
    private final String senderName;
    private final String emotionText;
    private final boolean isEmoji;
    private final int emojiIndex;

    public EmotionMessage(String senderName, String emotionText, boolean isEmoji, int emojiIndex) {
        super(MessageType.EMOTION);
        this.senderName = senderName;
        this.emotionText = emotionText;
        this.isEmoji = isEmoji;
        this.emojiIndex = emojiIndex;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getEmotionText() {
        return emotionText;
    }

    public boolean isEmoji() {
        return isEmoji;
    }

    public int getEmojiIndex() {
        return emojiIndex;
    }
}
