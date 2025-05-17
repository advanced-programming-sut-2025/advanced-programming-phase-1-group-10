package Models.Place;

import Models.Position;

public class NpcHosue extends Place {

    private final int houseHeight = 4;
    private final int houseWidth = 9;

    public NpcHosue(Position position, int height, int width) {
        super(position, height, width);
    }

    public int getHouseHeight() {
        return houseHeight;
    }

    public int getHouseWidth() {
        return houseWidth;
    }

    @Override
    public String getSymbol() {
        return "~~";
    }

    @Override
    public String getPlaceName() {
        return "NPCHouse";
    }
}
