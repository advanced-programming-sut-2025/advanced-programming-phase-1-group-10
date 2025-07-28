package Models.Place;

import Models.Position;

public class Lake extends Place {


    public Lake(Position position, int height, int width) {
        super(position, height, width);
    }

    @Override
    public String getSymbol() {
        return "WW";
    }

    @Override
    public String getPlaceName() {
        return "Lake";
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }

    @Override
    public int getWidth() {
        return super.getWidth();
    }

}
