package Client.Controllers;

import Common.Models.App;
import Common.Network.Messages.MessageTypes.MessageSendMessage;
import Common.Network.Messages.MessageTypes.PublicChatMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatManager {
    private static ChatManager instance;

    private final Map<String, java.util.List<ChatMessage>> privateChatMessages = new ConcurrentHashMap<>();
    private final java.util.List<ChatMessage> publicChatMessages = new ArrayList<>();
    private final Map<String, java.util.List<ChatMessageListener>> privateMessageListeners = new ConcurrentHashMap<>();
    private final java.util.List<ChatMessageListener> publicMessageListeners = new ArrayList<>();

    private ChatManager() {

    }

    public static synchronized ChatManager getInstance() {
        if (instance == null) {
            instance = new ChatManager();
        }
        return instance;
    }


    public void addSentMessage(String receiverName, String messageText) {
        String senderName = App.getInstance().getCurrentGame().getCurrentPlayer().getName();
        ChatMessage message = new ChatMessage(senderName, receiverName, messageText, System.currentTimeMillis(), true);
        addPrivateMessage(receiverName, message);
    }

    public void addReceivedMessage(String senderName, String messageText) {
        ChatMessage message = new ChatMessage(senderName,
                App.getInstance().getCurrentGame().getCurrentPlayer().getName(),
                messageText, System.currentTimeMillis(), false);
        addPrivateMessage(senderName, message);
    }

    public void handleMessageSendMessage(MessageSendMessage message) {

        String currentPlayerName = App.getInstance().getCurrentGame().getCurrentPlayer().getName();
        if (message.getReceiverPlayerName().equals(currentPlayerName)) {
            addReceivedMessage(message.getSenderPlayerName(), message.getText());
        }
    }

    public void addSentPublicMessage(String messageText) {
        String senderName = App.getInstance().getCurrentGame().getCurrentPlayer().getName();
        ChatMessage message = new ChatMessage(senderName, "ALL", messageText, System.currentTimeMillis(), true);
        addPublicMessage(message);
    }


    public void addReceivedPublicMessage(String senderName, String messageText) {
        ChatMessage message = new ChatMessage(senderName, "ALL", messageText, System.currentTimeMillis(), false);
        addPublicMessage(message);
    }

    public void handlePublicChatMessage(PublicChatMessage message) {
        String currentPlayerName = App.getInstance().getCurrentGame().getCurrentPlayer().getName();

        if (!message.getSenderPlayerName().equals(currentPlayerName)) {
            addReceivedPublicMessage(message.getSenderPlayerName(), message.getText());
        }
    }

    private void addPrivateMessage(String otherPlayerName, ChatMessage message) {
        if (!privateChatMessages.containsKey(otherPlayerName)) {
            privateChatMessages.put(otherPlayerName, new ArrayList<>());
        }
        privateChatMessages.get(otherPlayerName).add(message);


        notifyPrivateListeners(otherPlayerName, message);
    }

    private void addPublicMessage(ChatMessage message) {
        publicChatMessages.add(message);


        notifyPublicListeners(message);
    }

    public java.util.List<ChatMessage> getMessagesForPlayer(String playerName) {
        return privateChatMessages.getOrDefault(playerName, new ArrayList<>());
    }

    public java.util.List<ChatMessage> getPublicMessages() {
        return new ArrayList<>(publicChatMessages);
    }

    public void addMessageListener(String playerName, ChatMessageListener listener) {
        if (!privateMessageListeners.containsKey(playerName)) {
            privateMessageListeners.put(playerName, new ArrayList<>());
        }
        privateMessageListeners.get(playerName).add(listener);
    }

    public void addPublicMessageListener(ChatMessageListener listener) {
        publicMessageListeners.add(listener);
    }

    public void removeMessageListener(String playerName, ChatMessageListener listener) {
        if (privateMessageListeners.containsKey(playerName)) {
            privateMessageListeners.get(playerName).remove(listener);
        }
    }

    public void removePublicMessageListener(ChatMessageListener listener) {
        publicMessageListeners.remove(listener);
    }

    private void notifyPrivateListeners(String playerName, ChatMessage message) {
        if (privateMessageListeners.containsKey(playerName)) {
            for (ChatMessageListener listener : privateMessageListeners.get(playerName)) {
                listener.onNewMessage(message);
            }
        }

        if (privateMessageListeners.containsKey("ALL_MESSAGES")) {
            for (ChatMessageListener listener : privateMessageListeners.get("ALL_MESSAGES")) {
                listener.onNewMessage(message);
            }
        }
    }

    private void notifyPublicListeners(ChatMessage message) {
        for (ChatMessageListener listener : publicMessageListeners) {
            listener.onNewMessage(message);
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

        public boolean isPublic() {
            return "ALL".equals(receiverName);
        }
    }

    public interface ChatMessageListener {
        void onNewMessage(ChatMessage message);
    }
}
