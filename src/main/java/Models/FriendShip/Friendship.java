package Models.FriendShip;



import Models.PlayerStuff.Player;

import java.util.ArrayList;


public class Friendship {


    public Friendship(Player player, int xp) {
        this.player = player;
        this.xp = xp;
    }

    private final Player player;
    private int xp ;

    private final ArrayList<Message> messages = new ArrayList<>();
    private final ArrayList<Message> messageHistory = new ArrayList<>();
    private final ArrayList<Gift> giftHistory = new ArrayList<>();

    public Player getPlayer() {
        return player;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        int xp = this.getXp();

        if (xp >= 1000) {
            return 4;
        } else if (xp >= 600) {
            return 3;
        } else if (xp >= 300) {
            return 2;
        } else if (xp >= 100) {
            return 1;
        } else {
            return 0;
        }
    }


    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<Message> getMessageHistory() {
        return messageHistory;
    }

    public ArrayList<Gift> getGiftHistory() {
        return giftHistory;
    }
}




