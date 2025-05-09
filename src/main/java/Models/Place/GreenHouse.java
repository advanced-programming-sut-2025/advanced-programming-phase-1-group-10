package Models.Place;


import Models.Position;

public class GreenHouse extends Place {


    public GreenHouse(Position position, int height, int width) {
        super(position, height, width);
    }

    @Override
    public String getSymbol() {
        return "G";
    }
}
