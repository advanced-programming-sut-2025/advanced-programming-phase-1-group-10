package Models.Cooking;

import Models.Item;

public class Grocery implements Item {

    private GroceryType groceryType;
    private int numberOfGrocery;

    public Grocery(GroceryType groceryType, int numberOfGrocery){
        this.groceryType = groceryType;
        this.numberOfGrocery = numberOfGrocery;
    }


    @Override
    public String getName() {
        return groceryType.getName();
    }

    @Override
    public String getSymbol() {
        return "g";
    }

    @Override
    public int getNumber() {
        return numberOfGrocery;
    }

    @Override
    public void setNumber(int number) {
            numberOfGrocery = number;
    }
}
