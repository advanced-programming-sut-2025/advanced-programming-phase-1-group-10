package Controllers;

import Models.Animal.Animal;
import Models.App;
import Models.Result;

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
}
