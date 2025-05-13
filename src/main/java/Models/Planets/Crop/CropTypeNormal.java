package Models.Planets.Crop;

import Models.DateTime.Season;
import Models.Item;
import Models.Planets.SeedType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CropTypeNormal implements CropType {

    BLUE_JAZZ("Blue Jazz", SeedType.JAZZ_SEED, new ArrayList<>(Arrays.asList(1, 2, 2, 2)), 7, true, -1, 50, true, 45, List.of(Season.SPRING), false),
    CARROT("Carrot", SeedType.CARROT_SEED, new ArrayList<>(Arrays.asList(1, 1, 1)), 3, true, -1, 35, true, 75, List.of(Season.SPRING), false),
    CAULIFLOWER("Cauliflower", SeedType.CAULIFLOWER_SEED, new ArrayList<>(Arrays.asList(1, 2, 4, 4, 1)), 12, true, -1, 175, true, 75, List.of(Season.SPRING), true),
    COFFEE_BEAN("Coffee Bean", SeedType.COFFEE_BEAN_SEED, new ArrayList<>(Arrays.asList(1, 2, 2, 3, 2)), 10, false, 2, 15, false, -1, Arrays.asList(Season.SPRING,Season.SUMMER), false),
    GARLIC("Garlic", SeedType.GARLIC_SEED, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 4, true, -1, 60, true, 20, List.of(Season.SPRING), false),
    GREEN_BEAN("Green Bean", SeedType.BEAN_STARTER_SEED, new ArrayList<>(Arrays.asList(1, 1, 1, 3, 4)), 10, false, 3, 40, true, 25, List.of(Season.SPRING), false),
    KALE("Kale", SeedType.KALE_SEED, new ArrayList<>(Arrays.asList(1, 2, 2, 1)), 6, true, -1, 110, true, 50, List.of(Season.SPRING), false),
    PARSNIP("Parsnip", SeedType.PARSNIP_SEED, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 4, true, -1, 35, true, 25, List.of(Season.SPRING), false),
    POTATO("Potato", SeedType.POTATO_SEED, new ArrayList<>(Arrays.asList(1, 1, 1, 2, 1)), 6, true, -1, 80, true, 25, List.of(Season.SPRING), false),
    RHUBARB("Rhubarb", SeedType.RHUBARB_SEED, new ArrayList<>(Arrays.asList(2, 2, 2, 3, 4)), 13, true, -1, 220, false, -1, List.of(Season.SPRING), false),
    STRAWBERRY("Strawberry", SeedType.STRAWBERRY_SEED, new ArrayList<>(Arrays.asList(1, 1, 2, 2, 2)), 8, false, 4, 120, true, 50, List.of(Season.SPRING), false),
    TULIP("Tulip", SeedType.TULIP_BULB_SEED, new ArrayList<>(Arrays.asList(1, 1, 2, 2)), 6, true, -1, 30, true, 45, List.of(Season.SPRING), false),
    UNMILLED_RICE("Unmilled Rice", SeedType.RICE_SHOOT_SEED, new ArrayList<>(Arrays.asList(1, 2, 2, 3)), 8, true, -1, 30, true, 3, List.of(Season.SPRING), false),
    BLUEBERRY("Blueberry", SeedType.BLUEBERRY_SEED, new ArrayList<>(Arrays.asList(1, 3, 3, 4, 2)), 13, false, 4, 50, true, 25, List.of(Season.SUMMER), false),
    CORN("Corn", SeedType.CORN_SEED, new ArrayList<>(Arrays.asList(2, 3, 3, 3, 3)), 14, false, 4, 50, true, 25, Arrays.asList(Season.SUMMER,Season.FALL), false),
    HOPS("Hops", SeedType.HOP_STARTER_SEED, new ArrayList<>(Arrays.asList(1, 1, 2, 3, 4)), 11, false, 1, 25, true, 45, List.of(Season.SUMMER), false),
    HOT_PEPPER("Hot Pepper", SeedType.PEPPER_SEED, new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1)), 5, false, 3, 40, true, 13, List.of(Season.SUMMER), false),
    MELON("Melon", SeedType.MELON_SEED, new ArrayList<>(Arrays.asList(1, 2, 3, 3, 3)), 12, true, -1, 250, true, 113, List.of(Season.SUMMER), true),
    POPPY("Poppy", SeedType.POPPY_SEED, new ArrayList<>(Arrays.asList(1, 2, 2, 2)), 7, true, -1, 140, true, 45, List.of(Season.SUMMER), false),
    RADISH("Radish", null, new ArrayList<>(Arrays.asList(2, 1, 2, 1)), 6, true, -1, 90, true, 45, List.of(Season.SUMMER), false),
    RED_CABBAGE("Red Cabbage", SeedType.RED_CABBAGE_SEED, new ArrayList<>(Arrays.asList(2, 1, 2, 2, 2)), 9, true, -1, 260, true, 75, List.of(Season.SUMMER), false),
    STARFRUIT("Starfruit", SeedType.STARFRUIT_SEED, new ArrayList<>(Arrays.asList(2, 3, 2, 3, 3)), 13, true, -1, 750, true, 125, List.of(Season.SUMMER), false),
    SUMMER_SPANGLE("SUMMER Spangle", SeedType.SPANGLE_SEED, new ArrayList<>(Arrays.asList(1, 2, 3, 1)), 8, true, -1, 90, true, 45, List.of(Season.SUMMER), false),
    SUMMER_SQUASH("SUMMER Squash", null, new ArrayList<>(Arrays.asList(1, 1, 1, 2, 1)), 6, false, 3, 45, true, 63, List.of(Season.SUMMER), false),
    SUNFLOWER("Sunflower", SeedType.SUNFLOWER_SEED, new ArrayList<>(Arrays.asList(1, 2, 3, 2)), 8, true, -1, 80, true, 45, List.of(Season.SUMMER), false),
    TOMATO("Tomato", SeedType.TOMATO_SEED, new ArrayList<>(Arrays.asList(2, 2, 2, 2, 3)), 11, false, 4, 60, true, 20, List.of(Season.SUMMER), false),
    WHEAT("Wheat", SeedType.WHEAT_SEED, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 4, true, -1, 25, false, -1, Arrays.asList(Season.SUMMER,Season.FALL), false),
    AMARANTH("Amaranth", SeedType.AMARANTH_SEED, new ArrayList<>(Arrays.asList(1, 2, 2, 2)), 7, true, -1, 150, true, 50, List.of(Season.FALL), false),
    ARTICHOKE("Artichoke", SeedType.ARTICHOKE_SEED, new ArrayList<>(Arrays.asList(2, 2, 1, 2, 1)), 8, true, -1, 160, true, 30, List.of(Season.FALL), false),
    BEET("Beet", SeedType.BEET_SEED, new ArrayList<>(Arrays.asList(1, 1, 2, 2)), 6, true, -1, 100, true, 30, List.of(Season.FALL), false),
    BOK_CHOY("Bok Choy", SeedType.BOK_CHOY_SEED, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 4, true, -1, 80, true, 25, List.of(Season.FALL), false),
    BROCCOLI("Broccoli", SeedType.BROCCOLI_SEED, new ArrayList<>(Arrays.asList(2, 2, 2, 2)), 8, false, 4, 70, true, 63, List.of(Season.FALL), false),
    CRANBERRIES("Cranberries", SeedType.CRANBERRY_SEED, new ArrayList<>(Arrays.asList(1, 2, 1, 1, 2)), 7, false, 5, 75, true, 38, List.of(Season.FALL), false),
    EGGPLANT("Eggplant", SeedType.EGGPLANT_SEED, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 5, false, 5, 60, true, 20, List.of(Season.FALL), false),
    FAIRY_ROSE("Fairy Rose", SeedType.FAIRY_SEED, new ArrayList<>(Arrays.asList(1, 4, 4, 3)), 12, true, -1, 290, true, 45, List.of(Season.FALL), false),
    GRAPE("Grape", SeedType.GRAPE_STARTER_SEED, new ArrayList<>(Arrays.asList(1, 1, 2, 3, 3)), 10, false, 3, 80, true, 38, List.of(Season.FALL), false),
    PUMPKIN("Pumpkin", SeedType.PUMPKIN_SEED, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 3)), 13, true, -1, 320, false, -1, List.of(Season.FALL), true),
    YAM("Yam", SeedType.YAM_SEED, new ArrayList<>(Arrays.asList(1, 3, 3, 3)), 10, true, -1, 160, true, 45, List.of(Season.FALL), false),
    SWEET_GEM_BERRY("Sweet Gem Berry", SeedType.RARE_SEED, new ArrayList<>(Arrays.asList(2, 4, 6, 6, 6)), 24, true, -1, 3000, false, -1, List.of(Season.FALL), false),
    POWDERMELON("Powdermelon", SeedType.POWDERMELON_SEED, new ArrayList<>(Arrays.asList(1, 2, 1, 2, 1)), 7, true, -1, 60, true, 63, List.of(Season.WINTER), true),
    ANCIENT_FRUIT("Ancient Fruit", SeedType.ANCIENT_SEED, new ArrayList<>(Arrays.asList(2, 7, 7, 7, 5)), 28, false, 7, 550, false, -1, Arrays.asList(Season.SPRING,Season.SUMMER,Season.FALL), false);

    private final String name;
    private final SeedType source;
    private final ArrayList<Integer> cropTypes;
    private final int totalHarvestTime;
    private final boolean oneTime;
    private final int regrowthTime;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final List<Season> seasons;
    private final boolean canBecomeGiant;

    CropTypeNormal(String name, SeedType source, ArrayList<Integer> cropTypes, int totalHarvestTime, boolean oneTime, int regrowthTime, int baseSellPrice, boolean isEdible, int energy, List<Season> season, boolean canBecomeGiant) {
        this.name = name;
        this.source = source;
        this.cropTypes = cropTypes;
        this.totalHarvestTime = totalHarvestTime;
        this.oneTime = oneTime;
        this.regrowthTime = regrowthTime;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.seasons = season;
        this.canBecomeGiant = canBecomeGiant;
    }

    @Override
    public String getName() {
        return name;
    }

    public SeedType getSource() {
        return source;
    }

    public ArrayList<Integer> getCropTypes() {
        return cropTypes;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public int getRegrowthTime() {
        return regrowthTime;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public int getEnergy() {
        return energy;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    public static CropTypeNormal getCropTypeByName(String name){
        for(CropTypeNormal cropTypeNormal : CropTypeNormal.values()){
            if(cropTypeNormal.name.equalsIgnoreCase(name)){
                return cropTypeNormal;
            }
        }
        return null;
    }
}