package Models.Tools;

import Models.App;
import Models.PlayerStuff.Player;
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
        int energyCost = (int)( (getEnergyUsage() - getQuality().getValue()) * App.getInstance().getCurrentGame().getWeather().getToolEnergyModifer());
        if(tile.getPlace() == null && tile.getItem() == null && tile.getFarm() != null && tile.getPerson() == null && tile.getTileType() == null){
            tile.setPlow(true);
        }
        player.getEnergy().setEnergyAmount(
                player.getEnergy().getEnergyAmount() - energyCost
        );
    }
}
