package Models.Crafting;

import Models.Item;

public class Crafting implements Item {

    private CraftingType craftingType;
    private int numberOfCrafting;

    public String getName(){
        return craftingType.getName();
    }

    public int getNumber() {
        return numberOfCrafting;
    }

    @Override
    public void setNumber(int number) {
        numberOfCrafting = number;
    }


}
