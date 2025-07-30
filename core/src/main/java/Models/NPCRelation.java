package Models;

import Models.NPC.NPC;

import java.util.ArrayList;

public class NPCRelation {

    private NPC npc;
    private ArrayList<Quest> acceptedQuests;
    private int relationPoint;
    private boolean istalkedToday;
    private boolean isGiftSent;

    public static final int MAX_RELATION_LEVEL = 5;

    public NPCRelation(NPC npc, int relationPoint, boolean istalkedToday, boolean isGiftSent) {
        this.npc = npc;
        this.acceptedQuests = new ArrayList<>();
        this.relationPoint = relationPoint;
        this.istalkedToday = istalkedToday;
        this.isGiftSent = isGiftSent;
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    public ArrayList<Quest> getAcceptedQuests() {
        return acceptedQuests;
    }

    public void setAcceptedQuests(ArrayList<Quest> acceptedQuests) {
        this.acceptedQuests = acceptedQuests;
    }

    public int getRelationPoint() {
        return relationPoint;
    }

    public void setRelationPoint(int relationPoint) {
        this.relationPoint = relationPoint;
    }

    public boolean isIstalkedToday() {
        return istalkedToday;
    }

    public void setIstalkedToday(boolean istalkedToday) {
        this.istalkedToday = istalkedToday;
    }

    public boolean isGiftSent() {
        return isGiftSent;
    }

    public void setGiftSent(boolean giftSent) {
        isGiftSent = giftSent;
    }

    public int getFrinendShipLevel(){
        return (this.relationPoint < 200 * MAX_RELATION_LEVEL) ?(this.relationPoint / 200) : MAX_RELATION_LEVEL;
    }

}
