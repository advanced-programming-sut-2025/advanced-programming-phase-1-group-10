package Models.Crafting;

public enum CraftingType {

    CherryBomb("Cherry Bomb"),
    Bomb("Bomb"),
    MegaBomb("Mega Bomb"),
    Sprinkler("Sprinkler"),
    QualitySprinkler("Quality Sprinkler"),
    IridiumSprinkler("Iridium Sprinkler"),
    CharcoalKiln("Charcoal Kiln"),
    Furnace("Furnace"),
    Scarecrow("Scarecrow"),
    DeluxeScarecrow("Deluxe Scarecrow"),
    BeeHouse("Bee House"),
    CheesePress("Cheese Press"),
    Keg("Keg"),
    Loom("Loom"),
    MayonnaiseMachine("Mayonnaise Machine"),
    OilMaker("Oil Maker"),
    PreservesJar("Preserve Jar"),
    Dehydrator("Dehydrator"),
    MysticTreeSeed("Mystic Tree Seed"),
    FishSmoker("Fish Smoker"),
    ;

    private final String name;

    CraftingType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
