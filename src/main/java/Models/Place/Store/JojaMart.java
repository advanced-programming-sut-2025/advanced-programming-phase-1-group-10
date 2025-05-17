package Models.Place.Store;

import Models.Cooking.Grocery;
import Models.Cooking.GroceryType;
import Models.Crafting.Crafting;
import Models.Crafting.CraftingType;
import Models.Planets.Seed;
import Models.Planets.SeedType;
import Models.Position;
import Models.Seller;
import Models.Tile;

public class JojaMart extends Store {

    public JojaMart(Position position, int height, int width, Seller seller, int openHour, int closeHour) {
        super(position, height, width, seller, openHour, closeHour);
        initializeProducts();
    }

    private void initializeProducts() {
        int unlimited = Integer.MAX_VALUE;

        //products.add(new ProductOffering("Item", new Item(ItemType.JOJA_COLA, 1), null, null, null, 75, unlimited));
        products.add(new ProductOffering("Item", new Seed(SeedType.ANCIENT_SEED, 1), null, null, null,"Ancient seed", 500, 1));
        products.add(new ProductOffering("Item", new Crafting(CraftingType.GrassStarter, 1), null, null, null,"Grass Starter", 125, unlimited));
        products.add(new ProductOffering("Item", new Grocery(GroceryType.SUGAR, 1), null, null, null, "Sugar",125, unlimited));
        products.add(new ProductOffering("Item", new Grocery(GroceryType.WHEAT_FLOUR, 1), null, null, null, "Whaet Flour",125, unlimited));
        products.add(new ProductOffering("Item", new Grocery(GroceryType.RICE, 1), null, null, null, "Rice",250, unlimited));

        products.add(new ProductOffering("Item", new Seed(SeedType.PARSNIP_SEED, 1), null, null, null, "Parsnip seed", 25, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.BEAN_STARTER_SEED, 1), null, null, null, "Bean starter seed", 75, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.CAULIFLOWER_SEED, 1), null, null, null, "Cauliflower seed", 100, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.POTATO_SEED, 1), null, null, null, "Potato seed", 62, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.STRAWBERRY_SEED, 1), null, null, null, "Strawberry seed", 100, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.TULIP_BULB_SEED, 1), null, null, null, "Tulip bulb seed", 25, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.KALE_SEED, 1), null, null, null, "Kale seed", 87, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.COFFEE_BEAN_SEED, 1), null, null, null, "Coffee bean seed", 200, 1));
        products.add(new ProductOffering("Item", new Seed(SeedType.CARROT_SEED, 1), null, null, null, "Carrot seed", 5, 10));
        products.add(new ProductOffering("Item", new Seed(SeedType.RHUBARB_SEED, 1), null, null, null, "Rhubarb seed", 100, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.JAZZ_SEED, 1), null, null, null, "Jazz seed", 37, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.TOMATO_SEED, 1), null, null, null, "Tomato seed", 62, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.PEPPER_SEED, 1), null, null, null, "Pepper seed", 50, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.WHEAT_SEED, 1), null, null, null, "Wheat seed", 12, 10));
        //products.add(new ProductOffering("Item", new Seed(SeedType.SUMMER_SQUASH_SEED, 1), null, null, null, "Summer squash seed", 10, 10));
        //products.add(new ProductOffering("Item", new Seed(SeedType.RADISH_SEED, 1), null, null, null, "Radish seed", 50, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.MELON_SEED, 1), null, null, null, "Melon seed", 100, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.HOP_STARTER_SEED, 1), null, null, null, "Hop starter seed", 75, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.POPPY_SEED, 1), null, null, null, "Poppy seed", 125, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.SPANGLE_SEED, 1), null, null, null, "Spangle seed", 62, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.STARFRUIT_SEED, 1), null, null, null, "Starfruit seed", 400, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.CORN_SEED, 1), null, null, null, "Corn seed", 187, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.EGGPLANT_SEED, 1), null, null, null, "Eggplant seed", 25, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.PUMPKIN_SEED, 1), null, null, null, "Pumpkin seed", 125, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.BROCCOLI_SEED, 1), null, null, null, "Broccoli seed", 15, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.AMARANTH_SEED, 1), null, null, null, "Amaranth seed", 87, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.GRAPE_STARTER_SEED, 1), null, null, null, "Grape starter seed", 75, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.BEET_SEED, 1), null, null, null, "Beet seed", 20, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.YAM_SEED, 1), null, null, null, "Yam seed", 75, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.BOK_CHOY_SEED, 1), null, null, null, "Bok choy seed", 62, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.CRANBERRY_SEED, 1), null, null, null, "Cranberry seed", 300, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.POWDERMELON_SEED, 1), null, null, null, "Powdermelon seed", 20, 10));
        products.add(new ProductOffering("Item", new Seed(SeedType.SUNFLOWER_SEED, 1), null, null, null, "Sunflower seed", 125, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.FAIRY_SEED, 1), null, null, null, "Fairy seed", 250, 5));
        products.add(new ProductOffering("Item", new Seed(SeedType.RARE_SEED, 1), null, null, null, "Rare seed", 1000, 1));
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
