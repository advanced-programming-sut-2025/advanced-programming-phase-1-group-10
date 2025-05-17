package Models.Place;

import Models.Position;

public class Quarry extends Place {

    public Quarry(Position position, int height, int width) {
        super(position, height, width);
    }

    @Override
    public String getSymbol() {
        return "QQ";
    }

    @Override
    public String getPlaceName() {
        return "Quarry";
    }
}
