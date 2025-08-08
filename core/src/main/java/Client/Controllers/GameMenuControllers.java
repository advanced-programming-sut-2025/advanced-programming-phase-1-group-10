package Client.Controllers;

import Client.Assets.GreenHouseAsset;
import Client.Assets.HouseAsset;
import Common.Models.*;
import Common.Models.Map;
import Common.Models.NPC.*;
import Common.Models.Place.*;
import Common.Models.Place.Store.*;
import Common.Models.Config.FarmConfig;
import Common.Models.FriendShip.Friendship;
import Common.Models.Mineral.Mineral;
import Common.Models.Mineral.MineralTypes;
import Common.Models.Planets.Crop.Crop;
import Common.Models.Planets.Crop.ForagingCropType;
import Common.Models.Planets.Tree;
import Common.Models.Planets.TreeType;
import Common.Models.PlayerStuff.Player;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.*;
import java.util.stream.Collectors;

import static Common.Models.App.getInstance;

public class GameMenuControllers {


    public Result createGame(List<String> names, long seed, boolean isOnline) {
        //for (String name : names)
        //    if (!isUsernameExist(name)) return new Result(false, "Username " + name + " not found.");

        String admin;
        try{
             admin = names.get(0);
        } catch (Exception e) {
            return new Result(false, "No player to play");
        }


        Game game = new Game(admin,isOnline);

        for (String name : names) {
            game.getPlayers().add(new Player(name, seed));
        }

        App.getInstance().setCurrentGame(game);
        App.getInstance().getCurrentGame().setCurrentPlayer(game.getPlayerByName(admin));
        setUpCity(game);
        setUpNPCs(game);

        return new Result(true, "Game created.");
    }

    public Result quickGame(long seed){
        return createGame(Arrays.asList("user1","user2","user3","user4"),seed,false);
    }

    public boolean isUsernameExist(String username) {
        return App.getInstance().getUsers().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public void setUpCity(Game game) {
        game.getStores().addAll(Arrays.asList(
                new BlackSmith(new Position(58, 20), 6, 8, new Seller("Clint", "11", new Position(54, 12)), 9, 16),
                new CarpenterShop(new Position(61, 43), 7, 8, new Seller("Robin", "44", new Position(62, 45)), 9, 20),
                new JojaMart(new Position(53, 10), 4, 5, new Seller("Morris", "22", new Position(59, 16)), 9, 23),
                new FishStore(new Position(65, 17), 6, 6, new Seller("Willy", "55", new Position(66, 19)), 9, 17),
                new PierreGeneralStore(new Position(52, 37), 6, 6, new Seller("Pierrre", "33", new Position(53, 39)), 9, 17),
                new StardropSaloon(new Position(64, 56), 7, 6, new Seller("Gus", "77", new Position(68, 48)), 12, 24),
                new MarrineRanchStore(new Position(53, 56), 6, 10, new Seller("Marnie", "66", new Position(54, 65)), 9, 16)
        ));
        for (Store store : game.getStores()) {
            setUpPlace(game, store.getHeight(), store.getWidth(), store.getPosition(), store);
            getTileByPosition(store.getSeller().getPosition()).setPerson(store.getSeller());
        }
    }

    public void setUpNPCs(Game game) {
        game.getNpcs().addAll(Arrays.asList(
                new Abigel("Abigel",new Position(53,104),new NpcHosue(new Position(55,103),4,9)),
                new Harvey("Harvey",new Position(53,123),new NpcHosue(new Position(55,122),4,9)),
                new Lia("Lia",new Position(53,143),new NpcHosue(new Position(55,142),4,9)),
                new Robbin("Robbin",new Position(61,113),new NpcHosue(new Position(62,112),4,9)),
                new Sebastian("Sebastian",new Position(61,133),new NpcHosue(new Position(62,132),4,9))
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
        farm.getPlaces().add(createPlace(game, start, config.lakeOffset, config.lakeHeight, config.lakeWidth, PlaceType.LAKE));
        farm.getPlaces().add(createPlace(game, start, config.houseOffset, config.houseHeight, config.houseWidth, PlaceType.HOUSE));
        farm.getPlaces().add(createPlace(game, start, config.quarryOffset, config.quarryHeight, config.quarryWidth, PlaceType.QUARRY));
        farm.getPlaces().add(createPlace(game, start, config.greenhouseOffset, config.greenhouseHeight, config.greenhouseWidth, PlaceType.GREENHOUSE));

        return farm;
    }

    private FarmConfig getFarmConfig(String type) {
        return switch (type) {
            case "1" -> new FarmConfig(
                new Position(16, 24), 5, 15,
                new Position(8, 24), 8, 8,
                new Position(16, 4), 4, 8,
                new Position(5, 8), 10, 7
            );
            case "2" -> new FarmConfig(
                new Position(10, 40), 4, 12,
                new Position(12, 24), 8, 8,
                new Position(16, 8), 4, 6,
                new Position(5, 8), 10, 7
            );
            default -> throw new IllegalArgumentException("Invalid farm type: " + type);
        };
    }

    private Place createPlace(Game game, Position start, Position offset, int height, int width, PlaceType type) {
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
                if (!setUpPlace(game, height, width, absPos, place)) {
                    throw new RuntimeException("Failed to place greenhouse at: " + absPos);
                }
                //TextureRegion[][] broken = new GreenHouseAsset().getBroken();
                TextureRegion[][] greenhouseInside = new GreenHouseAsset().getGreenhouseInside();
                TextureRegion[][] greenhouseOutside = new GreenHouseAsset().getGreenhouuseOutside();


                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        Tile tile = place.getPlaceTiles()[i][j];
                        tile.setAssetRegionOutside(greenhouseOutside[height - 1 - i][j]);
                        tile.setAssetRegionInside(greenhouseInside[height - 1 - i][j]);
                    }
                }
                return place;
            case HOUSE:
                place = new House(absPos, height, width);
                if (!setUpPlace(game, height, width, absPos, place)) {
                    throw new RuntimeException("Failed to place house at: " + absPos);
                }
                TextureRegion[][] regionOutside = new HouseAsset().getHouseRegions();
                TextureRegion[][] regionInside = new HouseAsset().getHouseInsideRegions();

                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        Tile tile = place.getPlaceTiles()[i][j];
                        tile.setAssetRegionOutside(regionOutside[height - 1 - i][j]);
                        tile.setAssetRegionInside(regionInside[height - 1 - i][j]);
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


    public Item getRandomItem(ArrayList<Item> list, Random random) {
        if (list.isEmpty()) return null;
        return list.get(random.nextInt(list.size()));
    }

    public Tile getRandomTileArrayList(ArrayList<Tile> list, Random random) {
        if (list.isEmpty()) return null;
        return list.get(random.nextInt(list.size()));
    }

    public Tile getRandomTile(Tile[][] tiles, Random random) {
        int height = tiles.length;
        int width = tiles[0].length;
        Tile tile;
        do {
            int x = random.nextInt(height);
            int y = random.nextInt(width);
            tile = tiles[x][y];
        } while (tile == null);
        return tile;
    }

    public void putRandomMineral(Farm farm, int numberOfRandom, long seed) {
        Random random = new Random(seed);

        Tile[][] tiles = getPlaceByName(farm.getPlaces(), "Quarry").getPlaceTiles();
        ArrayList<Item> minerals = new ArrayList<>();
        for (MineralTypes type : MineralTypes.values()) {
            minerals.add(new Mineral(type, 1));
        }

        for (int i = 0; i < numberOfRandom; i++) {
            Tile randomTile = getRandomTile(tiles, random);
            Mineral mineral = new Mineral(MineralTypes.STONE, 1);
            if (!isAvailableTileForMineral(randomTile)) continue;
            randomTile.setItem(mineral);
        }

        for (int i = 0; i < numberOfRandom; i++) {
            Tile randomTile = getRandomTile(tiles, random);
            Mineral mineral = (Mineral) getRandomItem(minerals, random);
            if (!isAvailableTileForMineral(randomTile)) continue;
            randomTile.setItem(mineral);
        }
    }

    public boolean isAvailableTileForMineral(Tile tile) {
        return tile != null && tile.getItem() == null && tile.getTileType() != TileType.Wall;
    }

    public boolean isAvailableForPlant(Tile tile) {
        return tile.getItem() == null && tile.getPlace() == null && tile.getTileType() != TileType.Wall;
    }

    public void putRandomForagingPlanet(Farm farm, int numberOfRandom, long seed) {
        Random random = new Random(seed);

        ArrayList<Tile> tiles = Arrays.stream(farm.getTiles())
            .flatMap(Arrays::stream)
            .filter(this::isAvailableForPlant)
            .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Item> plants = new ArrayList<>();

        for (ForagingCropType type : ForagingCropType.values()) {
            if (type.getSeason() == App.getInstance().getCurrentGame().getGameTime().getSeason()) {
                plants.add(new Crop(type, 1));
            }
        }

        for (TreeType treeType : TreeType.values()) {
            if (treeType.isForaging()) {
                Tree newTree = new Tree(treeType);
                plants.add(newTree);
                GameController.plants.add(newTree); // Optional: consider moving this out for syncing
            }
        }

        for (int i = 0; i < numberOfRandom; i++) {
            Item randomItem = getRandomItem(plants, random);
            Tile tile = getRandomTileArrayList(tiles, random);
            if (tile != null && randomItem != null) {
                tile.setItem(randomItem);
                if (randomItem instanceof Tree) {
                    tile.setTree((Tree) randomItem);
                    tile.setItem((Tree) randomItem); // Optional duplicate, check your logic
                }
            }
        }

        ArrayList<Item> fiberAndMushroom = new ArrayList<>(Arrays.asList(
            new Crop(ForagingCropType.FIBER, random.nextInt(2)), // 0 or 1
            new Crop(ForagingCropType.COMMON_MUSHROOM, 1)
        ));

        for (int i = 0; i < 2 * numberOfRandom; i++) {
            Item randomItem = getRandomItem(fiberAndMushroom, random);
            Tile tile = getRandomTileArrayList(tiles, random);
            if (tile != null && randomItem != null) {
                tile.setItem(randomItem);
            }
        }
    }

    // Dummy for compatibility – implement based on your structure
    private Place getPlaceByName(List<Place> places, String name) {
        for (Place place : places) {
            if (place.getPlaceName().equalsIgnoreCase(name)) return place;
        }
        return null;
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


    public void setUpFarms(List<String> farmTypes, long worldSeed) {
        Game game = getInstance().getCurrentGame();
        List<Player> players = game.getPlayers();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            String farmTypeId = farmTypes.get(i);
            String mapNumber = String.valueOf(farmTypeId.charAt(farmTypeId.length() - 1));

            // Choose a unique starting point for each farm
            Position startingPoint = chooseStartingPoint(i);

            // Create the farm based on map number and position
            Farm farm = createFarm(mapNumber, startingPoint, game);

            // Place player just outside the house (e.g., bottom-left of house)
            Position housePos = getPlaceByName(farm.getPlaces(), "House").getPosition();
            Position playerPos = new Position(housePos.getX() - 1, housePos.getY() - 1);

            // Assign tile-based and pixel-based coordinates
            player.setPosition(playerPos);
            player.setX(playerPos.getY() * Map.tileSize);
            player.setY(playerPos.getX() * Map.tileSize);

            // Place the player into the map
            getTileByPosition(playerPos).setPerson(player);

            // === Deterministic Resource Placement ===
            long farmSeed = worldSeed + i * 31L; // unique but deterministic per player
            long mineralSeed = farmSeed ^ 0xABCDEF; // different for each purpose
            long foragingSeed = farmSeed ^ 0x123456;

            putRandomMineral(farm, 2, mineralSeed);
            putRandomForagingPlanet(farm, 3, foragingSeed);

            // Assign the farm to the player
            player.setFarm(farm);

            // Setup friendships or other game-specific logic
            setUpFriendShip(player);
        }
    }



}
