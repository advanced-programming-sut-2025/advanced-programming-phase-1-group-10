package Common.Models.Planets;

import Common.Models.DateTime.Season;
import Common.Models.Planets.Crop.CropType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TreeCropType implements CropType {
    APRICOT_TREE("Apricot", "Apricot Sapling", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.APRICOT, 1, 59, true, 38, 17, List.of(Season.SPRING)),
    CHERRY_TREE("Cherry", "Cherry Sapling", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.CHERRY, 1, 80, true, 38, 17, List.of(Season.SPRING)),
    BANANA_TREE("Banana", "Banana Sapling", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.BANANA, 1, 150, true, 75, 33, List.of(Season.SUMMER)),
    MANGO_TREE("Mango", "Mango Sapling", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.MANGO, 1, 130, true, 100, 45, List.of(Season.SUMMER)),
    ORANGE_TREE("Orange", "Orange Sapling", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.ORANGE, 1, 100, true, 38, 17, List.of(Season.SUMMER)),
    PEACH_TREE("Peach", "Peach Sapling", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.PEACH, 1, 140, true, 38, 17, List.of(Season.SUMMER)),
    APPLE_TREE("Apple", "Apple Sapling", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.APPLE, 1, 100, true, 38, 17, List.of(Season.FALL)),
    POMEGRANATE_TREE("Pomegranate", "Pomegranate Sapling", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.POMEGRANATE, 1, 140, true, 38, 17, List.of(Season.FALL)),
    OAK_TREE("Oak", "Acorns", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.OAK_RESIN, 7, 150, false, 0, 0, List.of(Season.SPRING, Season.SUMMER, Season.FALL)),
    MAPLE_TREE("Maple", "Maple Seeds", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.MAPLE_SYRUP, 9, 200, false, 0, 0, List.of(Season.SPRING, Season.SUMMER, Season.FALL)),
    PINE_TREE("Pine", "Pine Cones", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.PINE_TAR, 5, 100, false, 0, 0, List.of(Season.SPRING, Season.SUMMER, Season.FALL)),
    MAHOGANY_TREE("Mahogany", "Mahogany Seeds", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.SAP, 1, 2, true, -2, 0, List.of(Season.SPRING, Season.SUMMER, Season.FALL)),
    MUSHROOM_TREE("Mushroom", "Mushroom Tree Seeds", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.COMMON_MUSHROOM, 1, 40, true, 38, 17, List.of(Season.SPRING, Season.SUMMER, Season.FALL)),
    MYSTIC_TREE("Mystic", "Mystic Tree Seeds", new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28, FruitType.MYSTIC_SYRUP, 7, 1000, true, 500, 225, List.of(Season.SPRING, Season.SUMMER, Season.FALL));

    private final String name;
    private final String source;
    private final ArrayList<Integer> stages;
    private final int totalHarvestTime;
    private final FruitType fruitType;
    private final int fruitHarvestCycle;
    private final int fruitBaseSellPrice;
    private final boolean isFruitEdible;
    private final int fruitEnergy;
    private final int fruitBaseHealth;
    private final List<Season> seasons;

    TreeCropType(String name, String source, ArrayList<Integer> stages, int totalHarvestTime,
                 FruitType fruitType, int fruitHarvestCycle, int fruitBaseSellPrice,
                 boolean isFruitEdible, int fruitEnergy, int fruitBaseHealth, List<Season> seasons) {
        this.name = name;
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.fruitType = fruitType;
        this.fruitHarvestCycle = fruitHarvestCycle;
        this.fruitBaseSellPrice = fruitBaseSellPrice;
        this.isFruitEdible = isFruitEdible;
        this.fruitEnergy = fruitEnergy;
        this.fruitBaseHealth = fruitBaseHealth;
        this.seasons = seasons;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public ArrayList<Integer> getStages() {
        return stages;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public FruitType getFruitType() {
        return fruitType;
    }

    public int getFruitHarvestCycle() {
        return fruitHarvestCycle;
    }

    public int getFruitBaseSellPrice() {
        return fruitBaseSellPrice;
    }

    public boolean isFruitEdible() {
        return isFruitEdible;
    }

    public int getFruitEnergy() {
        return fruitEnergy;
    }

    public int getFruitBaseHealth() {
        return fruitBaseHealth;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public static TreeCropType getTreeCropTypeByName(String name) {
        for (TreeCropType treeCropType : TreeCropType.values()) {
            if (treeCropType.name.equalsIgnoreCase(name) || treeCropType.source.equalsIgnoreCase(name)) {
                return treeCropType;
            }
        }
        return null;
    }
}
