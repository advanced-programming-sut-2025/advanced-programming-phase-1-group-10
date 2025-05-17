package Models.Place.Store;

import Models.Mineral.Mineral;
import Models.Mineral.MineralTypes;
import Models.Position;
import Models.Seller;
import Models.Tile;

public class FishStore extends Store {

    public FishStore(Position position, int height, int width, Seller seller, int openHour, int closeHour) {
        super(position, height, width, seller, openHour, closeHour);
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
