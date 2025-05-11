package Models.Tools;

import Models.App;
import Models.Mineral.Mineral;
import Models.PlayerStuff.Player;
import Models.Tile;

public class Pickaxe extends Tool {
    public Pickaxe(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    @Override
    public String getName() {
        return "Pickaxe";
    }

    @Override
    public void use(Tile tile) {
        //Handle Energy
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        player.getEnergy().setEnergyAmount(player.getEnergy().getEnergyAmount() - (getEnergyUsage() - getQuality().getValue()));
        if(tile.getItem() instanceof Mineral){
            //TODO Mine based on Tool level
            if(player.getMiningAbility() >= 2){
                App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().addItem(tile.getItem());
                App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().addItem(tile.getItem());
            }
            player.setMiningAbility(player.getMiningAbility() + 10);
            tile.setItem(null);
        }
        if(tile.getisPlow()){
            tile.setPlow(false);
        }
        tile.setItem(null);
    }
}
