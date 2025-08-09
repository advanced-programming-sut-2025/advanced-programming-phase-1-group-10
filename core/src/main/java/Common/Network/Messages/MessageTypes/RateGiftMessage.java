package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

public class RateGiftMessage extends Message {

    private final String targetPlayerName;
    private final int giftSeed;
    private final double rate;


    public RateGiftMessage(String targetPlayerName, int giftSeed, double rate) {
        super(MessageType.RATE_GIFT);
        this.targetPlayerName = targetPlayerName;
        this.giftSeed = giftSeed;
        this.rate = rate;
    }

    public String getTargetPlayerName() {
        return targetPlayerName;
    }

    public int getGiftSeed() {
        return giftSeed;
    }

    public double getRate() {
        return rate;
    }
}
