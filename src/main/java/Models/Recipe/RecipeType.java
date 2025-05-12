package Models.Recipe;

public enum RecipeType {

    DehydratorRecipe("Dehydrator Recipe"),
    GrassStarterRecipe("Grass Starter Recipe"),
    FishSmokerRecipe("FishSmoker Recipe"),
    HashbrownsRecipe("Hashbrowns Recipe"),
    OmeletRecipe("Omelet Recipe"),
    PancakesRecipe("Pancakes Recipe"),
    BreadRecipe("Bread Recipe"),
    TortillaRecipe("Tortilla Recipe"),
    PizzaRecipe("Pizza Recipe"),
    MakiRollRecipe("Maki Roll Recipe"),
    TripleShotEspressoRecipe("Triple Shot Espresso Recipe"),
    CookieRecipe("Cookie Recipe"),
    PumpkinPieRecipe("Pumpkin Pie Recipe"),
    SpaghettiRecipe("Spaghetti Recipe"),
    RedPlateRecipe("Red Plate Recipe"),
    FruitSaladRecipe("Fruit Salad Recipe"),
    SalmonDinnerRecipe("Salmon Dinner Recipe")
    ;

    private final String name;

    RecipeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
