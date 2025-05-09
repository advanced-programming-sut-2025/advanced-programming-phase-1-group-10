package Models.Animal;

import Models.Item;

public class Fish implements Item {

    private FishType fishType;
    private int numberOfFish;

    @Override
    public String getName() {
        return fishType.getName();
    }

    @Override
    public String getSymbol() {
        return "";
    }

    @Override
    public int getNumber() {
        return numberOfFish;
    }

    @Override
    public void setNumber(int number) {
        numberOfFish = number;
    }
}
