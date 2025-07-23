package Controllers;

import Assets.HouseAsset;
import Models.*;
import Models.Config.FarmConfig;
import Models.FriendShip.Friendship;
import Models.Mineral.Mineral;
import Models.Mineral.MineralTypes;
import Models.NPC.*;
import Models.Place.*;
import Models.Place.Store.*;
import Models.Planets.Crop.Crop;
import Models.Planets.Crop.ForagingCropType;
import Models.Planets.Tree;
import Models.Planets.TreeType;
import Models.PlayerStuff.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static Models.App.getInstance;

public class GameMenuControllers {


    public Result createGame(String username1, String username2, String username3, String username4) {
        final ArrayList<String> names = new ArrayList<>(Arrays.asList(username1, username2, username3, username4));
        for (String name : names)
            if (!isUsernameExist(name)) return new Result(false, "Username " + name + " not found.");


        Game game = new Game(username1);

        for (String name : names) {
            game.getPlayers().add(new Player(name));
        }

        App.getInstance().setCurrentGame(game);
        App.getInstance().getCurrentGame().setCurrentPlayer(game.getPlayerByName(username1));
        setUpCity(game);
        setUpNPCs(game);

        return new Result(true, "Game created.");
    }

    public Result quickGame(){
        return createGame("user1","user2","user3","user4");
    }

    public boolean isUsernameExist(String username) {
        return App.getInstance().getUsers().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public void setUpCity(Game game) {
        game.getStores().addAll(Arrays.asList(
                new BlackSmith(new Position(58, 12), 4, 6, new Seller("Clint", "11", new Position(54, 12)), 9, 16),
                new CarpenterShop(new Position(61, 43), 4, 16, new Seller("Robin", "44", new Position(62, 45)), 9, 20),
                new JojaMart(new Position(53, 10), 4, 20, new Seller("Morris", "22", new Position(59, 16)), 9, 23),
                new FishStore(new Position(65, 17), 5, 8, new Seller("Willy", "55", new Position(66, 19)), 9, 17),
                new PierreGeneralStore(new Position(52, 37), 4, 5, new Seller("Pierrre", "33", new Position(53, 39)), 9, 17),
                new StardropSaloon(new Position(67, 46), 3, 12, new Seller("Gus", "77", new Position(68, 48)), 12, 24),
                new MarrineRanchStore(new Position(53, 56), 4, 12, new Seller("Marnie", "66", new Position(54, 65)), 9, 16)
        ));
        for (Store store : game.getStores()) {
            setUpPlace(game, store.getHeight(), store.getWidth(), store.getPosition(), store);
            getTileByPosition(store.getSeller().getPosition()).setPerson(store.getSeller());
        }
    }

    public void setUpNPCs(Game game) {
        game.getNpcs().addAll(Arrays.asList(
                new Abigel("Abigel",new Position(56,105),new NpcHosue(new Position(55,103),4,9)),
                new Harvey("Harvey",new Position(56,127),new NpcHosue(new Position(55,122),4,9)),
                new Lia("Lia",new Position(56,145),new NpcHosue(new Position(55,142),4,9)),
                new Robbin("Robbin",new Position(63,114),new NpcHosue(new Position(62,112),4,9)),
                new Sebastian("Sebastian",new Position(63,134),new NpcHosue(new Position(62,132),4,9))
                ));
        for(NPC npc : game.getNpcs()) {
            getTileByPosition(npc.getPosition()).setPerson(npc);
            setUpPlace(game,npc.getHosue().getHouseHeight(),npc.getHosue().getHouseWidth(),npc.getHosue().getPosition(),npc.getHosue());
        }
    }

    public Farm createFarm(String type, Position start, Game game) {
        FarmConfig config = getFarmConfig(type);
        Farm farm = new Farm();
        farm.setPosition(start);

        // Setup farm tiles
        for (int i = 0; i < Farm.farmHeight; i++) {
            for (int j = 0; j < Farm.farmWidth; j++) {
                Tile tile = game.getGameMap().getMap()[start.getX() + i][start.getY() + j];
                farm.getTiles()[i][j] = tile;
                tile.setFarm(farm);

                if ((i == 0 || i == Farm.farmHeight - 1 || j == 0 || j == Farm.farmWidth - 1)) {
                    if (!((j > 29 && j < 35) || (j > 126 && j < 132))) {
                        tile.setTileType(TileType.Wall);
                    }
                }
            }
        }

        // Add farm features
        farm.getPlaces().add(createPlace(game, start, farm, config.lakeOffset, config.lakeHeight, config.lakeWidth, PlaceType.LAKE));
        farm.getPlaces().add(createPlace(game, start, farm, config.houseOffset, config.houseHeight, config.houseWidth, PlaceType.HOUSE));
        farm.getPlaces().add(createPlace(game, start, farm, config.quarryOffset, config.quarryHeight, config.quarryWidth, PlaceType.QUARRY));
        farm.getPlaces().add(createPlace(game, start, farm, config.greenhouseOffset, config.greenhouseHeight, config.greenhouseWidth, PlaceType.GREENHOUSE));

        return farm;
    }

    private FarmConfig getFarmConfig(String type) {
        return switch (type) {
            case "1" -> new FarmConfig(
                new Position(16, 24), 5, 15,
                new Position(8, 24), 8, 8,
                new Position(16, 4), 4, 8,
                new Position(8, 8), 6, 7
            );
            case "2" -> new FarmConfig(
                new Position(10, 40), 4, 12,
                new Position(12, 24), 8, 8,
                new Position(16, 8), 4, 6,
                new Position(4, 8), 6, 7
            );
            default -> throw new IllegalArgumentException("Invalid farm type: " + type);
        };
    }

    private Place createPlace(Game game, Position start, Farm farm, Position offset, int height, int width, PlaceType type) {
        Position absPos = new Position(start.getX() + offset.getX(), start.getY() + offset.getY());
        Place place;

        switch (type) {
            case LAKE:
                place = new Lake(absPos, height, width);
                break;
            case QUARRY:
                place = new Quarry(absPos, height, width);
                break;
            case GREENHOUSE:
                place = new GreenHouse(absPos, height, width);
                break;
            case HOUSE:
                place = new House(absPos, height, width);
                if (!setUpPlace(game, height, width, absPos, place)) {
                    throw new RuntimeException("Failed to place house at: " + absPos);
                }
                TextureRegion[][] regions = new HouseAsset(new Texture("place/house/houseOutside.png")).getHouseRegions();
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        Tile tile = place.getPlaceTiles()[i][j];
                        tile.setAssetRegion(regions[height - 1 - i][j]);
                    }
                }
                return place;
            default:
                throw new IllegalArgumentException("Unknown place type: " + type);
        }

        setUpPlace(game, height, width, absPos, place);
        return place;
    }

    public Position chooseStartingPoint(int index) {
        switch (index) {
            case 0:
                return new Position(0, 0);
            case 1:
                return new Position(0, Map.mapWidth - Farm.farmWidth);
            case 2:
                return new Position(Map.mapHeight - Farm.farmHeight, 0);
            case 3:
                return new Position(Map.mapHeight - Farm.farmHeight, Map.mapWidth - Farm.farmWidth);
        }
        assert false : "Index out of bounds";
        return null;
    }



    public Place getPlaceByName(ArrayList<Place> places, String name) {
        for (Place place : places) {
            if (place.getClass().getSimpleName().equals(name)) {
                return place;
            }
        }
        assert false : "Place not found";
        return null;
    }

    public Tile getTileByPosition(Position position) {
        return App.getInstance().getCurrentGame().getGameMap().getMap()[position.getX()][position.getY()];
    }

    public Tile getRandomTile(Tile[][] map) {
        int height = map.length;
        int width = map[0].length;

        Random random = new Random();
        int row = random.nextInt(height);
        int col = random.nextInt(width);

        return map[row][col];
    }

    public boolean isAvailableTileForMineral(Tile tile) {
        return tile.getItem() == null;
    }

    public Item getRandomItem(ArrayList<Item> list) {
        if (list.isEmpty()) return null;
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    public Tile getRandomTileArrayList(ArrayList<Tile> list) {
        if (list.isEmpty()) return null;
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    public void putRandomMineral(Farm farm, int numberOfRandom) {
        Tile[][] tiles = getPlaceByName(farm.getPlaces(), "Quarry").getPlaceTiles();
        ArrayList<Item> minerals = new ArrayList<>();
        for (MineralTypes type : MineralTypes.values()) {
            minerals.add(new Mineral(type, 1));
        }
        for (int i = 0; i < numberOfRandom; i++) {
            Tile randomTile = getRandomTile(tiles);
            Mineral mineral = new Mineral(MineralTypes.STONE,1);
            if (!isAvailableTileForMineral(randomTile)) {
                continue;
            }
            randomTile.setItem(mineral);
        }
        for (int i = 0; i < numberOfRandom; i++) {
            Tile randomTile = getRandomTile(tiles);
            Mineral mineral = (Mineral) getRandomItem(minerals);
            if (!isAvailableTileForMineral(randomTile)) {
                continue;
            }
            randomTile.setItem(mineral);
        }
    }

    public boolean isAvailableForPlant(Tile tile) {
        return tile.getItem() == null && tile.getPlace() == null && tile.getTileType() != TileType.Wall;
    }

    public void putRandomForagingPlanet(Farm farm, int numberOfRandom) {
        ArrayList<Tile> tiles = Arrays.stream(farm.getTiles()).flatMap(Arrays::stream).filter(this::isAvailableForPlant).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Item> planets = new ArrayList<>();
        for (ForagingCropType foragingCropType : ForagingCropType.values()) {
            if (foragingCropType.getSeason() == App.getInstance().getCurrentGame().getGameTime().getSeason())
                planets.add(new Crop(foragingCropType, 1));
        }

        for(TreeType treeType : TreeType.values()) {
            if(treeType.isForaging()){
                planets.add(new Tree(treeType));
            }
        }

        for (int i = 0; i < numberOfRandom; i++) {
            Item randomItem = getRandomItem(planets);
            Tile tile = getRandomTileArrayList(tiles);
            if (tile != null && randomItem != null) tile.setItem(randomItem);
        }

        ArrayList<Item> fiberAndMushroom = new ArrayList<>(Arrays.asList(
                new Crop(ForagingCropType.FIBER, ThreadLocalRandom.current().nextInt(0,2)),
                new Crop(ForagingCropType.COMMON_MUSHROOM, 1)
        ));

        // 2/3 of items are common mushroom and fiber
        for (int i = 0; i < 2 * numberOfRandom; i++) {
            Item randomItem = getRandomItem(fiberAndMushroom);
            Tile tile = getRandomTileArrayList(tiles);
            if (tile != null && randomItem != null) tile.setItem(randomItem);
        }


    }

    public static boolean setUpPlace(Game game, int placeHeight, int placeWidth, Position position, Place place) {
        if (position.getX() < 0 || position.getY() < 0 ||
                position.getX() + placeHeight > Map.mapHeight ||
                position.getY() + placeWidth > Map.mapWidth) {
            return false;
        }

        for (int height = position.getX(); height < position.getX() + placeHeight; height++) {
            for (int width = position.getY(); width < position.getY() + placeWidth; width++) {
                Tile tile = game.getGameMap().getMap()[height][width];
                if (tile.getPlace() != null || tile.getTileType() == TileType.Wall) {
                    return false;
                }
            }
        }

        for (int height = position.getX(); height < position.getX() + placeHeight; height++) {
            for (int width = position.getY(); width < position.getY() + placeWidth; width++) {
                Tile tile = game.getGameMap().getMap()[height][width];
                tile.setPlace(place);
                place.getPlaceTiles()[height - position.getX()][width - position.getY()] = tile;
            }
        }

        return true;
    }

    public void setUpFriendShip(Player player) {
        for(Player p: App.getInstance().getCurrentGame().getPlayers()){
            if(!p.equals(player)) player.getFriendships().add(new Friendship(p,0));
        }
    }


    public void setUpFarms(ArrayList<String> farmTypes) {
        Game game = getInstance().getCurrentGame();
        List<Player> players = game.getPlayers();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            String farmTypeId = farmTypes.get(i);
            String mapNumber = String.valueOf(farmTypeId.charAt(farmTypeId.length() - 1));

            Position startingPoint = chooseStartingPoint(i);

            Farm farm = createFarm(mapNumber, startingPoint, game);

            // Place player just outside the house
            Position housePos = getPlaceByName(farm.getPlaces(), "House").getPosition();
            Position playerPos = new Position(housePos.getX() - 1, housePos.getY() - 1);
            player.setPosition(playerPos);
            player.setX(playerPos.getX() * Map.mapWidth);
            player.setY(playerPos.getY() * Map.mapHeight);

            getTileByPosition(playerPos).setPerson(player);

            // Setup resources and assign farm
            putRandomMineral(farm, 4);
            putRandomForagingPlanet(farm, 10);
            player.setFarm(farm);

            // Setup friendships
            setUpFriendShip(player);
        }
    }

}
