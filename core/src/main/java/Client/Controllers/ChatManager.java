package Client.Controllers;

import Common.Models.App;
import Common.Network.Messages.MessageTypes.MessageSendMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatManager {
    private static ChatManager instance;
    private final Map<String, List<ChatMessage>> chatMessages = new ConcurrentHashMap<>();
    private final Map<String, List<ChatMessageListener>> messageListeners = new ConcurrentHashMap<>();

    private ChatManager() {}

    public static synchronized ChatManager getInstance() {
        if (instance == null) {
            instance = new ChatManager();
        }
        return instance;
    }

    public void addSentMessage(String receiverName, String messageText) {
        String senderName = App.getInstance().getCurrentGame().getCurrentPlayer().getName();
        ChatMessage message = new ChatMessage(senderName, receiverName, messageText, System.currentTimeMillis(), true);
        addMessage(receiverName, message);
    }

    public void addReceivedMessage(String senderName, String messageText) {
        ChatMessage message = new ChatMessage(senderName,
            App.getInstance().getCurrentGame().getCurrentPlayer().getName(),
            messageText, System.currentTimeMillis(), false);
        addMessage(senderName, message);
    }

    public void handleMessageSendMessage(MessageSendMessage message) {

        String currentPlayerName = App.getInstance().getCurrentGame().getCurrentPlayer().getName();
        if (message.getReceiverPlayerName().equals(currentPlayerName)) {
            addReceivedMessage(message.getSenderPlayerName(), message.getText());
        }
    }

    private void addMessage(String otherPlayerName, ChatMessage message) {
        if (!chatMessages.containsKey(otherPlayerName)) {
            chatMessages.put(otherPlayerName, new ArrayList<>());
        }
        chatMessages.get(otherPlayerName).add(message);
        notifyListeners(otherPlayerName, message);
    }

    public List<ChatMessage> getMessagesForPlayer(String playerName) {
        return chatMessages.getOrDefault(playerName, new ArrayList<>());
    }

    public void addMessageListener(String playerName, ChatMessageListener listener) {
        if (!messageListeners.containsKey(playerName)) {
            messageListeners.put(playerName, new ArrayList<>());
        }
        messageListeners.get(playerName).add(listener);
    }

    public void removeMessageListener(String playerName, ChatMessageListener listener) {
        if (messageListeners.containsKey(playerName)) {
            messageListeners.get(playerName).remove(listener);
        }
    }

    private void notifyListeners(String playerName, ChatMessage message) {
        if (messageListeners.containsKey(playerName)) {
            for (ChatMessageListener listener : messageListeners.get(playerName)) {
                listener.onNewMessage(message);
            }
        }

        if (messageListeners.containsKey("ALL_MESSAGES")) {
            for (ChatMessageListener listener : messageListeners.get("ALL_MESSAGES")) {
                listener.onNewMessage(message);
            }
        }
    }

    public static class ChatMessage {
        private final String senderName;
        private final String receiverName;
        private final String messageText;
        private final long timestamp;
        private final boolean sentByMe;

        public ChatMessage(String senderName, String receiverName, String messageText, long timestamp, boolean sentByMe) {
            this.senderName = senderName;
            this.receiverName = receiverName;
            this.messageText = messageText;
            this.timestamp = timestamp;
            this.sentByMe = sentByMe;
        }

        public String getSenderName() {
            return senderName;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public String getMessageText() {
            return messageText;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public boolean isSentByMe() {
            return sentByMe;
        }
    }

    public interface ChatMessageListener {
        void onNewMessage(ChatMessage message);
    }
}
