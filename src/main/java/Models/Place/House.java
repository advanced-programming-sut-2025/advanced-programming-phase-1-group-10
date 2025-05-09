package Models.Place;

import Models.Position;

public class House extends Place {


    public House(Position position, int height, int width) {
        super(position, height, width);
    }

    @Override
    public String getSymbol() {
        return "H";
    }
}
