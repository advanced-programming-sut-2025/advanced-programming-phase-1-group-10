package Controllers;

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
        App.getInstance().getCurrentGame().getCurrentPlayer().setEnergy(energyInt);
        return new Result(true, "Player Energy set to " + energyInt);
    }

    public Result setUnlimitedEnergy() {
        App.getInstance().getCurrentGame().getCurrentPlayer().getEnergy().setEnergyAmount(Integer.MAX_VALUE);
        return new Result(true, "Player Energy set to unlimited energy");
    }



}
