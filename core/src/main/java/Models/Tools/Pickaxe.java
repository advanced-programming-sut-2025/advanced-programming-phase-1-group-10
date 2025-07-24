package Models.Tools;

import Models.App;
import Models.Item;
import Models.Mineral.Mineral;
import Models.PlayerStuff.Player;
import Models.Tile;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Pickaxe extends Tool {
    public Pickaxe(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    @Override
    public String getName() {
        return "Pickaxe";
    }

    @Override
    public Sprite show() {
        return toolAsset.show(this);
    }

    @Override
    public void use(Tile tile) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        boolean isUsed = false;

        Item item = tile.getItem();

        if (item instanceof Mineral) {


            if (player.getMiningLevel() >= 2) {
                player.getInventory().getBackPack().addItem(item);
            }

            player.setMiningAbility(player.getMiningAbility() + 10);
            if(player.getInventory().getBackPack().addItem(item)){
                tile.setItem(null);
            }
            isUsed = true;
        }

        if (tile.getisPlow()) {
            tile.setPlow(false);
            isUsed = true;
        }

        if (tile.getItem() != null) {
            tile.setItem(null);
            isUsed = true;
        }

        int energyCost = (int)( (getEnergyUsage() - getQuality().getValue()) * App.getInstance().getCurrentGame().getWeather().getToolEnergyModifer());
        player.getEnergy().setEnergyAmount(
                player.getEnergy().getEnergyAmount() - (isUsed ? energyCost : energyCost - 1)
        );
    }
}
