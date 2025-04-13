package src.Models.Cooking;

import src.Models.Item;

public enum CookingType {

    FRIED_EGG("Fried egg", "1 egg", 50, "", "Starter", 35),
    BAKED_FISH("Baked Fish", "1 Sardine + 1 Salmon + 1 wheat", 75, "", "Starter", 100),
    SALAD("Salad", "1 leek + 1 dandelion", 113, "", "Starter", 110),
    OMLELET("Olmelet", "1 egg + 1 milk", 100, "", "Stardrop Saloon", 125),
    PUMPKIN_PIE("pumpkin pie", "1 pumpking + 1 wheat flour + 1 milk + 1 sugar", 225, "", "Stardrop Saloon", 385),
    SPAGHETTI("spaghetti", "1 wheat flour + 1 tomato", 75, "", "Stardrop Saloon", 120),
    PIZZA("pizza", "1 wheat flour + 1 tomato + 1 cheese", 150, "", "Stardrop Saloon", 300),
    TORTILLA("Tortilla", "1 corn", 50, "", "Stardrop Saloon", 50),
    MAKI_ROLL("Maki Roll", "1 any fish + 1 rice + 1 fiber", 100, "", "Stardrop Saloon", 220),
    TRIPLE_SHOT_ESPRESSO("Triple Shot Espresso", "3 coffee", 200, "Max Energy + 100 (5 hours)", "Stardrop Saloon", 450),
    COOKIE("Cookie", "1 wheat flour + 1 sugar + 1 egg", 90, "", "Stardrop Saloon", 140),
    HASH_BROWNS("hash browns", "1 potato + 1 oil", 90, "Farming (5 hours)", "Stardrop Saloon", 120),
    PANCAKES("pancakes", "1 wheat flour + 1 egg", 90, "Foraging (11 hours)", "Stardrop Saloon", 80),
    FRUIT_SALAD("fruit salad", "1 blueberry + 1 melon + 1 apricot", 263, "", "Stardrop Saloon", 450),
    RED_PLATE("red plate", "1 red cabbage + 1 radish", 240, "Max Energy +50 (3 hours)", "Stardrop Saloon", 400),
    BREAD("bread", "1 wheat flour", 50, "", "Stardrop Saloon", 60),
    SALMON_DINNER("salmon dinner", "1 salmon + 1 Amaranth + 1 Kale", 125, "", "Leah reward", 300),
    VEGETABLE_MEDLEY("vegetable medley", "1 tomato + 1 beet", 165, "", "Foraging Level 2", 120),
    FARMERS_LUNCH("farmer's lunch", "1 omelet + 1 parsnip", 200, "Farming (5 hours)", "Farming level 1", 150),
    SURVIVAL_BURGER("survival burger", "1 bread + 1 carrot + 1 eggplant", 125, "Foraging (5 hours)", "Foraging level 3", 180),
    DISH_O_THE_SEA("dish O' the Sea", "2 sardines + 1 hash browns", 150, "Fishing (5 hours)", "Fishing level 2", 220),
    SEAFORM_PUDDING("seaform Pudding", "1 Flounder + 1 midnight carp", 175, "Fishing (10 hours)", "Fishing level 3", 300),
    MINERS_TREAT("miner's treat", "2 carrot + 1 suger + 1 milk", 125, "Mining (5 hours)", "Mining level 1", 200);


    private final String name;
    private final String ingredient;
    private final int energy;
    private final String buff;
    private final String source;
    private final int price;

    CookingType(String name, String ingredient, int energy, String buff, String source, int price) {
        this.name = name;
        this.ingredient = ingredient;
        this.energy = energy;
        this.buff = buff;
        this.source = source;
        this.price = price;
    }
}
