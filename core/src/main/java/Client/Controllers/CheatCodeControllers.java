package Client.Controllers;

import Common.Models.*;
import Common.Models.Animal.Animal;
import Common.Models.Mineral.Mineral;
import Common.Models.Mineral.MineralTypes;
import Common.Models.OtherItems.Bouquet;
import Common.Models.OtherItems.Ring;
import Common.Models.Place.GreenHouse;
import Common.Models.Place.Place;
import Common.Models.Planets.Crop.Crop;
import Common.Models.Planets.Seed;
import Common.Models.Planets.SeedType;
import Common.Models.Planets.Tree;
import Common.Models.PlayerStuff.Player;
import Common.Models.Tools.Fertilizer;
import Common.Models.Weather.Weather;

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

    public Result changeWeather(String type) {
        Weather weather = null;
        switch (type) {
            case "Sunny" -> weather = Weather.SUNNY;
            case "Rain" -> weather = Weather.RAIN;
            case "Storm" -> weather = Weather.STORM;
            case "Snow" -> weather = Weather.SNOW;
            default -> {
            }
        }
        if(weather == null){
            return new Result(false, "There is no weather with this type.");
        }
        App.getInstance().getCurrentGame().setNextDayWeather(weather);
        return new Result(true, "Next day weather set to " + type);
    }

    public Result addMoney(String count) {
        int money;
        try {
            money = Integer.parseInt(count);
        } catch (Exception exception) {
            return new Result(false, "Invalid money value");
        }
        App.getInstance().getCurrentGame().getCurrentPlayer().setGold(App.getInstance().getCurrentGame().getCurrentPlayer().getGold() + money);
        return new Result(true, "Money added successfully.");
    }

    public void addSeedToInventory(String seedName){
        SeedType seed = null ;
        for(SeedType seedType : SeedType.values()) {
            if (seedName.equals(seedType.getName())) {
                seed = seedType;
                break;
            }
        }
        if(seed == null){
            MessageSystem.showError("Doesn't exist the seed with this name.",5.0f);
            return;
        }
        Seed newSeed = new Seed(seed,2);
        if(seedName.equalsIgnoreCase("mixed seeds")){
            newSeed.setMixed(true);
        }
        boolean added = App.getInstance().getCurrentGame().
            getCurrentPlayer().getInventory().getBackPack().addItem(newSeed);
        if(added) {
            MessageSystem.showInfo(seedName + " added successfully!", 4.0f);
            App.getInstance().getCurrentGame().getCurrentPlayer().setGold(App.getInstance().
                getCurrentGame().getCurrentPlayer().getGold() - 100);
        }
        else
            MessageSystem.showError("Can't added seed!",4.0f);
    }

    public void addFertilizerToInventory(){
        Fertilizer fertilizer = new Fertilizer(2);
        boolean added = App.getInstance().getCurrentGame().
            getCurrentPlayer().getInventory().getBackPack().addItem(fertilizer);
        if(added) {
            MessageSystem.showInfo("Fertilizer added successfully!", 4.0f);
            App.getInstance().getCurrentGame().getCurrentPlayer().setGold(App.getInstance().
                getCurrentGame().getCurrentPlayer().getGold() - 500);
        }
            else
            MessageSystem.showError("Cant't add fertilizer!",4.0f);
    }

    public void getRing(){
        Ring ring = new Ring();
        boolean added = App.getInstance().getCurrentGame().
            getCurrentPlayer().getInventory()
            .getBackPack().addItem(ring);
        if(added){
            MessageSystem.showInfo("Ring added successfully!",4.0f);
            App.getInstance().getCurrentGame().getCurrentPlayer().setGold(App.getInstance().
                getCurrentGame().getCurrentPlayer().getGold() - 1000);
        }
        else
            MessageSystem.showError("Can't add ring!",4.0f);
    }

    public void getBouquet(){
        Bouquet bouquet = new Bouquet();
        boolean added = App.getInstance().getCurrentGame().
            getCurrentPlayer().getInventory()
            .getBackPack().addItem(bouquet);
        if(added){
            MessageSystem.showInfo("Bouquet added successfully!",4.0f);
            App.getInstance().getCurrentGame().getCurrentPlayer().setGold(App.getInstance().
                getCurrentGame().getCurrentPlayer().getGold() - 300);
        }
        else
            MessageSystem.showError("Can't add bouquet!",4.0f);
    }

    public void buildGreenhouse(){
        GreenHouse greenHouse = (GreenHouse) getPlaceByType("greenhouse");
        greenHouse.setFixed(true);
        MessageSystem.showInfo("Greenhouse builded successfully!",2.0f);
    }

    public Place getPlaceByType(String placeType) {
        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();

        if (currentPlayer.getFarm() == null) {
            return null;
        }

        Farm playerFarm = currentPlayer.getFarm();
        for (Place place : playerFarm.getPlaces()) {
            if (place.getClass().getSimpleName().equalsIgnoreCase(placeType)) {
                return place;
            }
        }
        return null;
    }


}
