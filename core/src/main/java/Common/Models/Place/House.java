package Common.Models.Place;

import Common.Models.Position;

public class House extends Place {


    public House(Position position, int height, int width) {
        super(position, height, width);
    }

    @Override
    public String getSymbol() {
        return "HH";
    }

    @Override
    public String getPlaceName() {
        return "House";
    }

}
