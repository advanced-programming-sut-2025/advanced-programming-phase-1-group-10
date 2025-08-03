package Models.Crafting;

import Models.Bar.Bar;
import Models.Bar.BarType;
import Models.Item;
import Models.Mineral.Mineral;
import Models.Mineral.MineralTypes;
import Models.Planets.Seed;
import Models.Planets.SeedType;

import java.util.ArrayList;
import java.util.Arrays;

public enum CraftingType {

    CherryBomb("Cherry Bomb", "mining", 1, null, new Item[] {
            new Mineral(MineralTypes.COPPER, 4),
            new Mineral(MineralTypes.COAL, 1)
    }),

    Bomb("Bomb", "mining", 2, null, new Item[] {
            new Mineral(MineralTypes.IRON, 4),
            new Mineral(MineralTypes.COAL, 1)
    }),

    MegaBomb("Mega Bomb", "mining", 3, null, new Item[] {
            new Mineral(MineralTypes.GOLD, 4),
            new Mineral(MineralTypes.COAL, 1)
    }),

    Sprinkler("Sprinkler", "farming", 1, null, new Item[] {
            new Bar(BarType.COPPER_BAR, 1),
            new Bar(BarType.IRON_BAR, 1)
    }),

    QualitySprinkler("Quality Sprinkler", "farming", 2, null, new Item[] {
            new Bar(BarType.IRON_BAR, 1),
            new Bar(BarType.GOLD_BAR, 1)
    }),

    IridiumSprinkler("Iridium Sprinkler", "farming", 3, null, new Item[] {
            new Bar(BarType.GOLD_BAR, 1),
            new Bar(BarType.IRIDIUM_BAR, 1)
    }),

    CharcoalKiln("Charcoal Kiln", "foraging", 1, null, new Item[] {
            //new ForagingCrop(ForagingCropType.WOOD, 20),
            new Bar(BarType.COPPER_BAR, 2)
    }),

    Furnace("Furnace", null, 0, null, new Item[] {
            new Mineral(MineralTypes.COPPER, 20),
            //new Mineral(MineralTypes.STONE, 25)
    }),

    Scarecrow("Scarecrow", null, 0, null, new Item[] {
            //new ForagingCrop(ForagingCropType.WOOD, 50),
            new Mineral(MineralTypes.COAL, 1),
            //new ForagingCrop(ForagingCropType.FIBER, 20)
    }),

    DeluxeScarecrow("Deluxe Scarecrow", "farming", 2, null, new Item[] {
            //new ForagingCrop(ForagingCropType.WOOD, 50),
            new Mineral(MineralTypes.COAL, 1),
            //new ForagingCrop(ForagingCropType.FIBER, 20),
            new Mineral(MineralTypes.IRIDIUM, 1)
    }),

    BeeHouse("Bee House", "farming", 1, null, new Item[] {
            //new ForagingCrop(ForagingCropType.WOOD, 40),
            new Mineral(MineralTypes.COAL, 8),
            new Bar(BarType.IRON_BAR, 1)
    }),

    CheesePress("Cheese Press", "farming", 2, null, new Item[] {
            //new ForagingCrop(ForagingCropType.WOOD, 45),
            //new Mineral(MineralTypes.STONE, 45),
            new Bar(BarType.COPPER_BAR, 1)
    }),

    Keg("Keg", "farming", 3, null, new Item[] {
            //new ForagingCrop(ForagingCropType.WOOD, 30),
            new Bar(BarType.COPPER_BAR, 1),
            new Bar(BarType.IRON_BAR, 1)
    }),

    Loom("Loom", "farming", 3, null, new Item[] {
            //new ForagingCrop(ForagingCropType.WOOD, 60),
            //new ForagingCrop(ForagingCropType.FIBER, 30)
            new Mineral(MineralTypes.COAL, 14),
    }),

    MayonnaiseMachine("Mayonnaise Machine", null, 0, null, new Item[] {
            //new ForagingCrop(ForagingCropType.WOOD, 15),
            //new Mineral(MineralTypes.STONE, 15),
            new Bar(BarType.COPPER_BAR, 1)
    }),

    OilMaker("Oil Maker", "farming", 3, null, new Item[] {
            //new ForagingCrop(ForagingCropType.WOOD, 100),
            new Bar(BarType.GOLD_BAR, 1),
            new Bar(BarType.IRON_BAR, 1)
    }),

    PreservesJar("Preserve Jar", "farming", 2, null, new Item[] {
            //new ForagingCrop(ForagingCropType.WOOD, 50),
            //new Mineral(MineralTypes.STONE, 40),
            new Mineral(MineralTypes.COAL, 8)
    }),

    Dehydrator("Dehydrator", null, 0, "Dehydrator Recipe", new Item[] {
            //new ForagingCrop(ForagingCropType.WOOD, 30),
            //new Mineral(MineralTypes.STONE, 20),
            //new ForagingCrop(ForagingCropType.FIBER, 30)
            new Mineral(MineralTypes.COAL, 14)
    }),

    GrassStarter("Grass Starter", null, 0, "Grass Starter Recipe", new Item[] {
            //new ForagingCrop(ForagingCropType.WOOD, 1),
            //new ForagingCrop(ForagingCropType.FIBER, 1)
            new Mineral(MineralTypes.COAL, 14)
    }),

    FishSmoker("Fish Smoker", null, 0, "Fish Smoker Recipe", new Item[] {
            //new ForagingCrop(ForagingCropType.WOOD, 50),
            new Bar(BarType.IRON_BAR, 3),
            new Mineral(MineralTypes.COAL, 10)
    }),

    MysticTreeSeed("Mystic Tree Seed", "foraging", 4, null, new Item[] {
            new Seed(SeedType.PINE_TREE, 5),
            new Seed(SeedType.MAPLE_TREE, 5),
            new Seed(SeedType.PINE_TREE, 5),
            new Seed(SeedType.MAHOGANY_TREE, 5)
    });

    private final String name;
    private final String abilityType;
    private final Integer abilityLevel;
    private final String requiredRecipe;
    private final ArrayList<Item> ingredients;

    CraftingType(String name, String abilityType, int abilityLevel, String requiredRecipe, Item[] ingredients) {
        this.name = name;
        this.abilityType = abilityType;
        this.abilityLevel = abilityLevel;
        this.requiredRecipe = requiredRecipe;
        this.ingredients = new ArrayList<>(Arrays.asList(ingredients));
    }

    public String getName() {
        return name;
    }
    public String getAbilityType() {return abilityType;}
    public Integer getAbilityLevel() {return abilityLevel;}
    public String getRequiredRecipe() {return requiredRecipe;}
    public ArrayList<Item> getIngredients() {return new ArrayList<>(ingredients);}
}
