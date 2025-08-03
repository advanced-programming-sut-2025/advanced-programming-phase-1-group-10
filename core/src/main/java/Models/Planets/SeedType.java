package Models.Planets;

import Models.DateTime.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum SeedType {
    JAZZ_SEED("Jazz Seeds", Season.SPRING),
    CARROT_SEED("Carrot Seeds", Season.SPRING),
    CAULIFLOWER_SEED("Cauliflower Seeds", Season.SPRING),
    COFFEE_BEAN_SEED("Coffee bean Seeds", Season.SPRING),
    GARLIC_SEED("Garlic Seeds", Season.SPRING),
    BEAN_STARTER_SEED("Bean starter Seeds", Season.SPRING),
    KALE_SEED("Kale Seeds", Season.SPRING),
    PARSNIP_SEED("Parsnip Seeds", Season.SPRING),
    RADISH_SEED("Radish Seeds", Season.SUMMER),
    POTATO_SEED("Potato Seeds", Season.SPRING),
    SUMMER_SQUASH("Summer Squash Seeds", Season.SUMMER),
    RHUBARB_SEED("Rhubarb Seeds", Season.SPRING),
    STRAWBERRY_SEED("Strawberry Seeds", Season.SPRING),
    TULIP_BULB_SEED("Tulip bulb Seeds", Season.SPRING),
    RICE_SHOOT_SEED("Rice shoot Seeds", Season.SPRING),
    BLUEBERRY_SEED("Blueberry Seeds", Season.SUMMER),
    CORN_SEED("Corn Seeds", Season.SUMMER),
    HOP_STARTER_SEED("Hop starter Seeds", Season.SUMMER),
    PEPPER_SEED("Pepper Seeds", Season.SUMMER),
    MELON_SEED("Melon Seeds", Season.SUMMER),
    POPPY_SEED("Poppy Seeds", Season.SUMMER),
    RED_CABBAGE_SEED("Red Cabbage Seeds", Season.SUMMER),
    STARFRUIT_SEED("Starfruit Seeds", Season.SUMMER),
    SPANGLE_SEED("Spangle Seeds", Season.SUMMER),
    SUNFLOWER_SEED("Sunflower Seeds", Season.SUMMER),
    TOMATO_SEED("Tomato Seeds", Season.SUMMER),
    WHEAT_SEED("Wheat Seeds", Season.SUMMER),
    AMARANTH_SEED("Amaranth Seeds", Season.FALL),
    ARTICHOKE_SEED("Artichoke Seeds", Season.FALL),
    BEET_SEED("Beet Seeds", Season.FALL),
    BOK_CHOY_SEED("Bok choy Seeds", Season.FALL),
    BROCCOLI_SEED("Broccoli Seeds", Season.FALL),
    CRANBERRY_SEED("Cranberry Seeds", Season.FALL),
    EGGPLANT_SEED("Eggplant Seeds", Season.FALL),
    FAIRY_SEED("Fairy Seeds", Season.FALL),
    GRAPE_STARTER_SEED("Grape starter Seeds", Season.FALL),
    PUMPKIN_SEED("Pumpkin Seeds", Season.FALL),
    YAM_SEED("Yam Seeds", Season.FALL),
    RARE_SEED("Rare Seeds", Season.FALL),
    POWDERMELON_SEED("Powdermelon Seeds", Season.WINTER),
    ANCIENT_SEED("Ancient Seeds", (Season) null),
    MIXED_SEED("Mixed Seeds", (Season) null),

    // Tree Seeds
    APRICOT_SAPLING("Apricot Sapling", TreeCropType.APRICOT_TREE),
    CHERRY_SAPLING("Cherry Sapling", TreeCropType.CHERRY_TREE),
    BANANA_SAPLING("Banana Sapling", TreeCropType.BANANA_TREE),
    MANGO_SAPLING("Mango Sapling", TreeCropType.MANGO_TREE),
    ORANGE_SAPLING("Orange Sapling", TreeCropType.ORANGE_TREE),
    PEACH_SAPLING("Peach Sapling", TreeCropType.PEACH_TREE),
    APPLE_SAPLING("Apple Sapling", TreeCropType.APPLE_TREE),
    POMEGRANATE_SAPLING("Pomegranate Sapling", TreeCropType.POMEGRANATE_TREE),
    OAK_TREE("Acorns", TreeCropType.OAK_TREE),
    MAPLE_TREE("Maple Seeds", TreeCropType.MAPLE_TREE),
    PINE_TREE("Pine Cones", TreeCropType.PINE_TREE),
    MAHOGANY_TREE("Mahogany Seeds", TreeCropType.MAHOGANY_TREE),
    MUSHROOM_TREE("Mushroom Tree Seeds", TreeCropType.MUSHROOM_TREE),
    MYSTIC_TREE("Mystic Tree Seeds", TreeCropType.MYSTIC_TREE);

    private final String name;
    private final Season season;
    private final TreeCropType treeCropType;

    SeedType(String name, Season season) {
        this.name = name;
        this.season = season;
        this.treeCropType = null;
    }

    SeedType(String name, TreeCropType treeCropType) {
        this.name = name;
        this.season = treeCropType.getSeasons().get(0);
        this.treeCropType = treeCropType;
    }


    public String getName() {
        return name;
    }

    public Season getSeason() {
        return season;
    }

    public TreeCropType getTreeCropType() {
        return treeCropType;
    }

    public static SeedType getSeedByName(String name){
        for(SeedType seedType : SeedType.values()){
            if(seedType.name.equalsIgnoreCase(name))
                return seedType;
        }
        return null;
    }
}
