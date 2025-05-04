package Models.Planets;

import Models.DateTime.Season;
import Models.Item;

public enum SeedType {
    JAZZ_SEED("Jazz seed",Season.SPRING),
    CARROT_SEED("Carrot seed",Season.SPRING),
    CAULIFLOWER_SEED("Cauliflower seed",Season.SPRING),
    COFFEE_BEAN_SEED("Coffee bean seed",Season.SPRING),
    GARLIC_SEED("Garlic seed",Season.SPRING),
    BEAN_STARTER_SEED("Bean starter seed",Season.SPRING),
    KALE_SEED("Kale seed",Season.SPRING),
    PARSNIP_SEED("Parsnip seed",Season.SPRING),
    POTATO_SEED("Potato seed",Season.SPRING),
    RHUBARB_SEED("Rhubarb seed",Season.SPRING),
    STRAWBERRY_SEED("Strawberry seed",Season.SPRING),
    TULIP_BULB_SEED("Tulip bulb seed",Season.SPRING),
    RICE_SHOOT_SEED("Rice shoot seed",Season.SPRING),
    BLUEBERRY_SEED("Blueberry seed",Season.SUMMER),
    CORN_SEED("Corn seed",Season.SUMMER),
    HOP_STARTER_SEED("Hop starter seed",Season.SUMMER),
    PEPPER_SEED("Pepper seed",Season.SUMMER),
    MELON_SEED("Melon seed",Season.SUMMER),
    POPPY_SEED("Poppy seed",Season.SUMMER),
    RED_CABBAGE_SEED("Red Cabbage seed",Season.SUMMER),
    STARFRUIT_SEED("Starfruit seed",Season.SUMMER),
    SPANGLE_SEED("Spangle seed",Season.SUMMER),
    SUNFLOWER_SEED("Sunflower seed",Season.SUMMER),
    TOMATO_SEED("Tomato seed",Season.SUMMER),
    WHEAT_SEED("Wheat seed",Season.SUMMER),
    AMARANTH_SEED("Amaranth seed",Season.FALL),
    ARTICHOKE_SEED("Artichoke seed",Season.FALL),
    BEET_SEED("Beet seed",Season.FALL),
    BOK_CHOY_SEED("Bok choy seed",Season.FALL),
    BROCCOLI_SEED("Broccoli seed",Season.FALL),
    CRANBERRY_SEED("Cranberry seed",Season.FALL),
    EGGPLANT_SEED("Eggplant seed",Season.FALL),
    FAIRY_SEED("Fairy seed",Season.FALL),
    GRAPE_STARTER_SEED("Grape starter seed",Season.FALL),
    PUMPKIN_SEED("Pumpkin seed",Season.FALL),
    YAM_SEED("Yam seed",Season.FALL),
    RARE_SEED("Rare seed",Season.FALL),
    POWDERMELON_SEED("Powdermelon seed",Season.WINTER),
    ANCIENT_SEED("Ancient seed", null),
    MIXED_SEED("Mixed seed", null),
    ;
    private final String name;
    private final Season season;

    SeedType(String name, Season season) {
        this.name = name;
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public Season getSeason() {
        return season;
    }
}
