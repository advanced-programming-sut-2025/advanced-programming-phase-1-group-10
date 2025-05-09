package Controllers;

import Models.*;
import Models.Place.*;
import Models.PlayerStuff.Player;

import java.util.ArrayList;
import java.util.Arrays;

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

        return new Result(true, "Game created.");
    }



    public boolean isUsernameExist(String username) {
        return App.getInstance().getUsers().stream().anyMatch(user -> user.getUsername().equals(username));
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
        switch (index){
            case 0: return new Position(1,1);
            case 1: return new Position(1,Map.mapWidth - Farm.farmWidth);
            case 2: return new Position(Map.mapHeight - Farm.farmHeight,1);
            case 3: return new Position(Map.mapHeight - Farm.farmHeight,Map.mapWidth - Farm.farmWidth);
        }
        assert false : "Index out of bounds";
        return null;
    }

    public Farm createFarmType2(Position startingPosition, Game game) {

        Farm farm = new Farm();
        farm.setPosition(startingPosition);
        //Create Farm
        for (int height = startingPosition.getX() + 1; height < startingPosition.getX() + Farm.farmHeight; height++) {
            for (int width = startingPosition.getY() + 1; width < startingPosition.getY() + Farm.farmWidth; width++) {
                Tile tile = game.getGameMap().getMap()[height][width];
                farm.getTiles().add(tile);
                tile.setFarm(farm);
            }
        }
        //Create lake, lake data:
        final int lakeHeight = 3;
        final int lakeWidth = 6;
        final Position lakeBasePositon = new Position( 2 * Farm.farmWidth / 3, 2 * Farm.farmWidth / 3);
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
        for (int height = startingPosition.getX() + 1; height < startingPosition.getX() + Farm.farmHeight; height++) {
            for (int width = startingPosition.getY() + 1; width < startingPosition.getY() + Farm.farmWidth; width++) {
                Tile tile = game.getGameMap().getMap()[height][width];
                farm.getTiles().add(tile);
                tile.setFarm(farm);
            }
        }
        //Create lake, lake data:
        final int lakeHeight = 2;
        final int lakeWidth = 4;
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

    private static void setUpPlace(Game game, int placeheight, int placewidth, Position position, Place place) {
        for (int height = position.getX(); height < position.getX() + placeheight; height++) {
            for (int width = position.getY(); width < position.getY() + placewidth; width++) {
                Tile tile = game.getGameMap().getMap()[height][width];
                tile.setPlace(place);
                place.getPlaceTiles().add(tile);
            }
        }
    }



}
