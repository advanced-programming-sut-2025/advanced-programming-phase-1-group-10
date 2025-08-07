package Common.Models.Tools;

import Client.Assets.ToolAsset;
import Client.Controllers.MessageSystem;
import Common.Models.App;
import Common.Models.Planets.Fruit;
import Common.Models.Planets.Tree;
import Common.Models.PlayerStuff.Player;
import Common.Models.Tile;
import Common.Network.Send.Message;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

public class Axe extends Tool {

    public Axe(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    @Override
    public String getName() {
        return "Axe";
    }

    @Override
    public Sprite show() {
        return ToolAsset.show(this);
    }

    @Override
    public Message use(Tile tile) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        boolean isUsed = false;

        if (tile.getItem() instanceof Tree) {
            Tree tree = (Tree) tile.getItem();
            if (tree.hasFruits()) {
                Fruit harvestedFruit = tree.harvestFruit();
                if (harvestedFruit != null) {
                    player.getInventory().getBackPack().addItem(harvestedFruit);
                }
            }

            tree.setChoped(true);
            int woodAmount = new Random().nextInt(5,15);
            Wood wood = new Wood(woodAmount);
            boolean added = player.getInventory().getBackPack().addItem(wood);
            if (added) {
                MessageSystem.showInfo("Got " + woodAmount + " wood from tree!", 4.0f);
            }

            player.setForagingAbility(player.getForagingAbility() + 10);
            tile.setItem(null);
            tile.setTree(null);
            isUsed = true;
        }

        int energyCost = (int) ((getEnergyUsage() - getQuality().getValue()) * App.getInstance().getCurrentGame().getWeather().getToolEnergyModifer());
        player.getEnergy().setEnergyAmount(
            player.getEnergy().getEnergyAmount() - (isUsed ? energyCost : energyCost - 1)
        );
        return null;
    }

}
