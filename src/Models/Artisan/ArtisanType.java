package src.Models.Artisan;

public enum ArtisanType {


    HONEY("Honey", 75, "It's a sweet syrup produced by bees.", 4, "", 350),
    CHEESE("Cheese", 100, "It's your basic cheese.", 3, "Milk or Large Milk", 230 | 350),
    GOAT_CHEESE("Goat Cheese", 100, "Soft cheese made from goat's milk.", 3, "Goat Milk or Large Goat Milk", 400 | 600),
    BEER("Beer", 50, "Drink in moderation.", 24, "Wheat", 200),
    VINEGAR("Vinegar", 13, "An aged fermented liquid used in many cooking recipes.", 10, "Rice", 100),
    COFFEE("Coffee", 75, "It smells delicious. This is sure to give you a boost.", 2, "Coffee Bean (5)", 150),
    JUICE("Juice", -1, "A sweet, nutritious beverage.", 96, "Any Vegetable", -1), // Energy and Price depend on input
    MEAD("Mead", 100, "A fermented beverage made from honey. Drink in moderation.", 10, "Honey", 300),
    PALE_ALE("Pale Ale", 50, "Drink in moderation.", 72, "Hops", 300),
    WINE("Wine", -1, "Drink in moderation.", 168, "Any Fruit", -1), // Energy and Price depend on fruit
    DRIED_MUSHROOMS("Dried Mushrooms", 50, "A package of gourmet mushrooms.", 8, "Any Mushroom (5)", -1), // Price = 7.5 * base + 25
    DRIED_FRUIT("Dried Fruit", 75, "Chewy pieces of dried fruit.", 8, "5 of Any Fruit (except Grapes)", -1), // Price = 7.5 * base + 25
    RAISINS("Raisins", 125, "It's said to be the Junimos' favorite food.", 8, "Grapes (5)", 600),
    COAL("Coal", -1, "Turns 10 pieces of wood into one piece of coal.", 1, "Wood (10)", 50);


    private String name;
    private String description;
    private int energy;
    private int processingTime;
    private String ingredients;
    private int sellPrice;

    ArtisanType(String name, int energy, String description, int processingTime, String ingredients, int sellPrice) {
        this.name = name;
        this.energy = energy;
        this.description = description;
        this.processingTime = processingTime;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
    }
}
