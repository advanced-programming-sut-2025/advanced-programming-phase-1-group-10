package Models.Tools;

import Models.App;
import Models.PlayerStuff.Player;
import Models.Result;
import Models.Tile;

public class Hoe extends Tool {

    public Hoe(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void use(Tile tile) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        player.getEnergy().setEnergyAmount(player.getEnergy().getEnergyAmount() - (getEnergyUsage() - getQuality().getValue()));
        tile.setPlow(true);
    }
}
