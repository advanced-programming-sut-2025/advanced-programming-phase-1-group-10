package Models.Crafting;

import Models.Item;

public class Crafting implements Item {

    private CraftingType craftingType;
    private int numberOfCrafting;

    public Crafting(CraftingType craftingType, int numberOfCrafting) {
        this.craftingType = craftingType;
        this.numberOfCrafting = numberOfCrafting;
    }

    @Override
    public String getName(){
        return craftingType.getName();
    }

    @Override
    public String getSymbol() {
        return "p";
    }

    @Override
    public int getNumber() {
        return numberOfCrafting;
    }

    @Override
    public void setNumber(int number) {
        numberOfCrafting = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Crafting(craftingType, number);
    }


}
