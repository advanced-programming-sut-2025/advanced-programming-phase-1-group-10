package Models.Place.Store;

import Models.*;
import Models.Place.Place;

public class Store extends Place {

    private final Seller seller;

    private String name;
    private StoreMenu storeMenu;

    private final int openHour;
    private final int closeHour;


    public Store(Position position, int height, int width, Seller seller, int openHour, int closeHour) {
        super(position, height, width);
        this.seller = seller;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.height = height;
        this.width = width;
        this.placeTiles = new Tile[height][width];
    }

    @Override
    public String getSymbol() {
        return "-";
    }

    public int getCloseHour() {
        return closeHour;
    }

    public int getOpenHour() {
        return openHour;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Seller getSeller() {
        return seller;
    }
}
