package Models.Place.Store;

import Models.*;
import Models.Place.Place;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Store extends Place {

    private final Seller seller;

    private String name;
    private StoreMenu storeMenu;

    private final int openHour;
    private final int closeHour;

    protected final ArrayList<ProductOffering> products = new ArrayList<>();

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

    public String showProducts() {

        StringBuilder message = new StringBuilder();

        for (ProductOffering po : products) {

            message.append(po.getName()).append(" ");
            message.append(po.getPrice()).append("\n");

        }
        String finalMessage = message.toString();
        if (finalMessage.isEmpty()) {
            finalMessage = "Nothing to show";
        }
        return finalMessage;
    }

    public String showAvailableProducts(){
        StringBuilder message = new StringBuilder();
        for (ProductOffering po : products) {

            if(po.getSoldToday() < po.getDailyLimit()){
                message.append(po.getName()).append(" ");
                message.append(po.getPrice()).append("\n");
            }
        }

        String finalMessage = message.toString();
        if (finalMessage.isEmpty()) {
            finalMessage = "Nothing to show";
        }
        return finalMessage;
    }
}
