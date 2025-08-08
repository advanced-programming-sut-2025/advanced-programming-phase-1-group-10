package Common.Models.Tools;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SeedPlanter extends Tool {

    public SeedPlanter(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Sprite show() {

        return null;
    }

//    @Override
//    public void use(Tile tile) {
//        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
//        Item selectedItem = player.getInventory().getBackPack().getSelectedItem();
//
//        if (selectedItem instanceof Seed) {
//            Seed seed = (Seed) selectedItem;
//            if (tile.isWatered() && tile.getCrop() == null) {
//                int energyCost = (int) ((getEnergyUsage() - getQuality().getValue()) * App.getInstance().getCurrentGame().getWeather().getToolEnergyModifer());
//                if (player.getEnergy().getEnergyAmount() >= energyCost) {
//                    player.getEnergy().setEnergyAmount(player.getEnergy().getEnergyAmount() - energyCost);
//
//                    Crop newCrop = new Crop(seed.getCropType());
//                    tile.setCrop(newCrop);
//
//                    if (seed.getNumber() > 1) {
//                        seed.setNumber(seed.getNumber() - 1);
//                    } else {
//                        player.getInventory().getBackPack().removeItem(seed);
//                    }
//                }
//            }
//        }
//    }
}
