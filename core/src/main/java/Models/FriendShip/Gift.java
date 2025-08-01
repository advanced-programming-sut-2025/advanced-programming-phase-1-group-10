package Models.FriendShip;


import Models.Item;
import Models.PlayerStuff.Player;

import java.util.concurrent.ThreadLocalRandom;

public class Gift {

    private final Player sender;
    private final Player receiver;
    private double rate;
    private Item item;
    private boolean isNotified;
    private final int seed;

    public Gift(Player sender,Player receiver, Item item, boolean isNotified) {
        this.sender = sender;
        this.receiver = receiver;
        this.rate = 0;
        this.item = item;
        this.isNotified = isNotified;
        this.seed =  ThreadLocalRandom.current().nextInt(0,100000);
    }

    public Player getSender() {
        return sender;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }

    public Player getReceiver() {
        return receiver;
    }

    public int getSeed() {
        return seed;
    }
}
