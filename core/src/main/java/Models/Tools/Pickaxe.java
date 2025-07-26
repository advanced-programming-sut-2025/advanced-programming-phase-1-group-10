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

        // Handle Mineral
        if (item instanceof Mineral) {
            boolean added = player.getInventory().getBackPack().addItem(item);
            if (added) {
                ((Mineral) item).setAxed(true);
                tile.setItem(null); // Only remove if added
                isUsed = true;
            }
            // Even if mineral wasn't removed, give XP
            player.setMiningAbility(player.getMiningAbility() + 10);
        }

        // Handle Plowed Tile
        if (tile.getisPlow()) {
            tile.setPlow(false);
            isUsed = true;
        }

        // Optional: If there's an unhandled item on tile, remove it (depends on game logic)
        // This block is dangerous unless you're sure you want to clear all items.
        // if (tile.getItem() != null) {
        //     tile.setItem(null);
        //     isUsed = true;
        // }

        // Energy consumption
        int energyCost = (int) ((getEnergyUsage() - getQuality().getValue()) *
            App.getInstance().getCurrentGame().getWeather().getToolEnergyModifer());

        player.getEnergy().setEnergyAmount(
            player.getEnergy().getEnergyAmount() - (isUsed ? energyCost : energyCost - 1)
        );
    }

}
