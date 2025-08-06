package Common.Models.Place;

import Common.Models.Position;
import Common.Models.Tile;


public class Place {

    protected Position position;
    protected int height;
    protected int width;
    protected Tile[][] placeTiles;

    public Place(Position position,int height,int width) {
        this.position = position;
        this.height = height;
        this.width = width;
        this.placeTiles = new Tile[height][width];
    }

    private String placeName;

    public String getPlaceName() {
        return placeName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Tile[][] getPlaceTiles() {
        return placeTiles;
    }

    public String getSymbol() {
        //Should never be printed
        return "!!";
    }

    public boolean contains(Position pos) {
        int px = pos.getY(); // treat Y as if it's X
        int py = pos.getX(); // treat X as if it's Y
        return px >= position.getY() && px < position.getY() + width &&
            py >= position.getX() && py < position.getX() + height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
