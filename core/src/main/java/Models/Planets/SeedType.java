package Models.Planets;

import Models.DateTime.Season;

public enum SeedType {
    JAZZ_SEED("Jazz Seeds",Season.SPRING),
    CARROT_SEED("Carrot Seeds",Season.SPRING),
    CAULIFLOWER_SEED("Cauliflower Seeds",Season.SPRING),
    COFFEE_BEAN_SEED("Coffee bean Seeds",Season.SPRING),
    GARLIC_SEED("Garlic Seeds",Season.SPRING),
    BEAN_STARTER_SEED("Bean starter Seeds",Season.SPRING),
    KALE_SEED("Kale Seeds",Season.SPRING),
    PARSNIP_SEED("Parsnip Seeds",Season.SPRING),
    RADISH_SEED("Radish Seeds",Season.SUMMER),
    POTATO_SEED("Potato Seeds",Season.SPRING),
    SUMMER_SQUASH("Summer Squash Seeds",Season.SUMMER),
    RHUBARB_SEED("Rhubarb Seeds",Season.SPRING),
    STRAWBERRY_SEED("Strawberry Seeds",Season.SPRING),
    TULIP_BULB_SEED("Tulip bulb Seeds",Season.SPRING),
    RICE_SHOOT_SEED("Rice shoot Seeds",Season.SPRING),
    BLUEBERRY_SEED("Blueberry Seeds",Season.SUMMER),
    CORN_SEED("Corn Seeds",Season.SUMMER),
    HOP_STARTER_SEED("Hop starter Seeds",Season.SUMMER),
    PEPPER_SEED("Pepper Seeds",Season.SUMMER),
    MELON_SEED("Melon Seeds",Season.SUMMER),
    POPPY_SEED("Poppy Seeds",Season.SUMMER),
    RED_CABBAGE_SEED("Red Cabbage Seeds",Season.SUMMER),
    STARFRUIT_SEED("Starfruit Seeds",Season.SUMMER),
    SPANGLE_SEED("Spangle Seeds",Season.SUMMER),
    SUNFLOWER_SEED("Sunflower Seeds",Season.SUMMER),
    TOMATO_SEED("Tomato Seeds",Season.SUMMER),
    WHEAT_SEED("Wheat Seeds",Season.SUMMER),
    AMARANTH_SEED("Amaranth Seeds",Season.FALL),
    ARTICHOKE_SEED("Artichoke Seeds",Season.FALL),
    BEET_SEED("Beet Seeds",Season.FALL),
    BOK_CHOY_SEED("Bok choy Seeds",Season.FALL),
    BROCCOLI_SEED("Broccoli Seeds",Season.FALL),
    CRANBERRY_SEED("Cranberry Seeds",Season.FALL),
    EGGPLANT_SEED("Eggplant Seeds",Season.FALL),
    FAIRY_SEED("Fairy Seeds",Season.FALL),
    GRAPE_STARTER_SEED("Grape starter Seeds",Season.FALL),
    PUMPKIN_SEED("Pumpkin Seeds",Season.FALL),
    YAM_SEED("Yam Seeds",Season.FALL),
    RARE_SEED("Rare Seeds",Season.FALL),
    POWDERMELON_SEED("Powdermelon Seeds",Season.WINTER),
    ANCIENT_SEED("Ancient Seeds", null),
    MIXED_SEED("Mixed Seeds", null),

    // Trees
    APRICOT_SAPLING("Apricot Sapling", Season.SPRING),
    CHERRY_SAPLING("Cherry Sapling", Season.SPRING),
    BANANA_SAPLING("Banana Sapling", Season.SUMMER),
    MANGO_SAPLING("Mango Sapling", Season.SUMMER),
    ORANGE_SAPLING("Orange Sapling", Season.SUMMER),
    PEACH_SAPLING("Peach Sapling", Season.SUMMER),
    APPLE_SAPLING("Apple Sapling", Season.FALL),
    POMEGRANATE_SAPLING("Pomegranate Sapling", Season.FALL),
    MUSHROOM_TREE_SEEDS("Mushroom Tree Seeds", Season.SPRING),
    MYSTIC_TREE_SEEDS("Mystic Tree Seeds", Season.SPRING),
    ACORNS("Acorns", null),
    MAPLE_SEED("Maple Seeds", null),
    PINE_CONE("Pine Cones", null),
    MAHOGANY_SEED("Mahogany Seeds", null)
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

    public static SeedType getSeedByName(String name){
        for(SeedType seedType : SeedType.values()){
            if(seedType.name.equalsIgnoreCase(name))
                return seedType;
        }
        return null;
    }
}
