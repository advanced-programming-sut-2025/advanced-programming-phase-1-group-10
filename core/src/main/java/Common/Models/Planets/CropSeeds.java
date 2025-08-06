package Common.Models.Planets;

import Common.Models.Planets.Crop.CropTypeNormal;

public enum CropSeeds {
    JAZZ_SEED,
    CARROT_SEED,
    CAULIFLOWER_SEED,
    COFFEE_SEED,
    GARLIC_SEED,
    BEAN_STARTER,
    KALE_SEED,
    PARSNIP_SEED,
    POTATO_SEED,
    RHUBARB_SEED,
    STRAWBERRY_SEED,
    TULIP_BULB,
    RICE_SHOT,
    BLUEBERRY_SEED,
    CORN_SEED,
    HOPS_STARTER,
    PEPPER_SEED,
    MELON_SEED,
    POPPY_SEED,
    RADISH_SEED,
    RED_CABBAGE_SEED,
    STAR_FRUIT_SEED,
    SPANGLE_SEED,
    SUMMER_SQUASH_SEED,
    SUNFLOWER_SEED,
    TOMATO_SEED,
    WHEAT_SEED,
    AMARANTH_SEED,
    ARTICHOKE_SEED,
    BEET_SEED,
    BOK_CHOY_SEED,
    BROCCOLI_SEED,
    CRANBERRY_SEED,
    EGGPLANT_SEED,
    FAIRY_SEED,
    GRAPE_STARTER,
    PUMPKIN_STARTER,
    YAM_SEED,
    RARE_SEED,
    POWDER_MELON_SEED,
    ANCIENT_SEED;

    public static CropTypeNormal cropOfThisSeed(SeedType seed) {
        switch (seed) {
            case JAZZ_SEED: return CropTypeNormal.BLUE_JAZZ;
            case CARROT_SEED: return CropTypeNormal.CARROT;
            case CAULIFLOWER_SEED: return CropTypeNormal.CAULIFLOWER;
            case COFFEE_BEAN_SEED: return CropTypeNormal.COFFEE_BEAN;
            case GARLIC_SEED: return CropTypeNormal.GARLIC;
            case KALE_SEED: return CropTypeNormal.KALE;
            case PARSNIP_SEED: return CropTypeNormal.PARSNIP;
            case POTATO_SEED: return CropTypeNormal.POTATO;
            case RHUBARB_SEED: return CropTypeNormal.RHUBARB;
            case STRAWBERRY_SEED: return CropTypeNormal.STRAWBERRY;
            case TULIP_BULB_SEED: return CropTypeNormal.TULIP;
            case RICE_SHOOT_SEED: return CropTypeNormal.UNMILLED_RICE;
            case BLUEBERRY_SEED: return CropTypeNormal.BLUEBERRY;
            case CORN_SEED: return CropTypeNormal.CORN;
            case HOP_STARTER_SEED: return CropTypeNormal.HOPS;
            case PEPPER_SEED: return CropTypeNormal.HOT_PEPPER;
            case MELON_SEED: return CropTypeNormal.MELON;
            case POPPY_SEED: return CropTypeNormal.POPPY;
            //case RADISH_SEED: return CropTypeNormal.RADISH;
            case RED_CABBAGE_SEED: return CropTypeNormal.RED_CABBAGE;
            case STARFRUIT_SEED: return CropTypeNormal.STARFRUIT;
            case SPANGLE_SEED: return CropTypeNormal.SUMMER_SPANGLE;
            //case SUMMER_SQUASH_SEED: return CropTypeNormal.SUMMER_SQUASH;
            case SUNFLOWER_SEED: return CropTypeNormal.SUNFLOWER;
            case TOMATO_SEED: return CropTypeNormal.TOMATO;
            case WHEAT_SEED: return CropTypeNormal.WHEAT;
            case AMARANTH_SEED: return CropTypeNormal.AMARANTH;
            case ARTICHOKE_SEED: return CropTypeNormal.ARTICHOKE;
            case BEET_SEED: return CropTypeNormal.BEET;
            case BOK_CHOY_SEED: return CropTypeNormal.BOK_CHOY;
            case BROCCOLI_SEED: return CropTypeNormal.BROCCOLI;
            case CRANBERRY_SEED: return CropTypeNormal.CRANBERRY;
            case EGGPLANT_SEED: return CropTypeNormal.EGGPLANT;
            case FAIRY_SEED: return CropTypeNormal.FAIRY_ROSE;
            case GRAPE_STARTER_SEED: return CropTypeNormal.GRAPE;
            case BEAN_STARTER_SEED: return CropTypeNormal.PUMPKIN;
            case YAM_SEED: return CropTypeNormal.YAM;
            case RARE_SEED: return CropTypeNormal.SWEET_GEM_BERRY;
            case POWDERMELON_SEED: return CropTypeNormal.POWDERMELON;
            case ANCIENT_SEED: return CropTypeNormal.ANCIENT_FRUIT;
            default: return null;
        }
    }

    public static CropSeeds getByName(String name) {
        String normalized = name.trim().toLowerCase().replace('_', ' ');
        switch (normalized) {
            case "jazz seed": return CropSeeds.JAZZ_SEED;
            case "carrot seed": return CropSeeds.CARROT_SEED;
            case "cauliflower seed": return CropSeeds.CAULIFLOWER_SEED;
            case "coffee seed": return CropSeeds.COFFEE_SEED;
            case "garlic seed": return CropSeeds.GARLIC_SEED;
            case "bean starter": return CropSeeds.BEAN_STARTER;
            case "kale seed": return CropSeeds.KALE_SEED;
            case "parsnip seed": return CropSeeds.PARSNIP_SEED;
            case "potato seed": return CropSeeds.POTATO_SEED;
            case "rhubarb seed": return CropSeeds.RHUBARB_SEED;
            case "strawberry seed": return CropSeeds.STRAWBERRY_SEED;
            case "tulip bulb": return CropSeeds.TULIP_BULB;
            case "rice shot": return CropSeeds.RICE_SHOT;
            case "blueberry seed": return CropSeeds.BLUEBERRY_SEED;
            case "corn seed": return CropSeeds.CORN_SEED;
            case "hops starter": return CropSeeds.HOPS_STARTER;
            case "pepper seed": return CropSeeds.PEPPER_SEED;
            case "melon seed": return CropSeeds.MELON_SEED;
            case "poppy seed": return CropSeeds.POPPY_SEED;
            case "radish seed": return CropSeeds.RADISH_SEED;
            case "red cabbage seed": return CropSeeds.RED_CABBAGE_SEED;
            case "star fruit seed": return CropSeeds.STAR_FRUIT_SEED;
            case "spangle seed": return CropSeeds.SPANGLE_SEED;
            case "summer squash seed": return CropSeeds.SUMMER_SQUASH_SEED;
            case "sunflower seed": return CropSeeds.SUNFLOWER_SEED;
            case "tomato seed": return CropSeeds.TOMATO_SEED;
            case "wheat seed": return CropSeeds.WHEAT_SEED;
            case "amaranth seed": return CropSeeds.AMARANTH_SEED;
            case "artichoke seed": return CropSeeds.ARTICHOKE_SEED;
            case "beet seed": return CropSeeds.BEET_SEED;
            case "bok choy seed": return CropSeeds.BOK_CHOY_SEED;
            case "broccoli seed": return CropSeeds.BROCCOLI_SEED;
            case "cranberry seed": return CropSeeds.CRANBERRY_SEED;
            case "eggplant seed": return CropSeeds.EGGPLANT_SEED;
            case "fairy seed": return CropSeeds.FAIRY_SEED;
            case "grape starter": return CropSeeds.GRAPE_STARTER;
            case "pumpkin starter": return CropSeeds.PUMPKIN_STARTER;
            case "yam seed": return CropSeeds.YAM_SEED;
            case "rare seed": return CropSeeds.RARE_SEED;
            case "powder melon seed": return CropSeeds.POWDER_MELON_SEED;
            case "ancient seed": return CropSeeds.ANCIENT_SEED;
            default: return null;
        }
    }
}
