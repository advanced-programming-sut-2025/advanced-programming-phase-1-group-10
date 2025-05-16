package Controllers;

import Models.Animal.Animal;
import Models.App;
import Models.DateTime.DateTime;
import Models.DateTime.DateTimeManager;
import Models.Mineral.Mineral;
import Models.Mineral.MineralTypes;
import Models.Place.GreenHouse;
import Models.Planets.Crop.Crop;
import Models.Planets.Tree;
import Models.Position;
import Models.Result;
import Models.Tile;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class CheatCodeControllers {
    public Result setEnergy(String energy) {
        int energyInt;
        try {
            energyInt = Integer.parseInt(energy);
        } catch (Exception exception) {
            return new Result(false, "Invalid energy value");
        }
        if(energyInt <= 0) {
            return new Result(false, "Invalid energy value");
        }
        App.getInstance().getCurrentGame().getCurrentPlayer().getEnergy().setEnergyAmount(energyInt);
        return new Result(true, "Player Energy set to " + energyInt);
    }

    public Result setUnlimitedEnergy() {
        App.getInstance().getCurrentGame().getCurrentPlayer().getEnergy().setEnergyAmount(Double.MAX_VALUE);
        return new Result(true, "Player Energy set to unlimited energy");
    }

    public Result setAnimalFriendShip(String name,String amount){
        Animal animal = App.getInstance().getCurrentGame().getAnimals().get(name);
        if(animal == null){
            return new Result(false, "there is not animal with this name.");
        }
        int Amount;
        try {
            Amount = Integer.parseInt(amount);
        } catch (Exception exception) {
            return new Result(false, "Invalid amount value");
        }
        animal.setFriendShip(Amount);
        return new Result(true,"Animal friendship set to " + Amount);
    }

    public Result  thorTile(Position point){
        Tile tile;
        try {
            tile = App.getInstance().getCurrentGame().getGameMap().getMap()[point.getX()][point.getY()];
        } catch (Exception exception) {
            return new Result(false, "There is not tile with this point.");
        }

        if(tile.getPlace() instanceof GreenHouse){
            return new Result(true,"Greenhouse is safe from thunder");
        }

        if(tile.getItem() instanceof Tree || tile.getItem() instanceof Crop){
            tile.setItem(new Mineral(MineralTypes.COAL, ThreadLocalRandom.current().nextInt(1,3)));
        } else if(tile.getItem() instanceof Mineral){

        } else{
            tile.setItem(null);
        }
        return new Result(true,"The God of Thunder showed his might.");

    }
    public Result advanceHour(int x) {
        IntStream.range(0, x).forEach(i -> App.getInstance().getCurrentGame().getGameTime().nextHour());
        return new Result(true, "Time advanced successfully.");
    }

    public Result advanceDay(int x) {
        IntStream.range(0, x).forEach(i -> App.getInstance().getCurrentGame().getGameTime().nextDay());
        return new Result(true, "Date advanced successfully.");
    }
}
