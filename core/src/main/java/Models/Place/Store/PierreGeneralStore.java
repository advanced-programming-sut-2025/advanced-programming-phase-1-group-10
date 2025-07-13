package Models.Place.Store;

import Models.Cooking.Grocery;
import Models.Planets.Seed;
import Models.Planets.SeedType;
import Models.Position;
import Models.Recipe.Recipe;
import Models.Recipe.RecipeType;
import Models.Cooking.GroceryType;
import Models.Seller;
import Models.Tile;

public class PierreGeneralStore extends Store {

    public PierreGeneralStore(Position position, int height, int width, Seller seller, int openHour, int closeHour) {
        super(position, height, width, seller, openHour, closeHour);
        initializeProducts();
    }

    public void initializeProducts() {

// Grocery Items (unlimited)
        products.add(new ProductOffering("Item", new Grocery(GroceryType.RICE, 1), null, null, null, "Rice", 200, Integer.MAX_VALUE));
        products.add(new ProductOffering("Item", new Grocery(GroceryType.WHEAT_FLOUR, 1), null, null, null, "Wheat Flour", 100, Integer.MAX_VALUE));
        products.add(new ProductOffering("Item", new Grocery(GroceryType.SUGAR, 1), null, null, null, "Sugar", 100, Integer.MAX_VALUE));
        products.add(new ProductOffering("Item", new Grocery(GroceryType.OIL, 1), null, null, null, "Oil", 200, Integer.MAX_VALUE));
        products.add(new ProductOffering("Item", new Grocery(GroceryType.VINEGAR, 1), null, null, null, "Vinegar", 200, Integer.MAX_VALUE));

// Special Items
        //products.add(new ProductOffering("Item", new Item(ItemType.Bouquet, 1), null, null, null, "Bouquet", 1000, 2));
        //products.add(new ProductOffering("Item", new Item(ItemType.WeddingRing, 1), null, null, null, "Wedding Ring", 10000, 2));

// Recipe Items
        products.add(new ProductOffering("Item", new Recipe(RecipeType.DehydratorRecipe, 1), null, null, null, "Dehydrator Recipe", 10000, 1));
        products.add(new ProductOffering("Item", new Recipe(RecipeType.GrassStarterRecipe, 1), null, null, null, "Grass Starter Recipe", 1000, 1));

        products.add(new ProductOffering("Item", new Seed(SeedType.PARSNIP_SEED, 1), null, null, null, "Parsnip seed", 30, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.BEAN_STARTER_SEED, 1), null, null, null, "Bean starter seed", 90, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.CAULIFLOWER_SEED, 1), null, null, null, "Cauliflower seed", 120, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.POTATO_SEED, 1), null, null, null, "Potato seed", 75, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.TULIP_BULB_SEED, 1), null, null, null, "Tulip bulb seed", 30, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.KALE_SEED, 1), null, null, null, "Kale seed", 105, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.JAZZ_SEED, 1), null, null, null, "Jazz seed", 45, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.GARLIC_SEED, 1), null, null, null, "Garlic seed", 60, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.RICE_SHOOT_SEED, 1), null, null, null, "Rice shoot seed", 60, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.MELON_SEED, 1), null, null, null, "Melon seed", 120, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.TOMATO_SEED, 1), null, null, null, "Tomato seed", 75, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.BLUEBERRY_SEED, 1), null, null, null, "Blueberry seed", 120, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.PEPPER_SEED, 1), null, null, null, "Pepper seed", 60, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.WHEAT_SEED, 1), null, null, null, "Wheat seed", 15, 5));
        //products.add(new ProductOffering("Item", new Seed(SeedType.RADISH_SEED, 1), null, null, null, "Radish seed", 60, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.POPPY_SEED, 1), null, null, null, "Poppy seed", 150, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.SPANGLE_SEED, 1), null, null, null, "Spangle seed", 75, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.HOP_STARTER_SEED, 1), null, null, null, "Hops starter seed", 90, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.CORN_SEED, 1), null, null, null, "Corn seed", 225, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.SUNFLOWER_SEED, 1), null, null, null, "Sunflower seed", 300, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.RED_CABBAGE_SEED, 1), null, null, null, "Red Cabbage Seed", 150, 5));

        products.add(new ProductOffering("Item", new Seed(SeedType.EGGPLANT_SEED, 1), null, null, null, "Eggplant seed", 30, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.PUMPKIN_SEED, 1), null, null, null, "Pumpkin seed", 150, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.BOK_CHOY_SEED, 1), null, null, null, "Bok choy seed", 75, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.YAM_SEED, 1), null, null, null, "Yam seed", 90, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.CRANBERRY_SEED, 1), null, null, null, "Cranberry seed", 360, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.FAIRY_SEED, 1), null, null, null, "Fairy seed", 300, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.AMARANTH_SEED, 1), null, null, null, "Amaranth seed", 105, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.GRAPE_STARTER_SEED, 1), null, null, null, "Grape starter seed", 90, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.ARTICHOKE_SEED, 1), null, null, null, "Artichoke seed", 45, 5));
    }

    @Override
    public String getSymbol() {
        return super.getSymbol();
    }

    @Override
    public int getCloseHour() {
        return super.getCloseHour();
    }

    @Override
    public int getOpenHour() {
        return super.getOpenHour();
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }

    @Override
    public int getWidth() {
        return super.getWidth();
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public Seller getSeller() {
        return super.getSeller();
    }

    @Override
    public Tile[][] getPlaceTiles() {
        return super.getPlaceTiles();
    }

}
