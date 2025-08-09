package Client.Controllers.FinalControllers;

import Client.Controllers.ChatManager;
import Client.Controllers.MessageSystem;
import Client.Main;
import Client.Network.ClientNetworkManager;
import Common.Models.App;
import Common.Models.PlayerStuff.Player;
import Common.Network.Messages.MessageTypes.MessageSendMessage;
import Common.Network.Messages.MessageTypes.PublicChatMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ChatController {
    public void initializeChatNotifications(Stage stage) {
        ChatManager.getInstance().addMessageListener("ALL_MESSAGES", new ChatManager.ChatMessageListener() {
            @Override
            public void onNewMessage(ChatManager.ChatMessage message) {
                if (!message.isSentByMe()) {
                    String senderName = message.getSenderName();

                    Dialog existingDialog = findExistingChatDialog(senderName,stage);
                    if (existingDialog == null) {
                        Gdx.app.postRunnable(() -> {
                            MessageSystem.showInfo("New message from " + senderName, 10.0f);
                        });
                    }
                }
            }
        });
    }

    public void openPrivateChatWindow(String playerName , Stage stage) {
        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();
        Player receiverPlayer = App.getInstance().getCurrentGame().getPlayerByName(playerName);

        Dialog existingDialog = findExistingChatDialog(playerName,stage);
        if (existingDialog != null) {
            existingDialog.toFront();
            return;
        }

        Dialog privateChatDialog = new Dialog("Chat with " + playerName, getSkin()) {
            private final TextArea messagesArea;
            private final TextField inputField;
            private final ChatManager.ChatMessageListener chatListener;

            {
                messagesArea = new TextArea("", getSkin());
                messagesArea.setDisabled(true);
                messagesArea.setPrefRows(10);

                java.util.List<ChatManager.ChatMessage> history = ChatManager.getInstance().getMessagesForPlayer(playerName);
                for (ChatManager.ChatMessage msg : history) {
                    String prefix = msg.isSentByMe() ? "Me: " : playerName + ": ";
                    messagesArea.appendText(prefix + msg.getMessageText() + "\n");
                }

                ScrollPane msgScroll = new ScrollPane(messagesArea, getSkin());
                msgScroll.setFadeScrollBars(false);
                msgScroll.setScrollingDisabled(true, false);

                inputField = new TextField("", getSkin());
                inputField.setMessageText("Type your message...");

                inputField.setTextFieldListener((textField, c) -> {
                    if (c == '\n') {
                        sendMessage();
                        return;
                    }
                });

                TextButton sendButton = new TextButton("Send", getSkin());
                sendButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        sendMessage();
                    }
                });

                TextButton closeButton = new TextButton("Close", getSkin());
                closeButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        ChatManager.getInstance().removeMessageListener(playerName, chatListener);
                        remove();
                    }
                });

                getContentTable().add(msgScroll).colspan(2).expand().fill().pad(10).width(500).row();
                getContentTable().add(inputField).colspan(2).expand().fill().pad(10).width(500).height(80).row();
                getContentTable().add(sendButton).width(150);
                getContentTable().add(closeButton).width(150);

                chatListener = new ChatManager.ChatMessageListener() {
                    @Override
                    public void onNewMessage(ChatManager.ChatMessage message) {
                        if (!message.isSentByMe() && message.getSenderName().equals(playerName)) {
                            Gdx.app.postRunnable(() -> {
                                messagesArea.appendText(playerName + ": " + message.getMessageText() + "\n");
                                msgScroll.setScrollY(messagesArea.getPrefHeight());
                            });
                        }
                    }
                };
                ChatManager.getInstance().addMessageListener(playerName, chatListener);
            }

            private void sendMessage() {
                String msg = inputField.getText().trim();
                if (!msg.isEmpty()) {
                    if(App.getInstance().getCurrentGame().isOnline()){
                        MessageSendMessage message = new MessageSendMessage(
                            currentPlayer.getName(),
                            playerName,
                            msg
                        );
                        ClientNetworkManager.getInstance().sendMessage(message);

                        ChatManager.getInstance().addSentMessage(playerName, msg);
                    }

                    messagesArea.appendText("Me: " + msg + "\n");
                    inputField.setText("");
                    ((ScrollPane)getContentTable().getChild(0)).setScrollY(messagesArea.getPrefHeight());
                }
            }

            @Override
            public boolean remove() {
                ChatManager.getInstance().removeMessageListener(playerName, chatListener);
                return super.remove();
            }
        };

        privateChatDialog.setSize(700, 350);
        privateChatDialog.setPosition(
            (Gdx.graphics.getWidth() - privateChatDialog.getWidth()) / 2,
            (Gdx.graphics.getHeight() - privateChatDialog.getHeight()) / 2
        );
        privateChatDialog.show(stage);
    }

    public Dialog findExistingChatDialog(String playerName, Stage stage) {
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Dialog) {
                Dialog dialog = (Dialog) actor;
                if (dialog.getTitleLabel().getText().toString().equals("Chat with " + playerName)) {
                    return dialog;
                }
            }
        }
        return null;
    }

    public void openPublicChatWindow(Stage stage) {
        Dialog existingDialog = findExistingPublicChatDialog(stage);
        if (existingDialog != null) {
            existingDialog.toFront();
            return;
        }

        Dialog publicChatDialog = new Dialog("Public Chat", getSkin()) {
            private final TextArea messagesArea;
            private final TextField inputField;
            private final ChatManager.ChatMessageListener chatListener;

            {
                messagesArea = new TextArea("", getSkin());
                messagesArea.setDisabled(true);
                messagesArea.setPrefRows(10);


                java.util.List<ChatManager.ChatMessage> history = ChatManager.getInstance().getPublicMessages();
                for (ChatManager.ChatMessage msg : history) {
                    String prefix = msg.isSentByMe() ? "Me: " : msg.getSenderName() + ": ";
                    messagesArea.appendText(prefix + msg.getMessageText() + "\n");
                }

                ScrollPane msgScroll = new ScrollPane(messagesArea, getSkin());
                msgScroll.setFadeScrollBars(false);
                msgScroll.setScrollingDisabled(true, false);

                inputField = new TextField("", getSkin());
                inputField.setMessageText("Type your message...");

                inputField.setTextFieldListener((textField, c) -> {
                    if (c == '\n') {
                        sendMessage();
                        return;
                    }
                });

                TextButton sendButton = new TextButton("Send", getSkin());
                sendButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        sendMessage();
                    }
                });

                TextButton closeButton = new TextButton("Close", getSkin());
                closeButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        ChatManager.getInstance().removePublicMessageListener(chatListener);
                        remove();
                    }
                });

                getContentTable().add(msgScroll).colspan(2).expand().fill().pad(10).width(500).row();
                getContentTable().add(inputField).colspan(2).expand().fill().pad(10).width(500).height(80).row();
                getContentTable().add(sendButton).width(150);
                getContentTable().add(closeButton).width(150);

                chatListener = new ChatManager.ChatMessageListener() {
                    @Override
                    public void onNewMessage(ChatManager.ChatMessage message) {
                        if (message.isPublic()) {
                            Gdx.app.postRunnable(() -> {
                                String prefix = message.isSentByMe() ? "Me: " : message.getSenderName() + ": ";
                                messagesArea.appendText(prefix + message.getMessageText() + "\n");
                                msgScroll.setScrollY(messagesArea.getPrefHeight());
                            });
                        }
                    }
                };
                ChatManager.getInstance().addPublicMessageListener(chatListener);
            }

            private void sendMessage() {
                String msg = inputField.getText().trim();
                if (!msg.isEmpty()) {
                    if(App.getInstance().getCurrentGame().isOnline()){
                        String senderName = App.getInstance().getCurrentGame().getCurrentPlayer().getName();
                        PublicChatMessage message = new PublicChatMessage(senderName, msg);
                        ClientNetworkManager.getInstance().sendMessage(message);
                        ChatManager.getInstance().addSentPublicMessage(msg);
                    } else {
                        messagesArea.appendText("Me: " + msg + "\n");
                    }

                    inputField.setText("");
                    ((ScrollPane)getContentTable().getChild(0)).setScrollY(messagesArea.getPrefHeight());
                }
            }


            @Override
            public boolean remove() {
                ChatManager.getInstance().removePublicMessageListener(chatListener);
                return super.remove();
            }
        };

        publicChatDialog.setSize(700, 350);
        publicChatDialog.setPosition(
            (Gdx.graphics.getWidth() - publicChatDialog.getWidth()) / 2,
            (Gdx.graphics.getHeight() - publicChatDialog.getHeight()) / 2
        );
        publicChatDialog.show(stage);
    }

    public Dialog findExistingPublicChatDialog(Stage stage) {
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Dialog) {
                Dialog dialog = (Dialog) actor;
                if (dialog.getTitleLabel().getText().toString().equals("Public Chat")) {
                    return dialog;
                }
            }
        }
        return null;
    }

    private Skin getSkin(){
        return Main.getInstance().getSkin();
    }
}
