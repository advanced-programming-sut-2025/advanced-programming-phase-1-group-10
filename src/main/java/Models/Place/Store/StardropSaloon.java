package Models.Place.Store;

import Models.Artisan.ArtisanProduct;
import Models.Artisan.ArtisanProductType;
import Models.Cooking.Cooking;
import Models.Cooking.CookingType;
import Models.Position;
import Models.Recipe.Recipe;
import Models.Recipe.RecipeType;
import Models.Seller;
import Models.Tile;

public class StardropSaloon extends Store {

    public StardropSaloon(Position position, int height, int width, Seller seller, int openHour, int closeHour) {
        super(position, height, width, seller, openHour, closeHour);
        initializeProducts();
    }

    private void initializeProducts() {
        int unlimited = Integer.MAX_VALUE;

        // محصولات از نوع Drink و Food با محدودیت نامحدود
        products.add(new ProductOffering(
                "Item",
                new ArtisanProduct(ArtisanProductType.BEER, 1),
                null, null, null,
                "Beer", 400, unlimited
        ));
        products.add(new ProductOffering(
                "Item",
                new Cooking(CookingType.SALAD, 1),
                null, null, null,
                "Salad", 220, unlimited
        ));
        products.add(new ProductOffering(
                "Item",
                new Cooking(CookingType.BREAD, 1),
                null, null, null,
                "Bread", 120, unlimited
        ));
        products.add(new ProductOffering(
                "Item",
                new Cooking(CookingType.SPAGHETTI, 1),
                null, null, null,
                "Spaghetti", 240, unlimited
        ));
        products.add(new ProductOffering(
                "Item",
                new Cooking(CookingType.PIZZA, 1),
                null, null, null,
                "Pizza", 600, unlimited
        ));
        products.add(new ProductOffering(
                "Item",
                new ArtisanProduct(ArtisanProductType.COFFEE, 1),
                null, null, null,
                "Coffee", 300, unlimited
        ));

        // دستور پخت‌ها (Recipes) با محدودیت یک‌بار خرید در روز
        products.add(new ProductOffering(
                "Item",
                new Recipe(RecipeType.HashbrownsRecipe, 1),
                null, null, null,
                "Hashbrowns Recipe", 50, 1
        ));
        products.add(new ProductOffering(
                "Item",
                new Recipe(RecipeType.OmeletRecipe, 1),
                null, null, null,
                "Omelet Recipe", 100, 1
        ));
        products.add(new ProductOffering(
                "Item",
                new Recipe(RecipeType.PancakesRecipe, 1),
                null, null, null,
                "Pancakes Recipe", 100, 1
        ));
        products.add(new ProductOffering(
                "Item",
                new Recipe(RecipeType.BreadRecipe, 1),
                null, null, null,
                "Bread Recipe", 100, 1
        ));
        products.add(new ProductOffering(
                "Item",
                new Recipe(RecipeType.TortillaRecipe, 1),
                null, null, null,
                "Tortilla Recipe", 100, 1
        ));
        products.add(new ProductOffering(
                "Item",
                new Recipe(RecipeType.PizzaRecipe, 1),
                null, null, null,
                "Pizza Recipe", 150, 1
        ));
        products.add(new ProductOffering(
                "Item",
                new Recipe(RecipeType.MakiRollRecipe, 1),
                null, null, null,
                "Maki Roll Recipe", 300, 1
        ));
        products.add(new ProductOffering(
                "Item",
                new Recipe(RecipeType.TripleShotEspressoRecipe, 1),
                null, null, null,
                "Triple Shot Espresso Recipe", 5000, 1
        ));
        products.add(new ProductOffering(
                "Item",
                new Recipe(RecipeType.CookieRecipe, 1),
                null, null, null,
                "Cookie Recipe", 300, 1
        ));
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
