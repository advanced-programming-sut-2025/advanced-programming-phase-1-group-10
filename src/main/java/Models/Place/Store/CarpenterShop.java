package Models.Place.Store;

import Models.Mineral.Mineral;
import Models.Mineral.MineralTypes;
import Models.Position;
import Models.Seller;
import Models.Tile;

public class CarpenterShop extends Store {

    public CarpenterShop(Position position, int height, int width, Seller seller, int openHour, int closeHour) {
        super(position, height, width, seller, openHour, closeHour);
        initializeProducts();
    }

    private void initializeProducts() {
        int unlimited = Integer.MAX_VALUE;

        products.add(new ProductOffering(
                "Place",
                null, null, null, null,
                "Barn",
                6000, 1
        ));
        products.add(new ProductOffering(
                "Place",
                null, null, null, null,
                "Big Barn",
                12000, 1
        ));
        products.add(new ProductOffering(
                "Place",
                null, null, null, null,
                "Deluxe Barn",
                25000, 1
        ));
        products.add(new ProductOffering(
                "Place",
                null, null, null, null,
                "Coop",
                4000, 1
        ));
        products.add(new ProductOffering(
                "Place",
                null, null, null, null,
                "Big Coop",
                10000, 1
        ));
        products.add(new ProductOffering(
                "Place",
                null, null, null, null,
                "Deluxe Coop",
                20000, 1
        ));
        products.add(new ProductOffering(
                "Place",
                null, null, null, null,
                "Well",
                1000, 1
        ));
        products.add(new ProductOffering(
                "Place",
                null, null, null, null,
                "Shipping Bin",
                250, unlimited
        ));

        /*
        // آیتم‌ها (موارد مصرفی)
        products.add(new ProductOffering(
                "Item",
                new Resource(ResourceType.WOOD, 1),
                null, null, null,
                "Wood",
                10, unlimited
        ));
        */
        products.add(new ProductOffering(
                "Item",
                new Mineral(MineralTypes.STONE, 1),
                null, null, null,
                "Stone",
                20, unlimited
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
