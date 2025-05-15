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

}
