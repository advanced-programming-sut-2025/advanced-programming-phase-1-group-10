package Models.Recipe;

public enum RecipeType {

    DehydratorRecipe("Dehydrator Recipe"),
    GrassStarterRecipe("Grass Starter Recipe"),
    FishSmokerRecipe("FishSmoker Recipe")
    ;

    private final String name;

    RecipeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
