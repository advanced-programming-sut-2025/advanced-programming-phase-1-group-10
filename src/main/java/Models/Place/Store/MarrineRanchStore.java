package Models.Place.Store;

import Models.Animal.Animal;
import Models.Animal.AnimalType;
import Models.Position;
import Models.Seller;
import Models.Tile;
import Models.Tools.MilkPail;
import Models.Tools.Quality;
import Models.Tools.Shear;

public class MarrineRanchStore extends Store {

    public MarrineRanchStore(Position position, int height, int width, Seller seller, int openHour, int closeHour) {
        super(position, height, width, seller, openHour, closeHour);
    }

    private void initializeProducts() {
        int unlimited = Integer.MAX_VALUE;

        // حیوانات با محدودیت خرید روزانه ۲ عدد
        products.add(new ProductOffering(
                "Animal",
                null, null,
                new Animal(AnimalType.CHICKEN, null),
                null,
                "Chicken", 800, 2
        ));
        products.add(new ProductOffering(
                "Animal",
                null, null,
                new Animal(AnimalType.COW,null),
                null,
                "Cow", 1500, 2
        ));
        products.add(new ProductOffering(
                "Animal",
                null, null,
                new Animal(AnimalType.GOAT,null),
                null,
                "Goat", 4000, 2
        ));
        products.add(new ProductOffering(
                "Animal",
                null, null,
                new Animal(AnimalType.DUCK,null),
                null,
                "Duck", 1200, 2
        ));
        products.add(new ProductOffering(
                "Animal",
                null, null,
                new Animal(AnimalType.SHEEP,null),
                null,
                "Sheep", 8000, 2
        ));
        products.add(new ProductOffering(
                "Animal",
                null, null,
                new Animal(AnimalType.RABBIT,null),
                null,
                "Rabbit", 8000, 2
        ));
        products.add(new ProductOffering(
                "Animal",
                null, null,
                new Animal(AnimalType.DINOSAUR,null),
                null,
                "Dinosaur", 14000, 2
        ));
        products.add(new ProductOffering(
                "Animal",
                null, null,
                new Animal(AnimalType.PIG,null),
                null,
                "Pig", 16000, 2
        ));
/*
        products.add(new ProductOffering(
                "Item",
                new AnimalProduct(AnimalProductType.HAY, 1),
                null, null, null,
                "Hay", 50, unlimited
        ));
*/
        products.add(new ProductOffering(
                "Tool",
                null, null, null,
                new MilkPail(Quality.STARTER, 4),
                "Milk Pail",
                1000,
                1
        ));

        products.add(new ProductOffering(
                "Tool",
                null, null, null,
                new Shear(Quality.STARTER, 4),
                "Shears",
                1000,
                1
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
