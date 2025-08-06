package Common.Models;

public class Quest {

    private int questLevel;
    private boolean isCompleted;
    private String explanation;
    private Item givenItems;
    private int goldAward;
    private Item itemAward;

    public Quest(int questLevel,boolean isCompleted, String explanation, Item givenItems, int goldAward, Item itemAward) {
        this.questLevel = questLevel;
        this.isCompleted = isCompleted;
        this.explanation = explanation;
        this.givenItems = givenItems;
        this.goldAward = goldAward;
        this.itemAward = itemAward;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Item getGivenItems() {
        return givenItems;
    }

    public void setGivenItems(Item givenItems) {
        this.givenItems = givenItems;
    }

    public int getGoldAward() {
        return goldAward;
    }

    public void setGoldAward(int goldAward) {
        this.goldAward = goldAward;
    }

    public Item getItemAward() {
        return itemAward;
    }

    public void setItemAward(Item itemAward) {
        this.itemAward = itemAward;
    }

    public int getQuestLevel() {
        return questLevel;
    }

    public void setQuestLevel(int questLevel) {
        this.questLevel = questLevel;
    }
}
