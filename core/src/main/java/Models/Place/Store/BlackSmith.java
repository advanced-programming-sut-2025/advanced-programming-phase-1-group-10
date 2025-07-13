package Models.Place.Store;

import Models.Mineral.Mineral;
import Models.Mineral.MineralTypes;
import Models.Position;
import Models.Seller;
import Models.Tile;

public class BlackSmith extends Store {

    public BlackSmith(Position position, int height, int width, Seller seller, int openHour, int closeHour) {
        super(position, height, width, seller, openHour, closeHour);
        initializeProducts();
    }

    private void initializeProducts() {
        int unlimited = Integer.MAX_VALUE;

        products.add(new ProductOffering(
                "Item",
                new Mineral(MineralTypes.COPPER, 1),
                null, null, null,
                "Copper", 75, unlimited
        ));
        products.add(new ProductOffering(
                "Item",
                new Mineral(MineralTypes.IRON, 1),
                null, null, null,
                "Iron", 150, unlimited
        ));
        products.add(new ProductOffering(
                "Item",
                new Mineral(MineralTypes.COAL, 1),
                null, null, null,
                "Coal", 150, unlimited
        ));
        products.add(new ProductOffering(
                "Item",
                new Mineral(MineralTypes.GOLD, 1),
                null, null, null,
                "Gold", 400, unlimited
        ));

        products.add(new ProductOffering(
                "Tool",
                null, null, null,
                null,
                "Copper Tool", 2000, 1
        ));
        products.add(new ProductOffering(
                "Tool",
                null, null, null,
                null,
                "Steel Tool", 5000, 1
        ));
        products.add(new ProductOffering(
                "Tool",
                null, null, null,
                null,
                "Gold Tool", 10000, 1
        ));
        products.add(new ProductOffering(
                "Tool",
                null, null, null,
                null,
                "Iridium Tool", 25000, 1
        ));

        // سطل‌های زباله (Trash Cans) با محدودیت یک‌بار خرید
        products.add(new ProductOffering(
                "Tool",
                null, null, null,
                null,
                "Copper Trash Can", 1000, 1
        ));
        products.add(new ProductOffering(
                "Tool",
                null, null, null,
                null,
                "Steel Trash Can", 2500, 1
        ));
        products.add(new ProductOffering(
                "Tool",
                null, null, null,
                null,
                "Gold Trash Can", 5000, 1
        ));
        products.add(new ProductOffering(
                "Tool",
                null, null, null,
                null,
                "Iridium Trash Can", 12500, 1
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
