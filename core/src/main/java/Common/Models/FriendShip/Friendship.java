package Common.Models.FriendShip;



import Common.Models.PlayerStuff.Player;
import Common.Models.PlayerStuff.TradeRequest;

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
    private final ArrayList<TradeRequest> tradeRequestHistory = new ArrayList<>();

    private boolean isFlowerGiven;
    private boolean isMarried;

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

        if (isMarried) {
            return 4;
        } else if (xp > 600 && isFlowerGiven) {
            return 3;
        } else if (xp > 300) {
            return 2;
        } else if (xp > 100) {
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

    public boolean isFlowerGiven() {
        return isFlowerGiven;
    }

    public void setFlowerGiven(boolean flowerGiven) {
        isFlowerGiven = flowerGiven;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public ArrayList<TradeRequest> getTradeRequestHistory() {
        return tradeRequestHistory;
    }
}




