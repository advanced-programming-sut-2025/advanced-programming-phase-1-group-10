package Models.Crafting;

public enum CraftingType {

    CherryBomb("Cherry Bomb", "mining", 1, null),
    Bomb("Bomb",  "mining", 2, null),
    MegaBomb("Mega Bomb", "mining", 3, null),
    Sprinkler("Sprinkler", "farming", 1, null),
    QualitySprinkler("Quality Sprinkler",  "farming", 2, null),
    IridiumSprinkler("Iridium Sprinkler",  "farming", 3, null),
    CharcoalKiln("Charcoal Kiln",  "foraging", 1, null),
    Furnace("Furnace", null, 0, null),
    Scarecrow("Scarecrow", null, 0, null),
    DeluxeScarecrow("Deluxe Scarecrow", "farming", 2, null),
    BeeHouse("Bee House", "farming", 1, null),
    CheesePress("Cheese Press", "farming", 2, null),
    Keg("Keg", "farming", 3, null),
    Loom("Loom", "farming", 3, null),
    MayonnaiseMachine("Mayonnaise Machine", null, 0, null),
    OilMaker("Oil Maker", "farming", 3, null),
    PreservesJar("Preserve Jar",  "farming", 2, null),
    Dehydrator("Dehydrator", null, 0, "Pierre's General Store"),
    GrassStarter("Grass Starter", null, 0, "Pierre's General Store"),
    FishSmoker("Fish Smoker", null, 0, "Fish Shop"),
    MysticTreeSeed("Mystic Tree Seed", "farming",4, null)
    ;

    private final String name;
    private final String abilityType;
    private final Integer abilityLevel;
    private final String laernedRecipe;

    CraftingType(String name, String abilityType, int abilityLevel, String learnedRecipe) {
        this.name = name;
        this.abilityType = abilityType;
        this.abilityLevel = abilityLevel;
        this.laernedRecipe = learnedRecipe;
    }

    public String getName() {
        return name;
    }
    public String getAbilityType() {return abilityType;}
    public Integer getAbilityLevel() {return abilityLevel;}
    public String getLaernedRecipe() {return laernedRecipe;}

}
