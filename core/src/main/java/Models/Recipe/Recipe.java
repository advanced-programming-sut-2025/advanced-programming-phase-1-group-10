package Models.Recipe;

import Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Recipe implements Item {

    private RecipeType recipeType;
    private int numberOfRecipes;

    public Recipe(RecipeType recipeType, int numberOfRecipes) {
        this.recipeType = recipeType;
        this.numberOfRecipes = numberOfRecipes;
    }

    @Override
    public String getName() {
        return recipeType.getName();
    }

    @Override
    public Sprite show() {
        return null;
    }

    @Override
    public int getNumber() {
        return numberOfRecipes;
    }

    @Override
    public void setNumber(int number) {
        numberOfRecipes = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Recipe(recipeType,number);
    }


}
