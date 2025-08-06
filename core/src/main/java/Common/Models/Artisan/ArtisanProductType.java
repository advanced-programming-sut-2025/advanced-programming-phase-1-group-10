package Common.Models.Artisan;

public enum ArtisanProductType {


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
    COAL("Coal", -1, "Turns 10 pieces of wood into one piece of coal.", 1, "Wood (10)", 50),
    CLOTH("Cloth", -1, "A bolt of fine wool cloth.", 4, "Wool", 470),
    MAYONNAISE("Mayonnaise", 50, "It looks spreadable.", 3, "Egg or Large Egg", 190 | 237),
    DUCK_MAYONNAISE("Duck Mayonnaise", 75, "It's a rich, yellow mayonnaise.", 3, "Duck Egg", 37),
    DINOSAUR_MAYONNAISE("Dinosaur Mayonnaise", 125, "It's thick and creamy, with a vivid green hue. It smells like grass and leather.", 3, "Dinosaur Egg", 800),
    TRUFFLE_OIL("Truffle Oil", 38, "A gourmet cooking ingredient.", 6, "Truffle", 1065),
    OIL("Oil", 13, "All purpose cooking oil.", 6, "Corn or Sunflower Seeds or Sunflower", 100),
    PICKLES("Pickles", -1, "A jar of your home-made pickles.", 6, "Any Vegetable", -1), // Price = 2 × Base Price + 50
    JELLY("Jelly", -1, "Gooey.", 72, "Any Fruit", -1), // Price = 2 × Fruit Base Price + 50
    SMOKED_FISH("Smoked Fish", -1, "A whole fish, smoked to perfection.", 1, "Any Fish + Coal", -1), // Price = 2 × Fish Price
    ANY_METAL_BAR("Any Metal Bar", -1, "Turns ore and coal into metal bars.", 4, "Any Ore (5) + Coal", -1); // Price = 10 × Ore Price



    private String name;
    private String description;
    private int energy;
    private int processingTime;
    private String ingredients;
    private int sellPrice;

    ArtisanProductType(String name, int energy, String description, int processingTime, String ingredients, int sellPrice) {
        this.name = name;
        this.energy = energy;
        this.description = description;
        this.processingTime = processingTime;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getEnergy() {
        return energy;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public String getIngredients() {
        return ingredients;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}
