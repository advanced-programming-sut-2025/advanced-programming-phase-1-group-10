package Controllers;

import Models.*;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

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
        setUpCity(game);
        setUpNPCs(game);

        return new Result(true, "Game created.");
    }


    public boolean isUsernameExist(String username) {
        return App.getInstance().getUsers().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public void setUpCity(Game game) {
        game.getStores().addAll(Arrays.asList(
                new BlackSmith(new Position(58, 12), 4, 6, new Seller("Clint", "1", new Position(54, 12)), 9, 16),
                new CarpenterShop(new Position(61, 43), 4, 16, new Seller("Robin", "4", new Position(62, 45)), 9, 20),
                new JojaMart(new Position(53, 10), 4, 20, new Seller("Morris", "2", new Position(59, 16)), 9, 23),
                new FishStore(new Position(65, 17), 5, 8, new Seller("Willy", "5", new Position(66, 19)), 9, 17),
                new PierreGeneralStore(new Position(52, 37), 4, 5, new Seller("Pierrre", "3", new Position(53, 39)), 9, 17),
                new StardropSaloon(new Position(67, 46), 3, 12, new Seller("Gus", "7", new Position(68, 48)), 12, 24),
                new MarrineRanchStore(new Position(53, 56), 4, 12, new Seller("Marnie", "6", new Position(54, 65)), 9, 16)
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


    public Farm createFarm(String num, Position position, Game game) {
        Farm farm = new Farm();
        if (num.equals("1")) {
            farm = createFarmType1(position, game);
        } else if (num.equals("2")) {
            farm = createFarmType2(position, game);
        }
        return farm;
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

    public Farm createFarmType2(Position startingPosition, Game game) {

        Farm farm = new Farm();
        farm.setPosition(startingPosition);
        //Create Farm
        for (int height = startingPosition.getX(); height < startingPosition.getX() + Farm.farmHeight; height++) {
            for (int width = startingPosition.getY(); width < startingPosition.getY() + Farm.farmWidth; width++) {
                Tile tile = game.getGameMap().getMap()[height][width];
                farm.getTiles()[height - startingPosition.getX()][width - startingPosition.getY()] = tile;
                tile.setFarm(farm);
            }
        }
        for (int height = 0; height < Farm.farmHeight; height++) {
            for (int width = 0; width < Farm.farmWidth; width++) {
                if ((height == 0 || height == Farm.farmHeight - 1 || width == 0 || width == Farm.farmWidth - 1)) {
                    if (!( (width > 29 && width < 35) || (width > 126 && width < 132) )){
                        farm.getTiles()[height][width].setTileType(TileType.Wall);
                    }
                }

            }
        }
        //Create lake, lake data:
        final int lakeHeight = 4;
        final int lakeWidth = 12;
        final Position lakeBasePositon = new Position(10, 40);
        final Position lakePosition = new Position(startingPosition.getX() + lakeBasePositon.getX(), startingPosition.getY() + lakeBasePositon.getY());
        farm.getPlaces().add(createLake(startingPosition, game, farm, lakeHeight, lakeWidth, lakePosition));
        //Create house, house data:
        final int houseHeight = 4;
        final int houseWidth = 4;
        final Position houseBasePostion = new Position(12, 24);
        final Position housePosition = new Position(startingPosition.getX() + houseBasePostion.getX(), startingPosition.getY() + houseBasePostion.getY());
        farm.getPlaces().add(createHouse(startingPosition, game, farm, houseHeight, houseWidth, housePosition));
        //Create Quarry
        final int quarryHeight = 4;
        final int quarryWidth = 6;
        final Position quarryBasePosition = new Position(16, 8);
        final Position quarryPostion = new Position(startingPosition.getX() + quarryBasePosition.getX(), startingPosition.getY() + quarryBasePosition.getY());
        farm.getPlaces().add(createQuarry(startingPosition, game, farm, quarryHeight, quarryWidth, quarryPostion));
        //Create GreenHouse
        final int greenHouseHeight = 6;
        final int greenHouseWidth = 7;
        final Position greenHouseBasePostion = new Position(4, 8);
        final Position greenHousePosition = new Position(startingPosition.getX() + greenHouseBasePostion.getX(), startingPosition.getY() + greenHouseBasePostion.getY());
        farm.getPlaces().add(createGreenHouse(startingPosition, game, farm, greenHouseHeight, greenHouseWidth, greenHousePosition));
        return farm;
    }


    public Farm createFarmType1(Position startingPosition, Game game) {

        Farm farm = new Farm();
        farm.setPosition(startingPosition);
        //Create Farm
        for (int height = startingPosition.getX(); height < startingPosition.getX() + Farm.farmHeight; height++) {
            for (int width = startingPosition.getY(); width < startingPosition.getY() + Farm.farmWidth; width++) {
                Tile tile = game.getGameMap().getMap()[height][width];
                farm.getTiles()[height - startingPosition.getX()][width - startingPosition.getY()] = tile;
                tile.setFarm(farm);
            }
        }
        for (int height = 0; height < Farm.farmHeight; height++) {
            for (int width = 0; width < Farm.farmWidth; width++) {
                if (height == 0 || height == Farm.farmHeight - 1 || width == 0 || width == Farm.farmWidth - 1) {
                    if (!( (width > 29 && width < 35) || (width > 126 && width < 132) )){
                        farm.getTiles()[height][width].setTileType(TileType.Wall);
                    }
                }

            }
        }
        //Create lake, lake data:
        final int lakeHeight = 5;
        final int lakeWidth = 15;
        final Position lakeBasePositon = new Position(16, 24);
        final Position lakePosition = new Position(startingPosition.getX() + lakeBasePositon.getX(), startingPosition.getY() + lakeBasePositon.getY());
        farm.getPlaces().add(createLake(startingPosition, game, farm, lakeHeight, lakeWidth, lakePosition));
        //Create house, house data:
        final int houseHeight = 4;
        final int houseWidth = 4;
        final Position houseBasePostion = new Position(8, 24);
        final Position housePosition = new Position(startingPosition.getX() + houseBasePostion.getX(), startingPosition.getY() + houseBasePostion.getY());
        farm.getPlaces().add(createHouse(startingPosition, game, farm, houseHeight, houseWidth, housePosition));
        //Create Quarry
        final int quarryHeight = 4;
        final int quarryWidth = 8;
        final Position quarryBasePosition = new Position(16, 4);
        final Position quarryPostion = new Position(startingPosition.getX() + quarryBasePosition.getX(), startingPosition.getY() + quarryBasePosition.getY());
        farm.getPlaces().add(createQuarry(startingPosition, game, farm, quarryHeight, quarryWidth, quarryPostion));
        //Create GreenHouse
        final int greenHouseHeight = 6;
        final int greenHouseWidth = 7;
        final Position greenHouseBasePostion = new Position(8, 8);
        final Position greenHousePosition = new Position(startingPosition.getX() + greenHouseBasePostion.getX(), startingPosition.getY() + greenHouseBasePostion.getY());
        farm.getPlaces().add(createGreenHouse(startingPosition, game, farm, greenHouseHeight, greenHouseWidth, greenHousePosition));
        return farm;
    }

    private GreenHouse createGreenHouse(Position startingPosition, Game game, Farm farm, int greenhouseHeight, int greenhouseWidth, Position grenhousePosition) {
        GreenHouse greenHouse = new GreenHouse(grenhousePosition, greenhouseHeight, greenhouseWidth);
        setUpPlace(game, greenhouseHeight, greenhouseWidth, grenhousePosition, greenHouse);
        return greenHouse;
    }


    private Quarry createQuarry(Position startingPosition, Game game, Farm farm, int quarryHeight, int quarryWidth, Position quarryPosition) {
        Quarry quarry = new Quarry(quarryPosition, quarryHeight, quarryWidth);
        setUpPlace(game, quarryHeight, quarryWidth, quarryPosition, quarry);
        return quarry;
    }


    private Lake createLake(Position startingPosition, Game game, Farm farm, int lakeHeight, int lakeWidth, Position lakePosition) {
        Lake lake = new Lake(lakePosition, lakeHeight, lakeWidth);
        setUpPlace(game, lakeHeight, lakeWidth, lakePosition, lake);
        return lake;
    }

    private House createHouse(Position startingPosition, Game game, Farm farm, int houseHeight, int houseWidth, Position housePostion) {
        House house = new House(housePostion, houseHeight, houseWidth);
        setUpPlace(game, houseHeight, houseWidth, housePostion, house);
        return house;
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
        if (tile.getItem() != null) {
            return false;
        }
        return true;
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
            Mineral mineral = (Mineral) getRandomItem(minerals);
            if (!isAvailableTileForMineral(randomTile)) {
                continue;
            }
            randomTile.setItem(mineral);
        }
    }

    public static void setUpPlace(Game game, int placeheight, int placewidth, Position position, Place place) {
        for (int height = position.getX(); height < position.getX() + placeheight; height++) {
            for (int width = position.getY(); width < position.getY() + placewidth; width++) {
                Tile tile = game.getGameMap().getMap()[height][width];
                tile.setPlace(place);
                place.getPlaceTiles()[height - position.getX()][width - position.getY()] = tile;
            }
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
    }

}
