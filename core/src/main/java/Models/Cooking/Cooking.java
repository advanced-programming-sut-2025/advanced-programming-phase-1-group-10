package Models.Cooking;

import Models.Item;

public class Cooking implements Item {

    private CookingType cookingType;
    private int numberOfCooking;

    public Cooking(CookingType cookingType, int numberOfCooking){
        this.cookingType = cookingType;
        this.numberOfCooking = numberOfCooking;
    }


    @Override
    public String getName() {
        return cookingType.getName();
    }

    @Override
    public String getSymbol() {
        return "Co";
    }

    @Override
    public int getNumber() {
        return numberOfCooking;
    }

    @Override
    public void setNumber(int number) {
        numberOfCooking = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Cooking(cookingType, number);
    }
}
