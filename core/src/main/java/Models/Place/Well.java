package Models.Place;

import Models.Position;

public class Well extends Place {

    public Well(Position position, int height, int width) {
        super(position, height, width);
    }

    @Override
    public String getSymbol() {
        return "W";
    }

    @Override
    public String getPlaceName() {
        return "Well";
    }
}
