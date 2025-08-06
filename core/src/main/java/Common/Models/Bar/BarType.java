package Common.Models.Bar;

public enum BarType {

    COPPER_BAR ("Copper Bar"),
    IRON_BAR ("Iron Bar"),
    GOLD_BAR  ("Gold Bar"),
    IRIDIUM_BAR("Iridium Bar"),
    RADIOACTIVE_BAR("Radioactive Bar")
        ;

    private final String name;

    BarType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
