package Models.Cooking;

import Models.Item;

public class Cooking implements Item {

    private CookingType cookingType;
    private int numberOfCooking;


    @Override
    public String getName() {
        return cookingType.getName();
    }

    @Override
    public int getNumber() {
        return numberOfCooking;
    }

    @Override
    public void setNumber(int number) {
        numberOfCooking = number;
    }
}
