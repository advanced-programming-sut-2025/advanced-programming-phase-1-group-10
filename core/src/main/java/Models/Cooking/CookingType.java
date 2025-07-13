package Models.Cooking;

import Models.Animal.AnimalProduct;
import Models.Animal.AnimalProductType;
import Models.Animal.Fish;
import Models.Animal.FishType;
import Models.Artisan.ArtisanProduct;
import Models.Artisan.ArtisanProductType;
import Models.Item;
import Models.Planets.Crop.Crop;
import Models.Planets.Crop.CropTypeNormal;
import Models.Planets.Crop.ForagingCropType;
import Models.Planets.Fruit;
import Models.Planets.FruitType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CookingType {
    FRIED_EGG("Fried Egg", 50, null, 0, null, 35, new Item[]{
            new AnimalProduct(AnimalProductType.EGG, 1)
    }),

    BAKED_FISH("Baked Fish", 75, null, 0, null, 100, new Item[]{
            new Fish(FishType.SARDINE, 1),
            new Fish(FishType.SALMON, 1),
            new Crop(CropTypeNormal.UNMILLED_RICE, 1)
    }),

    SALAD("Salad", 113, null, 0, null, 110, new Item[]{
            new Crop(ForagingCropType.LEEK, 1),
            new Crop(ForagingCropType.DANDELION, 1)
    }),

    OMELET("Omelet", 100, null, 0, "Omelet Recipe", 125, new Item[]{
            new AnimalProduct(AnimalProductType.EGG, 1),
            new AnimalProduct(AnimalProductType.COW_MILK, 1)
    }),

    PUMPKIN_PIE("Pumpkin Pie", 225, null, 0, "Pumpkin Pie Recipe", 385, new Item[]{
            new Crop(CropTypeNormal.PUMPKIN, 1),
            new Crop(CropTypeNormal.UNMILLED_RICE, 1),
            new AnimalProduct(AnimalProductType.COW_MILK, 1),
            new Crop(CropTypeNormal.SUMMER_SQUASH, 1)
    }),

    SPAGHETTI("Spaghetti", 75, null, 0, "Spaghetti Recipe", 120, new Item[]{
            new Crop(CropTypeNormal.UNMILLED_RICE, 1),
            new Crop(CropTypeNormal.TOMATO, 1)
    }),

    PIZZA("Pizza", 150, null, 0, "Pizza Recipe", 300, new Item[]{
            new Crop(CropTypeNormal.UNMILLED_RICE, 1),
            new Crop(CropTypeNormal.TOMATO, 1),
            new ArtisanProduct(ArtisanProductType.CHEESE, 1)
    }),

    TORTILLA("Tortilla", 50, null, 0, "Tortilla Recipe", 50, new Item[]{
            new Crop(CropTypeNormal.CORN, 1)
    }),

    MAKI_ROLL("Maki Roll", 100, null, 0, "Maki Roll Recipe", 220, new Item[]{
            new Fish(FishType.SALMON, 1),
            new Crop(CropTypeNormal.UNMILLED_RICE, 1),
            new Crop(CropTypeNormal.SUNFLOWER, 1)
    }),

    TRIPLE_SHOT_ESPRESSO("Triple Shot Espresso", 200,null, 0, "Triple Shot Espresso Recipe", 450, new Item[]{
            new ArtisanProduct(ArtisanProductType.COFFEE, 3)
    }),

    COOKIE("Cookie", 90, null, 0, "Cookie Recipe", 140, new Item[]{
            new Crop(CropTypeNormal.UNMILLED_RICE, 1),
            new Crop(CropTypeNormal.SUMMER_SQUASH, 1),
            new AnimalProduct(AnimalProductType.EGG, 1)
    }),

    HASH_BROWNS("Hash Browns", 90, null, 0, "Hashbrowns Recipe", 120, new Item[]{
            new Crop(CropTypeNormal.POTATO, 1),
            new ArtisanProduct(ArtisanProductType.OIL, 1)
    }),

    PANCAKES("Pancakes", 90, null, 0, "Pancakes Recipe", 80, new Item[]{
            new Crop(CropTypeNormal.UNMILLED_RICE, 1),
            new AnimalProduct(AnimalProductType.EGG, 1)
    }),

    FRUIT_SALAD("Fruit Salad", 263, null, 0, "Fruit Salad Recipe", 450, new Item[]{
            new Crop(CropTypeNormal.BLUEBERRY, 1),
            new Crop(CropTypeNormal.MELON, 1),
            new Fruit(FruitType.APRICOT, 1)
    }),

    RED_PLATE("Red Plate", 240, null, 0, "Red Plate Recipe", 400, new Item[]{
            new Crop(CropTypeNormal.RED_CABBAGE, 1),
            new Crop(CropTypeNormal.RADISH, 1)
    }),

    BREAD("Bread", 50, null, 0, "Bread Recipe", 60, new Item[]{
            new Crop(CropTypeNormal.UNMILLED_RICE, 1)
    }),

    SALMON_DINNER("Salmon Dinner", 125, null, 0, "Salmon Dinner Recipe", 300, new Item[]{
            new Fish(FishType.SALMON, 1),
            new Crop(CropTypeNormal.AMARANTH, 1),
            new Crop(CropTypeNormal.KALE, 1)
    }),

    VEGETABLE_MEDLEY("Vegetable Medley", 165, "Foraging" , 2,null, 120, new Item[]{
            new Crop(CropTypeNormal.TOMATO, 1),
            new Crop(CropTypeNormal.BEET, 1)
    }),

    FARMERS_LUNCH("Farmer's Lunch", 200, "Farming",1,null, 150, new Item[]{
            new Cooking(CookingType.OMELET, 1),
            new Crop(CropTypeNormal.PARSNIP, 1)
    }),

    SURVIVAL_BURGER("Survival Burger", 125, "Foraging",3,null, 180, new Item[]{
            new Cooking(CookingType.BREAD, 1),
            new Crop(CropTypeNormal.CARROT, 1),
            new Crop(CropTypeNormal.EGGPLANT, 1)
    }),

    DISH_O_THE_SEA("Dish O' The Sea", 150, "Fishing",2,null, 220, new Item[]{
            new Fish(FishType.SARDINE, 2),
            new Cooking(CookingType.HASH_BROWNS, 1)
    }),

    SEAFOAM_PUDDING("Seafoam Pudding", 175, "Fishing", 3,null, 300, new Item[]{
            new Fish(FishType.FLOUNDER, 1),
            new Fish(FishType.MIDNIGHT_CARP, 1)
    }),

    MINERS_TREAT("Miner's Treat", 125, "Mining" ,1,null, 200, new Item[]{
            new Crop(CropTypeNormal.CARROT, 2),
            new Crop(CropTypeNormal.SUMMER_SQUASH, 1),
            new AnimalProduct(AnimalProductType.COW_MILK, 1)
    });

    private final String name;
    private final int energy;
    private final String abilityType;
    private final int abilityLevel;
    private final String requiredRecipe;
    private final int price;
    private final ArrayList<Item> ingredients;

    CookingType(String name, int energy, String abilityType, int abilityLevel, String requiredRecipe,int price , Item[] items) {
        this.name = name;
        this.energy = energy;
        this.abilityType = abilityType;
        this.abilityLevel = abilityLevel;
        this.requiredRecipe = requiredRecipe;
        this.price = price;
        this.ingredients = new ArrayList<>(Arrays.asList(items));
    }

    public String getName() { return name; }
    public int getEnergy() { return energy; }
    public String getAbilityType() { return abilityType; }
    public int getAbilityLevel() { return abilityLevel; }
    public String getRequiredRecipe() { return requiredRecipe; }
    public int getPrice() { return price; }
    public List<Item> getIngredients() { return new ArrayList<>(ingredients); }
}