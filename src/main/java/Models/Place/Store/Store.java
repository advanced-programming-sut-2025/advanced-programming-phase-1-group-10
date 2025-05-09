package Models.Place.Store;

import Models.Place.Place;
import Models.Position;

public class Store extends Place {
    private String name;
    private StoreMenu storeMenu;


    public Store(Position position, int height, int width) {
        super(position, height, width);
    }
}
