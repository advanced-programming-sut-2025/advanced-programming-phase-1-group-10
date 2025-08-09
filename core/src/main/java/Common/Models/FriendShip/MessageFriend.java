package Common.Models.FriendShip;

import Common.Models.PlayerStuff.Player;

public class MessageFriend {

    private Player sender;
    private Player receiver;

    private String message;
    private boolean isNotified;

    public MessageFriend(Player sender, Player receiver, String message, boolean isNotified) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isNotified = isNotified;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }
}
