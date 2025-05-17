package Models.Recipe;

import Models.Item;

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
    public String getSymbol() {
        return "r";
    }

    @Override
    public int getNumber() {
        return numberOfRecipes;
    }

    @Override
    public void setNumber(int number) {
        numberOfRecipes = number;
    }
}
