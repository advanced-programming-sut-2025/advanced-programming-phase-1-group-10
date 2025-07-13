package Models.Tools;

import Models.App;
import Models.Planets.Fruit;
import Models.Planets.Tree;
import Models.PlayerStuff.Player;
import Models.Tile;

import java.util.concurrent.ThreadLocalRandom;

public class Axe extends Tool {

    public Axe(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    @Override
    public String getName() {
        return "Axe";
    }

    @Override
    public void use(Tile tile) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        boolean isUsed = false;

        if(tile.getItem() instanceof Tree) {
            player.getInventory().getBackPack().addItem(new Fruit(((Tree) tile.getItem()).getTreeType().getFruitType(), ThreadLocalRandom.current().nextInt(1,3)));
            player.setForagingAbility(player.getForagingAbility() + 10);
            tile.setItem(null);
            isUsed = true;
            //TODO ADD WOOD
        }

        int energyCost = (int)( (getEnergyUsage() - getQuality().getValue()) * App.getInstance().getCurrentGame().getWeather().getToolEnergyModifer());
        player.getEnergy().setEnergyAmount(
                player.getEnergy().getEnergyAmount() - (isUsed ? energyCost : energyCost - 1)
        );


    }
}
