package Models.Planets;

import Models.Item;

public class Fruit implements Item {
    private FruitType fruitType;
    private int numberOfFruit;


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
}
