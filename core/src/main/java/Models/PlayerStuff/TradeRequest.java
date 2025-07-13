package Models.PlayerStuff;



import Models.Item;
import Models.Person;



public class TradeRequest {
    private final Person sender;
    private final Person receiver;
    private final int price;
    private final Item sendItem;
    private  String targetItemName = null;
    private  int targetAmount = 0;

    private boolean isNotified;
    private boolean isAnswered;
    private boolean isAccepted;

    public TradeRequest(Person sender, Person receiver, Item sendItem, String targetItemName,int targetItemAmount) {
        this.sender = sender;
        this.receiver = receiver;
        this.sendItem = sendItem;
        this.targetItemName = targetItemName;
        this.targetAmount = targetItemAmount;
        isNotified = false;
        isAccepted = false;
        isAnswered = false;
        this.price = -1;
    }

    public TradeRequest(Person sender, Person receiver, Item sendItem, int price) {
        this.sender = sender;
        this.receiver = receiver;
        this.sendItem = sendItem;
        this.price = price;
        isNotified = false;
        isAnswered = false;
        isAccepted = false;
    }

    public Person getSender() {
        return sender;
    }

    public Person getReceiver() {
        return receiver;
    }

    public int getPrice() {
        return price;
    }

    public Item getSendItem() {
        return sendItem;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public String getTargetItemName() {
        return targetItemName;
    }

    public int getTargetAmount() {
        return targetAmount;
    }
}
