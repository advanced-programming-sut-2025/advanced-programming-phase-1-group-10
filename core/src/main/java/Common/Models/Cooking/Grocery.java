package Common.Models.Cooking;

import Common.Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

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
    public Sprite show() {
        return null;
    }

    @Override
    public int getNumber() {
        return numberOfGrocery;
    }

    @Override
    public void setNumber(int number) {
            numberOfGrocery = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Grocery(groceryType, number);
    }
}
