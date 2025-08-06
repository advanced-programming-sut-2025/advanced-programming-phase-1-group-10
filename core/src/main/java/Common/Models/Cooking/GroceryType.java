package Common.Models.Cooking;

public enum GroceryType {
    WHEAT_FLOUR("Wheat Flour"),
    OIL("Oil"),
    RICE("Rice"),
    VINEGAR("Vinegar"),
    SUGAR("Sugar")
    ;

    private final String name;

    GroceryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
