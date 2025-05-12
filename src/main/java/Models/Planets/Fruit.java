package Models.Planets;

import Models.Item;

public class Fruit implements Item {
    private FruitType fruitType;
    private int numberOfFruit;

    public Fruit(FruitType fruitType, int numberOfFruit) {
        this.fruitType = fruitType;
        this.numberOfFruit = numberOfFruit;
    }

    @Override
    public String getName() {
        return fruitType.getName();
    }

    @Override
    public String getSymbol() {
        return "f";
    }

    @Override
    public int getNumber() {
        return numberOfFruit;
    }

    @Override
    public void setNumber(int number) {
        numberOfFruit = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Fruit(fruitType, number);
    }
}
