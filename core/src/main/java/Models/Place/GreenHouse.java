package Models.Place;


import Models.Position;

public class GreenHouse extends Place {


    public GreenHouse(Position position, int height, int width) {
        super(position, height, width);
    }

    private boolean isFixed = false;

    @Override
    public String getSymbol() {
        return "GG";
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    @Override
    public String getPlaceName() {
        return "GreenHouse";
    }

}
