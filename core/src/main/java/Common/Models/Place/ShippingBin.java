package Common.Models.Place;

import Common.Models.Position;

public class ShippingBin extends Place {

    public ShippingBin(Position position, int height, int width) {
        super(position, height, width);
    }

    @Override
    public String getSymbol() {
        return "%";
    }

    @Override
    public String getPlaceName() {
        return "Shipping Bin";
    }
}
