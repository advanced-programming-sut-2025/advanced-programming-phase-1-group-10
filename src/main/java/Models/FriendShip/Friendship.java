package Models.FriendShip;



import Models.PlayerStuff.Player;

import java.util.ArrayList;


public class Friendship {


    public Friendship(Player player, int xp, int level) {
        this.player = player;
        this.xp = xp;
        this.level = level;

    }

    private final Player player;
    private int xp ;
    private int level;

    private final ArrayList<Message> messages = new ArrayList<>();
    private final ArrayList<Message> messageHistory = new ArrayList<>();
    private final ArrayList<Gift> gifts = new ArrayList<>();
    private final ArrayList<Integer> givenGifts = new ArrayList<>();

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
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<Message> getMessageHistory() {
        return messageHistory;
    }

    public ArrayList<Gift> getGifts() {
        return gifts;
    }

    public ArrayList<Integer> getGivenGifts() {
        return givenGifts;
    }



}




