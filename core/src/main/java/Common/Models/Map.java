package Common.Models;


public class Map {

    public static final int mapHeight = 120;
    public static final int mapWidth = 160;

    public static final int tileSize = 32;


    private final Tile[][] map = new Tile[mapHeight][mapWidth];

    public Map() {
        for (int height = 0; height < mapHeight; height++) {
            for (int width = 0; width < mapWidth; width++) map[height][width] = new Tile();
        }
        for (int height = 0; height < mapHeight; height++) {
            for (int width = 0; width < mapWidth; width++) {
                Tile tile = map[height][width];
                if ((height == 0 || height == Map.mapHeight - 1) || (width == 0 || width == Map.mapWidth - 1)){
                    tile.setTileType(TileType.Wall);
                }
            }
        }
    }

    public Tile[][] getMap() {
        return map;
    }

    public float getWidth() {
        return mapWidth * tileSize;
    }

    public float getHeight() {
        return mapHeight * tileSize;
    }

}
