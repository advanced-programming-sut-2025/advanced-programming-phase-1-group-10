package Models.Animal;

import Models.Item;

public class Fish implements Item {

    private FishType fishType;
    private int numberOfFish;

    public Fish(FishType fishType, int numberOfFish) {
        this.fishType = fishType;
        this.numberOfFish = numberOfFish;
    }

    @Override
    public String getName() {
        return fishType.getName();
    }

    @Override
    public String getSymbol() {
        return "Fi";
    }

    @Override
    public int getNumber() {
        return numberOfFish;
    }

    @Override
    public void setNumber(int number) {
        numberOfFish = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Fish(fishType, number);
    }
}
